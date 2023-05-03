package com.unibuc.gatewayservice.client;



import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public interface OrdersClient {

    @RequestMapping(method = RequestMethod.POST, value = "/order/{username}/{shoppingCartId}")
    void createOrder(@PathVariable(value = "shoppingCartId") String shoppingCartId,
                      @PathVariable(value = "username") String username
    );
}
