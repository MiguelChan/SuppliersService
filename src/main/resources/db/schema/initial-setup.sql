-- The commands below need to be run with the admin user.
create user flyway_user with password 'flyway-user-password';
create user app_user with password 'app-user-password';

create schema suppliersdb;
grant all privileges on schema suppliersdb to flyway_user;
grant usage on schema suppliersdb to app_user;
grant all privileges on all tables in schema suppliersdb to app_user;
alter default privileges in schema suppliersdb grant all on tables to app_user;