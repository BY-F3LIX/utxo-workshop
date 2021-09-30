#!/bin/bash
if [[ -z $1 ]]; then
    echo "no args provided"
    exit
fi

ID=$(docker ps | grep _$1_ | grep -Eo '^\S+')

if [[ -z $ID ]]; then
    exit
fi

docker inspect $ID | grep IPAddress | grep -Eo '([0-9]+\.){3}[0-9]+'
