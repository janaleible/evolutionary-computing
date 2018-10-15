#!/usr/bin/env bash

# ./experiment.sh baseline SphereEvaluation 100 none
# ./experiment.sh baseline BentCigarFunction 100 none
# ./experiment.sh baseline SchaffersEvaluation 100 none
# ./experiment.sh baseline KatsuuraEvaluation 100 none

./experiment.sh island SphereEvaluation 100 gridsearch/configs-island/28.yaml
./experiment.sh island BentCigarFunction 100 gridsearch/configs-island/28.yaml
./experiment.sh island SchaffersEvaluation 100 gridsearch/configs-island/28.yaml
./experiment.sh island KatsuuraEvaluation 100 gridsearch/configs-island/28.yaml

# ./experiment.sh gender SphereEvaluation 100 gridsearch/configs-gender/0.yaml
# ./experiment.sh gender BentCigarFunction 100 gridsearch/configs-gender/0.yaml
# ./experiment.sh gender SchaffersEvaluation 100 gridsearch/configs-gender/0.yaml
# ./experiment.sh gender KatsuuraEvaluation 100 gridsearch/configs-gender/0.yaml

# ./experiment.sh rts SphereEvaluation 100 gridsearch/configs-rts/0.yaml
# ./experiment.sh rts BentCigarFunction 100 gridsearch/configs-rts/0.yaml
# ./experiment.sh rts SchaffersEvaluation 100 gridsearch/configs-rts/0.yaml
# ./experiment.sh rts KatsuuraEvaluation 100 gridsearch/configs-rts/0.yaml

# ./experiment.sh taboo SphereEvaluation 100 gridsearch/configs-taboo/0.yaml
# ./experiment.sh taboo BentCigarFunction 100 gridsearch/configs-taboo/0.yaml
# ./experiment.sh taboo SchaffersEvaluation 100 gridsearch/configs-taboo/0.yaml
# ./experiment.sh taboo KatsuuraEvaluation 100 gridsearch/configs-taboo/0.yaml
