package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ContactDetail;

@Repository
public interface ContactDetailRepository extends JpaRepository<ContactDetail, Long> {

	public List<ContactDetail> findByCustomerId(Long customerId);
	
	public List<ContactDetail> findByGuarantorId(Long guarantorId);
	
	public List<ContactDetail> findByLinkPersonId(Long linkPersonId);

	List<ContactDetail> findByGuarantorIdAndContactTypeId(Long guarantorId, Long id);
	
	List<ContactDetail> findByGuarantorIdAndContactTypeIdAndIdNotIn(Long guarantorId, Long contTypId,Long id);
	
	public Optional<ContactDetail> findByGuarantorIdAndContactTypeAndContactNo(Long guarantorId, String type, String number);
}
