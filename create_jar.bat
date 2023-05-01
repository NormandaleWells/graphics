javac *.java
javac Graphics\*.java
"c:\Program Files\Java\jdk-17.0.2\bin\jar.exe" -c --file=graphics.jar -M .\Graphics\*.class .\Graphics\*.java .\*.class .\*.java .\LICENSE .\color_names.txt
del *.class
del Graphics\*.class
