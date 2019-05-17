SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_91
call git rev-parse HEAD > curr_com.txt
SET /p curr_com=<curr_com.txt
call mvn --settings D:\pg\apache-maven-3.5.2\__settings.xml --batch-mode sonar:sonar -Dsonar.host.url=http://10.8.90.56:9000 -Dsonar.login=admin -Dsonar.password=admin -Dsonar.projectKey=ODSBE -Dsonar.projectVersion=%curr_com% -Dsonar.tests=src/test/java -Dsonar.java.coveragePlugin=jacoco -Dsonar.junit.reportsPath=build/test-reports -Dsonar.jacoco.reportPath=build/test-reports/jacoco.exec
DEL curr_com.txt
PAUSE