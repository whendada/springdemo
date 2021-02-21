package com.whendada.springdemo.web.repository;

import com.whendada.springdemo.web.domain.Order;

public interface OrderRepository {

    Order save(Order order);
}
