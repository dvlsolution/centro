#!/bin/sh

JAVA_OPTS="$JAVA_OPTS -server -Xms512m -Xmx512m -Djava.awt.headless=true"
$JAVA_HOME/bin/java $JAVA_OPTS -jar ${project.build.finalName}.${project.packaging} $@
