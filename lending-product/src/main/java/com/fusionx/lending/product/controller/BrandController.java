package com.fusionx.lending.product.controller;


import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Brand.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2020      -                            		Menuka Jayasinghe          Created
 * <p>
 */
@RestController
@RequestMapping(value = "/brand")
@CrossOrigin(origins = "*")
public class BrandController extends MessagePropertyBase {

    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * Returns the all records
     *
     * @param tenantId the tenant id
     * @return the all records
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllBrand(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<Brand> brand = brandService.getAll();
        int size = brand.size();
        if (size > 0) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the record by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the Brand Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getBrandById(@PathVariable(value = "tenantId") String tenantId,
                                               @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<Brand> isPresentBrand = brandService.getById(id);
        if (isPresentBrand.isPresent()) {
            return new ResponseEntity<>(isPresentBrand.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the Brand by its code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the Brand
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getBrandByCode(@PathVariable(value = "tenantId") String tenantId,
                                                 @PathVariable(value = "code") String code) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<Brand> isPresentBrand = brandService.getByCode(code);
        if (isPresentBrand.isPresent()) {
            return new ResponseEntity<>(isPresentBrand.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the Brand by its name
     *
     * @param tenantId the tenant id
     * @param name     the name of the brand
     * @return the brand
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getBrandByName(@PathVariable(value = "tenantId") String tenantId,
                                                 @PathVariable(value = "name") String name) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<Brand> isPresentBrand = brandService.getByName(name);
        if (!isPresentBrand.isEmpty()) {
            return new ResponseEntity<>(isPresentBrand, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the brands by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the brands
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getBrandByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<Brand> isPresentBrand = brandService.getByStatus(status);
            int size = isPresentBrand.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentBrand, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the given brand object
     *
     * @param tenantId          the tenant id
     * @param commonAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addBrand(@PathVariable(value = "tenantId") String tenantId,
                                           @Valid @RequestBody CommonAddResource commonAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        Brand brand = brandService.addBrand(tenantId, commonAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(brand.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given brand record
     * @param tenantId the tenant id
     * @param id the id
     * @param commonUpdateResource object to update
     * @return the
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateBrand(@PathVariable(value = "tenantId") String tenantId,
                                              @PathVariable(value = "id") Long id,
                                              @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Brand> isPresentBrand = brandService.getById(id);
        if (isPresentBrand.isPresent()) {
            Brand brand = brandService.updateBrand(tenantId, id, commonUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), brand.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
