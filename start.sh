#!/bin/bash

# Clone source if directory does not exists
if [ ! -d "$HOME/pine-push-server-worker" ]; then
  git clone https://github.com/reaperes/pine-push-server-worker.git $HOME/pine-push-server-worker
fi

# update source
cd $HOME/pine-push-server-worker
git pull

# Set environments
export UNIQUSH_HOST=$UNIQUSH_PORT_8520_TCP_ADDR
export UNIQUSH_PORT=$UNIQUSH_PORT_8520_TCP_PORT
export RABBITMQ_HOST=$RABBITMQ_PORT_8510_TCP_ADDR
export RABBITMQ_PORT=$$RABBITMQ_PORT_8510_TCP_PORT

# Run application
mvn clean compile exec:java -Dexec.mainClass="org.nhnnext.Main" -Dexec.classpathScope=runtime
