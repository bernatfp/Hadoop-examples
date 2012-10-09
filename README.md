Hadoop-examples
===============
##Introduction

In these repo I'll try to give some examples on how to do MapReduce with Hadoop, since there are only a few examples on the Internet. Here you can find two problems solved with the Hadoop API, the Cascading framework and Pig.

Please, head to the [Wiki Page](https://github.com/bernatfp/Hadoop-examples/wiki) to get a deeper insight on the code.

##Instructions

These examples have been tested on a Pseudo-distributed configuration. They should work correctly in a Fully-distributed configuration too.
If you are running Hadoop in the Standalone mode, and need a tutorial to set up another configuration, you can follow this little [guide](http://wiki.apache.org/hadoop/QuickStart)

To run the Hadoop or the Cascading examples issue this set of commands: 

```
hadoop fs -put <fs filepath src> <hdfs filepath dst>
hadoop jar <our job .jar> <classname> <hdfs filepath src> <hdfs dst path>
```

To run the Pig examples:

```
hadoop fs -put <fs filepath src> <hdfs filepath dst>
pig <scriptname.pig>
```

Or, alternatively the same Pig script can be run without requiring an HDFS filesystem:

```
pig -x local <scriptname.pig>
```

##Notes

In this section you can find some advice I didn't find while trying to run my jobs in Hadoop.
 
###Before exporting to JAR
Remember you need to reference the Hadoop Core library (`hadoop-core-*.jar`) in your Java projects that make use of the Hadoop API (even when using Cascading).
Also, if you use Cascading, you must add the Cascading Core and Hadoop libraries to the project (`cascading-core-2.0.5.jar` and `cascading-hadoop-2.0.5.jar`).

###Before running the job
Be aware that Cascading won't run correctly unless you put all its dependences, found in `cascading-*/lib`, into the `$HADOOP_HOME/lib` directory.

