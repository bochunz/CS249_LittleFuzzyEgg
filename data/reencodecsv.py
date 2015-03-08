import sys
import csv

class MyDialect(csv.Dialect):
    strict = True
    skipinitialspace = True
    quoting = csv.QUOTE_MINIMAL
    delimiter = ','
    quotechar = '"'
    lineterminator = '\n'

def main():
    f = open(sys.argv[1], 'rb')
    r=csv.reader(f,MyDialect())
    with open(sys.argv[2], 'wb') as outf:
        writer = csv.writer(outf, MyDialect())
        for row in r:
            print row
            for i in range(len(row)):
                row[i] = row[i].decode('ascii', 'ignore').encode('utf-8', 'ignore')
            writer.writerow(row)

if __name__ == '__main__':
    main()