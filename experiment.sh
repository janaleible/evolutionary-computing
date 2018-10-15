#!/usr/bin/env bash

export LD_LIBRARY_PATH=.

approach=$1
function=$2
numberOfRuns=$3
configfile=$4


echo $function, $numberOfRuns

mkdir -p experiments
mkdir -p experiments/"$approach"-"$function"
mkdir -p experiments/"$approach"-"$function"_results

./build.sh > /dev/null

params=""
for ((seed=0; seed<numberOfRuns; seed++)); do

    if [ -e $configfile ]
    then
        params=$(python3 config2params.py $configfile)
    fi

    java $params -jar testrun.jar -submission=player12 -evaluation=$function -seed=$seed > experiments/"$approach"-"$function"/$seed.txt
    ./postprocessing.sh experiments/"$approach"-"$function"/$seed.txt experiments/"$approach"-"$function"_results/$seed
    echo $seed
done