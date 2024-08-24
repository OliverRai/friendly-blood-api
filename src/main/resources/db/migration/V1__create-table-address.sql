CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    postal_code INTEGER NOT NULL,
    district VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL
);