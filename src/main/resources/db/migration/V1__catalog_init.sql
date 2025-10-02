CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS categories (
                                          id VARCHAR(36) PRIMARY KEY,
                                          name VARCHAR(60) NOT NULL UNIQUE,
                                          color_hex VARCHAR(7)
);

CREATE TABLE IF NOT EXISTS habit_templates (
                                               id VARCHAR(36) PRIMARY KEY,
                                               category_id VARCHAR(36) NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
                                               name VARCHAR(80) NOT NULL,
                                               description VARCHAR(255),
                                               default_weekly_target INT NOT NULL,
                                               difficulty VARCHAR(10) NOT NULL,
                                               base_points INT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_template_category ON habit_templates(category_id);
