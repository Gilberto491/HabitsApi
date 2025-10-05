CREATE TABLE users (
                       id VARCHAR(36) PRIMARY KEY,
                       username VARCHAR(80) NOT NULL,
                       email VARCHAR(160) NOT NULL,
                       password_hash VARCHAR(100) NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       role VARCHAR(30) NOT NULL DEFAULT 'ROLE_USER'
);
CREATE UNIQUE INDEX uk_users_username ON users(username);
CREATE UNIQUE INDEX uk_users_email ON users(email);
