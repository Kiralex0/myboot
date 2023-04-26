CREATE TABLE users(
    id bigint primary key,
    first_name varchar(255) not null,
    surname varchar(255) not null,
    user_name varchar(255) not null,
    register_data timestamp,
    level_user bigint
)