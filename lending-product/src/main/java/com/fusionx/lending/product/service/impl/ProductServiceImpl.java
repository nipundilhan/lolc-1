package com.fusionx.lending.product.service.impl;
/**
 * Product  Service - Product Service
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.repository.BrandRepository;
import com.fusionx.lending.product.repository.ProductGroupRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.SegmentsRepository;
import com.fusionx.lending.product.resources.AddMainProduct;
import com.fusionx.lending.product.resources.UpdateProductRequest;
import com.fusionx.lending.product.service.ProductHistoryService;
import com.fusionx.lending.product.service.ProductSegmentService;
import com.fusionx.lending.product.service.ProductService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductHistoryService productHistoryService;
	
	@Autowired
	private ProductSegmentService productSegmentService;

	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;

	@Override
	public boolean exists(long id) {
		try {
			return productRepository.existsById(id);
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
	
//	@Override
//	public Page<Product> findAll(Pageable pageable) {
//		return 	productRepository.findAll(pageable);
//	}
	
	@Override
	public List<Product> findAll() {
		List<Product> products = productRepository.findAll();
		List<Product> productsAll = getProductInfo(products);		    
		  return productsAll;
	}
	
	@Override
	public List<Product> findByStatus(String status) {
		  List<Product> products = productRepository.findByStatus(CommonStatus.valueOf(status));  
		  List<Product> productsAll = getProductInfo(products);		    
		  return productsAll;
	}

	@Override
	public Product addProduct(String tenantId,String createdUser,AddMainProduct addProductRequest) {
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			
			Optional<Product> isExists = productRepository.findByProductCodeAndStatus(addProductRequest.getProductCode(),  CommonStatus.ACTIVE);
			if (isExists.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("productCode.duplicate"), ServiceEntity.CODE, EntityPoint.MAIN_PRODUCT);
			  			
			Optional<Brand>  brand = brandRepository.findById(stringToLong(addProductRequest.getBrandId()));
			if(!brand.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("brandId.invalid"), ServiceEntity.BRAND, EntityPoint.MAIN_PRODUCT);
			
			if(!brand.get().getName().contentEquals(addProductRequest.getBrandName()) || !brand.get().getStatus().equals(CommonStatus.ACTIVE))
			throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.BRAND, EntityPoint.MAIN_PRODUCT);
		
			
			Optional<ProductGroup>  productGroup = productGroupRepository.findById(stringToLong(addProductRequest.getProductGroupId()));
			if(!productGroup.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("ProductGroup.invalid"), ServiceEntity.PRODUCT_GROUP, EntityPoint.MAIN_PRODUCT);
			
				if(!productGroup.get().getName().contentEquals(addProductRequest.getProductGroupName()) || !productGroup.get().getStatus().equals(CommonStatus.ACTIVE))
				throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.PRODUCT_GROUP, EntityPoint.MAIN_PRODUCT);
			
				Product newProduct = new Product();
				newProduct.setBrand(brand.get());
				newProduct.setProductGroup(productGroup.get());
				newProduct.setProductCode(addProductRequest.getProductCode());
				newProduct.setProductName(addProductRequest.getProductName());
				newProduct.setDescription(addProductRequest.getDescription());
		     	newProduct.setStatus(CommonStatus.valueOf(addProductRequest.getStatus()));
		     	newProduct.setCreatedDate(currentTimestamp);
		     	newProduct.setCreatedUser(createdUser);
		     	newProduct.setSyncTs(validationService.getCreateOrModifyDate());
		     	newProduct.setTenantId(tenantId);
				
		     	newProduct = productRepository.saveAndFlush(newProduct);	

			return newProduct;
			
	}


	@Override
	public Optional<Product> findById(long id) {
		Optional<Product> objs = productRepository.findById(id);
		List<ProductSegment> oductSegmentList= new ArrayList<>();
		if (objs.isPresent()) {
	    	  oductSegmentList = productSegmentService.findByProductId(objs.get().getId());
	    	  objs.get().setProdoctSegments(oductSegmentList);
	    	  return  Optional.ofNullable(objs.get());
	    	  
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Product updateProduct( String tenantId, String modifiedUser, UpdateProductRequest updateProductRequest, Long id) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
			Optional<Product> isExists = productRepository.findById(id);

			if (isExists.isPresent()) {
				
				if(!isExists.get().getVersion().equals(Long.parseLong(updateProductRequest.getVersion())))
					throw new InvalidServiceIdException(environment.getProperty("common.version.mismatch"), ServiceEntity.VERSION, EntityPoint.MAIN_PRODUCT);
				
				Optional<Brand>  brand = brandRepository.findById(stringToLong(updateProductRequest.getBrandId()));
				if(!brand.isPresent())
					throw new InvalidServiceIdException(environment.getProperty("brandId.invalid"), ServiceEntity.BRAND, EntityPoint.MAIN_PRODUCT);
				
				if(!brand.get().getName().contentEquals(updateProductRequest.getBrandName()) || !brand.get().getStatus().equals(CommonStatus.ACTIVE))
				throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.BRAND, EntityPoint.MAIN_PRODUCT);
			
				
				Optional<ProductGroup>  productGroup = productGroupRepository.findById(stringToLong(updateProductRequest.getProductGroupId()));
				if(!productGroup.isPresent())
					throw new InvalidServiceIdException(environment.getProperty("ProductGroup.invalid"), ServiceEntity.PRODUCT_GROUP, EntityPoint.MAIN_PRODUCT);
				
					if(!productGroup.get().getName().contentEquals(updateProductRequest.getProductGroupName()) || !productGroup.get().getStatus().equals(CommonStatus.ACTIVE))
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.PRODUCT_GROUP, EntityPoint.MAIN_PRODUCT);
				
				if (updateProductRequest.getProductCode().equals(isExists.get().getProductCode())) {
				
					Product oldProduct= isExists.get();
					productHistoryService.addProductHistory(tenantId, oldProduct, modifiedUser);	
					oldProduct.setBrand(brand.get());
					oldProduct.setProductGroup(productGroup.get());
					oldProduct.setProductName(updateProductRequest.getProductName());
					oldProduct.setDescription(updateProductRequest.getDescription());
					oldProduct.setModifiedDate(currentTimestamp);
					oldProduct.setModifiedUser(modifiedUser);
					oldProduct.setStatus(CommonStatus.valueOf(updateProductRequest.getStatus()));
					oldProduct.setVersion(stringToLong(updateProductRequest.getVersion()));
					oldProduct.setSyncTs(validationService.getCreateOrModifyDate());
					oldProduct.setTenantId(tenantId); 				
				
					oldProduct = productRepository.saveAndFlush(oldProduct);
		    	
				return oldProduct;
	
				} else
					throw new InvalidServiceIdException(environment.getProperty("productCode.update"), ServiceEntity.CODE, EntityPoint.MAIN_PRODUCT);
				
			} else
				throw new OtherException(environment.getProperty("record-not-found"));
	}

	public List<Product> getProductInfo(List<Product> products){
		
		List<Product> productsAll = new ArrayList<>();
		List<ProductSegment> oductSegmentList= new ArrayList<>();

	    for (Product product :products) { 
	    	 oductSegmentList = productSegmentService.findByProductId(product.getId());
	    	 product.setProdoctSegments(oductSegmentList);
	    	productsAll.add(product);
	    }
		
		return productsAll;
	}
	
	@Override
	public List<Product> findByBrandId(Long brandId) {
		    List<Product> products =  (List<Product>) productRepository.findByBrandId(brandId);	 
		    List<Product> productsAll = new ArrayList<>();
			List<ProductSegment> oductSegmentList= new ArrayList<>();
		    for (Product product :products) { 
		    	  oductSegmentList = productSegmentService.findByProductId(product.getId());
		    	  product.setProdoctSegments(oductSegmentList);
		    	productsAll.add(product);
		    
		    }
			return productsAll;
	}
	
	@Override
	public List<Product> findByName(String name) {
		  List<Product> products =  (List<Product>) productRepository.findByProductNameContaining(name);
		  List<Product> productAll ;
		  productAll = getProductInfo(products);
		  return productAll;
	}

	@Override
	public Page<Product> searchProductList(String searchq, String name, String code, String status, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		if(status==null || status.isEmpty())
			status=null;
		
		return productRepository.searchProductList(searchq, name, code,status, pageable);
	}

  
}
