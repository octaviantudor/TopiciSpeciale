package com.unibuc.gatewayservice.controller;



import com.unibuc.gatewayservice.domain.items.*;
import com.unibuc.gatewayservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems(@RequestParam(value = "itemId", required = false) String itemId){
        log.info("Attempting to get all items");
        return itemService.findAllItems(itemId);
    }
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        log.info("Attempting to get all items");
        return itemService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody AddItemRequest addItemRequest){
        log.info("Attempting to create item with name: {}", addItemRequest.getName());
        return itemService.createItem(addItemRequest);
    }
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody AddCategoryRequest addCategoryRequest){
        log.info("Attempting to create category with name: {}", addCategoryRequest.getName());
        return itemService.createCategory(addCategoryRequest);
    }


    @PostMapping("/{itemId}/reviews")
    public void createReviewForItem(@PathVariable(name = "itemId") String itemId,
                                    @RequestBody ReviewDto reviewDto){

        log.info("Attempting to create a review for itemId: {}", itemId);
        this.itemService.createReviewForItem(reviewDto);


    }

    @GetMapping("/{itemId}/reviews")
    public List<ReviewDto> getReviewsForItem(@PathVariable(name = "itemId") String itemId){

        log.info("Attempting to get reviews for itemId: {}", itemId);
        return this.itemService.getReviewsForItem(itemId);
    }

    @GetMapping("/update")
    public ItemDto updateItem(@RequestParam(name = "itemId") String itemId,
                              @RequestParam(name = "quantity") String quantity,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "price") String price,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "categoryName") String categoryName
    ) {
        log.info("Attempting to update item quantity for ID: {}", itemId);
        return itemService.updateItem(itemId, quantity, name, price, description, categoryName);
    }

}
