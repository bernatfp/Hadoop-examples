import java.util.Properties;

import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.scheme.hadoop.TextLine;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;


public class Main {
	
	public static void main(String []args){
		
		//Paths
		String inPath = args[0];
		String outPath = args[1];
		
		//Properties
		Properties properties = new Properties();
	    AppProps.setApplicationJarClass( properties, Main.class );
	    HadoopFlowConnector flowConnector = new HadoopFlowConnector( properties );
	    
	    //Define fields
	    Fields line = new Fields("user","datasize");
	    
	    //Taps
	    Tap sourceTap = new Hfs(new TextDelimited(line,false,"\t"), inPath);
	    Tap destinationSink = new Hfs(new TextDelimited(true,"\t"), outPath);
	    
	    //1st step w/ source pipe
	    Pipe sPipe = new Pipe("source");
	    sPipe =	new GroupBy(sPipe, new Fields("user"));
	    
	    
	    //2nd step w/ destination pipe
	    Pipe dPipe = new Pipe("destination",sPipe);
	    dPipe = new Every(dPipe, new Fields("datasize"), new Sum(), Fields.ALL);
	    
		//Link the tap and sink with the pipes
		FlowDef flow = FlowDef.flowDef()
							  .setName("total_flow")
							  .addSource(sPipe, sourceTap)
							  .addTailSink(dPipe, destinationSink);
		
		//Finally we run the task
		flowConnector.connect(flow)
					 .complete();
	    
	}
	
}
