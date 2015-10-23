package org.storozhilov.webbytescount;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WebBytesCount {

    public static class LogLine {
	public String ip;
	public int bytes;
	public String userAgent;

	public LogLine(String line) {
	    Matcher m = Pattern.compile(regexp).matcher(line);
	    m.find();
	    ip = m.group(1);
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    m.find();
	    try {
		bytes = Integer.parseInt(m.group(1));
	    } catch (NumberFormatException e) {
		bytes = 0;
	    }
	    m.find();
	    m.find();
	    userAgent = m.group(1).replace("\"", "");
	}

	private static final String regexp = "([^\"^\\[]\\S*|\".+?\"|\\[.+?\\])\\s*";
    }

    public static class IpData implements Writable, WritableComparable<IpData> {
	public IntWritable amount;
	public IntWritable totalBytes;

	public IpData() {
	    this.amount = new IntWritable();
	    this.totalBytes = new IntWritable();
	}

	public IpData(int amount, int totalBytes) {
	    this.amount.set(amount);
	    this.totalBytes.set(totalBytes);
	}

	public IpData(IntWritable amount, IntWritable totalBytes) {
	    this.amount = amount;
	    this.totalBytes = totalBytes;
	}

	public void set(int amount, int totalBytes) {
	    this.amount.set(amount);
	    this.totalBytes.set(totalBytes);
	}

	@Override
	public void write(DataOutput out) throws IOException {
	    amount.write(out);
	    totalBytes.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
	    amount.readFields(in);
	    totalBytes.readFields(in);
	}

	@Override
	public boolean equals(Object o) {
	    if (o instanceof IpData) {
		IpData id = (IpData) o;
		return amount.equals(id.amount) && totalBytes.equals(id.totalBytes);
	    }
	    return false;
	}

	@Override
	public int compareTo(IpData o) {
	    if (this.amount.get() < o.amount.get()) {
		return -1;
	    } else if (this.amount.get() > o.amount.get()) {
		return 1;
	    } else if (this.totalBytes.get() < o.totalBytes.get()) {
		return -1;
	    } else if (this.totalBytes.get() > o.totalBytes.get()) {
		return 1;
	    } else {
		return 0;
	    }
	}

	@Override
	public int hashCode() {
	    return amount.hashCode() * 163 + totalBytes.hashCode();
	}
    }

    public static class WebBytesCountMapper extends Mapper<LongWritable, Text, Text, IpData> {

	private Text ip = new Text();
	private IpData data = new IpData();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    LogLine logLine = new LogLine(value.toString());
	    ip.set(logLine.ip);
	    data.set(1, logLine.bytes);
	    context.write(ip, data);
	}
    }

    public static class WebBytesReducer extends Reducer<Text, IpData, Text, IpData> {

	private IpData result = new IpData();

	@Override
	protected void reduce(Text key, Iterable<IpData> values, Context context) throws IOException, InterruptedException {
	    int amount = 0;
	    int totalBytes = 0;
	    for (IpData val : values) {
		amount += val.amount.get();
		totalBytes += val.totalBytes.get();
	    }
	    result.set(amount, totalBytes);
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
	job.setOutputValueClass(IpData.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
