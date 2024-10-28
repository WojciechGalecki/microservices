#!/usr/bin/env bash
set -e

cd docker
docker compose -f docker-compose-kafka.yml down