import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

/* This is a modified version from the Custom
 * Aggregator example found in the documentation.
 */

public class Stdev extends BaseOperation implements Aggregator {

	public Stdev(){
		super(1, new Fields("stdev"));
	}
	
	
	public static class Context{
		Double mean;
		Double variance;
		Integer n;
    }
	
	public void start(FlowProcess fp, AggregatorCall ac) {
		
		Context context = new Context();
		context.mean = (double) 0;
		context.variance = (double) 0;
		context.n = 0;
		
	    ac.setContext(context);
		
	}
	
	public void aggregate(FlowProcess fp, AggregatorCall ac) {
		
		TupleEntry arguments = ac.getArguments();
	    Context context = (Context) ac.getContext();

	    Integer x = arguments.getInteger(0);
	    Double lastMean = context.mean;
	    
	    context.n += 1;
	    context.mean = ((context.n -1) * lastMean + x) / context.n;
	    context.variance = ((context.n - 1) * context.variance + (x - context.mean) * (x - lastMean)) / context.n;
		
	}
	
	public void complete(FlowProcess fp, AggregatorCall ac) {
		
		Context context = (Context) ac.getContext();
	    Tuple result = new Tuple();
	    Double stdev = Math.sqrt(context.variance);
	    result.add(stdev);
	    ac.getOutputCollector().add(result);
		
	}

}
