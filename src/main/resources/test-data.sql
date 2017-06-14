INSERT INTO users (login, password, email)
VALUES ('ivanov', 'ivanov', 'ivanov@gmail.com');

INSERT INTO users (login, password, email)
VALUES ('petrov', 'petrov', 'petrov@gmail.com');

INSERT INTO users (login, password, email)
VALUES ('sidorov', 'sidorov', 'sidorov@gmail.com');

INSERT INTO car_type (type)
VALUES ('легковой'), ('грузопассажирский'), ('микроавтобус'),
  ('грузовой до 2 тонн'), ('грузовой от 2 до 5 тонн'),
  ('грузовой свыше 5 тонн'), ('длиномер');

INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='ivanov'),'+380671234567');
INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='ivanov'),'+380501234567');
INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='petrov'),'+380635557788');
INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='sidorov'),'+380731112233');
INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='sidorov'),'+380661112233');
INSERT INTO phones (user_id, phone)
VALUES ((select id from users where login='sidorov'),'+380951112233');