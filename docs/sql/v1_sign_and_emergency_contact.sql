-- 功能：签到寄语 + 紧急联系人（短信验证码确认）
-- 数据库：MySQL 8.x

CREATE TABLE IF NOT EXISTS sign_record (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    sign_date DATE NOT NULL,
    message_for_tomorrow VARCHAR(255) DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_sign_record_user_date (user_id, sign_date),
    KEY idx_sign_record_user_created (user_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emergency_contact (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(64) NOT NULL,
    contact_mobile VARCHAR(32) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bound_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_ec_user (user_id),
    KEY idx_ec_mobile (contact_mobile),
    UNIQUE KEY uk_ec_user_mobile (user_id, contact_mobile)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS risk_alert_log (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    alert_type VARCHAR(64) NOT NULL,
    alert_date DATE NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_risk_alert (user_id, alert_type, alert_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sign_goal_config (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    goal_days INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_sign_goal_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
