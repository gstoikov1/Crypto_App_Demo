CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    funds DECIMAL(10,2) DEFAULT 10000.00
    );

CREATE TABLE IF NOT EXISTS symbols (
                                       symbol VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    icon_url VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            user_id BIGINT NOT NULL,
                                            symbol VARCHAR(10) NOT NULL,
    price DECIMAL(18,8) NOT NULL,
    quantity DECIMAL(18,8) NOT NULL,
    trade_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type ENUM('BUY', 'SELL'),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (symbol) REFERENCES symbols(symbol)
    );



CREATE TABLE IF NOT EXISTS wallets (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       holder BIGINT,
                                       FOREIGN KEY (holder) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS symbols_in_wallet (
                                                 wallet_id BIGINT,
                                                 symbol VARCHAR(10),
                                                 quantity DECIMAL(18,8) DEFAULT 0,
                                                 PRIMARY KEY (wallet_id, symbol),
                                                 FOREIGN KEY (wallet_id) REFERENCES wallets(id),
                                                 FOREIGN KEY (symbol) REFERENCES symbols(symbol)
);

