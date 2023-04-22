-- ---------------------------------------
-- TRANSACTION INSERT
-- ---------------------------------------
INSERT INTO transaction (amount, create_date_time, update_time, account_id, deleted)
VALUES (5000, '2022-04-23T12:34:56', '2022-04-23T12:34:56', 1, false);

INSERT INTO transaction (amount, create_date_time, update_time, account_id, deleted)
VALUES (100, '2022-04-23T12:34:56', '2022-04-23T12:34:56', 1, false);

INSERT INTO transaction (amount, create_date_time, update_time, account_id, deleted)
    VALUES (900, '2022-04-23T12:34:56', '2022-04-23T12:34:56', 3, false);