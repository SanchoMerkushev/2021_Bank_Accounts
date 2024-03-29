DROP OWNED BY bank_user;
DROP USER IF EXISTS bank_user;

DROP DATABASE IF EXISTS bank_db;
CREATE DATABASE bank_db;

CREATE USER bank_user WITH ENCRYPTED PASSWORD '0';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bank_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO bank_user;

\connect bank_db
SET ROLE bank_user;
