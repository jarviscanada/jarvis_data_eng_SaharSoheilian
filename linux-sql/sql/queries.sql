-- round timestamp to the nearest 5 min interval
CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS $$
BEGIN
    RETURN
        date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$ LANGUAGE PLPGSQL;

-- Group hosts by hardware info
SELECT
    cpu_number,
    id as host_id,
    total_mem,
    row_number() OVER (
        PARTITION BY cpu_number ORDER BY total_mem DESC)
FROM host_info;

-- Average memory usage
SELECT
    usage.host_id,
    info.hostname,
    round5(usage.timestamp) AS ts,
    round(AVG(100*(info.total_mem - usage.memory_free)/info.total_mem), 4) AS avg_used_mem_percentage
FROM host_usage AS usage
    LEFT JOIN host_info AS info ON usage.host_id=info.id
    GROUP BY usage.host_id, info.hostname, ts ORDER BY info.hostname, ts;

-- Detect host failure
SELECT
    host_id,
    round5(timestamp) AS ts,
    COUNT(*) AS num_data_points
FROM host_usage
    GROUP BY host_id, ts HAVING COUNT(*) < 3;