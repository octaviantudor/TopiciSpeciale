package com.unibuc.shoppingcart.domain.entity;

import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "shopping_carts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @OneToMany(mappedBy="shoppingCart", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<ShoppingCartItem> items;
}
