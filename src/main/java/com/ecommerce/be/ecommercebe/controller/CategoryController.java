package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.CategoryDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CategoryResponse;
import com.ecommerce.be.ecommercebe.dto.response.CategoryResponse;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @PostMapping
    public ResponseData<CategoryResponse> createCategory(@Valid @RequestBody CategoryDTORequest request){
        logger.info("[CATEGORY_CONTROLLER][createCategory] Create Category {}", request.getCategoryName());
        CategoryResponse response = categoryService.createCategory(request);

        return new ResponseData<>("Category " + response.getCategoryName() + " is created", HttpStatus.CREATED.value(), response);
    }

    @GetMapping("/{id}")
    public ResponseData<CategoryResponse> getCategoryDetail(@PathVariable Long id){
        logger.info("[CATEGORY_CONTROLLER][getCategory] Get Category {}", id);
        CategoryResponse response = categoryService.getCategoryDetail(id);

        return new ResponseData<>("Get Category " + response.getCategoryName() + " successfully!", HttpStatus.ACCEPTED.value(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseData<CategoryResponse> deleteCategory(@PathVariable Long id){
        logger.info("[CATEGORY_CONTROLLER][deleteCategory] Delete Category {}", id);
        categoryService.deleteCategory(id);

        return new ResponseData<>("Delete Category " + id + " successfully!", HttpStatus.ACCEPTED.value(), null);
    }

    @PatchMapping("/{id}")
    public ResponseData<CategoryResponse> restoreCategory(@PathVariable Long id){
        logger.info("[CATEGORY_CONTROLLER][restoreCategory] Restore Category {}", id);
        CategoryResponse response = categoryService.restoreCategory(id);

        return new ResponseData<>("Restore Category " + response.getCategoryName() + " successfully!", HttpStatus.ACCEPTED.value(), response);
    }
}
