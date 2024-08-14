CREATE TABLE users(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    bloodType VARCHAR(3) NOT NULL,
    address VARCHAR(255) NOT NULL
);