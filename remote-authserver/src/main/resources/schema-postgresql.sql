create table resource_owner(
  id bigserial primary key,
  name varchar(200),
  username varchar(60),
  password varchar(100),
  email varchar(100)
);