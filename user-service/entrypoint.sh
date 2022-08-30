#!/bin/sh
set -e

host="config-server"
port="8888"
cmd="$@"


until curl http://"$host":"$port"; do
  >&2 echo "Config-server not available"
  sleep 10
done

>&2 echo "Config-server is available"

exec $cmd