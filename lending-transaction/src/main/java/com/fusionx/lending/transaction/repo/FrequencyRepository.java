package com.fusionx.lending.transaction.repo;

/**
 * Standing Order Type service
 *
 * @author Nisalak
 * @Dat 05-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   05-08-2019   FX-1505       FX-1526    Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Long> {


}
