package com.app.habits.infrastructure.rest;

import com.app.habits.application.dto.CategoryDto;
import com.app.habits.application.dto.HabitTemplateDto;
import com.app.habits.application.usecase.ListCategoriesUseCase;
import com.app.habits.application.usecase.ListTemplatesByCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final ListCategoriesUseCase listCategories;
    private final ListTemplatesByCategoryUseCase listTemplates;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> categories() {
        return ResponseEntity.ok(listCategories.execute());
    }

    @GetMapping("/templates")
    public ResponseEntity<List<HabitTemplateDto>> templates(@RequestParam String categoryId) {
        return ResponseEntity.ok(listTemplates.execute(categoryId));
    }
}
