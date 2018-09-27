rm -f group12/*.class
rm -f visual/*.class
rm -f player12.class
rm -f Player12.class
rm -f submission.jar

echo "compiling contest.jar"
javac -cp contest.jar player12.java group12/*.java visual/*.java

echo "packaging classes to submission.jar"
jar vcmf MainClass.txt submission.jar player12.class group12/*.class visual/*.class

ls -l ./submission.jar

curl -F 'teamcode=GG2o01g=' -F 'file=@./submission.jar' http://mac360.few.vu.nl:8080/EC_BB_ASSIGNMENT/FileUploader

lynx -dump http://mac360.few.vu.nl:8080/EC_BB_ASSIGNMENT/rankings.html
