package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.SystemGeneratedDocumentType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface SystemGeneratedDocumentTypeRepository extends JpaRepository<SystemGeneratedDocumentType, Long> {

	List<SystemGeneratedDocumentType> findByStatus(CommonStatus status);

	Optional<SystemGeneratedDocumentType> findByCode(String code);

	List<SystemGeneratedDocumentType> findByProductNameContaining(String name);

	List<SystemGeneratedDocumentType> findBySubProductNameContaining(String name);

	List<SystemGeneratedDocumentType> findByNameContaining(String name);

}
