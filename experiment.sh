#!/usr/bin/env bash

export LD_LIBRARY_PATH=.

function=$1
numberOfRuns=$2

echo $function, $numberOfRuns

mkdir -p experiments
mkdir -p experiments/$function
mkdir -p experiments/"$function"_results

./build.sh > /dev/null

for ((seed=0; seed<numberOfRuns; seed++)); do
    java -jar testrun.jar -submission=player12 -evaluation=$function -seed=$seed > experiments/$function/$seed.txt
    ./postprocessing.sh experiments/$function/$seed.txt experiments/"$function"_results/$seed
    echo $seed
done