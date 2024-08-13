create table articles
(
    id             serial primary key,
    title          varchar(100) not null,
    name           varchar(255) not null,
    content        text         not null,
    published_date date         not null
);