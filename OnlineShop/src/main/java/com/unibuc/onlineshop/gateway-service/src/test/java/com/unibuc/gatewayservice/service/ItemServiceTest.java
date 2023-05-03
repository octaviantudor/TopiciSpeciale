package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.domain.items.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemServiceClient itemServiceClient;

    @InjectMocks
    private ItemService itemService;


    @Test
    public void testFindAllItems() {
        // Mock the ItemServiceClient's getAllItemsRequest() method
        List<ItemDto> mockItemList = new ArrayList<>();
        mockItemList.add(ItemDto.builder().build());
        mockItemList.add(ItemDto.builder().build());
        when(itemServiceClient.getAllItemsRequest()).thenReturn(mockItemList);

        // Call the method being tested
        List<ItemDto> itemList = itemService.findAllItems(null);

        // Assert the result
        assertNotNull(itemList);
        assertEquals(2, itemList.size());
    }


    @Test
    public void testFindAllCategories() {
        // Mock the ItemServiceClient's getAllCategoriesRequest() method
        List<CategoryDto> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(new CategoryDto());
        mockCategoryList.add(new CategoryDto());
        when(itemServiceClient.getAllCategoriesRequest()).thenReturn(mockCategoryList);

        // Call the method being tested
        List<CategoryDto> categoryList = itemService.findAllCategories();

        // Assert the result
        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
    }

    @Test
    public void testCreateItem() {
        // Mock the ItemServiceClient's createItemRequest() method
        ResponseEntity<?> mockResponseEntity = ResponseEntity.ok().build();
        when(itemServiceClient.createItemRequest(AddItemRequest.builder().build())).thenReturn(ResponseEntity.ok().build());

        // Call the method being tested
        ResponseEntity<?> responseEntity = itemService.createItem(AddItemRequest.builder().build());

        // Assert the result
        assertNotNull(responseEntity);
        assertEquals(mockResponseEntity, responseEntity);
    }

    @Test
    public void testCreateCategory() {
        // Mock the ItemServiceClient's createCategoryRequest() method
        ResponseEntity<?> mockResponseEntity = ResponseEntity.ok().build();
        when(itemServiceClient.createCategoryRequest(AddCategoryRequest.builder().build())).thenReturn(ResponseEntity.ok().build());

        // Call the method being tested
        ResponseEntity<?> responseEntity = itemService.createCategory(AddCategoryRequest.builder().build());

        // Assert the result
        assertNotNull(responseEntity);
        assertEquals(mockResponseEntity, responseEntity);
    }

    @Test
    public void testCreateReviewForItem() {
        // Mock the ItemServiceClient's createReviewForItem() method
        when(itemServiceClient.createReviewForItem(new ReviewDto())).thenReturn(null);

        // Call the method being tested
        itemService.createReviewForItem(new ReviewDto());

        // No result to assert
    }

    @Test
    public void testGetReviewsForItem() {
        // Mock the ItemServiceClient's getAllReviewsForItem() method
        List<ReviewDto> mockReviewList = new ArrayList<>();
        mockReviewList.add(new ReviewDto());
        mockReviewList.add(new ReviewDto());
        when(itemServiceClient.getAllReviewsForItem("1")).thenReturn(mockReviewList);

        // Call the method being tested
        List<ReviewDto> reviewList = itemService.getReviewsForItem("1");

        // Assert the result
        assertNotNull(reviewList);
        assertEquals(2, reviewList.size());
    }
}