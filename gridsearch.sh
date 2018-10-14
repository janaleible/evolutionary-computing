#!/usr/bin/env bash

export LD_LIBRARY_PATH=.

./build.sh > /dev/null

approach=$1

mkdir -p gridsearch
mkdir -p gridsearch/configs-"$approach"

echo "generating configurations ..."
python3 gridsearch.py "generate-$approach"

function=$2
number_of_seeds=$3

echo $function

rm gridsearch/"$function"-"$approach"/*
mkdir gridsearch/"$function"-"$approach"

echo "running ..."

filename_regex="gridsearch/configs-"$approach"/(.*).yaml"

for filename in gridsearch/configs-"$approach"/*.yaml; do

    if [[ $filename =~ $filename_regex ]]
    then
        confignumber="${BASH_REMATCH[1]}"
    fi

    for ((seed=0; seed<number_of_seeds; seed++)); do
        echo $confignumber, $seed
        params=$(python3 config2params.py $filename)
        java $params -jar testrun.jar -submission=player12 -evaluation=$function -seed=$seed > gridsearch/"$function"-"$approach"/"$confignumber"_$seed.txt
    done
done
