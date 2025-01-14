#!/bin/bash
cd frontend
npm run build
cd ../backend
docker build -t micamaca44/ticket4ticket .
docker push micamaca44/ticket4ticket:latest
