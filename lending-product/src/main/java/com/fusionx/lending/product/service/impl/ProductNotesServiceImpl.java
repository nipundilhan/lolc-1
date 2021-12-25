package com.fusionx.lending.product.service.impl;
/**
 * Maintain ProductNotes- Service implementation 
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.ProductNotes;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ProductNotesRepository;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;
import com.fusionx.lending.product.service.ProductNotesService;
import com.fusionx.lending.product.service.ProductService;


@Component
@Transactional(rollbackFor = Exception.class)
public class ProductNotesServiceImpl implements ProductNotesService {

	@Autowired
	private ProductNotesRepository productNotesRepository;

	@Autowired
	private ProductService  productService;
	

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;

	@Override
	public Optional<ProductNotes> findById(long id) {
	
			Optional<ProductNotes> objs = productNotesRepository.findById(id);
			if (objs.isPresent()) {
				return Optional.ofNullable(objs.get());
			} else {
				return Optional.empty();
			}	
	}
	
	@Override
	public Optional<Collection<ProductNotes>> findAll() {
		return Optional.ofNullable(productNotesRepository.findAll());
	}
	
	@Override
	public boolean exists(long id) {

			return productNotesRepository.existsById(id);
	}
	
	private Long stringToLong(String str) {
		return Long.parseLong(str);
	}


	@Override
	public ProductNotes addProductNotes(String tenantId, String productId,AddNotesRequest addNotesRequest, String username) {
	
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

	    Optional<Product> product = productService.findById(Long.parseLong(productId));
		
		if(product.isPresent()) {
		
			ProductNotes newObj = new ProductNotes();
			newObj.setTenantId(tenantId);
			newObj.setProduct(product.get());
			newObj.setNotes(addNotesRequest.getNotes());
			newObj.setStatus(addNotesRequest.getStatus());
			newObj.setCreatedDate(currentTimestamp);
			newObj.setCreatedUser(username);	
			newObj.setVersion(stringToLong("0"));
			newObj = productNotesRepository.saveAndFlush(newObj);
			return newObj;
		
		}
		else{
			throw new ValidateRecordException(environment.getProperty("productId-invalid"),"message");
		}
	}

	@Override
	public ProductNotes updateProductNotes(String tenantId,UpdateNotesRequest updateNotesRequest, Long id, String username) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			
			  Optional<ProductNotes> isExists = productNotesRepository.findById(id);
				if (isExists.isPresent()) {
					
					ProductNotes oldObj = isExists.get();
								
					oldObj.setNotes(updateNotesRequest.getNotes());
					oldObj.setStatus(updateNotesRequest.getStatus());
					oldObj.setModifiedDate(currentTimestamp);
					oldObj.setModifiedUser(username);		
					oldObj.setVersion(stringToLong(updateNotesRequest.getVersion()));
					oldObj = productNotesRepository.saveAndFlush(oldObj);
					return oldObj;
				} else
					throw new OtherException(environment.getProperty("record-not-found"));
					
		}

	@Override
	public List<ProductNotes> findByProductId(Long productId) {
		  List<ProductNotes> productNotesList = (List<ProductNotes>) productNotesRepository.findByProductId(productId);
			
			return productNotesList;
	}


}