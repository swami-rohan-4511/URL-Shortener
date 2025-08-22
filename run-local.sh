#!/bin/bash

echo "Starting URL Shortener in development mode..."
echo ""
echo "This will use H2 in-memory database for local development"
echo "Access the application at: http://localhost:8080"
echo "Access H2 console at: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""

mvn spring-boot:run -Dspring-boot.run.profiles=dev
