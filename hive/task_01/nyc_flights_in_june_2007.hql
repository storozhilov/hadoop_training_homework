SELECT COUNT(*)
FROM (
  SELECT *
  FROM flights a
  JOIN vw_airports b ON a.Origin = b.iata
  WHERE a.year = 2007 AND a.month = 6
  AND b.city = "New York"
  UNION ALL
  SELECT *
  FROM flights c
  JOIN vw_airports d ON c.Dest = d.iata
  WHERE c.year = 2007 AND c.month = 6
  AND d.city = "New York"
) e;
