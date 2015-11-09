#!/bin/sh

beeline -n hadoop -u jdbc:hive2://localhost:10000 -f create_flights_table.hql
beeline -n hadoop -u jdbc:hive2://localhost:10000 -f create_airports_table.hql
