package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.repository.TcHeaderRepository;
import com.fusionx.lending.product.service.TcHeaderService;

/**
 * API Service related to tc-header.
 *
 * @author Achini
 * @version 1.0.0
 * @since 1.0.0
 *        <p>
 *        <br/>
 *        <br/>
 *        <b>Change History : </b> <br/>
 *        <br/>
 *        # Date		Story Point			Task No		Author			Description <br/>
 *        .....................................................................................................................<br/>
 *        1 23-12-2021	-					-			Achini			Created
 *        <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TcHeaderServiceImpl extends MessagePropertyBase implements TcHeaderService{

	@Autowired
	private TcHeaderRepository tcHeaderRepository;
	
	@Override
	public Page<TcHeader> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return tcHeaderRepository.findAll(pageable);
	}

	@Override
	public List<TcHeader> findByLeadId(Long leadId) {
		// TODO Auto-generated method stub
		return tcHeaderRepository.findByLeadId(leadId);
	}

	@Override
	public Optional<TcHeader> findDetailById(Long id) {
		// TODO Auto-generated method stub
		return tcHeaderRepository.findById(id);
	}

	@Override
	public TcHeader findActiveTcHeaderByLeadId(Long id) {
		// TODO Auto-generated method stub
		List<TcHeader> tcHeaderList=tcHeaderRepository.findByLeadIdAndStatus(id,CommonStatus.ACTIVE);
		return tcHeaderList!=null? tcHeaderList.get(0):null;
				
	}

}
