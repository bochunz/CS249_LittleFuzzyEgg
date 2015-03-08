import sys
import csv
import scipy.stats as ss

class MyDialect(csv.Dialect):
    strict = True
    skipinitialspace = True
    quoting = csv.QUOTE_MINIMAL
    delimiter = ','
    quotechar = '"'
    lineterminator = '\n'

def main():
    f = open('mytrain.csv', 'rb')
    reader = csv.reader(f,MyDialect())
    
    popularity_map = {}
    for row in reader:
        if row[1] in popularity_map:
            popularity_map[row[1]] += 1
        else:
            popularity_map[row[1]] = 1
    items = popularity_map.items()
    pops = []
    for sku, pop in items:
        pops.append(-pop)

    ranked = ss.rankdata(pops)

    for i in range(len(items)):
        print '{0},{1}'.format(items[i][0], int(ranked[i]))



if __name__ == '__main__':
    main()