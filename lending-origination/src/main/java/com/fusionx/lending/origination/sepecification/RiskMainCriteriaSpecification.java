package com.fusionx.lending.origination.sepecification;

import com.fusionx.lending.origination.core.SearchCriteria;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RiskMainCriteriaSpecification implements Specification<RiskMainCriteria> {

    private final List<SearchCriteria> searchCriteriaList;
    private final SpecificationBehaviour specificationBehaviour;

    public RiskMainCriteriaSpecification() {
        this.searchCriteriaList = new ArrayList<>();
        this.specificationBehaviour = new SpecificationBehaviour();
    }

    public void add (SearchCriteria searchCriteria) {
        searchCriteriaList.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<RiskMainCriteria> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return specificationBehaviour.toPredicate(root, criteriaBuilder, searchCriteriaList);
    }
}
