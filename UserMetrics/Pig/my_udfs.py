#I will define all my custom UDFs here
#bfages@tid.es

import math

@outputSchema("stdev:float")
def stdev(data_arr,avg):
	stdev = 0
	l = len(data_arr)
	for n in data_arr:
		stdev+=math.pow(n[0]-avg,2) 
	return math.sqrt((1.0/l)*stdev)

