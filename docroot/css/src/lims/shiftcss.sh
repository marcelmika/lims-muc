#!/bin/bash

# Shift css
shifter

# Remove limsmuc-min.css file
echo 'Removing -min.css file from build'
rm ../../build/limsmuc/limsmuc-min.css

echo 'Copying to: ' $1
cp -R ../../../css/ $1
