CREATE TABLE check_ins (
                           id          VARCHAR(36) PRIMARY KEY,
                           habit_id    VARCHAR(36) NOT NULL REFERENCES habits(id) ON DELETE CASCADE,
                           checked_on  DATE NOT NULL,
                           checked_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                           CONSTRAINT uk_checkin_habit_day UNIQUE (habit_id, checked_on)
);

CREATE INDEX idx_checkins_habit_day ON check_ins(habit_id, checked_on);
