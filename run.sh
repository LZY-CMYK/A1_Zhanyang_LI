#!/bin/bash
# Compile all Java sources into out/ and run the scripted console demonstration.
cd "$(dirname "$0")" || exit 1
rm -rf out
mkdir -p out
javac -d out src/*.java && java -cp out AssignmentOne
