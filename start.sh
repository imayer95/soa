#!/usr/bin/env bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

# build common
cd common
mvn clean install

# build warehouse
cd ../warehouse/
mvn clean install

# build operations
cd ../operations/
mvn clean install

cd ..
docker-compose up -d --build

echo
echo Application has started!
