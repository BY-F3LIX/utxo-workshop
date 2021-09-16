#!/bin/bash
name=$1
# $2 --validator
if [[ -z $name ]]; then
    name="Random-Node-$RANDOM" 
fi

public=""
listen=""

if [[ -n $3 ]]; then
    public="--public-addr $3"
    echo $public
fi

if [[ -n $4 ]]; then
    listen="--reserved-nodes $4" 
    echo $listen
fi
#64d6a1c09ea28d9e863d1cfe52e5d11fdcca4716febf0e5071299367b8eb15ac
#12D3KooWS5Z1PDPFVtNdAurtNxvHujpR8DRzbsbdgUofRP6B54SN
#12D3KooWS5Z1PDPFVtNdAurtNxvHujpR8DRzbsbdgUofRP6B54SN
# --port 30333 --ws-port 9944 --discover-local --no-mdns
# --listen-addr
# --ipc-path
# --public-addr
./target/release/utxo-workshop --tmp --name $name $2 $public $listen --rpc-cors all --prometheus-external --unsafe-rpc-external --unsafe-ws-external --no-telemetry --no-prometheus 2> /var/tmp/Log-$name.log