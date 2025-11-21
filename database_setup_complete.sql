-- Complete Database Setup for Algorithm Visualizer
-- Run this script to create all necessary tables and initial data

CREATE DATABASE IF NOT EXISTS algorithm_visualizers;
USE algorithm_visualizers;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    skill_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BEGINNER',
    total_algorithms_completed INT DEFAULT 0,
    total_time_spent INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Algorithms table
CREATE TABLE IF NOT EXISTS algorithms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty_level ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'EASY',
    description TEXT,
    time_complexity VARCHAR(50),
    space_complexity VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Algorithm Categories table
CREATE TABLE IF NOT EXISTS algorithm_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    difficulty_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BEGINNER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User Progress table
CREATE TABLE IF NOT EXISTS user_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    algorithm_id BIGINT NOT NULL,
    mastery_level ENUM('NOT_STARTED', 'LEARNING', 'PRACTICING', 'MASTERED') DEFAULT 'NOT_STARTED',
    completed_attempts INT DEFAULT 0,
    best_time INT,
    best_score DOUBLE DEFAULT 0.0,
    total_attempts INT DEFAULT 0,
    first_attempt_at TIMESTAMP NULL,
    last_attempt_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_algorithm (user_id, algorithm_id)
);

-- User Sessions table
CREATE TABLE IF NOT EXISTS user_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    algorithm_id BIGINT NOT NULL,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP NULL,
    time_taken INT,
    score DOUBLE DEFAULT 0.0,
    completed BOOLEAN DEFAULT FALSE,
    steps_data JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id) ON DELETE CASCADE
);

-- Insert initial algorithm categories
INSERT IGNORE INTO algorithm_categories (name, description, difficulty_level) VALUES
('Sorting', 'Algorithms for arranging data in order', 'BEGINNER'),
('Searching', 'Algorithms for finding specific elements', 'BEGINNER'),
('Data Structures', 'Basic data structure operations', 'BEGINNER'),
('Graph Algorithms', 'Algorithms for graph traversal and analysis', 'INTERMEDIATE'),
('Dynamic Programming', 'Optimization algorithms using memoization', 'ADVANCED'),
('Greedy Algorithms', 'Algorithms that make locally optimal choices', 'INTERMEDIATE');

-- Insert initial algorithms
INSERT IGNORE INTO algorithms (name, category, difficulty_level, description, time_complexity, space_complexity) VALUES
-- Sorting Algorithms
('Bubble Sort', 'Sorting', 'EASY', 'Simple comparison-based sorting algorithm', 'O(n²)', 'O(1)'),
('Selection Sort', 'Sorting', 'EASY', 'Finds minimum element and places it at beginning', 'O(n²)', 'O(1)'),
('Insertion Sort', 'Sorting', 'EASY', 'Builds sorted array one element at a time', 'O(n²)', 'O(1)'),
('Merge Sort', 'Sorting', 'MEDIUM', 'Divide and conquer sorting algorithm', 'O(n log n)', 'O(n)'),
('Quick Sort', 'Sorting', 'MEDIUM', 'Efficient divide and conquer sorting', 'O(n log n)', 'O(log n)'),
('Heap Sort', 'Sorting', 'HARD', 'Comparison-based sorting using binary heap', 'O(n log n)', 'O(1)'),

-- Searching Algorithms
('Linear Search', 'Searching', 'EASY', 'Sequential search through elements', 'O(n)', 'O(1)'),
('Binary Search', 'Searching', 'MEDIUM', 'Efficient search in sorted arrays', 'O(log n)', 'O(1)'),
('Jump Search', 'Searching', 'MEDIUM', 'Search by jumping ahead by fixed steps', 'O(√n)', 'O(1)'),

-- Data Structure Operations
('Array Operations', 'Data Structures', 'EASY', 'Basic array insertion, deletion, search', 'O(n)', 'O(1)'),
('Linked List Operations', 'Data Structures', 'EASY', 'Basic linked list operations', 'O(n)', 'O(1)'),
('Stack Operations', 'Data Structures', 'EASY', 'LIFO data structure operations', 'O(1)', 'O(n)'),
('Queue Operations', 'Data Structures', 'EASY', 'FIFO data structure operations', 'O(1)', 'O(n)');

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_user_progress_user_id ON user_progress(user_id);
CREATE INDEX idx_user_progress_algorithm_id ON user_progress(algorithm_id);
CREATE INDEX idx_user_sessions_user_id ON user_sessions(user_id);
CREATE INDEX idx_user_sessions_algorithm_id ON user_sessions(algorithm_id);
CREATE INDEX idx_algorithms_category ON algorithms(category);

-- Create a view for user performance summary (optional)
CREATE OR REPLACE VIEW user_performance_summary AS
SELECT 
    u.id as user_id,
    u.username,
    u.full_name,
    u.skill_level,
    u.total_algorithms_completed,
    u.total_time_spent,
    COUNT(DISTINCT up.algorithm_id) as algorithms_attempted,
    COUNT(DISTINCT CASE WHEN up.completed_attempts > 0 THEN up.algorithm_id END) as algorithms_completed,
    AVG(up.best_score) as average_score,
    MIN(up.best_time) as best_time_overall
FROM users u
LEFT JOIN user_progress up ON u.id = up.user_id
GROUP BY u.id, u.username, u.full_name, u.skill_level, u.total_algorithms_completed, u.total_time_spent;

-- Insert a demo user (password is 'demo123' hashed with BCrypt)
INSERT IGNORE INTO users (username, email, password_hash, full_name, skill_level) VALUES
('demo', 'demo@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFDYZpSfpEOeGQzrlQwJ6Jm', 'Demo User', 'BEGINNER');

COMMIT;