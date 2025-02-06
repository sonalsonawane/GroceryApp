-- Insert roles into the roles table
CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);
INSERT INTO roles (role_id, role_name)
VALUES 
    (1, 'ADMIN'),
    (2, 'USER');
    
commit;