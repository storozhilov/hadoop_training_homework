#!/bin/sh

hadoop_version=$(hadoop version|head -n 1|sed 's/Hadoop \([0-9\.]\+\)/\1/')

classpath=${HADOOP_INSTALL}/share/hadoop/hdfs/hadoop-hdfs-${hadoop_version}.jar
classpath=${classpath}:${HADOOP_INSTALL}/share/hadoop/common/hadoop-common-${hadoop_version}.jar

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
