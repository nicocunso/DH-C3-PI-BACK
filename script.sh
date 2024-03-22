#!/bin/bash

sudo docker compose down
sudo docker image rm img-carbook-backend
sudo docker image build -t img-carbook-backend .
sudo docker compose up -d