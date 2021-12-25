package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;


public interface FeatureBenifitTemplatePendingRepository   extends JpaRepository<FeatureBenifitTemplatePending, Long> {

	public List<FeatureBenefitTemplateItemPending> findAllByFeatureBenifitTemplateAndApproveStatus
								(FeatureBenifitTemplate featureBenefitTemplate,CommonApproveStatus approveStatus);
	
	@Query("SELECT up FROM FeatureBenifitTemplatePending up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.status) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.approveStatus) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<FeatureBenifitTemplatePending> searchPending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);
	

}
