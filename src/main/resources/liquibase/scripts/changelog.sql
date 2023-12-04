--liquibase formatted sql

--changeset seborisov:1
create table volunteers
(
    telegram_id bigint not null primary key,
    first_name  varchar(255),
    last_name   varchar(255)
);
create table trial_periods
(
    id               bigserial primary key,
    start_date       date,
    end_date         date,
    last_report_date date,
    owner_id         bigint,
    animal_id        bigint,
    animal_type      varchar(255),
    result           varchar(255)
);

create table reports
(
    id               bigserial primary key,
    photo_id         varchar(255),
    food_ration      varchar(255),
    general_health   varchar(255),
    behavior_changes varchar(255),
    receive_date     date,
    trial_period_id  bigint
);

create table cat_shelter
(
    id            bigserial not null primary key,
    about_me      varchar(255),
    location      varchar(255),
    name          varchar(255),
    safety_advice varchar(255),
    security      varchar(255),
    timetable     varchar(255)
);

create table dog_shelter
(
    id            bigserial not null primary key,
    about_me      varchar(255),
    location      varchar(255),
    name          varchar(255),
    safety_advice varchar(255),
    security      varchar(255),
    timetable     varchar(255)
);

create table cat
(
    id         bigserial not null primary key,
    name       varchar(255),
    age        integer
        constraint check_age check (age > 0),
    is_healthy boolean,
    vaccinated boolean,
    owner_id   bigint,
    shelter_id bigint
        constraint cat_cat_shelter_id_fk
            references cat_shelter
);

create table dog
(
    id         bigserial not null primary key,
    name       varchar(255),
    age        integer
        constraint check_age
            check (age > 0),
    is_healthy boolean,
    vaccinated boolean,
    owner_id   bigint,
    shelter_id bigint
        constraint dog_dog_shelter_id_fk
            references dog_shelter
);

create table users
(
    telegram_id  bigint not null primary key,
    first_name   varchar(255),
    last_name    varchar(255),
    phone        varchar(255),
    shelter_type varchar(255),
    shelter_name varchar(255)
);

create table owners
(
    id          bigserial primary key,
    telegram_id bigint,
    first_name  varchar(255),
    last_name   varchar(255),
    phone       varchar(255),
    animal_type varchar(31) not null
);