package com.unibuc.itemsservice.domain.mapper;

import com.unibuc.itemsservice.domain.dto.ReviewDto;
import com.unibuc.itemsservice.domain.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {


    public Review toEntity(ReviewDto reviewDto){
        return Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .build();
    }

    public ReviewDto toDto(Review review){
        return ReviewDto.builder()
                .id(review.getId().toString())
                .itemId(review.getItem().getId().toString())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}
