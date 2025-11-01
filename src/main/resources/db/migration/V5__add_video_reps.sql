-- Add reps column to videos table
ALTER TABLE videos ADD COLUMN reps INTEGER NOT NULL DEFAULT 3;

-- Add constraint: reps must be exactly 3
ALTER TABLE videos ADD CONSTRAINT check_reps CHECK (reps = 3);

-- Add comment
COMMENT ON COLUMN videos.reps IS 'Number of repetitions in the video (fixed to 3)';