CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand TEXT,
    model TEXT,
    price INTEGER
);

CREATE TABLE human (
    id SERIAL PRIMARY KEY,
    name TEXT,
    age integer,
    drivers_license boolean,
    car_id INTEGER REFERENCES car(id)
);
