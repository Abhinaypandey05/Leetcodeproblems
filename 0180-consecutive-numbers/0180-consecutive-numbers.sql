
SELECT DISTINCT Num AS ConsecutiveNums
FROM (
    SELECT
        Num,
        LAG(Num, 1) OVER (ORDER BY id) AS prev_num,
        LEAD(Num, 1) OVER (ORDER BY id) AS next_num
    FROM Logs
) t
WHERE Num = prev_num
  AND Num = next_num;