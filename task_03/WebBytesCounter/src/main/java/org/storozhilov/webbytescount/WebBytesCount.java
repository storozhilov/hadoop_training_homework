package org.storozhilov.webbytescount;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WebBytesCount {

    public static class WebBytesCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private Text ip = new Text();
	private IntWritable bytes = new IntWritable();
	public static final String regexp = "([^\"^\\[]\\S*|\".+?\"|\\[.+?\\])\\s*";

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    Matcher m = Pattern.compile(regexp).matcher(value.toString());
	    m.find();
	    ip.set(m.group(1));
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    try {
	        bytes.set(Integer.parseInt(m.group(1)));
	    } catch (NumberFormatException e) {
		bytes.set(0);
	    }
	    context.write(ip, bytes);
	}
    }

    public static class WebBytesReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	private IntWritable result = new IntWritable();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	    int sum = 0;
	    int amount = 0;
	    for (IntWritable val : values) {
		sum += val.get();
		amount++;
	    }
	    result.set(sum / amount);
	    context.write(key, result);
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = new Configuration();
	Job job = Job.getInstance(conf, "Web bytes count");
	job.setJarByClass(WebBytesCount.class);
	job.setMapperClass(WebBytesCountMapper.class);
	//job.setCombinerClass(WebBytesReducer.class);
	job.setReducerClass(WebBytesReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
