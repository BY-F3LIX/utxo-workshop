#!/bin/bash
name=$1
# $2 --validator
if [[ -z $name ]]; then
    name="Random-Node-$RANDOM" 
fi
bootnodes=""
if [[ -n $3 ]]; then
    bootnodes="--bootnodes $(<$3)"
    if [[ -z $(<$3) ]]; then
        bootnodes=""
        echo "List Empty"
    fi
fi

if [[ -n $4 ]]; then
    echo "add delay:"
    interfaces=$(ifconfig |  grep -Eo 'eth[0-9]+')
    ping -c 3 www.google.com
    for i in $interfaces
    do
        tc qdisc add dev $i root netem delay $4ms
        echo "added $4ms * x delay to $i"
    done
    ping -c 3 www.google.com
    
fi

# --port 30333 --ws-port 9944 --discover-local --no-mdns
# --listen-addr
# --ipc-path
# --public-addr
./target/release/utxo-workshop --tmp --name $name $bootnodes $2 --rpc-cors all --prometheus-external --unsafe-rpc-external --unsafe-ws-external --no-telemetry --discover-local --no-prometheus 2> /var/tmp/Log-$name.log