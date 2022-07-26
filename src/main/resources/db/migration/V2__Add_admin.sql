insert into usr (id, name, password, email, active)
values (1, 'admin', '1234','love@love.com', true);

insert into usr_role (usr_id, roles)
values (1, 'USER'), (1, 'ADMIN');