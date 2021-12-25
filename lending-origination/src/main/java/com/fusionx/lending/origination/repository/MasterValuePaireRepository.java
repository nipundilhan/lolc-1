package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.MasterValuePaire;
import com.fusionx.lending.origination.domain.MasterValuePaire;
/**
 * Master value paire Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface MasterValuePaireRepository extends JpaRepository<MasterValuePaire, Long>{
	
	public List<MasterValuePaire> findByStatus(String status);

	public List<MasterValuePaire> findByNameContaining(String name);

	public Optional<MasterValuePaire> findByCode(String code);

	public Optional<MasterValuePaire> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	public Optional<MasterValuePaire> findByIdAndStatus(Long id, String status);
	
	public Optional<MasterValuePaire> findByIdAndCodeAndNameAndStatus(Long id,String code, String name,String status);
	
	public Boolean existsByCodeAndName(String code, String name);
	
	public Boolean existsByName(String name);
	
	public Boolean existsByCode(String code);
	
	public Optional<MasterValuePaire> findByNameAndIdNotIn(String name, Long id);
	
	public Optional<MasterValuePaire> findByCodeAndNameAndIdNotIn(String code, String name, Long id);

}
