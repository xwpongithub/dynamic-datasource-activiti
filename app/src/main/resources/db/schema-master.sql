DROP TABLE IF EXISTS "public"."m_customer";
CREATE TABLE "public"."m_customer" (
                                       "id" int8 NOT NULL,
                                       "name" varchar(255) COLLATE "pg_catalog"."default",
                                       "username" varchar(255) COLLATE "pg_catalog"."default",
                                       "password" varchar(255) COLLATE "pg_catalog"."default",
                                       "driver" varchar(255) COLLATE "pg_catalog"."default",
                                       "url" varchar(255) COLLATE "pg_catalog"."default",
                                       PRIMARY KEY ("id")
)
;

INSERT INTO "public"."m_customer" ("id", "name", "username", "password", "driver", "url") VALUES (1, 'master', 'postgres', '123456', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/dynamic_ds_master');
INSERT INTO "public"."m_customer" ("id", "name", "username", "password", "driver", "url") VALUES (2, 'order', 'postgres', '123456', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/seata_order');
INSERT INTO "public"."m_customer" ("id", "name", "username", "password", "driver", "url") VALUES (3, 'account', 'postgres', '123456', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/seata_account');
INSERT INTO "public"."m_customer" ("id", "name", "username", "password", "driver", "url") VALUES (4, 'product', 'postgres', '123456', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/seata_product');
