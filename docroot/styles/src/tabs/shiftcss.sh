#!/bin/bash

# Shift css
shifter

# Remove -min.css file
echo 'Removing -min.css file from build'
rm ../../build/tabs/tabs-min.css

# Check if parameters was passed
if [ ! -z "$1" ]; then
    # Check if path exists
    if [ -d "$1" ]; then
        echo 'Copying to: ' $1
        cp -R ../../build/tabs $1
    fi
fi
