#!/bin/bash

TARGET_DIR="target"
MAIN_FILE="index.html"

./build.sh
google-chrome $TARGET_DIR/$MAIN_FILE &
