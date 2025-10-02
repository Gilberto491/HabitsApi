package com.app.habits.infrastructure.config;

import com.app.habits.application.usecase.ListCategoriesUseCase;
import com.app.habits.application.usecase.ListTemplatesByCategoryUseCase;
import com.app.habits.domain.port.CategoryRepositoryPort;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(CategoryRepositoryPort repo) {
        return new ListCategoriesUseCase(repo);
    }

    @Bean
    public ListTemplatesByCategoryUseCase listTemplatesByCategoryUseCase(HabitTemplateRepositoryPort repo) {
        return new ListTemplatesByCategoryUseCase(repo);
    }
}
