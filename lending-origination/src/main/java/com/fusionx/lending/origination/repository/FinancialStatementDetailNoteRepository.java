package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.origination.domain.FinancialStatementDetailNote;
/**
 * API Service related to financial statement detail note
 *
 * @author NipunDilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        20-09-2021      -               FXL-784     Nipun Dilhan      Created
 * <p>
 */

@Repository
public interface FinancialStatementDetailNoteRepository   extends JpaRepository<FinancialStatementDetailNote, Long> {

	List<FinancialStatementDetailNote> findAllByFinancialStatementDetailId(Long financialStatementDetailId);
	
}
