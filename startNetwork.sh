#!/bin/bash
sudo rm ~/logs/* 
sudo chmod 666 /var/run/docker.sock
docker network prune -f 
docker network create net0
docker network create net1
docker network create net2
docker network create net3
docker network create net4
docker network create net5
docker network create connect0-0
docker network create connect0-1
docker network create connect0-2
docker network create connect1-0
docker network create connect1-1
docker network create connect1-2
docker network create connect2-0
docker network create connect2-1
docker network create connect2-2
docker network create connect3-0
docker network create connect3-1
docker network create connect3-2
docker network create connect4-0
docker network create connect4-1
docker network create connect4-2
docker network create connect5-0
docker network create connect5-1
docker network create connect5-2
docker-compose up -d --remove-orphans Node0 Node1 Node3 Node4 Node5 Node6 Node7 Node8 Node9 Node10 Node11 Node12 Node14 Node16 Node17 Node18 Node19 Node20 Node21 Node22 Node23 Node24 Node25 Node26 Node27 Node28 Node29 Node30 Node31 Node32 Node33 Node34 Node35 Node36 Node37 Node38 Node39 Node40 Node41 Node42 Node43 Node44 Node45 Node46 Node47 Node48 Node49 Node50 Node51 Node52 Node53 Node54 Node55 Node56 Node57 Node58 Node60 Node61 Node62 Node63 Node64 Node65 Node66 Node67 Node68 Node69 Node70 Node71 Node72 Node73 Node75 Node76 Node77 Node78 Node79 Node80 Node81 Node83 Node84 Node85 Node86 Node87 Node88 Node89 Node90 Node91 Node92 Node93 Node94 Node95 Node96 Node97 Node98 Node99 Node100 Node101 Node102 Node103 Node105 Node106 Node107 Node108 Node109 Node110 Node111 Node112 Node113 Node114 Node115 Node116 Node117 Node118 Node119 Node120 Node121 Node123 Node124 Node125 Node127 Node128 Node129 Node130 Node131 Node132 Node133 Node134 Node135 Node136 Node137 Node138 Node139 Node140 Node141 Node142 Node143 Node144 Node145 Node146 Node148 Node149 Node150 Node151 Node152 Node153 Node154 Node155 Node156 Node157 Node158 Node160 Node161 Node162 Node163 Node164 Node166 Node168 Node169 Node170 Node171 Node172 Node173 Node174 Node175 Node176 Node177 Node178 Node179 Node180 Node181 Node182 Node185 Node186 Node187 Node189 Node190 Node191 Node192 Node193 Node194 Node195 Node196 Node197 Node198 Node199 Node200 Node201 Node202 Node203 Node204 Node205
sleep 10
java -jar /home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/App.jar
docker-compose up -d --remove-orphans Node2 Node13 Node15 Node59 Node74 Node82 Node104 Node122 Node126 Node147 Node159 Node165 Node167 Node183 Node184 Node188
