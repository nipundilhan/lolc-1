package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.TcHeader;
/**
 * API Service related to tc-header.
 *
 * @author Achini
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        23-12-2021          -               -           Achini                  Created
 * <p>
 */
@Service
public interface TcHeaderService {
	public Page<TcHeader> findAll(Pageable pageable);

	public List<TcHeader> findByLeadId(Long leadId);
	
	public Optional<TcHeader> findDetailById(Long id);
	
	public TcHeader findActiveTcHeaderByLeadId(Long id);
}
