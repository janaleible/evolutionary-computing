javac -cp contest.jar Player12.java
jar cmf MainClass.txt submission.jar Player12.class
echo "===== Test dummy function ==========="
java -jar testrun.jar -submission=Player12 -evaluation=SphereEvaluation -seed=1
echo "===== Test one contest function ====="
java -jar testrun.jar -submission=Player12 -evaluation=BentCigarFunction -seed=1
