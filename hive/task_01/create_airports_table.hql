CREATE EXTERNAL TABLE airports (
	iata STRING,
	airport STRING,
	city STRING,
	state STRING,
	country STRING,
	lat DOUBLE,
	long DOUBLE)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE LOCATION '/home/illy/hive/task_01/input/airports'
TBLPROPERTIES ("skip.header.line.count"="1");

CREATE VIEW vw_airports (iata, airport, city, state, country, lat, long)
AS SELECT
	regexp_replace(iata, "\"", ""),
	regexp_replace(airport, "\"", ""),
	regexp_replace(city, "\"", ""),
	regexp_replace(state, "\"", ""),
	regexp_replace(country, "\"", ""),
	lat,
	long
FROM airports;
