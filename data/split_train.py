import sys
import csv
import random

class MyDialect(csv.Dialect):
    strict = True
    skipinitialspace = True
    quoting = csv.QUOTE_MINIMAL
    delimiter = ','
    quotechar = '"'
    lineterminator = '\n'

def main():
    f = open('train_p.csv', 'rb')
    reader = csv.reader(f,MyDialect())
    
    rows = []
    for row in reader:
        rows.append(row)
    num_lines = len(rows)
    num_train = num_lines * 6 / 10


    random.shuffle(rows)
    train_set = rows[:num_train]
    test_set = rows[num_train:]

    print len(train_set), len(test_set)

    train_file = open('mytrain2.csv', 'w')
    writer = csv.writer(train_file, MyDialect())
    for row in train_set:
        writer.writerow(row)

    test_file = open('mytest2.csv', 'w')
    writer = csv.writer(test_file, MyDialect())

    test_ans = open('mytest2_label.csv', 'w')
    for row in test_set:
        writer.writerow(row[:1]+row[2:])
        test_ans.write(row[1] + "\n")


if __name__ == '__main__':
    main()