FROM ubuntu:20.10


RUN apt-get update
RUN apt-get install -y libc6 net-tools iproute2 iputils-ping

COPY ./target/release/utxo-workshop ./target/release/utxo-workshop
COPY run.sh run.sh

#ENTRYPOINT [ "bash","run.sh","2>", "/var/tmp/output.log"]
ENTRYPOINT ["/bin/bash","./run.sh"]
#ENTRYPOINT ./utxo-workshop --chain=local --base-path ./tmp/one/ --validator --port 30333 --ws-port 9944 --unsafe-ws-external 2> /var/tmp/output.log
#ENTRYPOINT ["/bin/sh","-c","./utxo-workshop","--chain=local","--base-path","./tmp/one/","--validator","--port","30333","--ws-port","9944","--unsafe-ws-external","2>","/var/tmp/output.log"]
#CMD ["--name","felix"]
#CMD ["2>","/var/tmp/output.log"]