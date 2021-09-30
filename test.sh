#!/bin/bash
bootnodes=""
if [[ -n $1 ]]; then
    bootnodes="--bootnodes $(<$1)"
fi
echo "$bootnodes something"