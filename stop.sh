#!/bin/bash
cat modules | xargs -i docker stop {}
cat modules | xargs -i docker rm {}
cat modules | xargs -i docker rmi docker_{}
cat modules | xargs -i docker rmi docker-{}
rm -rf ./docker/mysql/data
