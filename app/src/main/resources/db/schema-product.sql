DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id        serial,
    price       decimal(10,2)   DEFAULT NULL,
    stock      int4 DEFAULT NULL,
    last_update_time TIMESTAMPTZ(0) DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.undo_log
(
    id            SERIAL       NOT NULL,
    branch_id     BIGINT       NOT NULL,
    xid           VARCHAR(128) NOT NULL,
    context       VARCHAR(128) NOT NULL,
    rollback_info BYTEA        NOT NULL,
    log_status    INT          NOT NULL,
    log_created   TIMESTAMPTZ(0) NOT NULL,
    log_modified  TIMESTAMPTZ(0) NOT NULL,
    CONSTRAINT pk_undo_log PRIMARY KEY (id),
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
    );

CREATE SEQUENCE IF NOT EXISTS undo_log_id_seq INCREMENT BY 1 MINVALUE 1 ;


insert into product (id, price, stock)
VALUES (1, 10, 20);
