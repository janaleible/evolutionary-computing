javac -cp contest.jar Player12.java
jar cmf MainClass.txt submission.jar Player12.class
echo "===== Test Sphere function ==========="
java -jar testrun.jar -submission=Player12 -evaluation=SphereEvaluation -seed=1
echo "===== Test Bent Cigar ====="
java -jar testrun.jar -submission=Player12 -evaluation=BentCigarFunction -seed=1
echo "===== Test Katsuura ====="
java -jar testrun.jar -submission=Player12 -evaluation=KatsuuraEvaluation -seed=1
echo "===== Test Schaffers ====="
java -jar testrun.jar -submission=Player12 -evaluation=SchaffersEvaluation -seed=1
