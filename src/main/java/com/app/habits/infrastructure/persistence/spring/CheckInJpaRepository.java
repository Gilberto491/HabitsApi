package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = """
              SELECT ci.checked_on AS day,
                     COUNT(*)::bigint AS count
                FROM checkins ci
               WHERE ci.user_id = :userId
                 AND ci.checked_on BETWEEN :start AND :end
            GROUP BY ci.checked_on
            """, nativeQuery = true)
    List<DayRow> countPerDay(@Param("userId") String userId,
                             @Param("start") LocalDate start,
                             @Param("end") LocalDate end);

    @Query(value = """
              SELECT ci.habit_id AS id,
                     COUNT(*)::bigint AS count
                FROM checkins ci
               WHERE ci.user_id = :userId
                 AND ci.checked_on BETWEEN :start AND :end
            GROUP BY ci.habit_id
            """, nativeQuery = true)
    List<IdRow> countPerHabit(@Param("userId") String userId,
                              @Param("start") LocalDate start,
                              @Param("end") LocalDate end);

    @Query(value = """
              SELECT h.category_id AS id,
                     COUNT(*)::bigint AS count
                FROM checkins ci
                JOIN habits h ON h.id = ci.habit_id
               WHERE ci.user_id = :userId
                 AND ci.checked_on BETWEEN :start AND :end
            GROUP BY h.category_id
            """, nativeQuery = true)
    List<IdRow> countPerCategory(@Param("userId") String userId,
                                 @Param("start") LocalDate start,
                                 @Param("end") LocalDate end);

    @Query("""
                select distinct c.checkedOn
                from CheckInEntity c
                where c.habitId = :habitId
                order by c.checkedOn
            """)
    List<LocalDate> listCheckedDaysByHabit(@Param("habitId") String habitId);

    @Query("""
                select min(c.checkedOn)
                from CheckInEntity c
                where c.userId = :userId
            """)
    LocalDate findFirstCheckInDateByUser(@Param("userId") String userId);

    @Query(value = """
                select checked_on as day, count(distinct habit_id) as cnt
                from checkins
                where user_id = :userId
                  and checked_on between :start and :end
                group by checked_on
                order by checked_on
            """, nativeQuery = true)
    List<Object[]> countDistinctHabitsPerDay(
            @Param("userId") String userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    interface DayRow {
        LocalDate getDay();

        long getCount();
    }

    interface IdRow {
        String getId();

        long getCount();
    }
}
