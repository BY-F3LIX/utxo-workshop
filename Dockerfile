FROM ubuntu:20.10

COPY ./target/release/utxo-workshop utxo-workshop

RUN apt-get update
RUN apt-get install -y libc6
#RUN apt-get install -y rustc cargo

ENTRYPOINT ./utxo-workshop --chain=local --base-path ./tmp/one/ --validator --port 30333 --ws-port 9944