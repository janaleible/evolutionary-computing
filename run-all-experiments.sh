#!/usr/bin/env bash

# ./experiment.sh baseline SchaffersEvaluation 100 none
# ./experiment.sh baseline KatsuuraEvaluation 100 none
# ./experiment.sh baseline BentCigarFunction 100 none

./experiment.sh island BentCigarFunction 100 experiments/configs/BentCigar-island.yaml
./experiment.sh gender BentCigarFunction 100 experiments/configs/BentCigar-genders.yaml
./experiment.sh rts BentCigarFunction 100 experiments/configs/BentCigar-rts.yaml
./experiment.sh taboo BentCigarFunction 100 experiments/configs/BentCigar-taboo.yaml

./experiment.sh island SchaffersEvaluation 100 experiments/configs/Schaffers-island.yaml
./experiment.sh gender SchaffersEvaluation 100 experiments/configs/Schaffers-genders.yaml
./experiment.sh taboo SchaffersEvaluation 100 experiments/configs/Schaffers-taboo.yaml
./experiment.sh rts SchaffersEvaluation 100 experiments/configs/Schaffers-rts.yaml

./experiment.sh island KatsuuraEvaluation 100 experiments/configs/Katsuura-island.yaml
./experiment.sh gender KatsuuraEvaluation 100 experiments/configs/Katsuura-genders.yaml
./experiment.sh taboo KatsuuraEvaluation 100 experiments/configs/Katsuura-taboo.yaml
./experiment.sh rts KatsuuraEvaluation 100 experiments/configs/Katsuura-rts.yaml