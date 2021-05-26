#!/bin/bash

if [ "$#" -lt 5 ]; then
  echo "insufficient number of arguments"
  echo "script usage: /host_usage.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# get hostname to retrieve host_id from host_info
hostname=$(hostname -f)

# get server usage data
vmstat_out=`vmstat -t --unit M`

timestamp=$(echo "$vmstat_out" | tail -1 | awk '{print $(NF-1), $(NF)}')
memory_free=$(echo "$vmstat_out" | tail -1 | awk '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_out" | tail -1 | awk '{print $(NF-4)}' | xargs)
cpu_kernel=$(echo "$vmstat_out" | tail -1 | awk '{print $(NF-5)}' | xargs)
disk_io=$(vmstat -d --unit M | tail -1 | awk '{print $(NF-1)}' | xargs)
disk_available=$(df -BM / | awk 'NR==2 {print $4}' | sed 's/M//g' | xargs)

# create psql statements
host_id_stmt="(SELECT id from host_info WHERE hostname='$hostname')"

insert_stmt=$(cat <<-END
INSERT INTO host_usage
("timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES
('$timestamp', $host_id_stmt, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)
END
)

# insert data to psql
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"

exit $?