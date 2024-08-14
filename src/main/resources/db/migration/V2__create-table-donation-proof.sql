CREATE TABLE donation_proof(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    eTag VARCHAR(255) NOT NULL,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);