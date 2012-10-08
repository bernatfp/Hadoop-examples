REGISTER 'my_udfs.py' USING jython AS my_udfs;
A = LOAD 'input.txt' USING PigStorage('\t') AS (user: chararray, data: int);
B = GROUP A BY user;
C = FOREACH B GENERATE group, SUM(A.data), AVG(A.data), my_udfs.stdev(A.data,AVG(A.data));
STORE C INTO 'output' USING PigStorage('\t');
