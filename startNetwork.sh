#!/bin/bash
sudo rm ~/logs/* 
sudo chmod 666 /var/run/docker.sock
docker network prune -f 
docker network create net0
docker network create net1
docker network create net2
docker network create connect0-0
docker network create connect0-1
docker network create connect0-2
docker network create connect0-3
docker network create connect1-0
docker network create connect1-1
docker network create connect1-2
docker network create connect1-3
docker network create connect2-0
docker network create connect2-1
docker network create connect2-2
docker network create connect2-3
docker network create connect3-0
docker network create connect3-1
docker network create connect3-2
docker network create connect3-3
docker network create connect4-0
docker network create connect4-1
docker network create connect4-2
docker network create connect4-3
docker-compose up -d --remove-orphans Node0 Node1 Node2 Node3 Node4 Node5 Node6 Node7 Node8 Node9 Node10 Node11 Node12 Node13 Node14 Node15 Node16 Node17 Node18 Node19 Node20
docker-compose up -d --remove-orphans Node21 Node22 Node23 Node24 Node25 Node26 Node27 Node28 Node30 Node31 Node32 Node33 Node34 Node35 Node36 Node37 Node38 Node39 Node40 Node41 Node42
docker-compose up -d --remove-orphans Node43 Node44 Node45 Node46 Node47 Node48 Node49 Node50 Node51 Node52 Node53 Node54 Node55 Node56 Node57 Node58 Node59 Node60 Node61 Node63 Node64
docker-compose up -d --remove-orphans Node65 Node66 Node67 Node68 Node70 Node71 Node72 Node73 Node74 Node75 Node76 Node77 Node78 Node79 Node80 Node81 Node83 Node84 Node85 Node86 Node87
docker-compose up -d --remove-orphans Node88 Node89 Node90 Node91 Node93 Node94 Node95 Node96 Node97 Node98 Node99 Node100 Node101 Node102 Node103 Node104 Node105 Node106 Node108 Node109 Node110
docker-compose up -d --remove-orphans Node111 Node112 Node113 Node115 Node116 Node117 Node118 Node119 Node120 Node121 Node122 Node123 Node124 Node125 Node126 Node128 Node129 Node130 Node131 Node132 Node133
docker-compose up -d --remove-orphans Node134 Node135 Node136 Node137 Node138 Node139 Node140 Node141 Node142 Node143 Node144 Node145 Node146 Node147 Node148 Node149 Node150 Node151 Node152 Node153 Node154
docker-compose up -d --remove-orphans Node155 Node156 Node157 Node158 Node159 Node160 Node161 Node162 Node163 Node164 Node165 Node166 Node167 Node168 Node169 Node171 Node172 Node173 Node174 Node175 Node178
docker-compose up -d --remove-orphans Node179 Node180 Node181 Node182 Node183 Node184 Node185 Node186 Node187 Node189 Node190 Node191 Node192 Node193 Node194 Node195 Node196 Node197 Node198 Node200 Node201
docker-compose up -d --remove-orphans Node202 Node203 Node204 Node206 Node207 Node208 Node209 Node210 Node211 Node212 Node214 Node215 Node216 Node217 Node218 Node219 Node220 Node221 Node222 Node223 Node224
docker-compose up -d --remove-orphans Node225 Node226 Node227 Node228 Node229 Node230 Node231 Node232 Node233 Node234 Node235 Node236 Node237 Node238 Node239 Node240 Node241 Node242 Node243 Node244 Node245
docker-compose up -d --remove-orphans Node246 Node247 Node248 Node249 Node250 Node252 Node253 Node254 Node255 Node256 Node257 Node258 Node259 Node260 Node261 Node262 Node263 Node264 Node265 Node266 Node267
docker-compose up -d --remove-orphans Node268 Node269 Node270 Node271 Node272 Node273 Node274 Node275 Node276 Node277 Node278 Node279 Node280 Node281 Node282 Node283 Node284 Node285 Node286 Node287 Node288
docker-compose up -d --remove-orphans Node289 Node290 Node291 Node292 Node293 Node294 Node295 Node296 Node297 Node298 Node299 Node300 Node301 Node302 Node303 Node304 Node305 Node306 Node307 Node308 Node310
docker-compose up -d --remove-orphans Node311 Node312 Node313 Node314 Node315 Node316 Node317 Node318 Node319 Node320 Node321 Node322 Node323 Node324 Node325 Node326 Node327 Node328 Node329 Node330 Node331
docker-compose up -d --remove-orphans Node332 Node334 Node335 Node336 Node337 Node338 Node339 Node340 Node341 Node342 Node344 Node345 Node346 Node347 Node348 Node349 Node350 Node351 Node352 Node353 Node354
sleep 10
sudo java -jar ./network_generator/App.jar
docker-compose up -d --remove-orphans Node29 Node62 Node69 Node82 Node92 Node107 Node114 Node127 Node170 Node176 Node177 Node188 Node199 Node205 Node213 Node251 Node309 Node333 Node343
