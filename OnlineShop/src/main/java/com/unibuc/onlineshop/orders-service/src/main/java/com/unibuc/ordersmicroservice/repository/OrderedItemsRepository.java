package com.unibuc.ordersmicroservice.repository;

import com.unibuc.ordersmicroservice.domain.entity.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItem, Long> {

    @Query("SELECT oi FROM OrderedItem oi INNER JOIN Order o ON oi.order = o where o.userId =:userId")
    List<OrderedItem> findAllByUserId(@Param(value = "userId") String userId);
}
