package com.example.Trejd.Repositories;

import com.example.Trejd.Trejd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrejdRepository extends CrudRepository <Trejd, Long> {

        List<Trejd> findById(Trejd trejd);

        @Query(value = "SELECT * FROM trejd ORDER BY id DESC LIMIT 1", nativeQuery = true)
        Trejd getLastTrejd();
}
