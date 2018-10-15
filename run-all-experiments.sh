#!/usr/bin/env bash

# ./experiment.sh baseline SphereEvaluation 100 none
# ./experiment.sh baseline BentCigarFunction 100 none
# ./experiment.sh baseline SchaffersEvaluation 100 none
# ./experiment.sh baseline KatsuuraEvaluation 100 none

./experiment.sh island SphereEvaluation 100 experiments/configs/SphereEvaluation-island.yaml
./experiment.sh island BentCigarFunction 100 experiments/configs/BentCigarFunction-island.yaml
./experiment.sh island SchaffersEvaluation 100 experiments/configs/SchaffersEvaluation-island.yaml
./experiment.sh island KatsuuraEvaluation 100 experiments/configs/KatsuuraEvaluation-island.yaml

./experiment.sh gender SphereEvaluation 100 experiments/configs/gender.yaml
./experiment.sh gender BentCigarFunction 100 experiments/configs/gender.yaml
./experiment.sh gender SchaffersEvaluation 100 experiments/configs/gender.yaml
./experiment.sh gender KatsuuraEvaluation 100 experiments/configs/gender.yaml

./experiment.sh rts SphereEvaluation 100 experiments/configs/SphereEvaluation-rts.yaml
./experiment.sh rts BentCigarFunction 100 experiments/configs/BentCigarFunction-rts.yaml
./experiment.sh rts SchaffersEvaluation 100 experiments/configs/SchaffersEvaluation-rts.yaml
./experiment.sh rts KatsuuraEvaluation 100 experiments/configs/KatsuuraEvaluation-rts.yaml

./experiment.sh taboo SphereEvaluation 100 experiments/configs/taboo.yaml
./experiment.sh taboo BentCigarFunction 100 experiments/configs/taboo.yaml
./experiment.sh taboo SchaffersEvaluation 100 experiments/configs/taboo.yaml
./experiment.sh taboo KatsuuraEvaluation 100 experiments/configs/taboo.yaml
