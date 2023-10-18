CREATE TABLE actions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(80),
    form_link VARCHAR(128) UNIQUE,
    description TEXT,
    image LONGBLOB,
    status VARCHAR(20),
    timestamp TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);
