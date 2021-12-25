package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcStructuredPayment;
import com.fusionx.lending.product.domain.TcTaxDetail;

@Repository
public interface TcTaxDetailRepository  extends JpaRepository<TcTaxDetail, Long>
{

}
