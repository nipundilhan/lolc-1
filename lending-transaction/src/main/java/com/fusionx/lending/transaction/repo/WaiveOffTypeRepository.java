package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaiveOffTypeRepository extends JpaRepository<WaiveOffType, Long> {

    public List<WaiveOffType> findByStatus(CommonStatus status);

    public List<WaiveOffType> findByCode(String code);

    public Optional<WaiveOffType> findByCodeAndStatus(String code, CommonStatus status);

    public List<WaiveOffType> findByNameContaining(String name);

    public Optional<WaiveOffType> findByIdAndStatus(Long id, CommonStatus status);

    Optional<WaiveOffType> findByCodeAndStatusAndIdNotIn(String code, CommonStatus status, Long id);

}
