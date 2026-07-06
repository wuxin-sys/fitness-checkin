-- H2 建表（兼容 MySQL 语法，MODE=MySQL）
CREATE TABLE IF NOT EXISTS admin (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(100) NOT NULL,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    nickname    VARCHAR(50),
    gender      TINYINT      DEFAULT 0,
    phone       VARCHAR(20),
    role        TINYINT      DEFAULT 0,
    avatar      VARCHAR(255),
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS checkin (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT       NOT NULL,
    checkin_date DATE         NOT NULL,
    sport_type   VARCHAR(20)  NOT NULL,
    duration     INT          DEFAULT 0,
    calorie      INT          DEFAULT 0,
    remark       VARCHAR(200),
    create_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_checkin_user ON checkin(user_id);
CREATE INDEX IF NOT EXISTS idx_checkin_user_date ON checkin(user_id, checkin_date);

CREATE TABLE IF NOT EXISTS notice (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    content     TEXT         NOT NULL,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
