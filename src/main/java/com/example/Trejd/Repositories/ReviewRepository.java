package com.example.Trejd.Repositories;

import com.example.Trejd.Review;
import com.example.Trejd.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository <Review, Long> {

    @Query(value = "SELECT * FROM REVIEW WHERE customer_id =?1 AND written_by_performer =b'1' " +
            "OR performer_id =?1 AND written_by_performer = b'0' ",nativeQuery = true)
    List<Review> findReviewsOnUser(Long userId);

    List<Review> findAllByCustomerId(Long id);
}
