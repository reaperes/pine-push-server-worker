Pine push server worker
========================


Preparation
------------

1. install jdk8
2. install maven


Pine-push-server-worker Environment Variables
----------------------------------------------

| Name            | Default   | Description                  |
|:---------------:| :-------: | ---------------------------  |
| UNIQUSH_HOST    | localhost | Uniqush server host address  |
| UNIQUSH_PORT    | 9898      | Uniqush server port          |
| UNIQUSH_SERVICE | pine      | Uniqush service name         |
|                 |           |                              |
| RABBITMQ_HOST   | localhost | RabbitMQ server host address |
| RABBITMQ_PORT   | 5672      | RabbitMQ server node port    |
| RABBITMQ_QUEUE  | pine      | RabbitMQ queue name          |


Run
----

    $ mvn clean compile exec:java -Dexec.mainClass="org.nhnnext.Main" -Dexec.classpathScope=runtime
    
    or
    
    $ sudo docker run \
        -ti \
        --name pine-push-server-worker \
        --link rabbitmq:rabbitmq \
        --link uniqush:uniqush \
        reaperes/pine-push-server-worker
    

Documentation
--------------

    $ javadoc -d ./docs -sourcepath ./src/main/java -subpackages org.nhnnext