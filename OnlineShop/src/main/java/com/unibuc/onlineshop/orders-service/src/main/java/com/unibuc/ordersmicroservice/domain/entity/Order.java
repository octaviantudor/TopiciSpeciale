package com.unibuc.ordersmicroservice.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Integer price;

    private String status;

    private LocalDate date;

    @OneToMany(mappedBy="order", cascade = CascadeType.PERSIST)
    private List<OrderedItem> orderedItems;
}
