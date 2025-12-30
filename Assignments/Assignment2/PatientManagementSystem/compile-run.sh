#!/bin/bash

javac -d bin -cp "lib/sqlite-jdbc-3.51.0.0.jar" $(find src -name "*.java")
echo "done"

java --enable-native-access=ALL-UNNAMED -cp "bin;lib/sqlite-jdbc-3.51.0.0.jar" main.Main
