javac -d bin WebServer.java
jar cfe Server.jar WebServer -C bin/ .
#EXECUTE
#java -jar Server.jar
rm -rf bin