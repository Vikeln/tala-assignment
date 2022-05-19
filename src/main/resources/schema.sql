create sequence hibernate_sequence;

create table customer_account
(
    account_number     varchar(255) not null primary key,

    balance            double,

    date_time_created  date,
    last_modified_date date,

    ip_address         varchar(160)

);


create table customer_account_transaction
(
    id                      int          not null primary key,

    customer_account        varchar(255) not null,

    transaction_status      varchar(20)  not null,

    comments                varchar(255) not null,

    transactionType         int          not null,

    transaction_ref         varchar(10)  not null,

    amount_transferred      double,

    account_opening_balance double,

    account_closing_balance double,

    date_time_created       date,
    last_modified_date      date,

    ip_address              varchar(160)

);
