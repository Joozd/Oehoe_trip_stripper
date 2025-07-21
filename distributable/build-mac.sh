#!/bin/bash

echo "Cloning repo..."
git clone https://github.com/joozd/Oehoe_trip_stripper.git
cd Oehoe_trip_stripper || exit 1

echo "Checking Java version..."
JAVA_VER=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "Found Java version: $JAVA_VER"

if [[ "$JAVA_VER" != 17* ]]; then
  echo "Java 17 is required. Attempting to use Java 17 via /usr/libexec/java_home..."
  export JAVA_HOME=$(/usr/libexec/java_home -v17) || {
    echo "Java 17 not found. Please install it using:"
    echo "  brew install openjdk@17"
    exit 1
  }
  export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "Java version used for build:"
java -version

echo "Building distributable..."
./gradlew createDistributable