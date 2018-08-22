create table client_user(
  id bigserial primary key,
  username varchar(100),
  password varchar(50),
  access_token varchar(100) NULL,
  access_token_validity timestamp NULL,
  refresh_token varchar(100) NULL
);

insert into client_user (username, password) values ('aeloy', 'abc');
