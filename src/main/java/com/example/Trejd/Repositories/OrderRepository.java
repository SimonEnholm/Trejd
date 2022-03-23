package com.example.Trejd.Repositories;

import com.example.Trejd.OrderTrejd;
import com.example.Trejd.Skill;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.Order;
import java.util.List;

public interface OrderRepository extends CrudRepository <OrderTrejd, Long> {


}
