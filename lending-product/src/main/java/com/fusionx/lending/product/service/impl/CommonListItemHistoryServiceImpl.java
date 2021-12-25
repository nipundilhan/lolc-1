package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.CommonListItemHistory;
import com.fusionx.lending.product.repository.CommonListItemHistoryRepository;
import com.fusionx.lending.product.service.CommonListItemHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * API Service related to Common List Item.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class CommonListItemHistoryServiceImpl implements CommonListItemHistoryService {
    private CommonListItemHistoryRepository commonListItemHistoryRepository;

    @Autowired
    public void setCommonListItemHistoryRepository(CommonListItemHistoryRepository commonListItemHistoryRepository) {
        this.commonListItemHistoryRepository = commonListItemHistoryRepository;
    }

    @Override
    public void insertCommonListItemHistory(String tenantId, CommonListItem commonListItem) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        CommonListItemHistory commonListItemHistory = new CommonListItemHistory();

        commonListItemHistory.setTenantId(commonListItem.getTenantId());
        commonListItemHistory.setCommonListItemId(commonListItem.getId());
        commonListItemHistory.setCode(commonListItem.getCode());
        commonListItemHistory.setName(commonListItem.getName());
        commonListItemHistory.setReferenceCode(commonListItem.getReferenceCode());
        commonListItemHistory.setStatus(commonListItem.getStatus());
        commonListItemHistory.setAttribute1(commonListItem.getAttribute1());
        commonListItemHistory.setAttribute2(commonListItem.getAttribute2());
        commonListItemHistory.setAttribute3(commonListItem.getAttribute3());
        commonListItemHistory.setAttribute4(commonListItem.getAttribute4());
        commonListItemHistory.setAttribute5(commonListItem.getAttribute5());
        commonListItemHistory.setCreatedDate(commonListItem.getCreatedDate());
        commonListItemHistory.setCreatedUser(commonListItem.getCreatedUser());
        commonListItemHistory.setModifiedDate(commonListItem.getModifiedDate());
        commonListItemHistory.setModifiedUser(commonListItem.getModifiedUser());
        commonListItemHistory.setCommonListItemVersion(commonListItem.getVersion());
        commonListItemHistory.setSyncTs(currentTimestamp);
        commonListItemHistory.setHistoryCreatedDate(currentTimestamp);
        commonListItemHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        commonListItemHistoryRepository.saveAndFlush(commonListItemHistory);

    }

}
