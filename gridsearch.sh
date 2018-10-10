#!/usr/bin/env bash

export LD_LIBRARY_PATH=.

./build.sh > /dev/null

mkdir -p gridsearch
mkdir -p gridsearch/configurations

echo "generating configurations ..."
python3 gridsearch.py generate

function=$1
number_of_seeds=$2

echo $function

rm gridsearch/$function/*
mkdir -p gridsearch/$function

echo "running ..."

filename_regex="gridsearch/configurations/(.*).yaml"

for filename in gridsearch/configurations/*.yaml; do

    if [[ $filename =~ $filename_regex ]]
    then
        confignumber="${BASH_REMATCH[1]}"
    fi

    for ((seed=0; seed<number_of_seeds; seed++)); do
        echo $confignumber, $seed
        params=$(python3 config2params.py $filename)
        java $params -jar testrun.jar -submission=player12 -evaluation=$function -seed=$seed > gridsearch/$function/"$confignumber"_$seed.txt
    done
done
