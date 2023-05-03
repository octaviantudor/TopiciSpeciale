package com.unibuc.itemsservice.service;

import antlr.StringUtils;
import com.unibuc.itemsservice.domain.dto.AddItemRequest;
import com.unibuc.itemsservice.domain.dto.ItemDto;
import com.unibuc.itemsservice.domain.dto.ReviewDto;
import com.unibuc.itemsservice.domain.entity.Item;
import com.unibuc.itemsservice.domain.mapper.ItemsMapper;
import com.unibuc.itemsservice.domain.mapper.ReviewMapper;
import com.unibuc.itemsservice.repository.ItemsRepository;
import com.unibuc.itemsservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsMapper itemsMapper;
    private final CategoryService categoryService;
    private final ItemsRepository itemsRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public List<ItemDto> findAllItems(){
        return itemsRepository.findAll()
                .stream()
                .map(itemsMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto findById(String itemId) {
        return itemsRepository.findById(Long.valueOf(itemId))
                .map(itemsMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Item not found for id: " + itemId));
    }

    public void createItem(AddItemRequest addItemRequest) {
        var item = Item.builder()
                .name(addItemRequest.getName())
                .description(addItemRequest.getDescription())
                .price(Integer.valueOf(addItemRequest.getPrice()))
                .quantity(Integer.valueOf(addItemRequest.getQuantity()))
                .category(categoryService.findCategoryByName(addItemRequest.getCategoryName()))
                .build();

        itemsRepository.save(item);

    }




    public ItemDto updateItem(String itemId, String quantity, String name, String price, String description, String categoryName) {
        var itemOptional = itemsRepository.findById(Long.valueOf(itemId));

        var item = itemOptional.orElseThrow(() -> new RuntimeException("Item not found for ID: " +itemId));

        if (!Objects.isNull(name) && !Objects.isNull(description) && !Objects.isNull(categoryName) && !Objects.isNull(price)){
            item.setQuantity(Integer.parseInt(quantity));
        }
        else{
            item.setQuantity(item.getQuantity() - Integer.parseInt(quantity));
        }

        if (!Objects.isNull(name)){
            item.setName(name);
        }

        if (!Objects.isNull(description)){
            item.setDescription(description);
        }

        if (!Objects.isNull(price)){
            item.setPrice(Integer.parseInt(price));
        }

        if (!Objects.isNull(categoryName)){
            item.setCategory(categoryService.findCategoryByName(categoryName));
        }

        return itemsMapper.toDto(itemsRepository.save(item));

    }



    public void createReviewForItem(ReviewDto reviewDto) {

        var itemOptional = itemsRepository.findById(Long.valueOf(reviewDto.getItemId()));
        var item = itemOptional.orElseThrow(() -> new RuntimeException("Item not found for ID: " + reviewDto.getItemId()));

        var review = reviewMapper.toEntity(reviewDto);

        item.addReview(review);

        reviewRepository.save(review);

    }

    public List<ReviewDto> getReviewsForItem(String itemId){
        return reviewRepository.findAllByItem_Id(Long.valueOf(itemId))
                .stream().map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }
}
