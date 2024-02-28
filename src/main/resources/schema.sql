drop table books if exists;
drop table borrowed_books if exists;
create table books (
    id identity primary key,
    isbn varchar(50) unique not null,
    title varchar(50) not null,
    genre varchar(50) not null,
    description varchar(200) not null,
    author varchar(100) not null
);
create table borrowed_books (
    id identity primary key,
    book bigint not null,
    borrowed_date date,
    borrowed_time time,
    return_date date,
    return_time time
);