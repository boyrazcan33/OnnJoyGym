-- Update users table with new fields
ALTER TABLE users ADD COLUMN IF NOT EXISTS bio TEXT;
ALTER TABLE users ADD COLUMN IF NOT EXISTS goals VARCHAR(500);
ALTER TABLE users ADD COLUMN IF NOT EXISTS experience VARCHAR(50);
ALTER TABLE users ADD COLUMN IF NOT EXISTS gym_preference BIGINT;

-- Clubs table
CREATE TABLE IF NOT EXISTS clubs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    goal VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    gym_id BIGINT NOT NULL REFERENCES gyms(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    content TEXT NOT NULL,
    author_name VARCHAR(255),
    is_expert BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Videos table
CREATE TABLE IF NOT EXISTS videos (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    gym_id BIGINT NOT NULL REFERENCES gyms(id) ON DELETE CASCADE,
    category VARCHAR(100) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    s3_key VARCHAR(500) NOT NULL,
    s3_url TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Leaderboard table
CREATE TABLE IF NOT EXISTS leaderboard (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    gym_id BIGINT NOT NULL REFERENCES gyms(id) ON DELETE CASCADE,
    video_id BIGINT NOT NULL REFERENCES videos(id) ON DELETE CASCADE,
    category VARCHAR(100) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    ranking INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_reviews_gym_id ON reviews(gym_id);
CREATE INDEX IF NOT EXISTS idx_videos_user_id ON videos(user_id);
CREATE INDEX IF NOT EXISTS idx_videos_status ON videos(status);
CREATE INDEX IF NOT EXISTS idx_leaderboard_gym_category ON leaderboard(gym_id, category);
CREATE INDEX IF NOT EXISTS idx_leaderboard_weight ON leaderboard(weight DESC);

-- Insert sample clubs
INSERT INTO clubs (name, description, goal, level) VALUES
('Strength Club - Beginner', 'Build foundational strength with compound movements', 'STRENGTH', 'BEGINNER'),
('Hypertrophy Club - Intermediate', 'Muscle building program for intermediate lifters', 'HYPERTROPHY', 'INTERMEDIATE'),
('Powerlifting Club - Advanced', 'Advanced powerlifting program focusing on big 3 lifts', 'STRENGTH', 'ADVANCED')
ON CONFLICT DO NOTHING;