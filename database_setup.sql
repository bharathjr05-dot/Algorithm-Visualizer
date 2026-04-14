-- Create database
CREATE DATABASE IF NOT EXISTS algorithm_visualizers;
USE algorithm_visualizers;

-- Users table with detailed profile
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

-- Algorithm categories
CREATE TABLE IF NOT EXISTS algorithm_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    difficulty_level ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'EASY',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Algorithms master table
CREATE TABLE IF NOT EXISTS algorithms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    description TEXT,
    time_complexity VARCHAR(50),
    space_complexity VARCHAR(50),
    difficulty_level ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'EASY',
    average_completion_time INT DEFAULT 0,
    total_attempts INT DEFAULT 0,
    success_rate DECIMAL(5,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES algorithm_categories(id)
);

-- User algorithm sessions
CREATE TABLE IF NOT EXISTS user_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    algorithm_id BIGINT,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP NULL,
    input_array TEXT,
    array_size INT,
    total_steps INT,
    time_taken INT,
    completed BOOLEAN DEFAULT FALSE,
    score INT DEFAULT 0,
    mistakes_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id)
);

-- Algorithm execution steps (enhanced)
CREATE TABLE IF NOT EXISTS algorithm_steps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    algorithm_name VARCHAR(100) NOT NULL,
    step_number INT NOT NULL,
    description TEXT NOT NULL,
    array_state TEXT NOT NULL,
    highlighted_indices VARCHAR(255),
    comparison_count INT DEFAULT 0,
    swap_count INT DEFAULT 0,
    execution_time_ms INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES user_sessions(id)
);

-- User progress tracking
CREATE TABLE IF NOT EXISTS user_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    algorithm_id BIGINT,
    attempts INT DEFAULT 0,
    completed_attempts INT DEFAULT 0,
    best_time INT NULL,
    best_score INT DEFAULT 0,
    average_time DECIMAL(10,2),
    last_attempt TIMESTAMP NULL,
    mastery_level ENUM('NOVICE', 'LEARNING', 'PROFICIENT', 'EXPERT') DEFAULT 'NOVICE',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id),
    UNIQUE KEY unique_user_algorithm (user_id, algorithm_id)
);

-- User achievements
CREATE TABLE IF NOT EXISTS achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    badge_icon VARCHAR(255),
    criteria_type ENUM('TIME_BASED', 'ATTEMPT_BASED', 'ACCURACY_BASED', 'COMPLETION_BASED'),
    criteria_value INT,
    points INT DEFAULT 0
);

-- User earned achievements
CREATE TABLE IF NOT EXISTS user_achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    achievement_id BIGINT,
    earned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (achievement_id) REFERENCES achievements(id),
    UNIQUE KEY unique_user_achievement (user_id, achievement_id)
);

-- Algorithm performance analytics
CREATE TABLE IF NOT EXISTS algorithm_analytics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    algorithm_id BIGINT,
    date DATE,
    total_attempts INT DEFAULT 0,
    successful_attempts INT DEFAULT 0,
    average_time DECIMAL(10,2),
    average_score DECIMAL(5,2),
    unique_users INT DEFAULT 0,
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id),
    UNIQUE KEY unique_algorithm_date (algorithm_id, date)
);

-- User learning paths
CREATE TABLE IF NOT EXISTS learning_paths (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    difficulty_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED'),
    estimated_duration INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Learning path algorithms
CREATE TABLE IF NOT EXISTS learning_path_algorithms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    path_id BIGINT,
    algorithm_id BIGINT,
    sequence_order INT,
    is_mandatory BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (path_id) REFERENCES learning_paths(id),
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id)
);

-- User enrolled learning paths
CREATE TABLE IF NOT EXISTS user_learning_paths (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    path_id BIGINT,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    current_algorithm_id BIGINT,
    progress_percentage DECIMAL(5,2) DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (path_id) REFERENCES learning_paths(id),
    FOREIGN KEY (current_algorithm_id) REFERENCES algorithms(id)
);

-- User feedback and ratings
CREATE TABLE IF NOT EXISTS algorithm_feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    algorithm_id BIGINT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    feedback_text TEXT,
    difficulty_rating INT CHECK (difficulty_rating >= 1 AND difficulty_rating <= 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (algorithm_id) REFERENCES algorithms(id)
);

-- Insert sample data for comprehensive database demonstration

-- Algorithm categories
INSERT INTO algorithm_categories (name, description, difficulty_level) VALUES
('Sorting Algorithms', 'Algorithms for arranging data in order', 'EASY'),
('Search Algorithms', 'Algorithms for finding specific elements', 'MEDIUM'),
('Graph Algorithms', 'Algorithms for graph traversal and analysis', 'HARD'),
('Dynamic Programming', 'Optimization algorithms using memoization', 'HARD'),
('Data Structures', 'Implementation of various data structures', 'MEDIUM');

-- Algorithms master data
INSERT INTO algorithms (name, category_id, description, time_complexity, space_complexity, difficulty_level) VALUES
('Bubble Sort', 1, 'Simple comparison-based sorting algorithm', 'O(n²)', 'O(1)', 'EASY'),
('Selection Sort', 1, 'In-place comparison sorting algorithm', 'O(n²)', 'O(1)', 'EASY'),
('Insertion Sort', 1, 'Builds sorted array one element at a time', 'O(n²)', 'O(1)', 'EASY'),
('Quick Sort', 1, 'Efficient divide-and-conquer sorting algorithm', 'O(n log n)', 'O(log n)', 'MEDIUM'),
('Merge Sort', 1, 'Stable divide-and-conquer sorting algorithm', 'O(n log n)', 'O(n)', 'MEDIUM'),
('Binary Search', 2, 'Efficient search in sorted arrays', 'O(log n)', 'O(1)', 'EASY'),
('Linear Search', 2, 'Sequential search through elements', 'O(n)', 'O(1)', 'EASY'),
('Stack Operations', 5, 'LIFO data structure operations', 'O(1)', 'O(n)', 'EASY'),
('Queue Operations', 5, 'FIFO data structure operations', 'O(1)', 'O(n)', 'EASY'),
('Linked List Operations', 5, 'Dynamic data structure operations', 'O(n)', 'O(n)', 'MEDIUM');

-- Sample users
INSERT INTO users (username, email, password_hash, full_name, skill_level) VALUES
('john_doe', 'john@example.com', 'hashed_password_123', 'John Doe', 'BEGINNER'),
('jane_smith', 'jane@example.com', 'hashed_password_456', 'Jane Smith', 'INTERMEDIATE'),
('alex_wilson', 'alex@example.com', 'hashed_password_789', 'Alex Wilson', 'ADVANCED'),
('demo_user', 'demo@example.com', 'demo_password', 'Demo User', 'BEGINNER');

-- Learning paths
INSERT INTO learning_paths (name, description, difficulty_level, estimated_duration) VALUES
('Sorting Fundamentals', 'Master basic sorting algorithms', 'BEGINNER', 120),
('Advanced Sorting', 'Learn efficient sorting techniques', 'INTERMEDIATE', 180),
('Data Structure Basics', 'Understanding fundamental data structures', 'BEGINNER', 200),
('Algorithm Analysis', 'Time and space complexity analysis', 'ADVANCED', 300);

-- Learning path algorithms
INSERT INTO learning_path_algorithms (path_id, algorithm_id, sequence_order, is_mandatory) VALUES
(1, 1, 1, TRUE), (1, 2, 2, TRUE), (1, 3, 3, TRUE),
(2, 4, 1, TRUE), (2, 5, 2, TRUE),
(3, 8, 1, TRUE), (3, 9, 2, TRUE), (3, 10, 3, TRUE);

-- Achievements
INSERT INTO achievements (name, description, criteria_type, criteria_value, points) VALUES
('First Steps', 'Complete your first algorithm', 'COMPLETION_BASED', 1, 10),
('Speed Demon', 'Complete algorithm in under 30 seconds', 'TIME_BASED', 30, 25),
('Perfectionist', 'Complete algorithm with 100% accuracy', 'ACCURACY_BASED', 100, 50),
('Persistent Learner', 'Attempt same algorithm 10 times', 'ATTEMPT_BASED', 10, 30),
('Sorting Master', 'Complete all sorting algorithms', 'COMPLETION_BASED', 5, 100);

-- Sample user sessions and progress
INSERT INTO user_sessions (user_id, algorithm_id, session_start, session_end, input_array, array_size, total_steps, time_taken, completed, score) VALUES
(1, 1, '2024-01-15 10:00:00', '2024-01-15 10:05:30', '[64,34,25,12,22,11,90]', 7, 15, 330, TRUE, 85),
(1, 2, '2024-01-15 10:10:00', '2024-01-15 10:12:45', '[64,34,25,12,22,11,90]', 7, 12, 165, TRUE, 92),
(2, 1, '2024-01-16 14:30:00', '2024-01-16 14:32:15', '[5,2,8,1,9]', 5, 8, 135, TRUE, 95),
(3, 4, '2024-01-17 09:15:00', '2024-01-17 09:16:30', '[3,6,8,10,1,2,1]', 7, 18, 90, TRUE, 98);

-- User progress tracking
INSERT INTO user_progress (user_id, algorithm_id, attempts, completed_attempts, best_time, best_score, average_time, mastery_level) VALUES
(1, 1, 3, 3, 330, 85, 345.50, 'LEARNING'),
(1, 2, 2, 2, 165, 92, 180.00, 'PROFICIENT'),
(2, 1, 1, 1, 135, 95, 135.00, 'PROFICIENT'),
(3, 4, 1, 1, 90, 98, 90.00, 'EXPERT');

-- User achievements earned
INSERT INTO user_achievements (user_id, achievement_id) VALUES
(1, 1), (1, 4), (2, 1), (2, 2), (3, 1), (3, 2), (3, 3);

-- Algorithm feedback
INSERT INTO algorithm_feedback (user_id, algorithm_id, rating, feedback_text, difficulty_rating) VALUES
(1, 1, 4, 'Great visualization, helped me understand the concept', 2),
(2, 1, 5, 'Perfect for beginners, very clear steps', 1),
(3, 4, 4, 'Good implementation but could use more explanation', 4);

-- Complex Views for Analytics

-- User performance summary view
CREATE VIEW user_performance_summary AS
SELECT 
    u.id, u.username, u.skill_level,
    COUNT(DISTINCT up.algorithm_id) as algorithms_attempted,
    SUM(up.completed_attempts) as total_completions,
    AVG(up.best_score) as average_best_score,
    AVG(up.average_time) as overall_average_time,
    COUNT(ua.achievement_id) as achievements_earned
FROM users u
LEFT JOIN user_progress up ON u.id = up.user_id
LEFT JOIN user_achievements ua ON u.id = ua.user_id
GROUP BY u.id, u.username, u.skill_level;

-- Algorithm difficulty analysis view
CREATE VIEW algorithm_difficulty_analysis AS
SELECT 
    a.name, a.difficulty_level, a.time_complexity,
    COUNT(us.id) as total_sessions,
    AVG(us.time_taken) as avg_completion_time,
    AVG(us.score) as avg_score,
    (COUNT(CASE WHEN us.completed = TRUE THEN 1 END) * 100.0 / COUNT(us.id)) as success_rate,
    AVG(af.difficulty_rating) as user_perceived_difficulty
FROM algorithms a
LEFT JOIN user_sessions us ON a.id = us.algorithm_id
LEFT JOIN algorithm_feedback af ON a.id = af.algorithm_id
GROUP BY a.id, a.name, a.difficulty_level, a.time_complexity;

-- Learning path progress view
CREATE VIEW learning_path_progress AS
SELECT 
    ulp.user_id, u.username, lp.name as path_name,
    ulp.progress_percentage,
    COUNT(lpa.algorithm_id) as total_algorithms,
    COUNT(CASE WHEN up.completed_attempts > 0 THEN 1 END) as completed_algorithms,
    ulp.enrolled_at, ulp.completed_at
FROM user_learning_paths ulp
JOIN users u ON ulp.user_id = u.id
JOIN learning_paths lp ON ulp.path_id = lp.id
JOIN learning_path_algorithms lpa ON lp.id = lpa.path_id
LEFT JOIN user_progress up ON u.id = up.user_id AND lpa.algorithm_id = up.algorithm_id
GROUP BY ulp.id, ulp.user_id, u.username, lp.name, ulp.progress_percentage, ulp.enrolled_at, ulp.completed_at;

-- Stored Procedures for Complex Operations

DELIMITER //

-- Procedure to update user progress after session
CREATE PROCEDURE UpdateUserProgress(
    IN p_user_id BIGINT,
    IN p_algorithm_id BIGINT,
    IN p_time_taken INT,
    IN p_score INT,
    IN p_completed BOOLEAN
)
BEGIN
    DECLARE v_attempts INT DEFAULT 0;
    DECLARE v_completed_attempts INT DEFAULT 0;
    DECLARE v_best_time INT;
    DECLARE v_best_score INT DEFAULT 0;
    
    -- Get current progress
    SELECT attempts, completed_attempts, best_time, best_score 
    INTO v_attempts, v_completed_attempts, v_best_time, v_best_score
    FROM user_progress 
    WHERE user_id = p_user_id AND algorithm_id = p_algorithm_id;
    
    -- Insert or update progress
    INSERT INTO user_progress (user_id, algorithm_id, attempts, completed_attempts, best_time, best_score, last_attempt)
    VALUES (p_user_id, p_algorithm_id, 1, IF(p_completed, 1, 0), IF(p_completed, p_time_taken, NULL), IF(p_completed, p_score, 0), NOW())
    ON DUPLICATE KEY UPDATE
        attempts = attempts + 1,
        completed_attempts = completed_attempts + IF(p_completed, 1, 0),
        best_time = IF(p_completed AND (best_time IS NULL OR p_time_taken < best_time), p_time_taken, best_time),
        best_score = IF(p_completed AND p_score > best_score, p_score, best_score),
        last_attempt = NOW(),
        mastery_level = CASE 
            WHEN completed_attempts + IF(p_completed, 1, 0) >= 10 AND best_score >= 95 THEN 'EXPERT'
            WHEN completed_attempts + IF(p_completed, 1, 0) >= 5 AND best_score >= 80 THEN 'PROFICIENT'
            WHEN completed_attempts + IF(p_completed, 1, 0) >= 2 THEN 'LEARNING'
            ELSE 'NOVICE'
        END;
END //

-- Function to calculate user skill level
CREATE FUNCTION CalculateUserSkillLevel(p_user_id BIGINT) 
RETURNS VARCHAR(20)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE v_total_completions INT DEFAULT 0;
    DECLARE v_avg_score DECIMAL(5,2) DEFAULT 0;
    DECLARE v_achievements INT DEFAULT 0;
    
    SELECT 
        SUM(completed_attempts),
        AVG(best_score),
        (SELECT COUNT(*) FROM user_achievements WHERE user_id = p_user_id)
    INTO v_total_completions, v_avg_score, v_achievements
    FROM user_progress 
    WHERE user_id = p_user_id;
    
    IF v_total_completions >= 20 AND v_avg_score >= 90 AND v_achievements >= 5 THEN
        RETURN 'ADVANCED';
    ELSEIF v_total_completions >= 10 AND v_avg_score >= 75 AND v_achievements >= 3 THEN
        RETURN 'INTERMEDIATE';
    ELSE
        RETURN 'BEGINNER';
    END IF;
END //

DELIMITER ;

-- Triggers for automatic updates

-- Trigger to update algorithm statistics
DELIMITER //
CREATE TRIGGER update_algorithm_stats 
AFTER INSERT ON user_sessions
FOR EACH ROW
BEGIN
    UPDATE algorithms 
    SET total_attempts = total_attempts + 1,
        success_rate = (
            SELECT (COUNT(CASE WHEN completed = TRUE THEN 1 END) * 100.0 / COUNT(*))
            FROM user_sessions 
            WHERE algorithm_id = NEW.algorithm_id
        )
    WHERE id = NEW.algorithm_id;
END //
DELIMITER ;

-- Trigger to update user total time spent
DELIMITER //
CREATE TRIGGER update_user_time_spent
AFTER UPDATE ON user_sessions
FOR EACH ROW
BEGIN
    IF NEW.session_end IS NOT NULL AND OLD.session_end IS NULL THEN
        UPDATE users 
        SET total_time_spent = total_time_spent + NEW.time_taken
        WHERE id = NEW.user_id;
    END IF;
END //
DELIMITER ;

-- Indexes for performance optimization
CREATE INDEX idx_user_sessions_user_algorithm ON user_sessions(user_id, algorithm_id);
CREATE INDEX idx_algorithm_steps_session ON algorithm_steps(session_id);
CREATE INDEX idx_user_progress_user ON user_progress(user_id);
CREATE INDEX idx_user_achievements_user ON user_achievements(user_id);
CREATE INDEX idx_algorithm_feedback_algorithm ON algorithm_feedback(algorithm_id);
CREATE INDEX idx_user_sessions_date ON user_sessions(session_start);


