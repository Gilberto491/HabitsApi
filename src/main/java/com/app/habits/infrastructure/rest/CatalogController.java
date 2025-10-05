package com.app.habits.infrastructure.rest;

import com.app.habits.application.dto.CategoryDto;
import com.app.habits.application.dto.HabitTemplateDto;
import com.app.habits.application.usecase.ListCategoriesUseCase;
import com.app.habits.application.usecase.ListTemplatesByCategoryUseCase;
import com.app.habits.domain.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<CategoryDto>> categories(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(listCategories.execute());
    }

    @GetMapping("/templates")
    public ResponseEntity<List<HabitTemplateDto>> templates(@RequestParam String categoryId, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(listTemplates.execute(categoryId));
    }
}
