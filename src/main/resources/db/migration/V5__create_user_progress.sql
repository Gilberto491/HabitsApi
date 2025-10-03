CREATE TABLE user_progress (
                               user_id VARCHAR(36) PRIMARY KEY,
                               total_xp BIGINT NOT NULL DEFAULT 0,
                               level INT NOT NULL DEFAULT 1,
                               xp_into_level BIGINT NOT NULL DEFAULT 0
);

ALTER TABLE habits ADD COLUMN base_points INT NOT NULL DEFAULT 10;