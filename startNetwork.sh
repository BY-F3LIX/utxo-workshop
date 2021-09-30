#!/bin/bash
sudo rm ~/logs/* 
sudo chmod 666 /var/run/docker.sock
docker network prune -f 
net0=$(docker network create net0 | cut -c 1-12)
sudo tc qdisc add dev br-$net0 root netem delay 500ms
net1=$(docker network create net1 | cut -c 1-12)
sudo tc qdisc add dev br-$net1 root netem delay 500ms
net2=$(docker network create net2 | cut -c 1-12)
sudo tc qdisc add dev br-$net2 root netem delay 500ms
connect0=$(docker network create connect0 | cut -c 1-12)
sudo tc qdisc add dev br-$connect0 root netem delay 500ms
connect1=$(docker network create connect1 | cut -c 1-12)
sudo tc qdisc add dev br-$connect1 root netem delay 500ms
connect2=$(docker network create connect2 | cut -c 1-12)
sudo tc qdisc add dev br-$connect2 root netem delay 500ms
docker-compose up -d --remove-orphans Node0 Node1 Node3 Node4 Node5 Node6 Node7 Node8 Node10 Node12 Node13 Node14 Node15 Node16 Node17 Node19 Node20 Node23 Node25 Node26 ConNode0 ConNode1 ConNode2
sleep 10
java -jar /home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/App.jar
docker-compose up -d --remove-orphans Node2 Node9 Node11 Node18 Node21 Node22 Node24
