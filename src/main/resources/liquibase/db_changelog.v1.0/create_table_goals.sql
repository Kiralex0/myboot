CREATE TABLE goals(
    id bigserial primary key,
    title varchar(255) not null,
    user_id bigint not null,
    status varchar(255) not null,
    create_date timestamp not null,
    FOREIGN KEY (user_id) REFERENCES users(id)
)