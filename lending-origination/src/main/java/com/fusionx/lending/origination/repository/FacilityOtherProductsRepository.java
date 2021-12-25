package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityOtherProducts;

@Repository
public interface FacilityOtherProductsRepository extends JpaRepository<FacilityOtherProducts, Long> {

	List<FacilityOtherProducts> findByleadInfoId(Long id);
	Optional<FacilityOtherProducts> findByleadInfoIdAndProductCategoryCodeId(Long leadId, Long proId);
	Optional<FacilityOtherProducts> findByleadInfoIdAndProductCategoryCodeIdAndIdNotIn(Long leadId, Long proId, Long id);

}
