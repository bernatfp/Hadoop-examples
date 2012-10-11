Hadoop-examples
===============
##Introduction

In these repo I'll try to give some examples on how to do MapReduce with _Hadoop_, since there are only a few examples on the Internet. Here you can find two problems solved with the _Hadoop_ API, the _Cascading_ framework and _Pig_.

Please, head to the [Wiki Page](https://github.com/bernatfp/Hadoop-examples/wiki) to get a deeper insight on the code.

##Instructions

These examples have been tested on a _Pseudo-distributed_ configuration. They should work correctly in a _Fully-distributed_ configuration too.
If you are running Hadoop in the _Standalone_ mode, and need a tutorial to set up another configuration, you can follow this little [guide](http://wiki.apache.org/hadoop/QuickStart)

To run the _Hadoop_ or the _Cascading_ examples issue this set of commands: 

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
Remember you need to reference the _Hadoop_ Core library (`hadoop-core-*.jar`) in your _Java_ projects that make use of the _Hadoop_ API (even when using _Cascading_).
Also, if you use _Cascading_, you must add the _Cascading_ Core and Hadoop libraries to the project (`cascading-core-2.0.5.jar` and `cascading-hadoop-2.0.5.jar`).

###Before running the job
Be aware that _Cascading_ won't run correctly unless you put all its dependences, found in `cascading-*/lib`, into the same .jar as our job. Another (easier) option is to copy these libraries into the `$HADOOP_HOME/lib` directory, but it won't work if you're running Hadoop on a cluster unless you do the same to every node.

***

###### For any comments you can reach me at bernatfp at gmail or verdnat at twitter.

