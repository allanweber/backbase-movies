#!/bin/bash

docker rm -f movies_api movies

docker rmi -f movies_api:latest mongo:latest mongo:5.0.7