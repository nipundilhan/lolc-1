package com.fusionx.lending.transaction.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.domain.TransEventAccStatus;
import com.fusionx.lending.transaction.domain.TransEventCreditNote;
import com.fusionx.lending.transaction.domain.TransEventSubCode;
import com.fusionx.lending.transaction.domain.TransactionEvent;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.CurrConversionRateType;
import com.fusionx.lending.transaction.enums.PassbookStatus;
import com.fusionx.lending.transaction.enums.ServiceEntity;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.InvalidServiceIdException;
import com.fusionx.lending.transaction.exception.OptimisticLockException;
import com.fusionx.lending.transaction.repo.CreditNoteTypeRepository;
import com.fusionx.lending.transaction.repo.TransEventAccStatusRepository;
import com.fusionx.lending.transaction.repo.TransEventCreditNoteRepository;
import com.fusionx.lending.transaction.repo.TransEventSubCodeRepository;
import com.fusionx.lending.transaction.repo.TransactionEventRepository;
import com.fusionx.lending.transaction.resource.AddTransEventAccStatus;
import com.fusionx.lending.transaction.resource.AddTransEventSubCode;
import com.fusionx.lending.transaction.resource.TransEventAccStatusResource;
import com.fusionx.lending.transaction.resource.TransEventCreditNoteAddResource;
import com.fusionx.lending.transaction.resource.TransEventCreditNoteUpdateResource;
import com.fusionx.lending.transaction.resource.TransEventSubCodeResourse;
import com.fusionx.lending.transaction.resource.TransSubCodeResponse;
import com.fusionx.lending.transaction.resource.TransactionEventAddResource;
import com.fusionx.lending.transaction.resource.TransactionEventUpdateResource;
import com.fusionx.lending.transaction.resource.UpdateTransEventAccStatus;
import com.fusionx.lending.transaction.resource.UpdateTransEventSubCode;
import com.fusionx.lending.transaction.service.BankTransactionCodeService;
import com.fusionx.lending.transaction.service.BankTransactionSubCodeService;
import com.fusionx.lending.transaction.service.TransactionEventService;
import com.fusionx.lending.transaction.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class TransactionEventServiceImpl implements TransactionEventService {
    @Autowired
    private TransactionEventRepository repo;

    @Autowired
    private Environment environment;

    @Autowired
    private TransEventSubCodeRepository transEventSubCodeRepository;

    @Autowired
    private BankTransactionCodeService bankTransactionCodeService;

    @Autowired
    private BankTransactionSubCodeService bankTransactionSubCodeService;

    @Autowired
    private TransEventAccStatusRepository transEventAccStatusRepository;

    @Autowired
    private ValidateService validateService;
    
    @Autowired
    private TransEventCreditNoteRepository transEventCreditNoteRepository;
    
    @Autowired
    private CreditNoteTypeRepository creditNoteTypeRepository;

    private String invalidVersion = "common.invalid-version";

    @Override
    public List<?> findAll() {

        //BooleanBuilder bb = new BooleanBuilder(predicate);
		
		/*repo.findAll(bb.getValue(), pageable).map(data -> {
			data.setBrand(null);
			return data;
		});*/

        return repo.findAll();
    }

    @Override
    public List<?> findAllSubCode() {

        //BooleanBuilder bb = new BooleanBuilder(predicate);
		
		/*repo.findAll(bb.getValue(), pageable).map(data -> {
			data.setBrand(null);
			return data;
		});*/

        return transEventSubCodeRepository.findAll();
    }


    @Override
    public List<?> findAllAccStatus() {

        //BooleanBuilder bb = new BooleanBuilder(predicate);
		
		/*repo.findAll(bb.getValue(), pageable).map(data -> {
			data.setBrand(null);
			return data;
		});*/

        return transEventAccStatusRepository.findAll();
    }

    @Override
    public List<TransactionEvent> getAllTransactionEvents(Pageable pageable) {
        repo.findAll();
        return repo.findAll();
    }

    @Override
    public Optional<TransactionEvent> getTransactionEvents(Long id) {
        repo.findById(id);
        return repo.findById(id);
    }

    @Override
    public List<TransactionEvent> getTransEventByStatus(String status) {
        repo.findByStatus(status);
        return repo.findByStatus(status);
    }
    
    @Override
    public List<TransactionEvent> getTransEventByDescription(String description) {
        repo.findByDescriptionContains(description);
        return repo.findByDescriptionContains(description);
    }
    
    @Override
    public Optional<TransactionEvent> getTransEventByCode(String code) {
        repo.findByCode(code);
        return repo.findByCode(code);
    }

    @Override
    public void save(String tenantId, AddTransEventSubCode addTransEventSubCode) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(addTransEventSubCode.getTransEventId()));

        Optional<TransEventSubCode> presentByEvent = transEventSubCodeRepository.findByTransactionEventIdAndStatus(Long.parseLong(addTransEventSubCode.getTransEventId()), PassbookStatus.ACTIVE.toString());
        if (presentByEvent.isPresent()) {
            //if(presentByEvent.get().getStatus().equals(PassbookStatus.ACTIVE.toString())) {
            throw new CodeUniqueException(environment.getProperty("common.event-duplicate"));

            //}
        }
        if (!transactionEvent.isPresent()) {

            throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);

        }
        if (!transactionEvent.get().getCode().equals(addTransEventSubCode.getTransEventCode())) {
            throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);

        }
        Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeService.findById(Long.parseLong(addTransEventSubCode.getSubCodeId()));
        if (!bankTransactionSubCode.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("subCodeId.noRecord"), ServiceEntity.BANKTRANSACTIONSUB);

        }
        if (!bankTransactionSubCode.get().getSubCode().equals(addTransEventSubCode.getSubCode())) {
            throw new InvalidServiceIdException(environment.getProperty("subCodeId.notMatch"), ServiceEntity.BANKTRANSACTIONSUB);
        }

        Optional<TransEventSubCode> isPresentTransEventSubCode = transEventSubCodeRepository.findBybankTransactionSubCodeIdAndTransactionEventId(Long.parseLong(addTransEventSubCode.getSubCodeId()), Long.parseLong(addTransEventSubCode.getTransEventId()));
        if (isPresentTransEventSubCode.isPresent()) {
            if (isPresentTransEventSubCode.get().getTransactionEvent().getCode().equals(addTransEventSubCode.getTransEventCode())) {
                if (isPresentTransEventSubCode.get().getStatus().equals(PassbookStatus.ACTIVE.toString())) {
                    throw new CodeUniqueException(environment.getProperty("common.sub-code-duplicate"));
                }
            }
        }


        TransEventSubCode transEventSubCode = new TransEventSubCode();
        TransEventSubCodeResourse transEventSubCodeResourse = addTransEventSubCode;
        transEventSubCode.setCreatedUser(addTransEventSubCode.getCreatedUser());
        transEventSubCode.setCreatedDate(currentTimestamp);
        set(transEventSubCodeResourse, tenantId, transEventSubCode);


    }

    @Override
    public List<TransEventSubCode> getAllTransEventSubCode(Pageable pageable) {
        transEventSubCodeRepository.findAll();
        return transEventSubCodeRepository.findAll();
    }

    @Override
    public Optional<TransEventSubCode> findTransEventSubCode(Long id) {
        Optional<TransEventSubCode> tOptional = transEventSubCodeRepository.findById(id);
        //TransEventSubCode transEventSubCode=tOptional.get();
        //transEventSubCode.setBankTransactionCode(tOptional.get().getBankTransactionCode());
        return transEventSubCodeRepository.findById(id);
    }

    @Override
    public TransSubCodeResponse getTransEventSubCode(Long id) {
        Optional<TransEventSubCode> tOptional = transEventSubCodeRepository.findById(id);
        TransSubCodeResponse transSubCodeResponse = new TransSubCodeResponse();
        TransEventSubCode transEventSubCode = tOptional.get();
        transSubCodeResponse.setId(transEventSubCode.getId());
        transSubCodeResponse.setTenantId(transEventSubCode.getTenantId());
        transSubCodeResponse.setTransEvent(transEventSubCode.getTransactionEvent().getCode());
        transSubCodeResponse.setBankTransCode(transEventSubCode.getBankTransactionCode().getCode());
        transSubCodeResponse.setBankTransSubCode(transEventSubCode.getBankTransactionSubCode().getSubCode());

        transSubCodeResponse.setTransEventId(transEventSubCode.getTransactionEvent().getId());
        transSubCodeResponse.setBankTransCodeId(transEventSubCode.getBankTransactionCode().getId());
        transSubCodeResponse.setCurrConversionRateType(transEventSubCode.getCurrConversionRateType());
        transSubCodeResponse.setBankTransSubCodeId(transEventSubCode.getBankTransactionSubCode().getId());
        transSubCodeResponse.setStatus(transEventSubCode.getStatus());
        transSubCodeResponse.setCreatedUser(transEventSubCode.getCreatedUser());
        transSubCodeResponse.setCreatedDate(transEventSubCode.getCreatedDate());
        transSubCodeResponse.setModifiedUser(transEventSubCode.getModifiedUser());
        transSubCodeResponse.setModifiedDate(transEventSubCode.getModifiedDate());
        transSubCodeResponse.setBankTransactionCode(transEventSubCode.getBankTransactionCode());

        return transSubCodeResponse;
    }

    @Override
    public List<TransSubCodeResponse> getTransEventSubCodeByStatus(String status) {
        List<TransEventSubCode> list = transEventSubCodeRepository.findByStatus(status);
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        List<TransSubCodeResponse> codeResponses = new ArrayList<>();
        for (TransEventSubCode transEventSubCode : list) {
            codeResponses.add(setToSubCodeResource(transEventSubCode));
        }
        return codeResponses;
    }

    @Override
    public TransSubCodeResponse getTransEventSubCodeByeventCode(String eventCode) {
        Optional<TransactionEvent> transactionEvent = repo.findByCode(eventCode);
        Optional<TransEventSubCode> transEventSubCode = transEventSubCodeRepository.findByTransactionEventId(transactionEvent.get().getId());
        if (!transEventSubCode.isPresent()) {
            throw new NoSuchElementException();
        }
        TransSubCodeResponse codeResponses = new TransSubCodeResponse();
        /*
         * for(TransEventSubCode transEventSubCode:list) {
         * codeResponses.add(setToSubCodeResource(transEventSubCode)); }
         */
        codeResponses = setToSubCodeResource(transEventSubCode.get());
        return codeResponses;

    }

    @Override
    public void update(String tenantId, Long id, @Valid UpdateTransEventSubCode updateTransEventSubCode) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        Optional<TransEventSubCode> transEventSubCode = transEventSubCodeRepository.findById(id);
        Optional<TransEventSubCode> presentByEvent = transEventSubCodeRepository.findByTransactionEventIdAndIdNotIn(Long.parseLong(updateTransEventSubCode.getTransEventId()), id);
        if (!transEventSubCode.get().getTransactionEvent().getId().equals(Long.parseLong(updateTransEventSubCode.getTransEventId()))
                || !transEventSubCode.get().getTransactionEvent().getCode().equals(updateTransEventSubCode.getTransEventCode())) {
            throw new InvalidServiceIdException(environment.getProperty("eventId.cannotUpdate"), ServiceEntity.EVENT);
        }
        if (presentByEvent.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("common.event-duplicate"));
        }
        if (transEventSubCode.get().getVersion() != stringToLong(updateTransEventSubCode.getVersion())) {
            throw new OptimisticLockException(environment.getProperty(invalidVersion), "version");
        }
        if (transEventSubCode.isPresent()) {
 			/*Optional<TransEventSubCode> isPresentTransEventSubCode = transEventSubCodeRepository.findBybankTransactionSubCodeIdAndTransactionEventId(Long.parseLong(updateTransEventSubCode.getSubCodeId()),Long.parseLong(updateTransEventSubCode.getTransEventId()));
 			if(!isPresentTransEventSubCode.isPresent()) {
 				throw new InvalidServiceIdException(environment.getProperty("eventId.subcodeId.notMatch"), ServiceEntity.EVENT);
 			}else {
 				if(isPresentTransEventSubCode.get().getTransEventCode().equals(updateTransEventSubCode.getTransEventCode()))
 					throw new CodeUniqueException(environment.getProperty("common.sub-code-duplicate"));*/

 			Optional<TransEventSubCode> isPresentTransEventSubCode=transEventSubCodeRepository.findByBankTransactionSubCodeIdAndTransactionEventIdAndBankTransactionCodeIdAndIdNotIn(Long.parseLong(updateTransEventSubCode.getSubCodeId()),Long.parseLong(updateTransEventSubCode.getTransEventId()),Long.parseLong(updateTransEventSubCode.getBankTransactionCodeId()),id);
 				if(isPresentTransEventSubCode.isPresent()) {
 					throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));
 				}else {
 				
		 			TransEventSubCodeResourse transEventSubCodeResourse=updateTransEventSubCode;
					transEventSubCode.get().setModifiedUser(updateTransEventSubCode.getModifiedUser()); 
					transEventSubCode.get().setModifiedDate(currentTimestamp);
					set(transEventSubCodeResourse,tenantId,transEventSubCode.get());
 			}
 		}
 		}
	
// 		private void set(TransEventSubCodeResourse transEventSubCodeResourse,String tenantId,TransEventSubCode transEventSubCode) {
// 			 
// 			Optional<BankTransactionSubCode> bankTransactionSubCode=bankTransactionSubCodeService.findById(Long.parseLong(transEventSubCodeResourse.getSubCodeId()));
// 			if(!bankTransactionSubCode.isPresent()){
// 				throw new InvalidServiceIdException(environment.getProperty("subCodeId.noRecord"), ServiceEntity.BANKTRANSACTIONSUB);
//
// 			}    
// 			if(!bankTransactionSubCode.get().getSubCode().equals(transEventSubCodeResourse.getSubCode())) {
// 				throw new InvalidServiceIdException(environment.getProperty("subCodeId.notMatch"), ServiceEntity.BANKTRANSACTIONSUB);
// 			}else {
// 			transEventSubCode.setBankTransactionSubCode(bankTransactionSubCode.get());
// 			Optional<BankTransactionCode> bankTransactionCode=bankTransactionCodeService.findById(Long.parseLong(transEventSubCodeResourse.getBankTransactionCodeId()));
//
// 			if(!bankTransactionCode.isPresent()) {
//	 				throw new InvalidServiceIdException(environment.getProperty("transCodeId.noRecord"), ServiceEntity.BANKTRANSACTIONCODE);
// 			}else {
// 				BankTransactionCode present=bankTransactionSubCode.get().getBankTransactionCode();
// 				if(!bankTransactionCode.get().getCode().equals(transEventSubCodeResourse.getBankTransactionCode())|| !present.getCode().equals(transEventSubCodeResourse.getBankTransactionCode())) {
//
// 	 				throw new InvalidServiceIdException(environment.getProperty("transCodeId.notMatch"), ServiceEntity.BANKTRANSACTIONCODE);
// 				}
// 			}
// 			transEventSubCode.setBankTransactionCode(bankTransactionCode.get());
//			transEventSubCode.setTenantId(tenantId);
//			Optional<TransactionEvent> transactionEvent=getTransactionEvents(Long.parseLong(transEventSubCodeResourse.getTransEventId()));
//			if(!transactionEvent.isPresent()) {
// 				throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);
//
//			}if(!transactionEvent.get().getCode().equals(transEventSubCodeResourse.getTransEventCode())) {
//	 				throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);
//
//			}
//			transEventSubCode.setTransactionEvent(transactionEvent.get()); 
//			//transEventSubCode.setTransEventCode(transEventSubCodeResourse.getTransEventCode()); 
//			transEventSubCode.setCurrConversionRateType(CurrConversionRateType.valueOf(transEventSubCodeResourse.getCurrConversionRateType()));
//			transEventSubCode.setStatus(transEventSubCodeResourse.getStatus()); 
//			transEventSubCodeRepository.save(transEventSubCode);
// 			}
// 		}
// 		@Override
// 		public List<TransEventAccStatus> getAllTransEventAccStatus(Pageable pageable) {
// 			transEventAccStatusRepository.findAll();
// 			return transEventAccStatusRepository.findAll();
// 		}
// 		
// 		@Override
// 		public Optional<TransEventAccStatus> getTransEventAccStatus(Long id) {
// 			transEventAccStatusRepository.findById(id);
// 			return transEventAccStatusRepository.findById(id);
// 		}
// 		
// 		@Override
// 		public List<TransEventAccStatus> getTransEventAccStatusByStatus(String status){
// 			return transEventAccStatusRepository.findByStatus(status);
// 		}
//		
// 		public List<TransEventAccStatus> getTransEventAccStatusByEventCode(String eventCode){
// 			return transEventAccStatusRepository.findByTransEventCode(eventCode);
// 		}
// 		
 		@Override
 		public Optional<TransEventAccStatus> getTransEventAccStatusByEventCodeAndAccStatus(String accStatus,String transEventCode){
 			Optional<TransEventAccStatus> isPresentTransAccStatus = transEventAccStatusRepository.findByAccStatusAndTransEventCodeAndStatus(accStatus,transEventCode,CommonStatus.ACTIVE.toString());
			if (isPresentTransAccStatus.isPresent()) {
				return Optional.ofNullable(isPresentTransAccStatus.get());
			}
			else {
				return Optional.empty();
			}
 		}

 		public void saveAccStatus(String tenantId, @Valid AddTransEventAccStatus addTransEventAccStatus) {
 			Calendar calendar = Calendar.getInstance();
 			java.util.Date now = calendar.getTime();
 			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
 			
 			Optional<TransEventAccStatus> present=transEventAccStatusRepository.findByAccStatusAndTransactionEventId(addTransEventAccStatus.getAccStatus(), Long.parseLong(addTransEventAccStatus.getTransEventId()));
 			if(present.isPresent()) {
				throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));

 			}
 			Optional<TransactionEvent> transactionEvent=getTransactionEvents(Long.parseLong(addTransEventAccStatus.getTransEventId()));
 			if(!transactionEvent.isPresent()) {
 					throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);

 			}if(!transactionEvent.get().getCode().equals(addTransEventAccStatus.getTransEventCode())) {
 	 				throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);

 			}
 			TransEventAccStatus transEventAccStatus = new TransEventAccStatus();
 			TransEventAccStatusResource accStatusResource=addTransEventAccStatus;
 			transEventAccStatus.setCreatedUser(addTransEventAccStatus.getCreatedUser()); 
 			transEventAccStatus.setCreatedDate(currentTimestamp);
 			
 					setAccStatus(accStatusResource,tenantId,transEventAccStatus);

 				//}
 		}

//		private void setAccStatus(TransEventAccStatusResource accStatusResource, String tenantId,
//				TransEventAccStatus transEventAccStatus) {
//			Calendar calendar = Calendar.getInstance();
// 			java.util.Date now = calendar.getTime();
// 			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
//
//            Optional<TransEventSubCode> isPresentTransEventSubCode = transEventSubCodeRepository.findByBankTransactionSubCodeIdAndTransactionEventIdAndBankTransactionCodeIdAndIdNotIn(Long.parseLong(updateTransEventSubCode.getSubCodeId()), Long.parseLong(updateTransEventSubCode.getTransEventId()), Long.parseLong(updateTransEventSubCode.getBankTransactionCodeId()), id);
//            if (isPresentTransEventSubCode.isPresent()) {
//                throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));
//            } else {
//
//                TransEventSubCodeResourse transEventSubCodeResourse = updateTransEventSubCode;
//                transEventSubCode.get().setModifiedUser(updateTransEventSubCode.getModifiedUser());
//                transEventSubCode.get().setModifiedDate(currentTimestamp);
//                set(transEventSubCodeResourse, tenantId, transEventSubCode.get());
//            }
//        }
//    }

    private void set(TransEventSubCodeResourse transEventSubCodeResourse, String tenantId, TransEventSubCode transEventSubCode) {

        Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeService.findById(Long.parseLong(transEventSubCodeResourse.getSubCodeId()));
        if (!bankTransactionSubCode.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("subCodeId.noRecord"), ServiceEntity.BANKTRANSACTIONSUB);

        }
        if (!bankTransactionSubCode.get().getSubCode().equals(transEventSubCodeResourse.getSubCode())) {
            throw new InvalidServiceIdException(environment.getProperty("subCodeId.notMatch"), ServiceEntity.BANKTRANSACTIONSUB);
        } else {
            transEventSubCode.setBankTransactionSubCode(bankTransactionSubCode.get());
            Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeService.findById(Long.parseLong(transEventSubCodeResourse.getBankTransactionCodeId()));

            if (!bankTransactionCode.isPresent()) {
                throw new InvalidServiceIdException(environment.getProperty("transCodeId.noRecord"), ServiceEntity.BANKTRANSACTIONCODE);
            } else {
                BankTransactionCode present = bankTransactionSubCode.get().getBankTransactionCode();
                if (!bankTransactionCode.get().getCode().equals(transEventSubCodeResourse.getBankTransactionCode()) || !present.getCode().equals(transEventSubCodeResourse.getBankTransactionCode())) {

                    throw new InvalidServiceIdException(environment.getProperty("transCodeId.notMatch"), ServiceEntity.BANKTRANSACTIONCODE);
                }
            }
            transEventSubCode.setBankTransactionCode(bankTransactionCode.get());
            transEventSubCode.setTenantId(tenantId);
            Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(transEventSubCodeResourse.getTransEventId()));
            if (!transactionEvent.isPresent()) {
                throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);

            }
            if (!transactionEvent.get().getCode().equals(transEventSubCodeResourse.getTransEventCode())) {
                throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);

            }
            transEventSubCode.setTransactionEvent(transactionEvent.get());
            //transEventSubCode.setTransEventCode(transEventSubCodeResourse.getTransEventCode());
            transEventSubCode.setCurrConversionRateType(CurrConversionRateType.valueOf(transEventSubCodeResourse.getCurrConversionRateType()));
            transEventSubCode.setStatus(transEventSubCodeResourse.getStatus());
            transEventSubCodeRepository.save(transEventSubCode);
        }
    }

    @Override
    public List<TransEventAccStatus> getAllTransEventAccStatus(Pageable pageable) {
        transEventAccStatusRepository.findAll();
        return transEventAccStatusRepository.findAll();
    }

    @Override
    public Optional<TransEventAccStatus> getTransEventAccStatus(Long id) {
        transEventAccStatusRepository.findById(id);
        return transEventAccStatusRepository.findById(id);
    }

    @Override
    public List<TransEventAccStatus> getTransEventAccStatusByStatus(String status) {
        return transEventAccStatusRepository.findByStatus(status);
    }

    public List<TransEventAccStatus> getTransEventAccStatusByEventCode(String eventCode) {
        return transEventAccStatusRepository.findByTransEventCode(eventCode);
    }

//    public void saveAccStatus(String tenantId, @Valid AddTransEventAccStatus addTransEventAccStatus) {
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date now = calendar.getTime();
//        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
//
//        Optional<TransEventAccStatus> present = transEventAccStatusRepository.findByAccStatusAndTransactionEventId(addTransEventAccStatus.getAccStatus(), Long.parseLong(addTransEventAccStatus.getTransEventId()));
//        if (present.isPresent()) {
//            throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));
//
//        }
//        Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(addTransEventAccStatus.getTransEventId()));
//        if (!transactionEvent.isPresent()) {
//            throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);
//
//        }
//        if (!transactionEvent.get().getCode().equals(addTransEventAccStatus.getTransEventCode())) {
//            throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);
//
//        }
//        TransEventAccStatus transEventAccStatus = new TransEventAccStatus();
//        TransEventAccStatusResource accStatusResource = addTransEventAccStatus;
//        transEventAccStatus.setCreatedUser(addTransEventAccStatus.getCreatedUser());
//        transEventAccStatus.setCreatedDate(currentTimestamp);
//
//        setAccStatus(accStatusResource, tenantId, transEventAccStatus);
//
//        //}
//    }

    private void setAccStatus(TransEventAccStatusResource accStatusResource, String tenantId,
                              TransEventAccStatus transEventAccStatus) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

			/*if(validateService.validateAccountStatus(tenantId, (accStatusResource.getAccStatus()))){
					throw new InvalidServiceIdException(environment.getProperty("accStatus.noRecord"), ServiceEntity.ACCSTATUS);

			}*/
        transEventAccStatus.setAccStatus(accStatusResource.getAccStatus());
        transEventAccStatus.setTenantId(tenantId);
        transEventAccStatus.setTransactionEvent(getTransactionEvents(Long.parseLong(accStatusResource.getTransEventId())).get());
        transEventAccStatus.setTransEventCode(accStatusResource.getTransEventCode());
        transEventAccStatus.setAccStatusDesc(accStatusResource.getAccStatusDesc());
        transEventAccStatus.setStatus(accStatusResource.getStatus());
        transEventAccStatus.setSyncTs(currentTimestamp);
        transEventAccStatusRepository.save(transEventAccStatus);
    }

    @Override
    public void updateAccStatus(String tenantId, Long id, @Valid UpdateTransEventAccStatus updateTransEventAccStatus) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        Optional<TransEventAccStatus> transEventSubCode = transEventAccStatusRepository.findById(id);
        if (transEventSubCode.isPresent()) {
            Optional<TransEventAccStatus> present = transEventAccStatusRepository.findByAccStatusAndTransactionEventIdAndIdNotIn(updateTransEventAccStatus.getAccStatus(), Long.parseLong(updateTransEventAccStatus.getTransEventId()), id);
            if (present.isPresent()) {
                throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));

            }
            Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(updateTransEventAccStatus.getTransEventId()));
            if (!transactionEvent.isPresent()) {
                throw new InvalidServiceIdException(environment.getProperty("eventId.noRecord"), ServiceEntity.EVENT);

            }
            if (!transactionEvent.get().getCode().equals(updateTransEventAccStatus.getTransEventCode())) {
                throw new InvalidServiceIdException(environment.getProperty("eventId.notMatch"), ServiceEntity.EVENT);

            }
            TransEventAccStatusResource transEventSubCodeResourse = updateTransEventAccStatus;
            transEventSubCode.get().setModifiedUser(updateTransEventAccStatus.getModifiedUser());
            transEventSubCode.get().setModifiedDate(currentTimestamp);

            setAccStatus(transEventSubCodeResourse, tenantId, transEventSubCode.get());
        }
    }

    private TransSubCodeResponse setToSubCodeResource(TransEventSubCode transEventSubCode) {
        TransSubCodeResponse transSubCodeResponse = new TransSubCodeResponse();
        //TransEventSubCode transEventSubCode=tOptional.get();
        transSubCodeResponse.setId(transEventSubCode.getId());
        transSubCodeResponse.setTenantId(transEventSubCode.getTenantId());
        transSubCodeResponse.setTransEvent(transEventSubCode.getTransactionEvent().getCode());
        transSubCodeResponse.setBankTransCode(transEventSubCode.getBankTransactionCode().getCode());
        transSubCodeResponse.setBankTransSubCode(transEventSubCode.getBankTransactionSubCode().getSubCode());

        transSubCodeResponse.setTransEventId(transEventSubCode.getTransactionEvent().getId());
        transSubCodeResponse.setBankTransCodeId(transEventSubCode.getBankTransactionCode().getId());
        transSubCodeResponse.setBankTransSubCodeId(transEventSubCode.getBankTransactionSubCode().getId());
        transSubCodeResponse.setCurrConversionRateType(transEventSubCode.getCurrConversionRateType());
        transSubCodeResponse.setStatus(transEventSubCode.getStatus());
        transSubCodeResponse.setCreatedUser(transEventSubCode.getCreatedUser());
        transSubCodeResponse.setCreatedDate(transEventSubCode.getCreatedDate());
        transSubCodeResponse.setModifiedUser(transEventSubCode.getModifiedUser());
        transSubCodeResponse.setModifiedDate(transEventSubCode.getModifiedDate());
        transSubCodeResponse.setBankTransactionCode(transEventSubCode.getBankTransactionCode());
        return transSubCodeResponse;

    }

    /**
     * convert String to Long
     *
     * @param String
     * @return Long
     */
    private Long stringToLong(String value) {
        return Long.parseLong(value);
    }

    /**
     * @author Senitha
     * @date 26-NOV-2020
     */
    @Override
    public Optional<TransEventSubCode> findTransEventSubCodeByEventCode(String transEventCode) {

        Optional<TransEventSubCode> isPresentTransEventSubCode = transEventSubCodeRepository.findByTransactionEventCode(transEventCode);
        if (isPresentTransEventSubCode.isPresent()) {
            return Optional.ofNullable(isPresentTransEventSubCode.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void addTransactionEvent(String tenantId, @Valid TransactionEventAddResource transactionEventAddResource) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        Optional<TransactionEvent> transactionEventPresent = repo.findByCode(transactionEventAddResource.getCode());
        if (transactionEventPresent.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("common.code-duplicate"));

        }

        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setTenantId(tenantId);
        transactionEvent.setCode(transactionEventAddResource.getCode());
        transactionEvent.setDescription(transactionEventAddResource.getDescription());
        transactionEvent.setStatus(transactionEventAddResource.getStatus());
        transactionEvent.setSyncTs(currentTimestamp);
        transactionEvent.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        transactionEvent.setCreatedDate(currentTimestamp);
        repo.saveAndFlush(transactionEvent);
    }
    
    @Override
    public void updateTransactionEvent(String tenantId,  TransactionEventUpdateResource transactionEventAddResource, Long id) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        Optional<TransactionEvent> transactionEventPresent = getTransactionEvents(id);
        if (!transactionEventPresent.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("eventId.noRecord"));

        }
        if (transactionEventPresent.get().getVersion() != stringToLong(transactionEventAddResource.getVersion())) {
            throw new OptimisticLockException(environment.getProperty(invalidVersion), "version");
        }

        TransactionEvent transactionEvent = transactionEventPresent.get();
        transactionEvent.setTenantId(tenantId);
        //transactionEvent.setCode(transactionEventAddResource.getCode());
        transactionEvent.setDescription(transactionEventAddResource.getDescription());
        transactionEvent.setStatus(transactionEventAddResource.getStatus());
        transactionEvent.setSyncTs(currentTimestamp);
        transactionEvent.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        transactionEvent.setModifiedDate(currentTimestamp);
        repo.saveAndFlush(transactionEvent);
    }
    
    @Override
    public void addTransactionEventAndCreditNote(String tenantId, TransEventCreditNoteAddResource transEventCreditNoteAddResource) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(transEventCreditNoteAddResource.getTransEventId()));

        Optional<TransEventCreditNote> presentByEvent = transEventCreditNoteRepository.findByTransactionEventIdAndStatus(Long.parseLong(transEventCreditNoteAddResource.getTransEventId()), PassbookStatus.ACTIVE.toString());
        if (presentByEvent.isPresent()) {
            //if(presentByEvent.get().getStatus().equals(PassbookStatus.ACTIVE.toString())) {
            throw new CodeUniqueException(environment.getProperty("common.event-duplicate"));

            //}
        }
        if (!transactionEvent.isPresent()) {

            throw new CodeUniqueException(environment.getProperty("eventId.noRecord"));

        }
        if (!transactionEvent.get().getCode().equals(transEventCreditNoteAddResource.getTransEventCode())) {
            throw new CodeUniqueException(environment.getProperty("eventId.notMatch"));

        }
        if(!transactionEvent.get().getStatus().equalsIgnoreCase("ACTIVE")) {
        	throw new CodeUniqueException(environment.getProperty("eventId.invalidStatus"));
        }
        
        Optional<CreditNoteType> creditNoteType = creditNoteTypeRepository.findById(Long.parseLong(transEventCreditNoteAddResource.getCreditNoteId()));
        if (!creditNoteType.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("creditNoteId.noRecord"));

        }
        if (!creditNoteType.get().getCode().equals(transEventCreditNoteAddResource.getCreditNoteCode())) {
            throw new CodeUniqueException(environment.getProperty("creditNoteId.notMatch"));
        }

        Optional<TransEventCreditNote> isPresentTransEventCreditNote = transEventCreditNoteRepository.findByCreditNoteTypeIdAndTransactionEventId(Long.parseLong(transEventCreditNoteAddResource.getCreditNoteId()), Long.parseLong(transEventCreditNoteAddResource.getTransEventId()));
        if (isPresentTransEventCreditNote.isPresent()) {
            if (isPresentTransEventCreditNote.get().getTransactionEvent().getCode().equals(transEventCreditNoteAddResource.getTransEventCode())) {
                if (isPresentTransEventCreditNote.get().getStatus().equals(PassbookStatus.ACTIVE.toString())) {
                    throw new CodeUniqueException(environment.getProperty("common.creditNoteId-duplicate"));
                }
            }
        }


        TransEventCreditNote transEventCreditNote = new TransEventCreditNote();
       
        transEventCreditNote.setTenantId(tenantId);
        transEventCreditNote.setTransactionEvent(transactionEvent.get());
        transEventCreditNote.setCreditNoteType(creditNoteType.get());
        transEventCreditNote.setStatus(transEventCreditNoteAddResource.getStatus());
        transEventCreditNote.setSyncTs(currentTimestamp);
        transEventCreditNote.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        transEventCreditNote.setCreatedDate(currentTimestamp);
        transEventCreditNoteRepository.saveAndFlush(transEventCreditNote);
        


    }
    
    @Override
    public void updateTransactionEventAndCreditNote(String tenantId, TransEventCreditNoteUpdateResource transEventCreditNoteAddResource, Long id) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        Optional<TransEventCreditNote> presentTransEventSubCode = transEventCreditNoteRepository.findById(id);
        
        if (!presentTransEventSubCode.get().getTransactionEvent().getId().equals(Long.parseLong(transEventCreditNoteAddResource.getTransEventId()))
                || !presentTransEventSubCode.get().getTransactionEvent().getCode().equals(transEventCreditNoteAddResource.getTransEventCode())) {
            throw new CodeUniqueException(environment.getProperty("eventId.cannotUpdate"));
        }
        
        if (presentTransEventSubCode.get().getVersion() != stringToLong(transEventCreditNoteAddResource.getVersion())) {
            throw new OptimisticLockException(environment.getProperty(invalidVersion), "version");
        }
        
        Optional<TransEventCreditNote> presentByEvent = transEventCreditNoteRepository.findByTransactionEventIdAndIdNotIn(Long.parseLong(transEventCreditNoteAddResource.getTransEventId()), id);
        
        if (presentByEvent.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("common.event-duplicate"));
        }
        
        
        Optional<TransactionEvent> transactionEvent = getTransactionEvents(Long.parseLong(transEventCreditNoteAddResource.getTransEventId()));

        
        if (!transactionEvent.isPresent()) {

            throw new CodeUniqueException(environment.getProperty("eventId.noRecord"));

        }
        if (!transactionEvent.get().getCode().equals(transEventCreditNoteAddResource.getTransEventCode())) {
            throw new CodeUniqueException(environment.getProperty("eventId.notMatch"));

        }
        if(!transactionEvent.get().getStatus().equalsIgnoreCase("ACTIVE")) {
        	throw new CodeUniqueException(environment.getProperty("eventId.invalidStatus"));
        }
        Optional<CreditNoteType> creditNoteType = creditNoteTypeRepository.findById(Long.parseLong(transEventCreditNoteAddResource.getCreditNoteId()));
        if (!creditNoteType.isPresent()) {
            throw new CodeUniqueException(environment.getProperty("creditNoteId.noRecord"));

        }
        if (!creditNoteType.get().getCode().equals(transEventCreditNoteAddResource.getCreditNoteCode())) {
            throw new CodeUniqueException(environment.getProperty("creditNoteId.notMatch"));
        }

        Optional<TransEventCreditNote> isPresentTransEventCreditNote = transEventCreditNoteRepository.findByCreditNoteTypeIdAndTransactionEventIdAndIdNotIn(Long.parseLong(transEventCreditNoteAddResource.getCreditNoteId()), Long.parseLong(transEventCreditNoteAddResource.getTransEventId()), id);
        if (isPresentTransEventCreditNote.isPresent()) {
            if (isPresentTransEventCreditNote.get().getTransactionEvent().getCode().equals(transEventCreditNoteAddResource.getTransEventCode())) {
                if (isPresentTransEventCreditNote.get().getStatus().equals(PassbookStatus.ACTIVE.toString())) {
                    throw new CodeUniqueException(environment.getProperty("common.creditNoteId-duplicate"));
                }
            }
        }


        TransEventCreditNote transEventCreditNote = presentTransEventSubCode.get();
       
        transEventCreditNote.setTenantId(tenantId);
        transEventCreditNote.setTransactionEvent(transactionEvent.get());
        transEventCreditNote.setCreditNoteType(creditNoteType.get());
        transEventCreditNote.setStatus(transEventCreditNoteAddResource.getStatus());
        transEventCreditNote.setSyncTs(currentTimestamp);
        transEventCreditNote.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        transEventCreditNote.setModifiedDate(currentTimestamp);
        transEventCreditNoteRepository.saveAndFlush(transEventCreditNote);
        
    }
    
    @Override
    public List<TransEventCreditNote> findAllCreditNote() {
        return transEventCreditNoteRepository.findAll();
    }
    
    @Override
    public Optional<TransEventCreditNote> findCreditNoteById(Long transCodeId) {
        Optional<TransEventCreditNote> transEventCreditNote = transEventCreditNoteRepository.findById(transCodeId);
        if (transEventCreditNote.isPresent())
            return Optional.ofNullable(transEventCreditNote.get());
        else
            return Optional.empty();
    }
    
    @Override
    public Optional<Collection<TransEventCreditNote>> findCreditNoteByStatus(String status) {
        Optional<Collection<TransEventCreditNote>> creditNoteType = transEventCreditNoteRepository.findByStatus(status);
        if (creditNoteType.isPresent())
            return Optional.ofNullable(creditNoteType.get());
        else
            return Optional.empty();
    }
    
    @Override
    public List<TransEventCreditNote> findByCreditNoteId(Long creditNoteId) {
        return transEventCreditNoteRepository.findByCreditNoteTypeId(creditNoteId);
    }

}
