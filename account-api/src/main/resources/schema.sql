-- ---------------------------------------
-- CREATE TABLES
-- ---------------------------------------

CREATE TABLE customer (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255),
                          deleted boolean NOT NULL,
                          create_date_time DATETIME NOT NULL,
                          update_time DATETIME NOT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE account (
                         id bigint AUTO_INCREMENT NOT NULL,
                         customer_id bigint,
                         balance double NOT NULL,
                         account_type tinyint NOT NULL,
                         deleted boolean NOT NULL,
                         create_date_time timestamp NOT NULL,
                         update_time timestamp NOT NULL,
                         PRIMARY KEY (id),
                         CONSTRAINT FKdxvg1lsjh48i89v9p62n99cya FOREIGN KEY (customer_id) REFERENCES customer (id)
);
