DROP TABLE IF EXSTS bases;

CREATE TABLE bases (
    id BIGSERIAL PRIMARY KEY,
    server UUID NOT NULL,
    user string NOT NULL,
    world ,
    x ,
    y ,
    z ,
);