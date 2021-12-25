package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.RiskCalculation;
import com.fusionx.lending.origination.domain.RiskCalculationDetail;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.domain.RiskParaTempCalFeild;
import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
import com.fusionx.lending.origination.domain.RiskTemplateDetailValuePaire;
import com.fusionx.lending.origination.domain.RiskTemplateDetailVariance;
import com.fusionx.lending.origination.domain.ValuePaireDetail;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.FieldSetUpDescEnum;
import com.fusionx.lending.origination.enums.FieldSetUpTypeEnum;
import com.fusionx.lending.origination.enums.TableNamesEnum;
import com.fusionx.lending.origination.exception.TableRelatedPrimitiveValidationException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FieldSetupRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.RiskCalculationDetailRepository;
import com.fusionx.lending.origination.repository.RiskCalculationRepository;
import com.fusionx.lending.origination.repository.RiskGradingDetailRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.repository.RiskParaTempCalFeildRepository;
import com.fusionx.lending.origination.repository.RiskParameterTemplateRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailValuePaireRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailVarianceRepository;
import com.fusionx.lending.origination.repository.RiskTemplateRepository;
import com.fusionx.lending.origination.repository.ValuePaireDetailRepository;
import com.fusionx.lending.origination.resource.CreditScoreRootResource;
import com.fusionx.lending.origination.resource.RiskMainCriteriaResponse;
import com.fusionx.lending.origination.resource.TableRelatedValidationExceptionResource;
import com.fusionx.lending.origination.service.CreditScoreCalculationService;
import com.fusionx.lending.origination.service.ValidateService;

import bsh.EvalError;
import bsh.Interpreter;
/**
 * API Service related to Credit Score calculation
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        08-12-2021      -                        Nipun Dilhan             Created
 * <p>
 *
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class CreditScoreCalculationServiceImpl extends MessagePropertyBase implements CreditScoreCalculationService{

	private static final int ROUND_OFF_2_DECIMAL_POINTS = 2;
	
	private ValidateService validateService;
    @Autowired
    public void setValidateService( ValidateService validateService) {
        this.validateService = validateService;
    }
    
	private RiskTemplateRepository riskTemplateRepository;
    @Autowired
    public void setRiskTemplateRepository( RiskTemplateRepository riskTemplateRepository) {
        this.riskTemplateRepository = riskTemplateRepository;
    }
    
	private RiskTemplateDetailRepository riskTemplateDetailRepository;
    @Autowired
    public void setRiskTemplateRepository( RiskTemplateDetailRepository riskTemplateDetailRepository) {
        this.riskTemplateDetailRepository = riskTemplateDetailRepository;
    }
    
	private RiskParameterTemplateRepository riskParameterTemplateRepository;
    @Autowired
    public void setRiskParameterTemplateRepository( RiskParameterTemplateRepository riskParameterTemplateRepository) {
        this.riskParameterTemplateRepository = riskParameterTemplateRepository;
    }
    
	private RiskParaTempCalFeildRepository riskParaTempCalFeildRepository;
    @Autowired
    public void setRiskParaTempCalFeildRepository(  RiskParaTempCalFeildRepository riskParaTempCalFeildRepository) {
        this.riskParaTempCalFeildRepository = riskParaTempCalFeildRepository;
    }
    
	private FieldSetupRepository fieldSetupRepository;
    @Autowired
    public void setFieldSetupRepository( FieldSetupRepository fieldSetupRepository) {
        this.fieldSetupRepository = fieldSetupRepository;
    }
    
    
	private RiskTemplateDetailVarianceRepository riskTemplateDetailVarianceRepository;
    @Autowired
    public void setRiskTemplateDetailVarianceRepository( RiskTemplateDetailVarianceRepository riskTemplateDetailVarianceRepository) {
        this.riskTemplateDetailVarianceRepository = riskTemplateDetailVarianceRepository;
    }
    
	private RiskTemplateDetailValuePaireRepository riskTemplateDetailValuePairRepository;
    @Autowired
    public void setRiskTemplateDetailValuePairRepository(RiskTemplateDetailValuePaireRepository riskTemplateDetailValuePairRepository) {
        this.riskTemplateDetailValuePairRepository = riskTemplateDetailValuePairRepository;
    }
    
	private ValuePaireDetailRepository valuePaireDetailsRepository;
    @Autowired
    public void setRiskCalculationValueRepository(ValuePaireDetailRepository valuePaireDetailsRepository) {
        this.valuePaireDetailsRepository = valuePaireDetailsRepository;
    }
    
	private RiskGradingRepository riskGradingRepository;
    @Autowired
    public void setRiskGradingRepository( RiskGradingRepository riskGradingRepository) {
        this.riskGradingRepository = riskGradingRepository;
    }
    
	private RiskGradingDetailRepository riskGradingDetailsRepository;
    @Autowired
    public void setRiskGradingDetailsRepository( RiskGradingDetailRepository riskGradingDetailsRepository) {
        this.riskGradingDetailsRepository = riskGradingDetailsRepository;
    }
    
	private LeadInfoRepository leadInfoRepository;
    @Autowired
    public void setLeadInfoRepository( LeadInfoRepository leadInfoRepository) {
        this.leadInfoRepository = leadInfoRepository;
    }
    
	private RiskCalculationRepository riskCalculationRepository;
    @Autowired
    public void setRiskCalculationRepository( RiskCalculationRepository riskCalculationRepository) {
        this.riskCalculationRepository = riskCalculationRepository;
    }
    
	private RiskCalculationDetailRepository riskCalculationDetailRepository;
    @Autowired
    public void setRiskCalculationRepository( RiskCalculationDetailRepository riskCalculationDetailRepository) {
        this.riskCalculationDetailRepository = riskCalculationDetailRepository;
    }
    
    
    
    
    
    
	@Override
	public RiskCalculation calculateCreditScore(CreditScoreRootResource creditScoreRootResource) {
			
		Map<String, String> fieldValMap = new HashMap<>();
		
		creditScoreRootResource.getCreditScoreFieldValueResourceList().forEach(fieldValResource -> {			
			fieldValMap.put(fieldValResource.getField(), fieldValResource.getValue());
		});
		
		
		Optional<RiskTemplate> riskTemplateOptional = riskTemplateRepository.findById(validateService.stringToLong(creditScoreRootResource.getTemplateId()));
		if(!riskTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("creditSocreCalculation.risk-template-not-found"), "message");
		}
		
		List<RiskTemplateDetail> riskTemplateDetailList = riskTemplateDetailRepository.findByRiskTemplatesOrdrByMainCriteria(riskTemplateOptional.get().getId());

		BigDecimal finalScore = BigDecimal.ZERO;
		
		int index = 0;	
		
		List<RiskCalculationDetail> riskCalculationDetailsList = new ArrayList<>();
		
		for(RiskTemplateDetail riskTemplateDetail :riskTemplateDetailList) {
			
			index +=1;	
			
			RiskParameterTemplate riskParameterTemplates = riskTemplateDetail.getRiskParameterTemplates();
						
			String finalComputedValue = null;
			
			
			List<RiskParaTempCalFeild> riskParaTempCalFeildList = riskParaTempCalFeildRepository.findByRiskParameterTemplatesId(riskParameterTemplates.getId());
			
			if(("equation").equals(riskParameterTemplates.getCalculationMethod()) ) {
				
				Map<String, String> fieldValMapSpecific = new HashMap<>();				
				
				if((riskParaTempCalFeildList != null) && (!riskParaTempCalFeildList.isEmpty())) {
					for(RiskParaTempCalFeild riskParaTempCalFeild :riskParaTempCalFeildList) {
						String value = fieldValMap.get(riskParaTempCalFeild.getFieldSetups().getFieldId());
											
						if(value == null) {
							throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.FIELD_SET_UP.toString(),null,riskParaTempCalFeild.getFieldSetups().getId()
											,riskParaTempCalFeild.getFieldSetups().getFieldName(),null,environment.getProperty("creditSocreCalculation.field-not-found-list") ) );
						}
						
						if(!validateFieldType(riskParaTempCalFeild.getFieldSetups(),value)) {
							throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.FIELD_SET_UP.toString(),"field_setup_type",riskParaTempCalFeild.getFieldSetups().getId()
								,riskParaTempCalFeild.getFieldSetups().getFieldId(),value,environment.getProperty("creditSocreCalculation.field-setup-type-incompatible") ) );	
						}
						
						fieldValMapSpecific.put(riskParaTempCalFeild.getFieldSetups().getFieldId(), value);	
					}
				}else {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_PARA_TEMP_CAL_FIELD.toString(),"risk_parameter_template_id",riskParameterTemplates.getId()
							,null,null,environment.getProperty("creditSocreCalculation.risk-template-cal-field-not-found") ) );	
				}

				
				String equation = replaceFieldsWithValues(riskParameterTemplates.getEquation(), fieldValMapSpecific);

				Interpreter i = new Interpreter();
				Object result;
				try {
					
					i.eval("eq1 ="+equation);
					String ans1 = i.get("eq1").toString();
					
					finalComputedValue = ans1;
					
				} catch (EvalError e) {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_PARAMETER_TEMPLATE.toString(),"equation",riskParameterTemplates.getId()
							,null,null,environment.getProperty("creditSocreCalculation.equation-invalid") ) );
				} catch (Exception e) {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_PARAMETER_TEMPLATE.toString(),"equation",riskParameterTemplates.getId()
							,null,null,environment.getProperty("creditSocreCalculation.equation-invalid") ) );
				}

			}else {
				
				if((riskParaTempCalFeildList != null) && (!riskParaTempCalFeildList.isEmpty())) {
					if(riskParaTempCalFeildList.size() != 1) {
						throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_PARA_TEMP_CAL_FIELD.toString(),"risk_parameter_template_id",null
								,null,null,environment.getProperty("creditSocreCalculation.cannot-exists-more-than-one") ) );
					}
					
					FieldSetup fieldSetups = riskParaTempCalFeildList.get(0).getFieldSetups();
					String value = fieldValMap.get(fieldSetups.getFieldId());
					
					if(value == null) {
						throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.FIELD_SET_UP.toString(),null,fieldSetups.getId()
										,fieldSetups.getFieldId(),null,environment.getProperty("creditSocreCalculation.field-not-found-list") ) );
					}
					
					if(!validateFieldType(fieldSetups,value)) {
						throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.FIELD_SET_UP.toString(),"field_setup_type",fieldSetups.getId()
								,fieldSetups.getFieldId(),value,environment.getProperty("creditSocreCalculation.field-setup-type-incompatible") ) );	
					}
					
					finalComputedValue = value;
					
					
				}else {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_PARA_TEMP_CAL_FIELD.toString(),"risk_parameter_template_id",riskParameterTemplates.getId()
							,null,null,environment.getProperty("creditSocreCalculation.risk-template-cal-field-not-found") ) );	
				}
				
			}
						

			BigDecimal criteriaScore = BigDecimal.ZERO;
			
			if(("VARIANCE").equals(riskTemplateDetail.getIndicator())) {
				
				List<RiskTemplateDetailVariance> varianceList = riskTemplateDetailVarianceRepository.findByRiskTemplateDetailsId(riskTemplateDetail.getId());
				
				Long computeLongValue = validateService.stringToLong(finalComputedValue);
				
				if((varianceList!= null) && (!varianceList.isEmpty())) {
					for(RiskTemplateDetailVariance varianceRec :varianceList ){								
		
						Long fromValue = varianceRec.getFromValue();
						if(	(varianceRec.getFromValue() <= computeLongValue) && (varianceRec.getToValue() > computeLongValue)	) {

							criteriaScore = calculateIndividualFinalScore(varianceRec.getScore(),riskTemplateDetail.getWeightagePercentage());						
							break;
						}						
					}
					
				}else {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_TEMPLATE_DETAIL_VARIENCE.toString(),"risk_template_detail_id",null
							,null,riskTemplateDetail.getId().toString(),environment.getProperty("creditSocreCalculation.detail-varience-not-found") ) );
				}
				
			}else {
				
				List<RiskTemplateDetailValuePaire> valuePairList = riskTemplateDetailValuePairRepository.findByRiskTemplateDetailsId(riskTemplateDetail.getId());
				
				if((valuePairList!= null) && (!valuePairList.isEmpty())) {
					
					for(RiskTemplateDetailValuePaire valuePair :valuePairList ){			
						Optional<ValuePaireDetail> valPairOptional = valuePaireDetailsRepository.findById(valuePair.getValueId());
						if(!valPairOptional.isPresent()) {
							throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.VAL_PAIR_DETAIL.toString(),null,null
									,null,valuePair.getValueId().toString(),environment.getProperty("creditSocreCalculation.detail-val-pair-detail-not-found") ) );
						}
						
						if((finalComputedValue).equals(valPairOptional.get().getValue())) {
							
							criteriaScore = calculateIndividualFinalScore(valuePair.getScore(),riskTemplateDetail.getWeightagePercentage());

							break;
						}						
					}
					
				} else {
					throw new TableRelatedPrimitiveValidationException(new TableRelatedValidationExceptionResource(TableNamesEnum.RISK_TEMPLATE_DETAIL_VAL_PAIR.toString(),"risk_template_detail_id",null
							,null,riskTemplateDetail.getId().toString(),environment.getProperty("creditSocreCalculation.detail-val-pair-not-found") ) );
				}
								
			}
			
			RiskCalculationDetail riskCalculationDetail =  new RiskCalculationDetail();
			riskCalculationDetail.setRiskTemplateDetail(riskTemplateDetail);


			riskCalculationDetail.setRiskSubCriteriaId(riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias().getId());
			riskCalculationDetail.setRiskSubCriteriaName(riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias().getName());
			riskCalculationDetail.setScore(criteriaScore.setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));

						
			riskCalculationDetailsList.add(riskCalculationDetail);

						
			finalScore =	finalScore.add(criteriaScore);
						
		}
		
		Optional<RiskGradingDetail> riskGradingDetailOptional = getRiskGrading(finalScore,riskTemplateOptional.get().getRiskGradings());
		if(!riskGradingDetailOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("creditSocreCalculation.risk-grading-detail-not-found"), "message");
		}		
		
		RiskCalculation riskCalculation =  new RiskCalculation();
		riskCalculation.setRiskTemplate(riskTemplateOptional.get());
		riskCalculation.setTotalScore(finalScore.setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		riskCalculation.setRiskGradingDetail(riskGradingDetailOptional.get());
		riskCalculation.setRiskGradingDetailName(riskGradingDetailOptional.get().getGrading());
		riskCalculation.setRiskCalculationDetailsList(riskCalculationDetailsList);

		return riskCalculation;
				
	}
	

	
	
	private String replaceFieldsWithValues(String equation , Map<String, String> fieldValMapSpecific) {
		
		String finalEquation =  equation;
		
		
		for (Map.Entry<String, String> pair : fieldValMapSpecific.entrySet()) {
		   //System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", pair.getKey(), pair.getValue())); 
		    
		    finalEquation =finalEquation.replaceAll(pair.getKey(), pair.getValue());
		}
		
		return finalEquation;
				
	}
	
	
	private Optional<RiskGradingDetail> getRiskGrading(BigDecimal score , RiskGrading riskGrading) {
		
		RiskGradingDetail matchedRiskGradeDetail = null;

		if( (riskGrading != null) ){
				List<RiskGradingDetail> riskGradingDetailList = riskGradingDetailsRepository.findByRiskGradingsId(riskGrading.getId());
			
				for(RiskGradingDetail riskGradingDetail :riskGradingDetailList) {
					if(	( ( score).compareTo(BigDecimal.valueOf(riskGradingDetail.getFromScore()  )) > 0) 
							&& ( score).compareTo(BigDecimal.valueOf(riskGradingDetail.getToScore())) <= 0)    	 {

						
						matchedRiskGradeDetail = riskGradingDetail;
						break;
					}
				}			
		}
		
		return Optional.ofNullable(matchedRiskGradeDetail);
	
	}
	
	
	@Override
	public Long saveCreditScoreCalculation(CreditScoreRootResource calculateCreditScore ,String tenantId ,Long leadInfoId ,String user) {
		RiskCalculation riskCalculation = calculateCreditScore(calculateCreditScore);
		
		Optional<LeadInfo> leadInfoOptional = leadInfoRepository.findById(leadInfoId);
		if(!leadInfoOptional.isPresent()) {
			
		}		
		
		riskCalculation.setTenantId(tenantId);
		riskCalculation.setLeadInfo(leadInfoOptional.get());
		riskCalculation.setStatus(CommonStatus.ACTIVE);
		riskCalculation.setCreatedDate(validateService.getCreateOrModifyDate());
		riskCalculation.setCreatedUser(user);
		riskCalculation.setModifiedDate(validateService.getCreateOrModifyDate());
		riskCalculation.setModifiedUser(user);
		riskCalculation.setSyncTs(validateService.getCreateOrModifyDate());		
		riskCalculationRepository.save(riskCalculation);
		
		List<RiskCalculationDetail> riskCalculationDetailsList = riskCalculation.getRiskCalculationDetailsList();
		
		
		for(RiskCalculationDetail riskCalDetail:riskCalculationDetailsList) {
			
			riskCalDetail.setTenantId(tenantId);
			riskCalDetail.setRiskCalculation(riskCalculation);
			riskCalDetail.setCreatedDate(validateService.getCreateOrModifyDate());
			riskCalDetail.setCreatedUser(user);
			riskCalDetail.setModifiedDate(validateService.getCreateOrModifyDate());
			riskCalDetail.setModifiedUser(user);
			riskCalDetail.setSyncTs(validateService.getCreateOrModifyDate());
			riskCalculationDetailRepository.save(riskCalDetail);
		}
		
		return riskCalculation.getId();
		
	}
	
    
	@Override
	public Optional<RiskCalculation> getDetailsByRiskCalculationId(Long riskCalculationId) {
		
		Optional<RiskCalculation> riskCalculationOptional = riskCalculationRepository.findById(riskCalculationId);
		if(!riskCalculationOptional.isPresent()) {
			return Optional.empty();
		}
		
		RiskCalculation riskCalculation = riskCalculationOptional.get();
		RiskTemplate riskTemplate = riskCalculation.getRiskTemplate();
		
		List<RiskMainCriteria> riskMainCriteriasDistinctList = riskTemplateDetailRepository.findByRiskTemplateDistinctRiskMainCriterias(riskTemplate.getId());
		
		if((riskMainCriteriasDistinctList == null) && (riskMainCriteriasDistinctList.isEmpty())) {
			return Optional.empty();
		}
		
		
		List<RiskMainCriteriaResponse> riskMainCriteriaResponseList = new ArrayList<>();
		
		for(RiskMainCriteria riskMainCriteria :riskMainCriteriasDistinctList) {
			
			RiskMainCriteriaResponse riskMainCriteriaResponse = new RiskMainCriteriaResponse();
			
			List<RiskCalculationDetail> riskCalDetByMainCriteriaList = riskCalculationDetailRepository.findByRiskCalIdAndRiskMainCriteriaId(riskCalculationId,riskMainCriteria.getId());
		
			BigDecimal totalPercentage = BigDecimal.ZERO;
			BigDecimal totalScore = BigDecimal.ZERO;
			for(RiskCalculationDetail riskCalculationDetail :riskCalDetByMainCriteriaList) {
				
				
				riskCalculationDetail.setRiskSubCriteriaId(riskCalculationDetail.getRiskTemplateDetail().getRiskParameterTemplates().getRiskSubCriterias().getId());
				riskCalculationDetail.setRiskSubCriteriaName(riskCalculationDetail.getRiskTemplateDetail().getRiskParameterTemplates().getRiskSubCriterias().getName());
				riskCalculationDetail.setPercentage(riskCalculationDetail.getRiskTemplateDetail().getWeightagePercentage());
				
				totalScore = totalScore.add(riskCalculationDetail.getScore());
				totalPercentage = totalPercentage.add(BigDecimal.valueOf(riskCalculationDetail.getRiskTemplateDetail().getWeightagePercentage()));
			}
			
			riskMainCriteriaResponse.setMainCriteriaId(riskMainCriteria.getId());
			riskMainCriteriaResponse.setMainCriteriaName(riskMainCriteria.getName());
			riskMainCriteriaResponse.setTotalPercentage(totalPercentage);
			riskMainCriteriaResponse.setTotalScore(totalScore);
			riskMainCriteriaResponse.setRiskCalculationDetailsList(riskCalDetByMainCriteriaList);
			riskMainCriteriaResponseList.add(riskMainCriteriaResponse);
		
		}
		
		
		riskCalculation.setRiskMainCriteriaResponseList(riskMainCriteriaResponseList);
		
		return Optional.of(riskCalculation);

	}
	
	private BigDecimal calculateIndividualFinalScore(Long indvScr ,Double weight ) {
		return (BigDecimal.valueOf(indvScr)).multiply(convertPercentageToDecimal(weight));
	}
	
	
	private BigDecimal convertPercentageToDecimal(Double percentage) {
		
		BigDecimal prcntg = new BigDecimal(percentage);
		prcntg =  prcntg.divide(BigDecimal.valueOf(100));
		return prcntg;
		
	}
	
	private boolean validateFieldType(FieldSetup fieldSetups, String value) {
		
		if (!(FieldSetUpTypeEnum.STRING).equals(fieldSetups.getFieldSetupType())) {
			String ptrn = FieldSetUpDescEnum.valueOf(fieldSetups.getFieldSetupType().toString()).getPattern();

			if (Pattern.matches(ptrn, value)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
		

	}
}
