#!/bin/bash

if [ "$#" -lt 5 ]; then
  echo "insufficient number of arguments"
  echo "script usage: /host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# get hardware specification
hostname=$(hostname -f)
lscpu_out=`lscpu`
meminfo_out=$(cat /proc/meminfo)

cpu_number=$(echo "$lscpu_out"  | grep -E "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | grep -E "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | grep -E "Model name:" | sed 's/Model name://g' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | grep -E "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | grep -E "L2 cache:" | awk '{print $3}' | sed 's/K//g' | xargs)
total_mem=$(echo "$meminfo_out"  | grep -E "MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(vmstat -t | tail -1 | awk '{print $(NF-1), $(NF)}')

# psql insert statement
insert_stmt=$(cat <<-END
INSERT INTO host_info
(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem,"timestamp")
VALUES
('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp')
END
)

# insert data to psql
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"

exit $?