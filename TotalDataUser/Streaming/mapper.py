import sys

#Do nothing as we don't need to manipulate our data
def map(line):
	print line

if __name__ == '__main__':
	for line in sys.stdin:
		map(line.strip())
