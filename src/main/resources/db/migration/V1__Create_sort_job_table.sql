CREATE TABLE IF NOT EXISTS sort_job (
    id UUID PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    payload TEXT NOT NULL,
    result TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
