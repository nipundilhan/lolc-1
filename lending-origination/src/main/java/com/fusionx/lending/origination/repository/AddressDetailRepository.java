package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AddressDetail;

@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {

	public List<AddressDetail> findByCustomerId(Long customerId);
	
	public List<AddressDetail> findByGuarantorId(Long guarantorId);
	
	public List<AddressDetail> findByLinkPersonId(Long linkPersonId);
	
	List<AddressDetail> findByGuarantorIdAndAddressTypeId(Long guarantorId, Long id);
	
	List<AddressDetail> findByGuarantorIdAndAddressTypeIdAndIdNotIn(Long guarantorId,Long addrsTypId, Long id);
}
