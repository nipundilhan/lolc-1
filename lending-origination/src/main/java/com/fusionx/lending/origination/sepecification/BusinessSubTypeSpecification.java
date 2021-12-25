package com.fusionx.lending.origination.sepecification;

import com.fusionx.lending.origination.core.SearchCriteria;
import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.enums.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BusinessSubTypeSpecification implements Specification<BusinessSubType> {

    private List<SearchCriteria> searchCriteriaList;

    public BusinessSubTypeSpecification() {
        searchCriteriaList = new ArrayList<>();
    }

    public void add (SearchCriteria searchCriteria) {
        searchCriteriaList.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<BusinessSubType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria searchCriteria: searchCriteriaList) {
            SearchOperation searchOperation = searchCriteria.getOperation();
            switch (searchOperation) {
                case EQUAL:
                    predicates.add(criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
                            "%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case GREATER_THAN:
                    predicates.add(criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
                    break;
                case LESS_THAN:
                    predicates.add(criteriaBuilder.lessThan(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
                    break;
                case NOT_EQUAL:
                    predicates.add(criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
                    break;
                case LIKE_START:
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
                            "%" + searchCriteria.getValue().toString().toLowerCase()));
                    break;
                case LIKE_END:
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
                            searchCriteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case IN:
                    predicates.add(criteriaBuilder.in(root.get(searchCriteria.getKey())).value(searchCriteria.getValue()));
                    break;
                case NOT_IN:
                    predicates.add(criteriaBuilder.not(root.get(searchCriteria.getKey())).in(searchCriteria.getValue()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
