-- Club Progress Tracking table
CREATE TABLE IF NOT EXISTS club_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    club_id BIGINT NOT NULL REFERENCES clubs(id) ON DELETE CASCADE,
    current_week INTEGER NOT NULL DEFAULT 1,
    start_date DATE NOT NULL DEFAULT CURRENT_DATE,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, club_id)
);

-- Index for performance
CREATE INDEX IF NOT EXISTS idx_club_progress_user ON club_progress(user_id);
CREATE INDEX IF NOT EXISTS idx_club_progress_club ON club_progress(club_id);

-- Comment
COMMENT ON TABLE club_progress IS 'Tracks user progress through weekly club programs';