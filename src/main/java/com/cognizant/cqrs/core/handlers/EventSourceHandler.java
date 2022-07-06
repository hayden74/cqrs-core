package com.cognizant.cqrs.core.handlers;

import com.cognizant.cqrs.core.domain.AggregateRoot;

public interface EventSourceHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
