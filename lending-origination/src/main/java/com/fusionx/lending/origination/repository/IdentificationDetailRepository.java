package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.IdentificationDetail;

@Repository
public interface IdentificationDetailRepository  extends JpaRepository<IdentificationDetail, Long> {

	public List<IdentificationDetail> findByCustomerId(Long customerId);
	
	public List<IdentificationDetail> findByGuarantorId(Long guarantorId);
	
	public List<IdentificationDetail> findByLinkPersonId(Long linkPersonId);

	public List<IdentificationDetail> findByIdentificationNoContaining(String identificationNo);

	public Optional<IdentificationDetail> findByGuarantorIdAndIdentificationType(Long guarantorId, String type);
	
	public Optional<IdentificationDetail> findByIdAndStatus(Long id, String status);
	
	public Optional<IdentificationDetail> findByGuarantorIdAndIdentificationTypeAndIdentificationNo(Long id, String type, String number);
	
	List<IdentificationDetail> findByGuarantorIdAndIdentificationTypeId(Long guarantorId, Long id);
	
	List<IdentificationDetail> findByGuarantorIdAndIdentificationTypeIdAndIdNotIn(Long guarantorId, Long idnfTyeId, Long id);
}

