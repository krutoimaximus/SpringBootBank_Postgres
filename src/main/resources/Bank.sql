INSERT INTO public.users (user_id, password, username) VALUES (1, 'admin', 'admin');
INSERT INTO public.users (user_id, password, username) VALUES (53, 'user', 'user');
INSERT INTO public.client (id, address, cnp, email, name) VALUES (1, 'Penza', '768696', 'orge', 'Max');
INSERT INTO public.client (id, address, cnp, email, name) VALUES (55, 'London', '1122334345678', 'dog@ya.com', 'Dog');
INSERT INTO public.client (id, address, cnp, email, name) VALUES (57, 'Berlin', '1123434455678', 'abc_com@ya.com', 'Win');
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (47, 'abc@ya.com', 'Mage', '256595', 500, null);
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (49, 'abc@ya.com', 'Mage', '256595', 500, null);
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (52, 'tyty@ya.com', 'Boss', '+7 927 0971866', 10000, null);
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (47, 'abc@ya.com', 'Mage', '256595', 500, null);
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (49, 'abc@ya.com', 'Mage', '256595', 500, null);
INSERT INTO public.employee (id, email, name, phone, salary, user_id) VALUES (52, 'tyty@ya.com', 'Boss', '+7 927 0971866', 10000, null);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (43, 'Employee updated. ID: 24', '2022-08-05 12:18:43.482000', null);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (46, 'Employee updated. ID: 24', '2022-08-05 12:22:44.730000', null);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (51, 'Employee added: ID: 49', '2022-08-05 12:49:36.067000', 1);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (54, 'Employee added: ID: 52', '2022-08-05 14:16:22.086000', 1);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (56, 'Client added. ID: 55; Name: Dog', '2022-08-05 14:21:08.596000', 1);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (58, 'Client added. ID: 57; Name: Win', '2022-08-05 14:22:05.580000', 1);
INSERT INTO public.log (id, operation, timestamp, user_user_id) VALUES (60, 'Account added. ID: 59; Owner: Dog', '2022-08-05 14:22:50.906000', 1);
INSERT INTO public.role (role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (role_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (53, 2);
INSERT INTO public.account (id, balance, date, type, owner_id) VALUES (59, 10000, '2022-08-05 14:22:50.759000', 'Debit', 55);