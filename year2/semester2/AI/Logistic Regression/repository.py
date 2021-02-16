def load_data():
    rez = dict()
    rez['clase'] = ['Iris-setosa', 'Iris-versicolor', 'Iris-virginica']
    rez['data'] = []
    rez['outputs'] = []
    input1 = []
    input2 = []
    input3 = []

    f = open('data.txt', 'r')
    for line in f:
        if line != '':
            elems = line.split(',')
            if elems[-1][:-1] == 'Iris-setosa':
                input1.append([float(i) for i in elems[:-1]])
            elif elems[-1][:-1] == 'Iris-versicolor':
                input2.append([float(i) for i in elems[:-1]])
            elif elems[-1][:-1] == 'Iris-virginica':
                input3.append([float(i) for i in elems[:-1]])

    return input1, input2, input3
