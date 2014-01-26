javaaddpath("gluegen.jar");
javaaddpath("gluegen-natives-linux-amd64.jar");
javaaddpath("jogl-all.jar");
javaaddpath("jogl-all-natives-linux-amd64.jar");
javaaddpath(".");

testClass = javaObject('timo.test.OctaveTest',4);
disp(num2str(testClass.multiple(5)));
tT = javaObject('timo.test.OctaveTriangle');
tT.run;
