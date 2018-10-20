#!/usr/bin/env bash

for result in `find experiments/* -type d`; do
    directory=`basename "$result"`
    if [ -d "$result"_results ]; then
        echo $directory
       python3 visual/plotFitness.py "$directory" "../report/figures"
    fi
done