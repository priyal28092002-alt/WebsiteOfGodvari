-- Admin user: email=admin@godavari.com, password=Admin@123
-- BCrypt hash of "Admin@123"
INSERT INTO users (name, email, password, phone, role, company_name, city, state, is_active, created_at, updated_at)
SELECT 'Godavari Admin', 'admin@godavari.com',
       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
       '9999999999', 'ADMIN', 'Godavari Building Materials', 'Nashik', 'Maharashtra', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@godavari.com');

-- Manager user: email=manager@godavari.com, password=Admin@123
INSERT INTO users (name, email, password, phone, role, company_name, city, state, is_active, created_at, updated_at)
SELECT 'Store Manager', 'manager@godavari.com',
       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
       '9999999998', 'MANAGER', 'Godavari Building Materials', 'Nashik', 'Maharashtra', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'manager@godavari.com');
