package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.enums.AppraisalSearchEnum;
import com.fusionx.lending.product.enums.LeadStatusEnum;
import com.fusionx.lending.product.resources.CustomerRequestResponseResource;
import com.fusionx.lending.product.resources.LeadInfoRequestResponseResource;
import com.fusionx.lending.product.resources.LendingAppraisalAdvanceSearchResponse;
import com.fusionx.lending.product.service.AppraisalService;
import com.fusionx.lending.product.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * API Service related to  Appraisal
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        16-10-2021      -                 -           Rohan                   Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AppraisalServiceImpl implements AppraisalService {

    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    /**
     * Retrieve {@link LendingAppraisalAdvanceSearchResponse} by search
     *
     * @param tenantId            the tenant id
     * @param searchKey           the search key
     * @param appraisalSearchEnum the search criteria
     * @return the list of Lending appraisal advance search response
     */
    @Override
    public List<LendingAppraisalAdvanceSearchResponse> getAllAppraisalBySearch(String tenantId, String searchKey, String appraisalSearchEnum) {
        List<LeadInfoRequestResponseResource> leadInfoRequest = remoteService.getAllAppraisalByLeadsStatus(tenantId, commonModuleProperties.getLendingOrigination(), String.valueOf(LeadStatusEnum.COMPLETED));
        Predicate<LeadInfoRequestResponseResource> filterRequest = null;
        if (searchKey.equals("-1") && appraisalSearchEnum.equals("-1")) {
            filterRequest = ld -> ld.getId() != null;
        } else if (AppraisalSearchEnum.valueOf(appraisalSearchEnum).equals(AppraisalSearchEnum.LEAD_NUMBER)) {
            filterRequest = searchKey.equalsIgnoreCase("-1") ?
                    ld -> ld.getId() != null :
                    ld -> ld.getId().toLowerCase().contains(searchKey.toLowerCase());
        } else if (AppraisalSearchEnum.valueOf(appraisalSearchEnum).equals(AppraisalSearchEnum.CUSTOMER_NAME)) {
            filterRequest = searchKey.equalsIgnoreCase("-1") ?
                    ld -> ld.getId() != null :
                    ld -> ld.getCustomers().get(0).getFullName().toLowerCase().contains(searchKey.toLowerCase());
        } else if (AppraisalSearchEnum.valueOf(appraisalSearchEnum).equals(AppraisalSearchEnum.CUSTOMER_REFERENCE_NUMBER)) {
            filterRequest = searchKey.equalsIgnoreCase("-1") ?
                    ld -> ld.getId() != null :
                    ld -> ld.getCustomers().get(0).getCusReferenceCode().toLowerCase().contains(searchKey.toLowerCase());
        } else if (AppraisalSearchEnum.valueOf(appraisalSearchEnum).equals(AppraisalSearchEnum.BR_NUMBER)) {
            filterRequest = searchKey.equalsIgnoreCase("-1") ?
                    ld -> ld.getId() != null :
                    ld -> ld.getCustomers().get(0).getBrNumber().toLowerCase().contains(searchKey.toLowerCase());
        }
        // product details not found in lead info
        /*else if(!searchKey.equalsIgnoreCase("-1")&& AppraisalSearchEnum.valueOf(appraisalSearchEnum).equals(AppraisalSearchEnum.PRODUCT)){
            filterRequest=ld -> ld.getProduct.toLowerCase().contains(searchKey.toLowerCase());
        }*/
        

        return leadInfoRequest.stream()
                .sorted(Comparator.comparing(LeadInfoRequestResponseResource::getId))
                .filter(filterRequest)
                .map(this::castLendingAppraisalAdvanceSearchResponse)
                .collect(Collectors.toList());
    }

    /**
     * this method responsible for cast {@link LeadInfoRequestResponseResource} into {@link LendingAppraisalAdvanceSearchResponse}
     *
     * @param leadInfoRequestResponseResource the Lead info request response resource
     * @return LendingAppraisalAdvanceSearchResponse
     */
    
    

    public LendingAppraisalAdvanceSearchResponse castLendingAppraisalAdvanceSearchResponse(LeadInfoRequestResponseResource leadInfoRequestResponseResource){
        CustomerRequestResponseResource customerRequest = (leadInfoRequestResponseResource.getCustomers()).get(0);
        return LendingAppraisalAdvanceSearchResponse.builder()
                .id(leadInfoRequestResponseResource.getId())
                .customerCode(customerRequest.getCusReferenceCode())
                .customerName(customerRequest.getFullName())
                .customerId(customerRequest.getCustomerId())
                .customerBrNumber(customerRequest.getBrNumber())
                .leadNumber(leadInfoRequestResponseResource.getId())
                .subProduct("Not Found") // missing data
                .status(leadInfoRequestResponseResource.getStatus())
                .build();
    } 

}
