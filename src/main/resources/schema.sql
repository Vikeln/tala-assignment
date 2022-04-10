create sequence hibernate_sequence;

create table customer_account (
    account_number varchar(255) not null primary key,

    balance double,

    transactions_today integer,

    deposits_today integer,
    total_deposits_today double,

    withdrawals_today integer,
    total_withdrawals_today double,

    date_time_created date,
    last_modified_date date,

    ip_address varchar(160)

);
