package com.unibuc.itemsservice.repository;

import com.unibuc.itemsservice.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByItem_Id(Long itemId);
}
