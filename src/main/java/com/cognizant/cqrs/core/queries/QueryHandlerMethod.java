package com.cognizant.cqrs.core.queries;

import com.cognizant.cqrs.core.domain.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    Page<BaseEntity> handle(T query, Pageable pageable);
}
