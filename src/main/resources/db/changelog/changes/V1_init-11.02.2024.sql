CREATE SCHEMA IF NOT EXISTS bank;

CREATE TABLE IF NOT EXISTS bank.users
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(30)         NOT NULL,
    login             VARCHAR(100) UNIQUE NOT NULL,
    creation_date     TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    modification_date TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)
    );

CREATE TABLE IF NOT EXISTS bank.account
(
    uuid              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pincode              VARCHAR(4) NOT NULL,
    balance           DECIMAL(18, 2)   DEFAULT 0,
    user_id           BIGINT,
    creation_date     TIMESTAMP(6)     DEFAULT CURRENT_TIMESTAMP(6),
    modification_date TIMESTAMP(6)     DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES bank.users (id)
    );

CREATE TABLE IF NOT EXISTS bank.statistic
(
    id                SERIAL PRIMARY KEY,
    operation_type    VARCHAR(50)    NOT NULL,
    source_account    UUID,
    target_account    UUID,
    quantity          DECIMAL(18, 2) NOT NULL,
    creation_date     TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    modification_date TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (source_account) REFERENCES bank.account (uuid)
    );

