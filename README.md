Pine push server worker
========================


Preparation
------------

1. install jdk8
2. install maven


Pine-push-server-worker Environment Variables
----------------------------------------------

| Name         | Default   | Description                 |
|:------------:| --------- | --------------------------- |
| UNIQUSH_HOST | localhost | Uniqush server host address |
| UNIQUSH_PORT | 9898



Run
----

    $ mvn exec:java -Dexec.mainClass="org.nhnnext.Main" -Dexec.classpathScope=runtime
    

Documentation
--------------

    $ javadoc -d ./docs -sourcepath ./src/main/java -subpackages org.nhnnext