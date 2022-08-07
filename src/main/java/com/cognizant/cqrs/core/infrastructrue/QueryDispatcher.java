package com.cognizant.cqrs.core.infrastructrue;

import com.cognizant.cqrs.core.domain.BaseEntity;
import com.cognizant.cqrs.core.queries.BaseQuery;
import com.cognizant.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> Page send(BaseQuery query, Pageable pageable);
}
