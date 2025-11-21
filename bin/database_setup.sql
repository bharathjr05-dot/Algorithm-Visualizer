-- Create database
CREATE DATABASE IF NOT EXISTS algorithm_visualizer;
USE algorithm_visualizer;

-- Create algorithm_steps table
CREATE TABLE IF NOT EXISTS algorithm_steps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    algorithm_name VARCHAR(100) NOT NULL,
    step_number INT NOT NULL,
    description TEXT NOT NULL,
    array_state TEXT NOT NULL,
    highlighted_indices VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data for demonstration
INSERT INTO algorithm_steps (algorithm_name, step_number, description, array_state, highlighted_indices) VALUES
('Bubble Sort', 0, 'Starting Bubble Sort with array', '[64, 34, 25, 12, 22, 11, 90]', ''),
('Bubble Sort', 1, 'Comparing elements at positions 0 and 1', '[64, 34, 25, 12, 22, 11, 90]', '0,1'),
('Bubble Sort', 2, 'Swapped elements at positions 0 and 1', '[34, 64, 25, 12, 22, 11, 90]', '0,1');

-- Create users table for future enhancements
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_progress table for tracking learning progress
CREATE TABLE IF NOT EXISTS user_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    algorithm_name VARCHAR(100) NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    completion_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);