#!/usr/bin/env bash
set -e

cd docker
docker compose down
docker compose up -d