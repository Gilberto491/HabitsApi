CREATE TABLE IF NOT EXISTS habits (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    name VARCHAR(80) NOT NULL,
    description VARCHAR(500),
    target_per_week INT NOT NULL CHECK (target_per_week BETWEEN 1 AND 7),
    difficulty VARCHAR(10) NOT NULL CHECK (difficulty IN ('EASY','MEDIUM','HARD')),
    points_per_checkin INT NOT NULL CHECK (points_per_checkin BETWEEN 1 AND 1000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );

ALTER TABLE habits
    ADD CONSTRAINT fk_habits_category
        FOREIGN KEY (category_id) REFERENCES categories(id);