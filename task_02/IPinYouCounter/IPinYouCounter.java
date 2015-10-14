package org.storozhilov;
import java.io.*;
import java.util.*;
import java.net.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
//import org.apache.hadoop.io.*;
//import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;

public class IPinYouCounter {
    public static void main(String[] args) throws Exception {
	//if (args.length < 2) {
	//    System.err.println("Usage: java IPinYouCounter <hdfs_input_dir> <hdfs_output_dir>");
	//    System.exit(1);
	//}
	
	Configuration conf = new Configuration();
	String inPath = conf.get("path.in");
	System.out.println("In path is " + inPath);
	String outPath = conf.get("path.out");
	System.out.println("Out path is " + outPath);
	FileSystem fs = FileSystem.get(conf);

    }

    private static final String[] InputFiles = {
	    "bid.20130606.txt.bz2",
	    "bid.20130608.txt.bz2",
	    "bid.20130610.txt.bz2",
	    "bid.20130612.txt.bz2",
	    "bid.20130607.txt.bz2",
	    "bid.20130609.txt.bz2",
	    "bid.20130611.txt.bz2"
    };
}
