package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.*;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.*;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.MasterDefCountryService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Module Definition - Location Definition
 *
 * @author Nipun Dilhan (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        10-Sep-2021						FXL-775				NipunDilhan (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class MasterDefCountryServiceImpl extends MessagePropertyBase implements MasterDefCountryService {

    protected static final String MASTER_DEFINITION = "masterDefinitionId";
    protected static final String MASTER_DEFINITION_PENDING = "masterDefinitionPendingId";

    private MasterDefinitionRepository masterDefinitionRepository;
    private MasterDefinitionPendingRepository masterDefinitionPendingRepository;
    private MasterDefCountryRepository masterDefCountryRepository;
    private MasterDefProvinceRepository masterDefProvinceRepository;
    private MasterDefStateRepository masterDefStateRepository;
    private MasterDefDistrictRepository masterDefDistrictRepository;
    private MasterDefCountryPendingRepository masterDefCountryPendingRepository;
    private MasterDefProvincePendingRepository masterDefProvincePendingRepository;
    private MasterDefStatePendingRepository masterDefStatePendingRepository;
    private MasterDefDistrictPendingRepository masterDefDistrictPendingRepository;
    private MasterDefCountryHistoryRepository masterDefCountryHistoryRepository;
    private MasterDefProvinceHistoryRepository masterDefProvinceHistoryRepository;
    private MasterDefStateHistoryRepository masterDefStateHistoryRepository;
    private MasterDefDistrictHistoryRepository masterDefDistrictHistoryRepository;
    private ValidationService validationService;
    private LendingWorkflowService lendingWorkflowService;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";
    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
    private static final String DOMAIN = "LendingWF";


    @Autowired
    public void setMasterDefinitionRepository(MasterDefinitionRepository masterDefinitionRepository) {
        this.masterDefinitionRepository = masterDefinitionRepository;
    }

    @Autowired
    public void setMasterDefinitionPendingRepository(MasterDefinitionPendingRepository masterDefinitionPendingRepository) {
        this.masterDefinitionPendingRepository = masterDefinitionPendingRepository;
    }

    @Autowired
    public void setMasterDefCountryRepository(MasterDefCountryRepository masterDefCountryRepository) {
        this.masterDefCountryRepository = masterDefCountryRepository;
    }

    @Autowired
    public void setMasterDefProvinceRepository(MasterDefProvinceRepository masterDefProvinceRepository) {
        this.masterDefProvinceRepository = masterDefProvinceRepository;
    }

    @Autowired
    public void setMasterDefStateRepository(MasterDefStateRepository masterDefStateRepository) {
        this.masterDefStateRepository = masterDefStateRepository;
    }

    @Autowired
    public void setMasterDefDistrictRepository(MasterDefDistrictRepository masterDefDistrictRepository) {
        this.masterDefDistrictRepository = masterDefDistrictRepository;
    }

    @Autowired
    public void setMasterDefCountryPendingRepository(MasterDefCountryPendingRepository masterDefCountryPendingRepository) {
        this.masterDefCountryPendingRepository = masterDefCountryPendingRepository;
    }

    @Autowired
    public void setMasterDefProvincePendingRepository(MasterDefProvincePendingRepository masterDefProvincePendingRepository) {
        this.masterDefProvincePendingRepository = masterDefProvincePendingRepository;
    }

    @Autowired
    public void setMasterDefStatePendingRepository(MasterDefStatePendingRepository masterDefStatePendingRepository) {
        this.masterDefStatePendingRepository = masterDefStatePendingRepository;
    }

    @Autowired
    public void setMasterDefDistrictPendingRepository(MasterDefDistrictPendingRepository masterDefDistrictPendingRepository) {
        this.masterDefDistrictPendingRepository = masterDefDistrictPendingRepository;
    }

    @Autowired
    public void setMasterDefCountryHistoryRepository(MasterDefCountryHistoryRepository masterDefCountryHistoryRepository) {
        this.masterDefCountryHistoryRepository = masterDefCountryHistoryRepository;
    }

    @Autowired
    public void setMasterDefProvinceHistoryRepository(MasterDefProvinceHistoryRepository masterDefProvinceHistoryRepository) {
        this.masterDefProvinceHistoryRepository = masterDefProvinceHistoryRepository;
    }

    @Autowired
    public void setMasterDefStateHistoryRepository(MasterDefStateHistoryRepository masterDefStateHistoryRepository) {
        this.masterDefStateHistoryRepository = masterDefStateHistoryRepository;
    }

    @Autowired
    public void setMasterDefDistrictHistoryRepository(MasterDefDistrictHistoryRepository masterDefDistrictHistoryRepository) {
        this.masterDefDistrictHistoryRepository = masterDefDistrictHistoryRepository;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setLendingWorkflowService(LendingWorkflowService lendingWorkflowService) {
        this.lendingWorkflowService = lendingWorkflowService;
    }

    @Override
    public MasterDefinition create(MasterDefCountryMainAddResource masterDefCountryMainAddResource, Long masterDefinitionId, String user, String tenantId) {

        Optional<MasterDefinition> masterDefinitionOptional = masterDefinitionRepository.findById(masterDefinitionId);
        if (!masterDefinitionOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION);
        }

        List<MasterDefCountryAddResource> countryList = masterDefCountryMainAddResource.getCountry();

        List<Long> countryIdList = masterDefCountryRepository.findCountryIdByMasterDef(masterDefinitionId);
        List<String> provinceIdList = new ArrayList<>();
        List<String> stateIdList = new ArrayList<>();
        List<String> districtIdList = new ArrayList<>();

        StringBuilder message = new StringBuilder();

        if ((countryList != null) && (!countryList.isEmpty())) {
            for (MasterDefCountryAddResource countryResource : countryList) {

                message.append(" country id - " + countryResource.getCountryId() + " , ");

                GeoResponse geoResponse = validationService.validateGeoHierachy(tenantId, countryResource.getCountryId(), countryResource.getCountryName(), null, Constants.COUNTRY_CODE, message.toString());
                if (geoResponse == null) {
                    throw new ValidateRecordException(message.append(" invalid country id ").toString(), MESSAGE);
                }
                List<MasterDefProvinceAddRequest> provinceList = null;
                MasterDefCountry masterDefCountry = null;
                if ((!(countryIdList).contains(validationService.stringToLong(countryResource.getCountryId())))) {

                    countryIdList.add(validationService.stringToLong(countryResource.getCountryId()));
                    masterDefCountry = saveCountry(countryResource, user, masterDefinitionOptional.get(), tenantId);
                    saveCountryHistory(masterDefCountry);
                    provinceList = countryResource.getProvince();

                } else {
                    throw new ValidateRecordException(message.append(" pending record already exists ").toString(), MESSAGE);
                }


                if ((provinceList != null) && (!provinceList.isEmpty())) {
                    for (MasterDefProvinceAddRequest provinceRequest : provinceList) {

                        message.append(" province id - " + provinceRequest.getProvinceId() + " , ");
                        geoResponse = validationService.validateGeoHierachy(tenantId, provinceRequest.getProvinceId(), provinceRequest.getProvinceName(), countryResource.getCountryId(), Constants.PROVINCE_CODE, message.toString());
                        if (geoResponse == null) {
                            throw new ValidateRecordException(message.append(" invalid province id ").toString(), MESSAGE);
                        }
                        MasterDefProvince masterDefProvince = null;
                        List<MasterDefStateAddResource> stateList = null;
                        if ((!(provinceIdList).contains(provinceRequest.getProvinceId().trim()))) {

                            masterDefProvince = saveProvince(provinceRequest, user, masterDefCountry, tenantId);
                            saveProvinceHistory(masterDefProvince);
                            stateList = provinceRequest.getState();
                            provinceIdList.add(provinceRequest.getProvinceId().trim());
                        }


                        if ((stateList != null) && (!stateList.isEmpty())) {
                            for (MasterDefStateAddResource stateRequest : stateList) {

                                message.append(" state id - " + stateRequest.getStateId() + " , ");
                                geoResponse = validationService.validateGeoHierachy(tenantId, stateRequest.getStateId(), stateRequest.getStateName(), provinceRequest.getProvinceId(), Constants.PROVINCE_CODE, message.toString());
                                if (geoResponse == null) {
                                    throw new ValidateRecordException(message.append(" invalid state id ").toString(), MESSAGE);
                                }
                                MasterDefState masterDefState = null;
                                List<MasterDefDistrictAddResource> districtList = null;
                                if ((!(stateIdList).contains(stateRequest.getStateId().trim()))) {

                                    masterDefState = saveState(stateRequest, user, masterDefProvince, tenantId);
                                    saveStateHistory(masterDefState);
                                    districtList = stateRequest.getDistrict();
                                    stateIdList.add(stateRequest.getStateId().trim());
                                }


                                if ((districtList != null) && (!districtList.isEmpty())) {
                                    for (MasterDefDistrictAddResource districtRequest : districtList) {

                                        message.append(" district id - " + districtRequest.getDistrictId() + " , ");
                                        geoResponse = validationService.validateGeoHierachy(tenantId, districtRequest.getDistrictId(), districtRequest.getDistrictName(), stateRequest.getStateId(), Constants.PROVINCE_CODE, message.toString());
                                        if (geoResponse == null) {
                                            throw new ValidateRecordException(message.append(" invalid district id ").toString(), MESSAGE);
                                        }
                                        if ((!(districtIdList).contains(districtRequest.getDistrictId().trim()))) {

                                            MasterDefDistrict masterDefDistrict = saveDistrict(districtRequest, user, masterDefState, tenantId);
                                            saveDistrictHistory(masterDefDistrict);
                                        }
                                    }
                                }


                            }
                        }


                    }
                }


            }
        }

        return masterDefinitionOptional.get();

    }


    @Override
    public MasterDefLocationDetailsReponse findByMasterDefinitionId(Long masterDefinitionId) {

        Optional<MasterDefinition> masterDefinitionOptional = masterDefinitionRepository.findById(masterDefinitionId);
        if (!masterDefinitionOptional.isPresent()) {
            return null;
        }

        List<MasterDefCountry> countryList = masterDefCountryRepository.findAllByMasterDefinition(masterDefinitionOptional.get());

        if ((countryList != null) && (!countryList.isEmpty())) {
            for (MasterDefCountry country : countryList) {
                List<MasterDefProvince> provinceList = masterDefProvinceRepository.findAllByMasterDefCountry(country);
                country.setProvince(provinceList);

                if ((provinceList != null) && (!provinceList.isEmpty())) {
                    for (MasterDefProvince province : provinceList) {
                        List<MasterDefState> stateList = masterDefStateRepository.findAllByMasterDefProvince(province);
                        province.setState(stateList);

                        if ((stateList != null) && (!stateList.isEmpty())) {
                            for (MasterDefState state : stateList) {
                                List<MasterDefDistrict> districtList = masterDefDistrictRepository.findAllByMasterDefState(state);
                                state.setDistrict(districtList);
                            }
                        }

                    }
                }

            }
        }

        MasterDefLocationDetailsReponse response = new MasterDefLocationDetailsReponse();
        response.setCountry(countryList);
        return response;
    }


    @Override
    public MasterDefinitionPending updateMasterDefinitionCountry(String tenantId, String user, Long id, MasterDefCountryMainUpdateResource masterDefCountryMainUpdateResource) {

        Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(id);
        if (!isPresentMasterDefinition.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION);
        }


        MasterDefinition masterDefinition = isPresentMasterDefinition.get();
        MasterDefinitionPending masterDefinitionPending = null;

        if (masterDefCountryMainUpdateResource.getMasterDefinitionPendingId() != null) {
            Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(validationService.stringToLong(masterDefCountryMainUpdateResource.getMasterDefinitionPendingId()));
            if (!masterDefinitionPendingOptional.isPresent()) {
                throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION_PENDING);
            }
            masterDefinitionPending = masterDefinitionPendingOptional.get();

            List<MasterDefCountryPending> countryPendingList = masterDefCountryPendingRepository.findAllByMasterDefinitionPending(masterDefinitionPending);
            if (!countryPendingList.isEmpty()) {
                throw new ValidateRecordException("already pending records exists ,approve or reject them first ", MESSAGE);
            }

        } else {
            List<MasterDefinitionPending> activePendingList = masterDefinitionPendingRepository.findAllByMasterDefinitionAndStatus(masterDefinition, CommonStatus.ACTIVE);
            if (!activePendingList.isEmpty()) {
                throw new ValidateRecordException("already pending records exists for this master definition , cant create new  ", MESSAGE);
            }

        }

        masterDefinitionPending = approveOrGenerateWorkFlow(tenantId, user, masterDefinition, masterDefinitionPending, masterDefCountryMainUpdateResource);

        return masterDefinitionPending;
    }


    public MasterDefinitionPending approveOrGenerateWorkFlow(String tenantId, String user, MasterDefinition md, MasterDefinitionPending mdp, MasterDefCountryMainUpdateResource masterDefCountryMainUpdateResource) {

        if (mdp != null) {
            saveToPendingTables(md, mdp, tenantId, user, masterDefCountryMainUpdateResource);
        } else {

            WorkflowRulesResource workflowRulesResource = null;
            Long processId = null;
            WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
            LendingWorkflow lendingWorkflow = null;

            WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
            workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
            workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

            workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

            if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
                processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
                if (processId != null) {
                    lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                    mdp = saveDummyMasterDefinitionPending(md, lendingWorkflow);
                    saveToPendingTables(md, mdp, tenantId, user, masterDefCountryMainUpdateResource);
                } else {
                    mdp = saveDummyMasterDefinitionPending(md, lendingWorkflow);
                    saveToPendingTables(md, mdp, tenantId, user, masterDefCountryMainUpdateResource);

                    updateMasterDefCountryUsingPending(mdp.getId());
                }
            }

        }

        return mdp;
    }


    @Override
    public MasterDefLocationDetailsPendingReponse findByMasterDefinitionPendingId(Long masterDefinitionPendingId) {

        Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(masterDefinitionPendingId);
        if (!masterDefinitionPendingOptional.isPresent()) {
            return null;
        }

        List<MasterDefCountryPending> countryList = masterDefCountryPendingRepository.findAllByMasterDefinitionPending(masterDefinitionPendingOptional.get());

        if ((countryList != null) && (!countryList.isEmpty())) {
            for (MasterDefCountryPending country : countryList) {
                List<MasterDefProvincePending> provinceList = masterDefProvincePendingRepository.findAllByMasterDefCountryPending(country);
                country.setProvince(provinceList);

                if ((provinceList != null) && (!provinceList.isEmpty())) {
                    for (MasterDefProvincePending province : provinceList) {
                        List<MasterDefStatePending> stateList = masterDefStatePendingRepository.findAllByMasterDefProvincePending(province);
                        province.setState(stateList);

                        if ((stateList != null) && (!stateList.isEmpty())) {
                            for (MasterDefStatePending state : stateList) {
                                List<MasterDefDistrictPending> districtList = masterDefDistrictPendingRepository.findAllByMasterDefStatePending(state);
                                state.setDistrict(districtList);
                            }
                        }

                    }
                }

            }
        }

        MasterDefLocationDetailsPendingReponse response = new MasterDefLocationDetailsPendingReponse();
        response.setCountryList(countryList);
        return response;
    }


    public MasterDefCountry saveCountry(MasterDefCountryAddResource countryResource, String user, MasterDefinition md, String tenantId) {

        MasterDefCountry masterDefCountry = new MasterDefCountry();

        masterDefCountry.setTenantId(tenantId);
        masterDefCountry.setMasterDefinition(md);
        masterDefCountry.setCountryId(validationService.stringToLong(countryResource.getCountryId()));
        masterDefCountry.setCountryName(countryResource.getCountryName());
        masterDefCountry.setCreatedDate(validationService.getCreateOrModifyDate());
        masterDefCountry.setCreatedUser(user);
        masterDefCountry.setStatus(CommonStatus.valueOf(countryResource.getStatus()));
        masterDefCountry.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefCountry.setModifiedUser(user);
        masterDefCountry.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefCountryRepository.save(masterDefCountry);
    }

    public MasterDefProvince saveProvince(MasterDefProvinceAddRequest provinceRequest, String user, MasterDefCountry mdc, String tenantId) {

        MasterDefProvince masterDefProvince = new MasterDefProvince();

        masterDefProvince.setTenantId(tenantId);
        masterDefProvince.setMasterDefCountry(mdc);
        masterDefProvince.setProvinceId(validationService.stringToLong(provinceRequest.getProvinceId()));
        masterDefProvince.setProvinceName(provinceRequest.getProvinceName());
        masterDefProvince.setCreatedDate(validationService.getCreateOrModifyDate());
        masterDefProvince.setCreatedUser(user);
        masterDefProvince.setStatus(CommonStatus.valueOf(provinceRequest.getStatus()));
        masterDefProvince.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefProvince.setModifiedUser(user);
        masterDefProvince.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefProvinceRepository.save(masterDefProvince);
    }

    public MasterDefState saveState(MasterDefStateAddResource stateRequest, String user, MasterDefProvince mdp, String tenantId) {

        MasterDefState masterDefState = new MasterDefState();

        masterDefState.setTenantId(tenantId);
        masterDefState.setMasterDefProvince(mdp);
        masterDefState.setStateId(validationService.stringToLong(stateRequest.getStateId()));
        masterDefState.setStateName(stateRequest.getStateName());
        masterDefState.setCreatedDate(validationService.getCreateOrModifyDate());
        masterDefState.setCreatedUser(user);
        masterDefState.setStatus(CommonStatus.valueOf(stateRequest.getStatus()));
        masterDefState.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefState.setModifiedUser(user);
        masterDefState.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefStateRepository.save(masterDefState);
    }

    public MasterDefDistrict saveDistrict(MasterDefDistrictAddResource districtRequest, String user, MasterDefState mds, String tenantId) {

        MasterDefDistrict masterDefDistrict = new MasterDefDistrict();

        masterDefDistrict.setTenantId(tenantId);
        masterDefDistrict.setMasterDefState(mds);
        masterDefDistrict.setDistrictId(validationService.stringToLong(districtRequest.getDistrictId()));
        masterDefDistrict.setDistrictName(districtRequest.getDistrictName());
        masterDefDistrict.setCreatedDate(validationService.getCreateOrModifyDate());
        masterDefDistrict.setCreatedUser(user);
        masterDefDistrict.setStatus(CommonStatus.valueOf(districtRequest.getStatus()));
        masterDefDistrict.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefDistrict.setModifiedUser(user);
        masterDefDistrict.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefDistrictRepository.save(masterDefDistrict);
    }


    public void saveCountryHistory(MasterDefCountry masterDefCountry) {
        MasterDefCountryHistory masterDefCountryHistory = new MasterDefCountryHistory();

        masterDefCountryHistory.setTenantId(masterDefCountry.getTenantId());
        masterDefCountryHistory.setMasterDefinitionId(masterDefCountry.getMasterDefinition().getId());
        masterDefCountryHistory.setMasterDefCountryId(masterDefCountry.getId());
        masterDefCountryHistory.setCountryId(masterDefCountry.getId());
        masterDefCountryHistory.setCountryName(masterDefCountry.getCountryName());
        masterDefCountryHistory.setCreatedDate(masterDefCountry.getCreatedDate());
        masterDefCountryHistory.setCreatedUser(masterDefCountry.getCreatedUser());

        masterDefCountryHistory.setStatus(masterDefCountry.getStatus());
        masterDefCountryHistory.setModifiedDate(masterDefCountry.getModifiedDate());
        masterDefCountryHistory.setModifiedUser(masterDefCountry.getModifiedUser());
        masterDefCountryHistory.setSyncTs(masterDefCountry.getSyncTs());
        masterDefCountryHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        masterDefCountryHistory.setHistoryCreatedUser(masterDefCountry.getModifiedUser());
        masterDefCountryHistoryRepository.save(masterDefCountryHistory);
    }


    public void saveProvinceHistory(MasterDefProvince masterDefProvince) {
        MasterDefProvinceHistory masterDefProvinceHistory = new MasterDefProvinceHistory();

        masterDefProvinceHistory.setTenantId(masterDefProvince.getTenantId());
        masterDefProvinceHistory.setMasterDefCountryId(masterDefProvince.getMasterDefCountry().getId());
        masterDefProvinceHistory.setMasterDefProvinceId(masterDefProvince.getId());
        masterDefProvinceHistory.setProvinceId(masterDefProvince.getProvinceId());
        masterDefProvinceHistory.setProvinceName(masterDefProvince.getProvinceName());
        masterDefProvinceHistory.setCreatedDate(masterDefProvince.getCreatedDate());
        masterDefProvinceHistory.setCreatedUser(masterDefProvince.getCreatedUser());

        masterDefProvinceHistory.setStatus(masterDefProvince.getStatus());
        masterDefProvinceHistory.setModifiedDate(masterDefProvince.getModifiedDate());
        masterDefProvinceHistory.setModifiedUser(masterDefProvince.getModifiedUser());
        masterDefProvinceHistory.setSyncTs(masterDefProvince.getSyncTs());
        masterDefProvinceHistory.setHistoryCreatedDate(masterDefProvince.getModifiedDate());
        masterDefProvinceHistory.setHistoryCreatedUser(masterDefProvince.getModifiedUser());

        masterDefProvinceHistoryRepository.save(masterDefProvinceHistory);
    }

    public void saveStateHistory(MasterDefState masterDefState) {

        MasterDefStateHistory masterDefStateHistory = new MasterDefStateHistory();
        masterDefStateHistory.setTenantId(masterDefState.getTenantId());
        masterDefStateHistory.setMasterDefProvinceId(masterDefState.getMasterDefProvince().getId());
        masterDefStateHistory.setMasterDefStateId(masterDefState.getId());
        masterDefStateHistory.setStateId(masterDefState.getStateId());
        masterDefStateHistory.setStateName(masterDefState.getStateName());
        masterDefStateHistory.setCreatedDate(masterDefState.getCreatedDate());
        masterDefStateHistory.setCreatedUser(masterDefState.getCreatedUser());

        masterDefStateHistory.setStatus(masterDefState.getStatus());
        masterDefStateHistory.setModifiedDate(masterDefState.getModifiedDate());
        masterDefStateHistory.setModifiedUser(masterDefState.getModifiedUser());
        masterDefStateHistory.setSyncTs(masterDefState.getSyncTs());
        masterDefStateHistory.setHistoryCreatedDate(masterDefState.getModifiedDate());
        masterDefStateHistory.setHistoryCreatedUser(masterDefState.getModifiedUser());
        masterDefStateHistoryRepository.save(masterDefStateHistory);

    }


    public void saveDistrictHistory(MasterDefDistrict masterDefDistrict) {

        MasterDefDistrictHistory masterDefDistrictHistory = new MasterDefDistrictHistory();
        masterDefDistrictHistory.setTenantId(masterDefDistrict.getTenantId());
        masterDefDistrictHistory.setMasterDefStateId(masterDefDistrict.getMasterDefState().getId());
        masterDefDistrictHistory.setMasterDefDistrictId(masterDefDistrict.getId());
        masterDefDistrictHistory.setDistrictId(masterDefDistrict.getId());
        masterDefDistrictHistory.setDistrictName(masterDefDistrict.getDistrictName());
        masterDefDistrictHistory.setCreatedDate(masterDefDistrict.getCreatedDate());
        masterDefDistrictHistory.setCreatedUser(masterDefDistrict.getCreatedUser());

        masterDefDistrictHistory.setStatus(masterDefDistrict.getStatus());
        masterDefDistrictHistory.setModifiedDate(masterDefDistrict.getModifiedDate());
        masterDefDistrictHistory.setModifiedUser(masterDefDistrict.getModifiedUser());
        masterDefDistrictHistory.setSyncTs(masterDefDistrict.getSyncTs());
        masterDefDistrictHistory.setHistoryCreatedDate(masterDefDistrict.getModifiedDate());
        masterDefDistrictHistory.setHistoryCreatedUser(masterDefDistrict.getModifiedUser());
        masterDefDistrictHistoryRepository.save(masterDefDistrictHistory);
    }


    public MasterDefinitionPending saveDummyMasterDefinitionPending(MasterDefinition md, LendingWorkflow lendingWorkflow) {
        MasterDefinitionPending masterDefinitionPending = new MasterDefinitionPending();

		masterDefinitionPending.setTenantId(md.getTenantId());
		if (lendingWorkflow != null)
			masterDefinitionPending.setLendingWorkflow(lendingWorkflow);
		masterDefinitionPending.setMasterDefinition(md);
		masterDefinitionPending.setCode(md.getCode());
		masterDefinitionPending.setName(md.getName());
		masterDefinitionPending.setModule(md.getModule());
		masterDefinitionPending.setBusinessPrinciple(md.getBusinessPrinciple());
		masterDefinitionPending.setDescription(md.getDescription());
		masterDefinitionPending.setSubModuleCode(md.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(md.getSubModuleName());
		masterDefinitionPending.setVersion(md.getVersion());
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.MASTER_DEFINITION);
		masterDefinitionPending.setStatus(md.getStatus());
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(md.getCreatedUser());
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefinitionPendingRepository.save(masterDefinitionPending);
    }


    public void saveToPendingTables(MasterDefinition masterDefinition, MasterDefinitionPending masterDefinitionPending, String tenantId, String user, MasterDefCountryMainUpdateResource masterDefCountryMainUpdateResource) {

        List<MasterDefCountryUpdateResource> countryList = masterDefCountryMainUpdateResource.getCountry();

        List<Long> countryIdList = masterDefCountryRepository.findCountryIdByMasterDef(masterDefinition.getId());

        List<Long> provinceIdList = new ArrayList<>();
        List<Long> stateIdList = new ArrayList<>();
        List<Long> districtIdList = new ArrayList<>();

        if ((countryList != null) && (!countryList.isEmpty())) {
            for (MasterDefCountryUpdateResource countryResource : countryList) {
                MasterDefCountry masterDefCountry = null;

                GeoResponse geoResponse = validationService.validateGeoHierachy(tenantId, countryResource.getCountryId(), countryResource.getCountryName(), null, Constants.COUNTRY_CODE, null);
                if (geoResponse == null) {
                    throw new ValidateRecordException("invalid country id", "message");
                }

                if (countryResource.getId() != null) {
                    Optional<MasterDefCountry> masterDefCountryOptional = masterDefCountryRepository.findById(validationService.stringToLong(countryResource.getId()));
                    if (!masterDefCountryOptional.isPresent()) {

                    }
                    masterDefCountry = masterDefCountryOptional.get();
                    if (!(masterDefCountry.getCountryId()).equals(validationService.stringToLong(countryResource.getCountryId()))) {
                        throw new ValidateRecordException("incompatible country id", "message");
                    }
                    countryResource.setCountryName(geoResponse.getGeohiName());
                    provinceIdList = masterDefProvinceRepository.findProvinceIdByMasterDefCountry(masterDefCountry.getId());
                } else {
                    if (((countryIdList).contains(validationService.stringToLong(countryResource.getCountryId())))) {
                        throw new ValidateRecordException("country already added to this master definition ", "message");
                    }
                    countryIdList.add(validationService.stringToLong(countryResource.getCountryId()));
                }


                MasterDefCountryPending countryPending = saveCountryPending(countryResource, masterDefCountry, user, masterDefinitionPending, tenantId);


                List<MasterDefProvinceUpdateRequest> provinceList = countryResource.getProvince();
                if ((provinceList != null) && (!provinceList.isEmpty())) {
                    for (MasterDefProvinceUpdateRequest provinceRequest : provinceList) {


                        MasterDefProvince masterDefProvince = null;

                        geoResponse = validationService.validateGeoHierachy(tenantId, provinceRequest.getProvinceId(), provinceRequest.getProvinceName(), countryResource.getCountryId(), Constants.PROVINCE_CODE, null);
                        if (geoResponse == null) {
                            throw new ValidateRecordException("invalid province id", "message");
                        }

                        if (provinceRequest.getId() != null) {
                            Optional<MasterDefProvince> masterDefProvinceOptional = masterDefProvinceRepository.findById(validationService.stringToLong(provinceRequest.getId()));
                            if (!masterDefProvinceOptional.isPresent()) {
                                throw new ValidateRecordException("invalid master def province id", "message");
                            }
                            masterDefProvince = masterDefProvinceOptional.get();

                            if (!(masterDefProvince.getProvinceId()).equals(validationService.stringToLong(provinceRequest.getProvinceId()))) {
                                throw new ValidateRecordException("incompatible province id", "message");
                            }
                            provinceRequest.setProvinceName(geoResponse.getGeohiName());
                            stateIdList = masterDefStateRepository.findStateIdByMasterDefProvince(masterDefProvince.getProvinceId());

                        } else {
                            if (((provinceIdList).contains(validationService.stringToLong(provinceRequest.getProvinceId())))) {
                                throw new ValidateRecordException("province already added to this master definition country ", "message");
                            }
                            provinceIdList.add(validationService.stringToLong(provinceRequest.getProvinceId()));
                        }

                        MasterDefProvincePending provincePending = saveProvincePending(provinceRequest, masterDefProvince, user, countryPending, tenantId);

                        List<MasterDefStateUpdateResource> stateList = provinceRequest.getState();
                        if ((stateList != null) && (!stateList.isEmpty())) {
                            for (MasterDefStateUpdateResource stateRequest : stateList) {
                                MasterDefState masterDefState = null;

                                geoResponse = validationService.validateGeoHierachy(tenantId, stateRequest.getStateId(), stateRequest.getStateName(), provinceRequest.getProvinceId(), Constants.STATE_CODE, null);
                                if (geoResponse == null) {
                                    throw new ValidateRecordException("invalid state id", "message");
                                }

                                if (stateRequest.getId() != null) {
                                    Optional<MasterDefState> masterDefStateOptional = masterDefStateRepository.findById(validationService.stringToLong(stateRequest.getId()));
                                    if (!masterDefStateOptional.isPresent()) {
                                        throw new ValidateRecordException("invalid master def state id", "message");
                                    }
                                    masterDefState = masterDefStateOptional.get();

                                    if (!(masterDefState.getStateId()).equals(validationService.stringToLong(stateRequest.getStateId()))) {
                                        throw new ValidateRecordException("incompatible state id", "message");
                                    }
                                    stateRequest.setStateName(geoResponse.getGeohiName());
                                    districtIdList = masterDefDistrictRepository.findDistrictIdByMasterDefState(masterDefState.getStateId());

                                } else {
                                    if (((stateIdList).contains(validationService.stringToLong(stateRequest.getStateId())))) {
                                        throw new ValidateRecordException("state already added to this master definition province ", "message");
                                    }
                                    stateIdList.add(validationService.stringToLong(stateRequest.getStateId()));
                                }

                                MasterDefStatePending statePending = saveStatePending(stateRequest, masterDefState, user, provincePending, tenantId);

                                List<MasterDefDistrictUpdateResource> districtList = stateRequest.getDistrict();
                                if ((districtList != null) && (!districtList.isEmpty())) {
                                    for (MasterDefDistrictUpdateResource districtRequest : districtList) {
                                        MasterDefDistrict masterDefDistrict = null;

                                        geoResponse = validationService.validateGeoHierachy(tenantId, districtRequest.getDistrictId(), districtRequest.getDistrictName(), stateRequest.getStateId(), Constants.DISTRICT_CODE, null);
                                        if (geoResponse == null) {
                                            throw new ValidateRecordException("invalid district id", "message");
                                        }

                                        if (districtRequest.getId() != null) {
                                            Optional<MasterDefDistrict> masterDefDistrictOptional = masterDefDistrictRepository.findById(validationService.stringToLong(districtRequest.getId()));
                                            if (!masterDefDistrictOptional.isPresent()) {
                                                throw new ValidateRecordException("invalid master def district id", "message");
                                            }
                                            masterDefDistrict = masterDefDistrictOptional.get();

                                            if (!(masterDefDistrict.getDistrictId()).equals(validationService.stringToLong(districtRequest.getDistrictId()))) {
                                                throw new ValidateRecordException("incompatible district id", "message");
                                            }
                                            districtRequest.setDistrictName(geoResponse.getGeohiName());

                                        } else {
                                            if (((districtIdList).contains(validationService.stringToLong(districtRequest.getDistrictId())))) {
                                                throw new ValidateRecordException("district already added to this master definition state", "message");
                                            }
                                            districtIdList.add(validationService.stringToLong(districtRequest.getDistrictId()));
                                        }

                                        saveDistrictPending(districtRequest, masterDefDistrict, user, statePending, tenantId);
                                    }


                                }
                            }
                        }
                    }


                }

            }
        }
    }


    public MasterDefCountryPending saveCountryPending(MasterDefCountryUpdateResource countryResource, MasterDefCountry masterDefCountry, String user, MasterDefinitionPending mdp, String tenantId) {


        MasterDefCountryPending masterDefCountryPending = new MasterDefCountryPending();

        masterDefCountryPending.setTenantId(tenantId);
        masterDefCountryPending.setMasterDefinitionPending(mdp);
        masterDefCountryPending.setMasterDefCountry(masterDefCountry);
        masterDefCountryPending.setCountryId(validationService.stringToLong(countryResource.getCountryId()));
        masterDefCountryPending.setCountryName(countryResource.getCountryName());
        masterDefCountryPending.setStatus(CommonStatus.valueOf(countryResource.getStatus()));
        masterDefCountryPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
        masterDefCountryPending.setPenCreatedUser(user);
        masterDefCountryPending.setSyncTs(validationService.getCreateOrModifyDate());

        masterDefCountryPending = masterDefCountryPendingRepository.save(masterDefCountryPending);
        return masterDefCountryPending;
    }


    public MasterDefProvincePending saveProvincePending(MasterDefProvinceUpdateRequest provinceRequest, MasterDefProvince masterDefProvince, String user, MasterDefCountryPending mdcp, String tenantId) {

        MasterDefProvincePending masterDefProvincePending = new MasterDefProvincePending();

        masterDefProvincePending.setTenantId(tenantId);
        masterDefProvincePending.setMasterDefCountryPending(mdcp);
        masterDefProvincePending.setMasterDefProvince(masterDefProvince);
        masterDefProvincePending.setProvinceId(validationService.stringToLong(provinceRequest.getProvinceId()));
        masterDefProvincePending.setProvinceName(provinceRequest.getProvinceName());
        masterDefProvincePending.setPenCreatedDate(validationService.getCreateOrModifyDate());
        masterDefProvincePending.setPenCreatedUser(user);
        masterDefProvincePending.setStatus(CommonStatus.valueOf(provinceRequest.getStatus()));
        masterDefProvincePending.setSyncTs(validationService.getCreateOrModifyDate());
        masterDefProvincePending = masterDefProvincePendingRepository.save(masterDefProvincePending);
        return masterDefProvincePending;
    }


    public MasterDefStatePending saveStatePending(MasterDefStateUpdateResource stateRequest, MasterDefState masterDefState, String user, MasterDefProvincePending mdpp, String tenantId) {

        MasterDefStatePending masterDefStatePending = new MasterDefStatePending();

        masterDefStatePending.setTenantId(tenantId);
        masterDefStatePending.setMasterDefProvincePending(mdpp);
        masterDefStatePending.setMasterDefState(masterDefState);
        masterDefStatePending.setStateId(validationService.stringToLong(stateRequest.getStateId()));
        masterDefStatePending.setStateName(stateRequest.getStateName());
        masterDefStatePending.setPenCreatedDate(validationService.getCreateOrModifyDate());
        masterDefStatePending.setPenCreatedUser(user);
        masterDefStatePending.setStatus(CommonStatus.valueOf(stateRequest.getStatus()));
        masterDefStatePending.setSyncTs(validationService.getCreateOrModifyDate());
        masterDefStatePending = masterDefStatePendingRepository.save(masterDefStatePending);
        return masterDefStatePending;
    }


    public MasterDefDistrictPending saveDistrictPending(MasterDefDistrictUpdateResource districtRequest, MasterDefDistrict masterDefDistrict, String user, MasterDefStatePending mdsp, String tenantId) {

        MasterDefDistrictPending masterDefDistrictPending = new MasterDefDistrictPending();

        masterDefDistrictPending.setTenantId(tenantId);
        masterDefDistrictPending.setMasterDefStatePending(mdsp);
        masterDefDistrictPending.setMasterDefDistrict(masterDefDistrict);
        masterDefDistrictPending.setDistrictId(validationService.stringToLong(districtRequest.getDistrictId()));
        masterDefDistrictPending.setDistrictName(districtRequest.getDistrictName());
        masterDefDistrictPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
        masterDefDistrictPending.setPenCreatedUser(user);
        masterDefDistrictPending.setStatus(CommonStatus.valueOf(districtRequest.getStatus()));
        masterDefDistrictPending.setSyncTs(validationService.getCreateOrModifyDate());
        masterDefDistrictPending = masterDefDistrictPendingRepository.save(masterDefDistrictPending);
        return masterDefDistrictPending;
    }


    @Override
    public MasterDefinitionPending updateMasterDefCountryUsingPending(Long masterDefinitionPendingId) {

        Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(masterDefinitionPendingId);
        if (!masterDefinitionPendingOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION_PENDING);
        }

        List<MasterDefCountryPending> countryPendingList = masterDefCountryPendingRepository.findAllByMasterDefinitionPending(masterDefinitionPendingOptional.get());
        if ((countryPendingList != null) && (!countryPendingList.isEmpty())) {
            for (MasterDefCountryPending countryPending : countryPendingList) {
                MasterDefCountry masterDefCountry = updateCountryUsingPending(countryPending);
                saveCountryHistory(masterDefCountry);

                List<MasterDefProvincePending> provincePendingList = masterDefProvincePendingRepository.findAllByMasterDefCountryPending(countryPending);
                if ((provincePendingList != null) && (!provincePendingList.isEmpty())) {
                    for (MasterDefProvincePending provincePending : provincePendingList) {
                        MasterDefProvince masterDefProvince = updateProvinceUsingPending(provincePending);
                        saveProvinceHistory(masterDefProvince);

                        List<MasterDefStatePending> statePendingList = masterDefStatePendingRepository.findAllByMasterDefProvincePending(provincePending);
                        if ((statePendingList != null) && (!statePendingList.isEmpty())) {
                            for (MasterDefStatePending statePending : statePendingList) {
                                MasterDefState masterDefState = updateStateUsingPending(statePending);
                                saveStateHistory(masterDefState);

                                List<MasterDefDistrictPending> districtPendingList = masterDefDistrictPendingRepository.findAllByMasterDefStatePending(statePending);
                                if ((districtPendingList != null) && (!districtPendingList.isEmpty())) {
                                    for (MasterDefDistrictPending districtPending : districtPendingList) {
                                        MasterDefDistrict masterDefDistrict = updateDistrictUsingPending(districtPending);
                                        saveDistrictHistory(masterDefDistrict);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return masterDefinitionPendingOptional.get();

    }

    public MasterDefCountry updateCountryUsingPending(MasterDefCountryPending mdcp) {

        MasterDefCountry masterDefCountry = null;
        if (mdcp.getMasterDefCountry() != null) {
            masterDefCountry = mdcp.getMasterDefCountry();
        } else {
            masterDefCountry = new MasterDefCountry();

            masterDefCountry.setTenantId(mdcp.getTenantId());
            masterDefCountry.setCreatedDate(validationService.getCreateOrModifyDate());
            masterDefCountry.setCreatedUser(mdcp.getPenCreatedUser());
            masterDefCountry.setMasterDefinition(mdcp.getMasterDefinitionPending().getMasterDefinition());
        }


        masterDefCountry.setCountryId(mdcp.getCountryId());
        masterDefCountry.setCountryName(mdcp.getCountryName());
        masterDefCountry.setStatus(mdcp.getStatus());

        masterDefCountry.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefCountry.setModifiedUser(mdcp.getPenCreatedUser());
        masterDefCountry.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefCountryRepository.save(masterDefCountry);

    }

    public MasterDefProvince updateProvinceUsingPending(MasterDefProvincePending mdpp) {

        MasterDefProvince masterDefProvince = null;
        if (mdpp.getMasterDefProvince() != null) {
            masterDefProvince = mdpp.getMasterDefProvince();
        } else {
            masterDefProvince = new MasterDefProvince();

            masterDefProvince.setTenantId(mdpp.getTenantId());
            masterDefProvince.setCreatedDate(validationService.getCreateOrModifyDate());
            masterDefProvince.setCreatedUser(mdpp.getPenCreatedUser());
            masterDefProvince.setMasterDefCountry(mdpp.getMasterDefCountryPending().getMasterDefCountry());
        }


        masterDefProvince.setProvinceId(mdpp.getProvinceId());
        masterDefProvince.setProvinceName(mdpp.getProvinceName());
        masterDefProvince.setStatus(mdpp.getStatus());

        masterDefProvince.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefProvince.setModifiedUser(mdpp.getPenCreatedUser());
        masterDefProvince.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefProvinceRepository.save(masterDefProvince);

    }

    public MasterDefState updateStateUsingPending(MasterDefStatePending mdsp) {

        MasterDefState masterDefState = null;
        if (mdsp.getMasterDefState() != null) {
            masterDefState = mdsp.getMasterDefState();
        } else {
            masterDefState = new MasterDefState();

            masterDefState.setTenantId(mdsp.getTenantId());
            masterDefState.setCreatedDate(validationService.getCreateOrModifyDate());
            masterDefState.setCreatedUser(mdsp.getPenCreatedUser());
            masterDefState.setMasterDefProvince(mdsp.getMasterDefProvincePending().getMasterDefProvince());
        }


        masterDefState.setStateId(mdsp.getStateId());
        masterDefState.setStateName(mdsp.getStateName());
        masterDefState.setStatus(mdsp.getStatus());

        masterDefState.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefState.setModifiedUser(mdsp.getPenCreatedUser());
        masterDefState.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefStateRepository.save(masterDefState);

    }


    public MasterDefDistrict updateDistrictUsingPending(MasterDefDistrictPending mddp) {

        MasterDefDistrict masterDefDistrict = null;
        if (mddp.getMasterDefDistrict() != null) {
            masterDefDistrict = mddp.getMasterDefDistrict();
        } else {
            masterDefDistrict = new MasterDefDistrict();

            masterDefDistrict.setTenantId(mddp.getTenantId());
            masterDefDistrict.setCreatedDate(validationService.getCreateOrModifyDate());
            masterDefDistrict.setCreatedUser(mddp.getPenCreatedUser());
            masterDefDistrict.setMasterDefState(mddp.getMasterDefStatePending().getMasterDefState());
        }


        masterDefDistrict.setDistrictId(mddp.getDistrictId());
        masterDefDistrict.setDistrictName(mddp.getDistrictName());
        masterDefDistrict.setStatus(mddp.getStatus());

        masterDefDistrict.setModifiedDate(validationService.getCreateOrModifyDate());
        masterDefDistrict.setModifiedUser(mddp.getPenCreatedUser());
        masterDefDistrict.setSyncTs(validationService.getCreateOrModifyDate());

        return masterDefDistrictRepository.save(masterDefDistrict);

    }


}
