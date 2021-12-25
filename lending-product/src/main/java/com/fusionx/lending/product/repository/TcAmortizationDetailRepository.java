package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcAmortizationDetail;
import com.fusionx.lending.product.domain.TcHeader;

@Repository
public interface TcAmortizationDetailRepository   extends JpaRepository<TcAmortizationDetail, Long> {


}
