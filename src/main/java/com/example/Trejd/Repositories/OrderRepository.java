package com.example.Trejd.Repositories;

import com.example.Trejd.OrderTrejd;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository <OrderTrejd, Long> {
}
