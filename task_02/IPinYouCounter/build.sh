#!/bin/sh

classpath=${HADOOP_INSTALL}/share/hadoop/hdfs/hadoop-hdfs-2.7.1.jar
classpath=${classpath}:${HADOOP_INSTALL}/share/hadoop/common/hadoop-common-2.7.1.jar

if [ -d IPinYouCounter ] ; then
	rm -Rf IPinYouCounter/*
else
	mkdir IPinYouCounter
fi

if [ -r IPinYouCounter.jar ] ; then
	rm -f IPinYouCounter.jar
fi

javac -classpath ${classpath} -d IPinYouCounter/ IPinYouCounter.java
jar -cvf IPinYouCounter.jar -C IPinYouCounter/ .
