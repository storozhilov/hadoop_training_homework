//package org.storozhilov;
//
///**
// * Hello world!
// *
// */
//public class WebBytesCounter
//{
//    public static void main( String[] args )
//    {
//        System.out.println( "Hello World!" );
//    }
//}

package org.storozhilov;
import java.io.*;
import java.util.*;
import java.net.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
//import org.apache.hadoop.io.*;
//import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.commons.compress.compressors.bzip2.*;

public class WebBytesCounter {
    public static void main(String[] args) throws Exception {
	//if (args.length < 2) {
	//    System.err.println("Usage: java WebBytesCounter <hdfs_input_dir> <hdfs_output_dir>");
	//    System.exit(1);
	//}
	System.out.println(Arrays.toString(args));
	
	Configuration conf = new Configuration();
	GenericOptionsParser gop = new GenericOptionsParser(conf, args);

	System.out.println("getRemainingArgs(): " + Arrays.toString(gop.getRemainingArgs()));
	System.out.println("getCommandLine().getArgs(): " + Arrays.toString(gop.getCommandLine().getArgs()));

	//String inPath = conf.get("path.in");
	String inPath = "/users/illy/task_01/input";
	System.out.println("In path is " + inPath);

	//String outPath = conf.get("path.out");
	String outPath = "/users/illy/task_01/input";
	System.out.println("Out path is " + outPath);

	FileSystem fs = FileSystem.get(conf);

	TreeMap<String, Integer> calculationMap = new TreeMap<String, Integer>();

	// Calculation
	for (String inputFile : InputFiles) {
	    String inputFilePath = new File(inPath, inputFile).toString();
	    System.out.println("Processing " + inputFilePath + " file");
	    FSDataInputStream fsdis = fs.open(new Path(inputFilePath));
	    BufferedInputStream bis = new BufferedInputStream(fsdis);
	    BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(bis);
	    BufferedReader br = new BufferedReader(new InputStreamReader(bzIn));
	    String line = br.readLine();
	    int linesCount = 0;
	    while (line != null) {
		String iPinYouId = line.split("\\t")[2];
		Integer iPinYouIdCount = calculationMap.get(iPinYouId);
		iPinYouIdCount = (iPinYouIdCount == null) ? 1 : iPinYouIdCount + 1;
		calculationMap.put(iPinYouId, iPinYouIdCount);
		line = br.readLine();
	    }
	    System.out.println(linesCount + " lines of " + inputFilePath + " file has been processed");
	}
	
	// Sorting
	System.out.println("Sorting result ...");
	TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
	Iterator it = calculationMap.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
	    sortMap.put(pair.getValue(), pair.getKey());
	    it.remove(); // avoids a ConcurrentModificationException
	}

	// Writing result to file
	System.out.println("Writing result to file ...");
	Path outFileName = new Path(new File(outPath, "bid_result.txt").toString());
	if (fs.exists(outFileName)) {
	    fs.delete(outFileName, true);
	}
	FSDataOutputStream fout = fs.create(outFileName);
	it = sortMap.descendingMap().entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>) it.next();
	    fout.writeUTF(pair.getKey() + "\t" + pair.getValue() + "\n");
	    it.remove(); // avoids a ConcurrentModificationException
	}
    }

    private static final String[] InputFiles = {
	    "bid.20130606.txt.bz2",
	    "bid.20130607.txt.bz2",
	    "bid.20130608.txt.bz2",
	    "bid.20130609.txt.bz2",
	    "bid.20130610.txt.bz2",
	    "bid.20130611.txt.bz2",
	    "bid.20130612.txt.bz2"
    };
}
