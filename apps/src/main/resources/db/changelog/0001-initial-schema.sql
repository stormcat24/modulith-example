-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE posts
(
    id         INT AUTO_INCREMENT,
    title      VARCHAR(100),
    content    TEXT,
    author     VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE comments
(
    id         INT AUTO_INCREMENT,
    post_id    INT,
    comment    TEXT,
    author     VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;