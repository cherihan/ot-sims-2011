drop table if exists departures;
create table departures (
  id integer primary key autoincrement,
  starts_from string not null,
  goes_to string not null,
  ts timestamp not null
);

