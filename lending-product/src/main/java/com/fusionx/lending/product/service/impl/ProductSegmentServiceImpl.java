package com.fusionx.lending.product.service.impl;
/**
 * Maintain Product Segment Service - Service implementation 
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
import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.ProductSegmentRepository;
import com.fusionx.lending.product.repository.SegmentsRepository;
import com.fusionx.lending.product.resources.AddProductSegmentRequest;
import com.fusionx.lending.product.resources.UpdateProductSegmentRequest;
import com.fusionx.lending.product.service.ProductSegmentService;
import com.fusionx.lending.product.service.ProductService;
import com.fusionx.lending.product.service.ValidationService;


@Component
@Transactional(rollbackFor = Exception.class)
public class ProductSegmentServiceImpl implements ProductSegmentService {

	@Autowired
	private ProductSegmentRepository repo;
	
    @Autowired
	private ProductService  productService;
	
	@Autowired
	private SegmentsRepository segmentsRepository;
	
	@Autowired
	private ValidationService validationService;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;

	@Override
	public boolean exists(long id) {
		try {
			return repo.existsById(id);
		} finally {

		}
	}
	private Long stringToLong(String str) {
		try {
			Long val = Long.parseLong(str);
			return val;
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public ProductSegment addProductSegment(String tenantId, String createdUser, AddProductSegmentRequest addProductSegmentRequest) {
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			
			ProductSegment newProductSegment = new ProductSegment();
			
			Optional<Product>  product = productService.findById(Long.parseLong(addProductSegmentRequest.getProductId()));			
			if(!product.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("productId-invalid"), ServiceEntity.PRODUCT, EntityPoint.PRODUCT_SEGMENT);

			Optional<Segments> segment = segmentsRepository.findByIdAndStatus(stringToLong(addProductSegmentRequest.getSegmentId()),CommonStatus.ACTIVE );
			if(!segment.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("segmentId.invalid"), ServiceEntity.SEGMENT, EntityPoint.PRODUCT_SEGMENT);
		
			if(!segment.get().getCode().equalsIgnoreCase(addProductSegmentRequest.getSegmentCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.SEGMENT, EntityPoint.PRODUCT_SEGMENT);
		
			if(repo.existsByProductIdAndSegmentsIdAndStatus(Long.parseLong(addProductSegmentRequest.getProductId()), 
					Long.parseLong(addProductSegmentRequest.getSegmentId()),
					CommonStatus.ACTIVE))
				throw new InvalidServiceIdException(environment.getProperty("psegmentId.duplicate"), ServiceEntity.SEGMENT, EntityPoint.PRODUCT_SEGMENT);
           
			newProductSegment.setProduct(product.get());
			newProductSegment.setSegments(segment.get());
			newProductSegment.setStatus(CommonStatus.valueOf(addProductSegmentRequest.getStatus()));
			newProductSegment.setCreatedDate(currentTimestamp);
			newProductSegment.setCreatedUser(createdUser);
			newProductSegment.setSyncTs(validationService.getCreateOrModifyDate());
			newProductSegment.setTenantId(tenantId);
			
			newProductSegment = repo.saveAndFlush(newProductSegment);		
		
			return newProductSegment;
	}
	@Override
	public Optional<ProductSegment> findById(long id) {
		 Optional<ProductSegment> objs = repo.findById(id);
		if (objs.isPresent()) {
			return Optional.ofNullable(objs.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override 
	public ProductSegment updateProductSegment(Long id, String modifiedUser, String tenantId,UpdateProductSegmentRequest updateProductSegmentRequest)  {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		ProductSegment productSegment = null;
		
		Optional<ProductSegment> isExists = repo.findById(id);
		if (isExists.isPresent()) {			
			ProductSegment oldProductSegment = isExists.get();
			
			Optional<Product>  product = null;
			Optional<Segments>  segments = null;
			
			if(repo.existsByProductIdAndSegmentsIdAndIdNotInAndStatus(id,
					stringToLong(updateProductSegmentRequest.getSegmentId()),oldProductSegment.getId(), CommonStatus.ACTIVE))
				throw new InvalidServiceIdException(environment.getProperty("psegmentId.duplicate"), ServiceEntity.SEGMENT, EntityPoint.PRODUCT_SEGMENT);

			if(!isExists.get().getVersion().equals(Long.parseLong(updateProductSegmentRequest.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty("common.version.mismatch"), ServiceEntity.VERSION, EntityPoint.PRODUCT_SEGMENT);
			
			
			if(!isExists.get().getProduct().getId().equals(Long.parseLong(updateProductSegmentRequest.getProductId()))) {
			product = productService.findById(Long.parseLong(updateProductSegmentRequest.getProductId()));			
			if(!product.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("productId-invalid"), ServiceEntity.PRODUCT, EntityPoint.PRODUCT_SEGMENT);
			}
		
			if(!isExists.get().getSegments().getId().equals(Long.parseLong(updateProductSegmentRequest.getSegmentId()))) {
			segments = segmentsRepository.findById(Long.parseLong(updateProductSegmentRequest.getSegmentId()));
			if(!segments.isPresent() || !segments.get().getCode().equalsIgnoreCase(updateProductSegmentRequest.getSegmentCode()))
				throw new InvalidServiceIdException(environment.getProperty("segmentId.invalid"), ServiceEntity.SEGMENT, EntityPoint.PRODUCT_SEGMENT);	
			oldProductSegment.setSegments(segments.get());
			}

			oldProductSegment.setModifiedDate(currentTimestamp);
			oldProductSegment.setModifiedUser(modifiedUser);
			oldProductSegment.setStatus(CommonStatus.valueOf(updateProductSegmentRequest.getStatus()));
			oldProductSegment.setVersion(stringToLong(updateProductSegmentRequest.getVersion()));
			oldProductSegment.setTenantId(tenantId);
			oldProductSegment.setSyncTs(validationService.getCreateOrModifyDate());
			
			productSegment = repo.saveAndFlush(oldProductSegment);	
			
		}
		return productSegment;
	}
	
	@Override
	public List<ProductSegment> findAll() {

		 Collection<ProductSegment> objs = repo.findAll();

		 return (List<ProductSegment>) objs;
	}
	
	@Override
	public List<ProductSegment> findByStatus(String status) {
		  
		 Collection<ProductSegment> objs = repo.findByStatus(CommonStatus.valueOf(status));

		 return (List<ProductSegment>) objs;
	}
	
	
	@Override
	public List<ProductSegment> findByProductId(Long productId) {
		
		 Collection<ProductSegment> productSegments = repo.findByProductId(productId);

		 return (List<ProductSegment>) productSegments;
	}

	

  
}
