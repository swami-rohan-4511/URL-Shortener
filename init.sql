-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS shortener;

-- Use the shortener database
USE shortener;

-- Create the short_urls table
CREATE TABLE IF NOT EXISTS short_urls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(16) NOT NULL UNIQUE,
    target VARCHAR(2048) NOT NULL,
    click_count BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL,
    INDEX idx_code (code),
    INDEX idx_created_at (created_at)
);

-- Grant permissions to the shortener user
GRANT ALL PRIVILEGES ON shortener.* TO 'shortener'@'%';
FLUSH PRIVILEGES;
