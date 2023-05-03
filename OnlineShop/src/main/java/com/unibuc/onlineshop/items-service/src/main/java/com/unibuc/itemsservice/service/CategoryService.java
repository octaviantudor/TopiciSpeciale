package com.unibuc.itemsservice.service;

import com.unibuc.itemsservice.domain.dto.AddCategoryRequest;
import com.unibuc.itemsservice.domain.dto.CategoryDto;
import com.unibuc.itemsservice.domain.entity.Category;
import com.unibuc.itemsservice.domain.mapper.CategoryMapper;
import com.unibuc.itemsservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> findAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public Category findCategoryByName(String name){
        return categoryRepository.findCategoryByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Category with specified name does not exist!!"));
    }

    public void createItem(AddCategoryRequest addCategoryRequest) {
        var category = Category.builder()
                .name(addCategoryRequest.getName())
                .description(addCategoryRequest.getDescription())
                .build();

        categoryRepository.save(category);
    }
}
