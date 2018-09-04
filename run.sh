javac -cp contest.jar player12.java
jar cmf MainClass.txt submission.jar player12.class
echo "===== Test dummy function ==========="
java -jar testrun.jar -submission=player12 -evaluation=SphereEvaluation -seed=1
echo "===== Test one contest function ====="
java -jar testrun.jar -submission=player12 -evaluation=BentCigarFunction -seed=1
