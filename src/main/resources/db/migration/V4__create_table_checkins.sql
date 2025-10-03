CREATE TABLE checkins (
                           id          VARCHAR(36) PRIMARY KEY,
                           user_id     varchar(100) NOT NULL,
                           habit_id    VARCHAR(36) NOT NULL REFERENCES habits(id) ON DELETE CASCADE,
                           checked_on  DATE NOT NULL,
                           checked_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                           CONSTRAINT uk_checkin_habit_day UNIQUE (habit_id, checked_on)
);

CREATE INDEX IF NOT EXISTS idx_checkins_user_date ON checkins(user_id, checked_on);
CREATE INDEX IF NOT EXISTS idx_checkins_habit_date ON checkins(habit_id, checked_on);
CREATE INDEX IF NOT EXISTS idx_habits_category ON habits(category_id);
create index if not exists idx_checkins_habit_day on checkins(habit_id, checked_on);
create index if not exists idx_checkins_user_day  on checkins(user_id, checked_on);