package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.AddressDetail;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.AddressDetailRepository;
import com.fusionx.lending.origination.resource.AddressDetailsResource;
import com.fusionx.lending.origination.resource.CommonListRemote;
import com.fusionx.lending.origination.resource.ContactDetailsResource;
import com.fusionx.lending.origination.resource.GeoHierarchyResource;
import com.fusionx.lending.origination.resource.GeoLevelResources;
import com.fusionx.lending.origination.service.AddressDetailService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class AddressDetailServiceImpl extends MessagePropertyBase implements AddressDetailService {

	@Autowired
	AddressDetailRepository addressDetailRepository;
	
	@Value("${comn-geo-hierarchy}")
    private String urlGeoHierarchy;
	
	@Value("${comn-common-list}")
    private String urlCommonList;
	
	@Value("${comn-country}")
    private String urlComnCountry;
	@Autowired
	RemoteService remoteService;
	@Autowired
	ValidateService validateService;
	
	private String paddAddressCountryIdNotMatch="paddAddressCountryId.not-match";
	@Override
	public void serAddress(List<AddressDetailsResource> addresses, String tenantId, String userName,
			Customer customer, Guarantor guarantor) {
		for(AddressDetailsResource address:addresses) {
			AddressDetail addressDetail=new AddressDetail();
			List<AddressDetail> addressDetails;
				if(address.getId()!=null  && !address.getId().isEmpty() ) {
					Optional<AddressDetail> opIdentificationDetail = addressDetailRepository.findById(Long.parseLong(address.getId()));
					if(opIdentificationDetail.isPresent()) {
						addressDetail=opIdentificationDetail.get();
						addressDetail.setModifiedUser(userName);
						addressDetail.setModifiedDate(getCreateOrModifyDate());
					}else {
			        	throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "address");

					}
				}
			serviceValidationPersonAddress(address, tenantId);
			addressDetail.setTenantId(tenantId);
			addressDetail.setStatus("ACTIVE");
			if(customer!=null)
			addressDetail.setCustomer(customer);
			
			
			if(guarantor!=null) {
				
				if(address.getId()!=null && !address.getId().isEmpty()) {
					
					addressDetails = addressDetailRepository.findByGuarantorIdAndAddressTypeIdAndIdNotIn(guarantor.getId(), validateService.stringToLong(address.getAddressTypeId()), validateService.stringToLong(address.getId()));
					if(!addressDetails.isEmpty()) {
						throw new ValidateRecordException(environment.getProperty("contactType.duplicate"), "contactType");
					}
				}else {
					addressDetails = addressDetailRepository.findByGuarantorIdAndAddressTypeId(guarantor.getId(), validateService.stringToLong(address.getAddressTypeId()));
					if(!addressDetails.isEmpty()) {
					throw new ValidateRecordException(environment.getProperty("addressType.duplicate"), "addressType");
					}
				}
				
				addressDetail.setGuarantor(guarantor);
			}
			
			if(address.getAddressTypeId()!=null && !address.getAddressTypeId().isEmpty())
			addressDetail.setAddressTypeId(Long.parseLong(address.getAddressTypeId()));
			addressDetail.setAddress1(address.getAddress1());
			addressDetail.setAddress2(address.getAddress2());
			addressDetail.setAddress3(address.getAddress3());
			addressDetail.setAddress4(address.getAddress4());
			
			if(address.getGeoLevelId()!=null && !address.getGeoLevelId().isEmpty())
			addressDetail.setGeoLevelId(Long.parseLong(address.getGeoLevelId()));
			
			if(address.getCountryGeoId()!=null && !address.getCountryGeoId().isEmpty())
			addressDetail.setCountryGeoId(Long.parseLong(address.getCountryGeoId()));
			addressDetail.setPostalCode(address.getPostalCode());;
			
			addressDetail.setCreatedUser(userName);
			addressDetail.setCreatedDate(getCreateOrModifyDate());
			addressDetail.setSyncTs(getCreateOrModifyDate());
			addressDetailRepository.save(addressDetail);
			}
		
	}
	
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	
	private void serviceValidationPersonAddress(AddressDetailsResource perAddress, String tenantId) {
		/*if(perAddress.getPaddId()!=null && !perAddress.getPaddId().isEmpty() &&
				(personId==null || !remoteService.existsPersonAddress(tenantId, personId, remoteService.stringToLong(perAddress.getPaddId())))) {
				throw new InvalidServiceIdException(environment.getProperty(paddAddressCountryIdNotMatch), ServiceEntity.ADDRESS_ID);
		}*/
		

		if(perAddress.getGeoLevelId()!=null && !perAddress.getGeoLevelId().isEmpty()) {
			GeoLevelResources geoLevelResources=(GeoLevelResources)remoteService.checkIsExist(tenantId, perAddress.getGeoLevelId(), urlGeoHierarchy, GeoLevelResources.class);
			if(geoLevelResources==null || geoLevelResources.getServiceStatus()==null) {
				if(geoLevelResources==null || !geoLevelResources.getGeohiName().equalsIgnoreCase(perAddress.getGeoLevelDesc()))
					throw new InvalidServiceIdException(environment.getProperty("id.not-match"), ServiceEntity.GEO_LEVEL_ID);
			}else {
				remoteService.serviceValidationExceptionHadle(geoLevelResources.getServiceStatus(), ServiceEntity.GEO_LEVEL_ID, ServicePoint.ADDRESS);
			}
		}

		if(perAddress.getCountryGeoId()!=null && !perAddress.getCountryGeoId().isEmpty()) {
			GeoHierarchyResource geoHierarchyResource=(GeoHierarchyResource)remoteService.checkIsExist(tenantId, perAddress.getCountryGeoId(), urlComnCountry, GeoHierarchyResource.class);
			if(geoHierarchyResource==null || geoHierarchyResource.getServiceStatus()==null) {
				if(geoHierarchyResource==null || !geoHierarchyResource.getGeohiName().equalsIgnoreCase(perAddress.getCountryGeoDesc()))
					throw new InvalidServiceIdException(environment.getProperty("id.not-match"), ServiceEntity.ADDRESS_COUNTRY);
			}else {
				remoteService.serviceValidationExceptionHadle(geoHierarchyResource.getServiceStatus(), ServiceEntity.ADDRESS_COUNTRY, ServicePoint.ADDRESS);
			}
		}
		if(perAddress.getAddressTypeId()!=null && !perAddress.getAddressTypeId().isEmpty()) {
			CommonListRemote addressTypeCommonList=(CommonListRemote)remoteService.checkIsExist(tenantId, perAddress.getAddressTypeId(), urlCommonList, CommonListRemote.class);
			if(addressTypeCommonList==null || addressTypeCommonList.getServiceStatus()==null) {
				if(addressTypeCommonList==null || !addressTypeCommonList.getCmlsDesc().equalsIgnoreCase(perAddress.getAddressTypeDesc())
						|| !addressTypeCommonList.getCmlsReferenceCode().equalsIgnoreCase(CommonListReferenceCode.ADDRESSTYPE.toString())) {
					throw new InvalidServiceIdException(environment.getProperty("id.not-match"), ServiceEntity.ADDRESS_TYPE);
				}
				if(!addressTypeCommonList.getCmlsStatus().equals("ACTIVE")) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ADDRESS_TYPE);
				}
			}else {
				remoteService.serviceValidationExceptionHadle(addressTypeCommonList.getServiceStatus(), ServiceEntity.ADDRESS_TYPE,  ServicePoint.ADDRESS);
			}
		}
	}
}
