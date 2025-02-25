 CREATE TABLE spaceship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    crew_capacity INT NOT NULL,
    length DOUBLE PRECISION NOT NULL,
    propulsion_type VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);