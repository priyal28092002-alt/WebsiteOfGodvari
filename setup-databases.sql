-- =============================================
-- Godavari Building Materials Nashik
-- Database Setup Script
-- =============================================

CREATE DATABASE IF NOT EXISTS godavari_users;
CREATE DATABASE IF NOT EXISTS godavari_products;
CREATE DATABASE IF NOT EXISTS godavari_orders;
CREATE DATABASE IF NOT EXISTS godavari_inventory;
CREATE DATABASE IF NOT EXISTS godavari_quotations;
CREATE DATABASE IF NOT EXISTS godavari_billing;
CREATE DATABASE IF NOT EXISTS godavari_notifications;

-- =============================================
-- USER SERVICE DB
-- =============================================
USE godavari_users;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role ENUM('ADMIN','MANAGER','SALES_EXECUTIVE','CUSTOMER','CONTRACTOR') NOT NULL DEFAULT 'CUSTOMER',
    gst_number VARCHAR(20),
    company_name VARCHAR(255),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(10),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================================
-- PRODUCT SERVICE DB
-- =============================================
USE godavari_products;

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(100) UNIQUE NOT NULL,
    category_id BIGINT NOT NULL,
    brand VARCHAR(100),
    unit VARCHAR(50) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    mrp DECIMAL(12,2),
    gst_rate DECIMAL(5,2) DEFAULT 18.00,
    hsn_code VARCHAR(20),
    image_url VARCHAR(500),
    min_order_qty INT DEFAULT 1,
    bulk_discount_percent DECIMAL(5,2) DEFAULT 0,
    bulk_min_qty INT DEFAULT 100,
    is_active BOOLEAN DEFAULT TRUE,
    rating DOUBLE DEFAULT 0,
    review_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- =============================================
-- INVENTORY SERVICE DB
-- =============================================
USE godavari_inventory;

CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    product_name VARCHAR(255),
    quantity_available INT NOT NULL DEFAULT 0,
    quantity_reserved INT DEFAULT 0,
    low_stock_threshold INT DEFAULT 50,
    warehouse_location VARCHAR(100),
    last_restocked_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stock_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    transaction_type ENUM('IN','OUT','ADJUSTMENT') NOT NULL,
    quantity INT NOT NULL,
    reference_id VARCHAR(100),
    notes TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- ORDER SERVICE DB
-- =============================================
USE godavari_orders;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    status ENUM('PENDING','CONFIRMED','PROCESSING','SHIPPED','DELIVERED','CANCELLED') DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) NOT NULL,
    gst_amount DECIMAL(12,2) DEFAULT 0,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    delivery_charge DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) NOT NULL,
    delivery_address TEXT,
    delivery_city VARCHAR(100),
    delivery_pincode VARCHAR(10),
    payment_status ENUM('PENDING','PAID','FAILED','REFUNDED') DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    expected_delivery_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    sku VARCHAR(100),
    unit VARCHAR(50),
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    gst_rate DECIMAL(5,2),
    gst_amount DECIMAL(12,2),
    total_price DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- =============================================
-- QUOTATION SERVICE DB
-- =============================================
USE godavari_quotations;

CREATE TABLE IF NOT EXISTS quotations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quotation_number VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    company_name VARCHAR(255),
    status ENUM('DRAFT','SUBMITTED','REVIEWED','APPROVED','REJECTED','EXPIRED') DEFAULT 'DRAFT',
    total_amount DECIMAL(12,2),
    valid_until DATE,
    notes TEXT,
    admin_remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quotation_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quotation_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    quantity INT NOT NULL,
    unit VARCHAR(50),
    quoted_price DECIMAL(12,2),
    notes TEXT,
    FOREIGN KEY (quotation_id) REFERENCES quotations(id)
);

-- =============================================
-- BILLING SERVICE DB
-- =============================================
USE godavari_billing;

CREATE TABLE IF NOT EXISTS invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    user_gst VARCHAR(20),
    billing_address TEXT,
    subtotal DECIMAL(12,2) NOT NULL,
    cgst DECIMAL(12,2) DEFAULT 0,
    sgst DECIMAL(12,2) DEFAULT 0,
    igst DECIMAL(12,2) DEFAULT 0,
    total_gst DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) NOT NULL,
    payment_status ENUM('PENDING','PAID','OVERDUE') DEFAULT 'PENDING',
    due_date DATE,
    paid_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
