package com.app.habits.infrastructure.config;

import com.app.habits.application.usecase.DoCheckInUseCase;
import com.app.habits.application.usecase.GetCurrentWeekOverviewUseCase;
import com.app.habits.application.usecase.LevelingService;
import com.app.habits.application.usecase.ListCategoriesUseCase;
import com.app.habits.application.usecase.ListTemplatesByCategoryUseCase;
import com.app.habits.application.usecase.ListTodayWithStatusUseCase;
import com.app.habits.application.usecase.UndoTodayCheckInUseCase;
import com.app.habits.domain.port.CategoryRepositoryPort;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.domain.port.GamificationProgressPort;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;
import com.app.habits.infrastructure.persistence.adapter.CheckInRepositoryAdapter;
import com.app.habits.infrastructure.persistence.adapter.GamificationProgressRepositoryAdapter;
import com.app.habits.infrastructure.persistence.spring.CheckInJpaRepository;
import com.app.habits.infrastructure.persistence.spring.ProgressJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

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

    @Bean
    public CheckInRepositoryPort checkInRepositoryPort(CheckInJpaRepository jpa) {
        return new CheckInRepositoryAdapter(jpa);
    }

    @Bean
    public DoCheckInUseCase doCheckInUseCase(CheckInRepositoryPort c,
                                             HabitRepositoryPort h,
                                             GamificationProgressPort progressPort,
                                             LevelingService leveling,
                                             Clock clock) {
        return new DoCheckInUseCase(c, h, progressPort, leveling, clock);
    }

    @Bean
    public UndoTodayCheckInUseCase undoTodayCheckInUseCase(CheckInRepositoryPort c, HabitRepositoryPort h, Clock clock) {
        return new UndoTodayCheckInUseCase(c, h, clock);
    }

    @Bean
    public ListTodayWithStatusUseCase listTodayWithStatusUseCase(CheckInRepositoryPort c, Clock clock) {
        return new ListTodayWithStatusUseCase(c, clock);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public GetCurrentWeekOverviewUseCase getCurrentWeekOverviewUseCase(
            HabitRepositoryPort habitRepo,
            CheckInRepositoryPort checkInRepo,
            CategoryRepositoryPort categoryRepo,
            Clock clock
    ) {
        return new GetCurrentWeekOverviewUseCase(habitRepo, checkInRepo, clock, categoryRepo);
    }

    @Bean
    public GamificationProgressPort gamificationProgressPort(ProgressJpaRepository jpa) {
        return new GamificationProgressRepositoryAdapter(jpa);
    }

    @Bean
    public LevelingService levelingService() {
        return new LevelingService();
    }

}
