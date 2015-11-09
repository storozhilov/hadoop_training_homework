SELECT iata, airport, SUM(flights_amount) total_flights_amount
FROM (
  SELECT b.iata, b.airport, COUNT(*) flights_amount
  FROM flights a
  JOIN vw_airports b ON b.iata = a.Origin
  WHERE a.Year = 2007 AND a.Month IN (6, 7, 8)
  AND b.country = "USA"
  GROUP BY b.iata, b.airport
  
  UNION ALL
  
  SELECT d.iata, d.airport, COUNT(*) flights_amount
  FROM flights c
  JOIN vw_airports d ON d.iata = c.Dest
  WHERE c.Year = 2007 AND c.Month IN (6, 7, 8)
  AND d.country = "USA"
  GROUP BY d.iata, d.airport
) e
GROUP BY iata, airport
ORDER BY total_flights_amount DESC
LIMIT 5;
