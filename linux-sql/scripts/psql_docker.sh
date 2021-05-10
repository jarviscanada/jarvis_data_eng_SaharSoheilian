#!/bin/bash

usage=$1
db_username=$2
db_password=$3
docker_exists=$(docker container ls -a -f name=jrvs-psql | wc -l)

systemctl status docker || systemctl start docker

case $usage in
  create)
    if [ "$docker_exists" = 2 ]; then
      echo "container is already created"
      exit 1
    elif [ "$#" -ne 3 ]; then
      echo "\"db_username\" or \"db_password\" is not given"
      exit 1
    else
      docker volume create pgdata
      docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
      exit $?
    fi
    ;;
  start)
    if [ "$docker_exists" -ne 2 ]; then
      echo "container is not created"
      exit 1
    else
      docker container start jrvs-psql
      exit $?
    fi
    ;;
  stop)
    if [ "$docker_exists" -ne 2 ]; then
        echo "container is not created"
        exit 1
    else
      docker container stop jrvs-psql
      exit  $?
    fi
    ;;
  *)
    echo "unknown: $usage"
    exit 1
esac

