javac -cp ".;gluegen.jar;jogl-all.jar" -source 1.6 -target 1.6 timo/test/KainTutorial.java
java -cp ".;gluegen.jar;gluegen-natives-windows-amd64.jar;jogl-all.jar;jogl-all-natives-windows-amd64.jar" timo.test.KainTutorial


::java -Djava.library.path=lib -cp ".;gluegen.jar;gluegen-rt-natives-windows-amd64.jar;gluegen-rt-natives-windows-i586.jar;jogl-all.jar;jogl-all-natives-windows-amd64.jar" timo.test.KainTutorial