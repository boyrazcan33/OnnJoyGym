-- Update admin user with correct credentials
-- Email: tallinntraining@gmail.com
-- Password: Onnjoyadmin2025?

-- Delete old admin if exists
DELETE FROM users WHERE email = 'admin@onjoygym.com';

-- Insert new admin user
INSERT INTO users (email, password, role, is_activated) VALUES
('tallinntraining@gmail.com', '$2a$10$eoyr5zfA3razKfx9lVJ5bONMChA5MU2j29mMIAatZf6/mEQ/LMesK', 'ADMIN', TRUE)
ON CONFLICT (email) DO UPDATE SET
    password = EXCLUDED.password,
    role = EXCLUDED.role,
    is_activated = EXCLUDED.is_activated;