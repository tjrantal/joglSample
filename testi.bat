javac -cp ".;jarLib/gluegen.jar;jarLib/jogl-all.jar" -source 1.6 -target 1.6 timo/test/KainTutorial.java
jar cfm joglSample.jar manifest/MANIFEST.MF timo/test/*.class earthmap1k.jpg
java -jar joglSample.jar


::java -Djava.library.path=lib -cp ".;gluegen.jar;gluegen-rt-natives-windows-amd64.jar;gluegen-rt-natives-windows-i586.jar;jogl-all.jar;jogl-all-natives-windows-amd64.jar" timo.test.KainTutorial