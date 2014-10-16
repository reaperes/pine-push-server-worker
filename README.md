Pine push server worker
========================


Preparation
------------

1. install jdk8
2. install maven


Run
----

    $ mvn exec:java -Dexec.mainClass="org.nhnnext.Main" -Dexec.classpathScope=runtime
    

Documentation
--------------

    $ javadoc -d ./docs -sourcepath ./src/main/java -subpackages org.nhnnext