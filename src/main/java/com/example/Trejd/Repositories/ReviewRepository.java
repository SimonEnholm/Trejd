package com.example.Trejd.Repositories;

import com.example.Trejd.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository <Review, Long> {


    List<Review> findAllByCustomerId(Long id);
}
