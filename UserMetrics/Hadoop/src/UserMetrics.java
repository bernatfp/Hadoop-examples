import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;


public class UserMetrics {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	     private final static IntWritable dataSize = new IntWritable(0);
	     private Text word = new Text();
	
	     public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	       String line = value.toString();
	       StringTokenizer tokenizer = new StringTokenizer(line);
	       while (tokenizer.hasMoreTokens()) {
	         
	    	 word.set(tokenizer.nextToken());
	         dataSize.set(Integer.parseInt(tokenizer.nextToken(), 10));
	         output.collect(word, dataSize);
	       }
	     }
	   }
	
	
	   public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, Text> {
		   public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	       
			   double len = 0, val;
			   String outputValue;
			   double sum = 0, avg = 0, variance = 0, mean = 0, lastMean = 0, stdev;			   
			   while (values.hasNext()) {
				   
				   val = values.next().get(); 
				   sum += val;
				   len++;
				   lastMean = mean;
				   mean = ((len -1) * lastMean + val) / len;
				   variance = ((len - 1) * variance + (val - mean) * (val - lastMean)) / len; 
			   }
			   
			   avg = sum/len;
			   stdev = Math.sqrt(variance);
			   
			   outputValue = new String(Double.toString(sum) + "  " + Double.toString(avg) + "  " + Double.toString(stdev));
			   output.collect(key, new Text(outputValue));
		   }
	   }
	
	public static void main(String[] args) throws Exception {
	     JobConf conf = new JobConf(UserMetrics.class);
	     conf.setJobName("usermetrics");
	
	     conf.setOutputKeyClass(Text.class);
	     conf.setMapOutputValueClass(IntWritable.class);
	     conf.setOutputValueClass(Text.class);
	
	     conf.setMapperClass(Map.class);
	     conf.setReducerClass(Reduce.class);
	
	     conf.setInputFormat(TextInputFormat.class);
	     conf.setOutputFormat(TextOutputFormat.class);
	
	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	     JobClient.runJob(conf);
	   }
	
}
