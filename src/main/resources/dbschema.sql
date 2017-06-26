/*
visual representation od schema :
http://dbdesigner.net/registrations/invite/QkFoN0J6b01kWE5sY2w5cFpHa0QvUlFCT2d4bGVIQnBjbVZ6VlRvZ1FXTjBhWFpsVTNWd2NHOXlkRG82VkdsdFpWZHBkR2hhYjI1bFd3aEpkVG9KVkdsdFpRMXdXUjNBVXhrZ253azZEVzVoYm05ZmJuVnRhUUlJQVRvTmJtRnViMTlrWlc1cEJqb05jM1ZpYldsamNtOGlCeVpBT2dsNmIyNWxTU0lJVlZSREJqb0dSVVpKSWdoVlZFTUdPdzFVUUFvPS0tYWI5NTUxMGM5MWFiOGI0ZTRlODU1YWRkY2U3OWJkN2E2YTZmZDBkMA%3D%3D
*/
DROP TABLE IF EXISTS "orders" CASCADE;
CREATE TABLE "orders" (
  "id"          SERIAL         NOT NULL,
  "title"       VARCHAR(200)   NOT NULL,
  "customer_id" INT            NOT NULL,
  "date"        TIMESTAMP WITH TIME ZONE,
  "description" VARCHAR(2000)  NOT NULL,
  "price"       NUMERIC(12, 2) NOT NULL,
  "private"     BOOLEAN        NOT NULL DEFAULT FALSE,
  "finished"    BOOLEAN        NOT NULL DEFAULT FALSE,
  CONSTRAINT orders_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);

DROP TABLE IF EXISTS "car_catalog" CASCADE;
CREATE TABLE "car_catalog" (
  "id"       SERIAL         NOT NULL,
  "car_type" INT            NOT NULL,
  "capacity" NUMERIC(12, 2) NOT NULL,
  "load"     INT            NOT NULL,
  CONSTRAINT car_catalog_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);

DROP TABLE IF EXISTS "phones" CASCADE;
CREATE TABLE "phones" (
  "id"      SERIAL      NOT NULL,
  "user_id" INT         NOT NULL,
  "number"   VARCHAR(13) NOT NULL UNIQUE,
  CONSTRAINT phones_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "users" CASCADE;
CREATE TABLE "users" (
  "id"       SERIAL      NOT NULL,
  "login"    VARCHAR(15) NOT NULL UNIQUE,
  "password" VARCHAR(200) NOT NULL,
  "email"    VARCHAR(30) UNIQUE,
  CONSTRAINT users_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "car_type" CASCADE;
CREATE TABLE "car_type" (
  "id"   SERIAL      NOT NULL,
  "type" VARCHAR(50) NOT NULL UNIQUE,
  CONSTRAINT car_type_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "user_cars" CASCADE;
CREATE TABLE "user_cars" (
  "id"          SERIAL       NOT NULL,
  "car_id"      INT          NOT NULL,
  "user_id"     INT          NOT NULL,
  "description" VARCHAR(2000),
  "location"    VARCHAR(200) NOT NULL,
  CONSTRAINT user_cars_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "bids" CASCADE;
CREATE TABLE "bids" (
  "id"          SERIAL NOT NULL,
  "executor_id" INT    NOT NULL,
  "notes"       VARCHAR(2000),
  "date"        TIMESTAMP WITH TIME ZONE,
  "price"       NUMERIC(12, 2),
  "order_id"    INT    NOT NULL,
  CONSTRAINT bids_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "offers" CASCADE;
CREATE TABLE "offers" (
  "id"          SERIAL         NOT NULL,
  "user_car"    INT            NOT NULL,
  "description" VARCHAR(2000),
  "price"       NUMERIC(12, 2) NOT NULL,
  CONSTRAINT offers_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "order_execution" CASCADE;
CREATE TABLE "order_execution" (
  "id"             SERIAL NOT NULL,
  "bid_id"         INT    NOT NULL,
  "note"           VARCHAR(2000),
  "success_finish" BOOLEAN DEFAULT NULL,
  CONSTRAINT order_execution_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "reviews" CASCADE;
CREATE TABLE "reviews" (
  "id"                 SERIAL NOT NULL,
  "order_execution_id" INT    NOT NULL,
  "executor_review"    VARCHAR(2000),
  "customer_review"    VARCHAR(2000),
  "customer_rating"    INT,
  "executor_rating"    INT,
  CONSTRAINT reviews_pk PRIMARY KEY ("id"),
  CONSTRAINT valid_customer_rating CHECK (customer_rating IS NULL OR
                                          (customer_rating >= 0 OR
                                           customer_rating <= 10) ),
  CONSTRAINT valid_executor_rating CHECK (executor_rating IS NULL OR
                                          (executor_rating >= 0 OR
                                           executor_rating <= 10) )
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "roles" CASCADE;
CREATE TABLE "roles" (
  "id"   SERIAL      NOT NULL,
  "role" VARCHAR(10) NOT NULL UNIQUE,
  CONSTRAINT roles_pk PRIMARY KEY ("id")
) WITH (
OIDS = FALSE
);


DROP TABLE IF EXISTS "user_roles" CASCADE;
CREATE TABLE "user_roles" (
  "user_id" INT NOT NULL,
  "role_id" INT NOT NULL
) WITH (
OIDS = FALSE
);
ALTER TABLE public.user_roles ADD id SERIAL NOT NULL;
ALTER TABLE public.user_roles ADD CONSTRAINT user_roles_id_pk PRIMARY KEY (id);


ALTER TABLE "orders"
  ADD CONSTRAINT "orders_fk0" FOREIGN KEY ("customer_id")
REFERENCES "users" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "car_catalog"
  ADD CONSTRAINT "car_catalog_fk0" FOREIGN KEY ("car_type") REFERENCES
  "car_type" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "phones"
  ADD CONSTRAINT "phones_fk0" FOREIGN KEY ("user_id")
REFERENCES "users" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "user_cars"
  ADD CONSTRAINT "user_cars_fk0" FOREIGN KEY ("car_id")
REFERENCES "car_catalog" ("id");

ALTER TABLE "user_cars"
  ADD CONSTRAINT "user_cars_fk1" FOREIGN KEY
  ("user_id") REFERENCES "users" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "bids"
  ADD CONSTRAINT "bids_fk0" FOREIGN KEY ("executor_id")
REFERENCES "users" ("id");

ALTER TABLE "bids"
  ADD CONSTRAINT "bids_fk1" FOREIGN KEY ("order_id")
REFERENCES "orders" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "order_execution"
  ADD CONSTRAINT "order_execution_fk0" FOREIGN
KEY ("bid_id") REFERENCES "bids" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "reviews"
  ADD CONSTRAINT "reviews_fk0" FOREIGN KEY
  ("order_execution_id") REFERENCES "order_execution" ("id") ON DELETE
RESTRICT ON UPDATE CASCADE;

ALTER TABLE "offers"
  ADD CONSTRAINT "offers_fk0" FOREIGN KEY ("user_car")
REFERENCES "user_cars" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "user_roles"
  ADD CONSTRAINT "user_roles_fk0" FOREIGN KEY
  ("user_id") REFERENCES "users" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "user_roles"
  ADD CONSTRAINT "user_roles_fk1" FOREIGN KEY ("role_id") REFERENCES "roles" ("id")
ON DELETE RESTRICT ON UPDATE CASCADE;