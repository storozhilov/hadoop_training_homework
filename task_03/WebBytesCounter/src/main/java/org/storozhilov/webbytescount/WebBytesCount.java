package org.storozhilov.webbytescount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class WebBytesCount {
    public static class WebBytesCountMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
	// TODO: Mapper's code is here
    }
}
