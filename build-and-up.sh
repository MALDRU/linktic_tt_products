#!/bin/bash
set -e

echo "🧹 Limpiando y construyendo la aplicación con Gradle..."
./gradlew clean build

echo "🐳 Construyendo y levantando servicios con Docker Compose..."
docker-compose up -d