-- ---------------------------------------
-- CUSTOMER INSERT
-- ---------------------------------------

INSERT INTO customer (first_name, last_name, email, create_date_time, update_time, deleted) VALUES ( 'James', 'Smith', 'JamesSmith@example.com', '2022-04-21 10:00:00', '2022-04-21 10:00:00', 0);
INSERT INTO customer (first_name, last_name, email, create_date_time, update_time, deleted) VALUES ( 'Christopher', 'Anderson', 'ChristopherAnderson@example.com', '2022-04-22 12:00:00', '2022-04-22 12:00:00', 0);
INSERT INTO customer (first_name, last_name, email, create_date_time, update_time, deleted) VALUES ( 'Ronald', 'Clark', 'RonaldClark@example.com', '2022-04-22 12:00:00', '2022-04-22 12:00:00', 0);
INSERT INTO customer (first_name, last_name, email, create_date_time, update_time, deleted) VALUES ( 'Mary', 'Wright', 'MaryWright@example.com', '2022-04-22 12:00:00', '2022-04-22 12:00:00', 0);

-- ---------------------------------------
-- ACCOUNT INSERT
-- ---------------------------------------
INSERT INTO account (balance, account_type, create_date_time, update_time, customer_id, deleted)
VALUES (5000, 0, '2022-04-20T12:34:56', '2022-04-20T12:34:56', 1, false);

INSERT INTO account (balance, account_type, create_date_time, update_time, customer_id, deleted)
VALUES (100, 0, '2022-04-21T12:34:56', '2022-04-21T12:34:56', 1, false);

INSERT INTO account (balance, account_type, create_date_time, update_time, customer_id, deleted)
VALUES (900, 0, '2022-04-21T12:34:56', '2022-04-21T12:34:56', 2, false);
INSERT INTO account (balance, account_type, create_date_time, update_time, customer_id, deleted)
VALUES (900, 0, '2022-04-21T12:34:56', '2022-04-21T12:34:56', 3, false);


