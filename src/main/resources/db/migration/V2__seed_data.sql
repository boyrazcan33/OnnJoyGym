-- Insert admin user (password will be hashed in code later)
INSERT INTO users (email, password, role) VALUES
('admin@onjoygym.com', 'PLACEHOLDER', 'ADMIN');

-- Insert sample gyms
INSERT INTO gyms (name, address, description) VALUES
('MyFitness Rotermann', 'Rotermanni 14, Tallinn', 'Modern gym in Rotermann Quarter with excellent equipment'),
('MyFitness Ülemiste', 'Ülemiste City, Tallinn', 'Large facility near Ülemiste with powerlifting area');