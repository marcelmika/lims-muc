#!/bin/bash

# Shift js
shifter

# Copy to folder
echo 'Copying to: ' $1
cp -R ../../../js/ $1
