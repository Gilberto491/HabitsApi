package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface CheckInJpaRepository extends JpaRepository<CheckInEntity, String> {

    boolean existsByHabitIdAndCheckedOn(String habitId, LocalDate day);

    Integer deleteByHabitIdAndCheckedOn(String habitId, LocalDate checkedOn);

    interface TodayRow {
        String getId();

        String getHabitId();

        String getName();

        Boolean getDone();

        OffsetDateTime getCheckedAt();
    }

    @Query("""
                select h.id as id,
                       h.id as habitId,
                       h.name as name,
                       (case when c.id is not null then true else false end) as done,
                       c.checkedAt as checkedAt
                  from HabitEntity h
                  left join CheckInEntity c
                         on c.habitId = h.id and c.checkedOn = :today
                 where h.userId = :userId and h.active = true
                 order by h.name
            """)
    List<TodayRow> listTodayStatus(String userId, LocalDate today);
}
