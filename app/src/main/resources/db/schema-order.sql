-- 逐渐自增版本
DROP TABLE IF EXISTS p_order;
CREATE TABLE p_order
(
    id    serial,
    user_id          int8 DEFAULT NULL,
    product_id       int8 DEFAULT NULL,
    amount           int4 DEFAULT NULL,
    total_price      decimal(10,2)       DEFAULT NULL,
    status           VARCHAR(100) DEFAULT NULL,
    add_time         TIMESTAMPTZ(0)     DEFAULT CURRENT_TIMESTAMP,
    last_update_time TIMESTAMPTZ(0)     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.undo_log;
CREATE TABLE public.undo_log
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


truncate table p_order;
truncate table undo_log restart identity;
