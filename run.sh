#!/bin/bash
name=$1
# $2 --validator
if [[ -z $name ]]; then
    name="Random-Node-$RANDOM" 
fi


./target/release/utxo-workshop --chain=local --tmp --name $name $2 --port 30333 --ws-port 9944 --unsafe-ws-external 2> /var/tmp/Log-$name.log