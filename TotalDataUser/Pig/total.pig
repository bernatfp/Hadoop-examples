A = LOAD 'input.txt' USING PigStorage('\t') AS (user: chararray, data: int);
B = GROUP A BY user;
C = FOREACH B GENERATE group,SUM(A.data);
STORE C INTO 'output.txt';
  
