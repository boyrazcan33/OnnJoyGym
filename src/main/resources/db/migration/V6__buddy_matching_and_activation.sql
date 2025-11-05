-- Remove old goals field (no longer needed)
ALTER TABLE users DROP COLUMN IF EXISTS goals;

-- Add buddy matching fields to users table
ALTER TABLE users ADD COLUMN IF NOT EXISTS training_goal VARCHAR(100);
ALTER TABLE users ADD COLUMN IF NOT EXISTS gender VARCHAR(50);
ALTER TABLE users ADD COLUMN IF NOT EXISTS preferred_locations TEXT; -- JSON array of gym IDs
ALTER TABLE users ADD COLUMN IF NOT EXISTS daily_schedule TEXT; -- JSON array, max 2 selections
ALTER TABLE users ADD COLUMN IF NOT EXISTS social_behavior VARCHAR(100);
ALTER TABLE users ADD COLUMN IF NOT EXISTS age_range VARCHAR(20);
ALTER TABLE users ADD COLUMN IF NOT EXISTS telegram_username VARCHAR(100); -- @username format

-- Add activation flag
ALTER TABLE users ADD COLUMN IF NOT EXISTS is_activated BOOLEAN DEFAULT FALSE;

-- Add gender field to videos for gender-based categories
ALTER TABLE videos ADD COLUMN IF NOT EXISTS user_gender VARCHAR(50);

-- Add comments for clarity
COMMENT ON COLUMN users.is_activated IS 'User activated after buddy match OR video upload';
COMMENT ON COLUMN users.preferred_locations IS 'JSON array of gym IDs for buddy matching (max 5)';
COMMENT ON COLUMN users.daily_schedule IS 'JSON array of time slots (max 2)';
COMMENT ON COLUMN users.telegram_username IS 'Telegram username for buddy contact after acceptance';
COMMENT ON COLUMN videos.user_gender IS 'User gender for category validation (MALE/FEMALE)';