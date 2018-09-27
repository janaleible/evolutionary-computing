rm -f group12/*.class
rm -f visual/*.class
rm -f player12.class
rm -f Player12.class

javac -cp contest.jar player12.java group12/*.java visual/*.java
jar vcmf MainClass.txt submission.jar player12.class group12/*.class visual/*.class
