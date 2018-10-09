./build.sh > /dev/null

echo "===== Test Sphere function ==========="
params=$(python3 config2params.py sphere_config.yaml)
java $params -jar testrun.jar -submission=player12 -evaluation=SphereEvaluation -seed=1

echo "===== Test Bent Cigar ====="
params=$(python3 config2params.py bentcigar_config.yaml)
java  $params -jar testrun.jar -submission=player12 -evaluation=BentCigarFunction -seed=1

echo "===== Test Schaffers ====="
params=$(python3 config2params.py schaffers_config.yaml)
java  $params -jar testrun.jar -submission=player12 -evaluation=SchaffersEvaluation -seed=1

echo "===== Test Katsuura ====="
params=$(python3 config2params.py katsuura_config.yaml)
java  $params -jar testrun.jar -submission=player12 -evaluation=KatsuuraEvaluation -seed=1
