-- ---------------------------------------
-- CREATE TABLES
-- ---------------------------------------
CREATE TABLE transaction (
                         id bigint AUTO_INCREMENT NOT NULL,
                         account_id bigint,
                         amount double NOT NULL,
                         deleted boolean NOT NULL,
                         create_date_time timestamp NOT NULL,
                         update_time timestamp NOT NULL,
                         PRIMARY KEY (id)
);
