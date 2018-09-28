#!/usr/bin/env bash

export LD_LIBRARY_PATH=.

./build.sh > /dev/null

functions=("SphereEvaluation" "BentCigarFunction" "KatsuuraEvaluation" "SchaffersEvaluation")
seeds=("01" "02" "03" "04" "05")

for function in ${functions[@]}; do
    mkdir -p gridsearch/$function

    for seed in ${seeds[@]}; do
        for config in {1..1151}; do
            java -jar testrun.jar -submission=player12 -evaluation=$function -seed=$config$seed > gridsearch/$function/$config"_"$seed.txt
        done
    done
done

