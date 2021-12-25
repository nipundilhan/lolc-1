package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.BrandHistory;
import com.fusionx.lending.product.repository.BrandHistoryRepository;
import com.fusionx.lending.product.service.BrandHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

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

@Component
@Transactional(rollbackFor = Exception.class)
public class BrandHistoryServiceImpl implements BrandHistoryService {

    private BrandHistoryRepository brandHistoryRepository;

    @Autowired
    public void setBrandHistoryRepository(BrandHistoryRepository brandHistoryRepository) {
        this.brandHistoryRepository = brandHistoryRepository;
    }

    @Override
    public void saveHistory(String tenantId,
                            Brand brand, String historyCreatedUser) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        BrandHistory brandHistory = new BrandHistory();

        brandHistory.setTenantId(tenantId);
        brandHistory.setBrandId(brand.getId());
        brandHistory.setCode(brand.getCode());
        brandHistory.setName(brand.getName());
        brandHistory.setDescription(brand.getDescription());
        brandHistory.setStatus(brand.getStatus());
        brandHistory.setCreatedDate(brand.getCreatedDate());
        brandHistory.setCreatedUser(brand.getCreatedUser());
        brandHistory.setModifiedDate(brand.getModifiedDate());
        brandHistory.setModifiedUser(brand.getModifiedUser());
        brandHistory.setVersion(brand.getVersion());
        brandHistory.setHistoryCreatedUser(historyCreatedUser);
        brandHistory.setHistoryCreatedDate(currentTimestamp);
        brandHistory.setSyncTs(currentTimestamp);

        brandHistoryRepository.saveAndFlush(brandHistory);
    }

}
