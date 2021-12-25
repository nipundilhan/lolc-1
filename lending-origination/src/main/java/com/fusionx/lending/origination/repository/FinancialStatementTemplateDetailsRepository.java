package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;

@Repository
public interface FinancialStatementTemplateDetailsRepository extends JpaRepository<FinancialStatementTemplateDetails, Long> {

	List<FinancialStatementTemplateDetails>  findAllByFinancialStatementAndParentId(FinancialStatementTemplate financialStatementTemplate , Long parentId);

    @Query("SELECT fstd FROM FinancialStatementTemplateDetails fstd WHERE fstd.financialStatement.id =:templateId AND fstd.parentId IN (:parentList) ")
    public List<FinancialStatementTemplateDetails> findAllByFinancialStatementTemplateAndParentList(
    	@Param("templateId")Long templateId,
		@Param("parentList")List<Long> parentList);
    
    List<FinancialStatementTemplateDetails>  findByFinancialStatementId(Long financialStatementId);
}
