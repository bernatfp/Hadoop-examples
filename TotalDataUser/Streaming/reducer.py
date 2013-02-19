import sys


if __name__ == '__main__':
	prev_key = ''
	accum = 0
	for line in sys.stdin:
		key,value = line.strip().split('\t')
		if prev_key != key and prev_key != '':
			print prev_key + '\t' + str(accum)
			accum = 0
		prev_key = key
		accum += int(value)
	print prev_key + '\t' + str(accum)
