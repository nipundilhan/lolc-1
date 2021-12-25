package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ReferenceDetailsContactInfo;

@Repository
public interface ReferenceDetailsContactInfoRepository extends JpaRepository<ReferenceDetailsContactInfo, Long> {

	List<ReferenceDetailsContactInfo> findByReferenceDetailsId(Long referenceDetailsId);

}
