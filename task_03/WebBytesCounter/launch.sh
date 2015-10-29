#!/bin/sh

hadoop fs -rm ${HOME}/task_03/output/'*'
hadoop fs -rmdir ${HOME}/task_03/output
hadoop jar target/WebBytesCount-1.0-SNAPSHOT.jar org.storozhilov.webbytescount.WebBytesCount "${HOME}/task_03/input" "${HOME}/task_03/output"

echo "Result data is:"
echo "-----------------------------------------"
hadoop fs -cat ${HOME}/task_03/output/'*'
