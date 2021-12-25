package com.fusionx.lending.transaction.service.impl;


import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.domain.CreditNoteTransactionType;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.PostingType;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.repo.CreditNoteTransactionTypeRepository;
import com.fusionx.lending.transaction.repo.CreditNoteTypeRepository;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeUpdateResource;
import com.fusionx.lending.transaction.service.CreditNoteTransactionTypeService;
import com.fusionx.lending.transaction.service.ValidationService;


@Service
@Transactional(rollbackFor = Exception.class)
public class CreditNoteTransactionTypeServiceImpl  extends MessagePropertyBase  implements CreditNoteTransactionTypeService{
	
	
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
	private ValidationService validationService;
	@Autowired
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}
	

    private BankTransactionSubCodeRepository bankTransactionSubCodeRepository;
	@Autowired
	public void setBankTransactionSubCodeRepository(BankTransactionSubCodeRepository bankTransactionSubCodeRepository) {
		this.bankTransactionSubCodeRepository = bankTransactionSubCodeRepository;
	}
	
    private CreditNoteTransactionTypeRepository creditNoteTransactionTypeRepository;
	@Autowired
	public void setCreditNoteTransactionTypeRepository(CreditNoteTransactionTypeRepository creditNoteTransactionTypeRepository) {
		this.creditNoteTransactionTypeRepository = creditNoteTransactionTypeRepository;
	}
	

    private CreditNoteTypeRepository creditNoteTypeRepository;
	@Autowired
	public void setCreditNoteTypeRepository(CreditNoteTypeRepository creditNoteTypeRepository) {
		this.creditNoteTypeRepository = creditNoteTypeRepository;
	}
	
	@Override
	public List<CreditNoteTransactionType> getAll() {
		return creditNoteTransactionTypeRepository.findAll();	
	}
	
	
	@Override
	public Optional<CreditNoteTransactionType> getById(Long id) {
		return creditNoteTransactionTypeRepository.findById(id);	
	}
	
	
	@Override
	public List<CreditNoteTransactionType> getByStatus(String status) {
		return creditNoteTransactionTypeRepository.findByStatus(CommonStatus.valueOf(status));	
	}
	
	
	@Override
	public List<CreditNoteTransactionType> getByCreditNoteType(Long id) {
		return creditNoteTransactionTypeRepository.findByCreditNoteTypeId(id);	
	}
	
	@Override
	public Long create(CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource,String user , String tenantId) {
		
				
		Optional<CreditNoteType> creditNoteTypeOptional = creditNoteTypeRepository.findById(validationService.stringToLong(creditNoteTransactionTypeAddResource.getCreditNoteTypeId()));
		if(!creditNoteTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "creditNoteTypeId");
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(creditNoteTypeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_STATUS), "creditNoteTypeId");
		}
		
		
		CreditNoteTransactionType creditNoteTransactionType = 	new CreditNoteTransactionType();
				
		creditNoteTransactionType =createIntermediateCreditNoteTransactionType(creditNoteTransactionType ,creditNoteTransactionTypeAddResource);
		
		Optional<BankTransactionSubCode> bankTransactionSubCodeOptional = bankTransactionSubCodeRepository.findById(validationService.stringToLong(creditNoteTransactionTypeAddResource.getTransactionSubCodeId()));
		if(!bankTransactionSubCodeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "transactionSubCodeId");
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(bankTransactionSubCodeOptional.get().getStatus().toString())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_STATUS), "transactionSubCodeId");
		}
		if(!(bankTransactionSubCodeOptional.get().getSubCode()).equals(creditNoteTransactionTypeAddResource.getTransactionSubCode())) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "transactionSubCode");
		}
		
		
		creditNoteTransactionType.setBankTransactionSubCode(bankTransactionSubCodeOptional.get());
		
		creditNoteTransactionType.setTenantId(tenantId);
		creditNoteTransactionType.setCreditNoteType(creditNoteTypeOptional.get());
		creditNoteTransactionType.setCreatedDate(validationService.getCreateOrModifyDate());
		creditNoteTransactionType.setCreatedUser(user);
		creditNoteTransactionType = creditNoteTransactionTypeRepository.save(creditNoteTransactionType);
	
		return creditNoteTransactionType.getId();
	}
	
	@Override
	public Long update(CreditNoteTransactionTypeUpdateResource resource ,  Long creditNoteTransactionTypeId , String user , String tenantId) {
		
		Optional<CreditNoteTransactionType> creditNoteTransactionTypeOptional = creditNoteTransactionTypeRepository.findById(creditNoteTransactionTypeId);
		if(!creditNoteTransactionTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "creditNoteTransactionTypeId");
		}
		
		CreditNoteTransactionType creditNoteTransactionType = creditNoteTransactionTypeOptional.get();
		if(!(creditNoteTransactionType.getVersion()).equals(validationService.stringToLong(resource.getVersion()))) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-version"), "version");
		}
		
		if(!(resource.getTransactionSubCodeId()).equals(creditNoteTransactionType.getBankTransactionSubCode().getId().toString())) {
			throw new ValidateRecordException("cannot update", "transactionSubCodeId");
		}
		if(!(resource.getTransactionSubCode()).equals(creditNoteTransactionType.getBankTransactionSubCode().getSubCode().toString())) {
			throw new ValidateRecordException("cannot update", "transactionSubCode");
		}
		
		
		Optional<CreditNoteType> creditNoteTypeOptional = creditNoteTypeRepository.findById(validationService.stringToLong(resource.getCreditNoteTypeId()));
		if(!creditNoteTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "creditNoteTypeId");
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(creditNoteTypeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_STATUS), "creditNoteTypeId");
		}
	
//		if(!(creditNoteTransactionType.getCreditNoteType().getId().toString()).equals(resource.getCreditNoteTypeId())) {
//			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "creditNoteTypeId");
//		}


		CreditNoteTransactionTypeAddResource resourceConvert = modelMapper.map(resource, CreditNoteTransactionTypeAddResource.class);
	
		creditNoteTransactionType =createIntermediateCreditNoteTransactionType(creditNoteTransactionType ,resourceConvert);
		
		creditNoteTransactionType.setTenantId(tenantId);
		creditNoteTransactionType.setModifiedDate(validationService.getCreateOrModifyDate());
		creditNoteTransactionType.setModifiedUser(user);
		
		creditNoteTransactionTypeRepository.save(creditNoteTransactionType);

		return creditNoteTransactionType.getId();	
	}
	
	public CreditNoteTransactionType createIntermediateCreditNoteTransactionType(CreditNoteTransactionType cntt ,CreditNoteTransactionTypeAddResource resource) {
		CreditNoteTransactionType creditNoteTransactionType = 	cntt;
		

		creditNoteTransactionType.setPostingType(PostingType.valueOf(resource.getPostingType()));
		creditNoteTransactionType.setStatus(CommonStatus.valueOf(resource.getStatus()));
		creditNoteTransactionType.setIsDebitBalanceEnable(CommonStatus.valueOf(resource.getIsDebitBalanceEnable()));

		creditNoteTransactionType.setSyncTs(validationService.getCreateOrModifyDate());
		
		return creditNoteTransactionType;		
	}
	
	
	

}
