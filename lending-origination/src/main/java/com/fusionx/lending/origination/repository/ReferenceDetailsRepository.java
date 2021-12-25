package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ReferenceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ReferenceDetailsRepository extends JpaRepository<ReferenceDetails, Long> {

	public List<ReferenceDetails> findByStatus(CommonStatus status);

	public List<ReferenceDetails> findByLeadInfoId(Long leadInfoId); 

	public Optional<ReferenceDetails> findById(Long id);

}
