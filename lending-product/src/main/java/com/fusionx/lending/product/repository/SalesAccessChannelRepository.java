package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;
/**
 * Sales Access Channel service
 * @author 	Piyumi
 * @Dat     07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface SalesAccessChannelRepository extends JpaRepository<SalesAccessChannel, Long>{
	
	public List<SalesAccessChannel> findByStatus(CommonStatus status);

	public List<SalesAccessChannel> findByNameContaining(String name);

	public Optional<SalesAccessChannel> findByCode(String code);
	
	public Optional<SalesAccessChannel> findByIdAndNameAndStatus(Long id,String name,CommonStatus status);

}
