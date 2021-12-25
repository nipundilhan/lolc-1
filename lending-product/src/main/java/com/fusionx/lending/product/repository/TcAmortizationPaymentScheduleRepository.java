package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcAmortizationPaymentSchedule;

@Repository
public interface TcAmortizationPaymentScheduleRepository    extends JpaRepository<TcAmortizationPaymentSchedule, Long> {
	
	List<TcAmortizationPaymentSchedule> findAllByTcAmortizationDetailId(Long tcAmortizationDetailId);

}
