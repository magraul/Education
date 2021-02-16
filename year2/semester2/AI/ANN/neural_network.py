import numpy
from math import exp, log


def sigmoid(z):
    return [1 / (1 + exp(-i)) for i in z]


def entropy(y, sig):
    m = len(y)
    y = [argmax(i) for i in y]
    sig = [max(i) for i in sig]
    t1 = [-i * log(j) for i, j in zip(y, sig)]
    t2 = [(1 - i) * log(1 - j) for i, j in zip(y, sig)]
    t3 = [i - j for i, j in zip(t1, t2)]
    return 1 / m * sum(t3)


class NeuralNetwork:
    def __init__(self, numar_features, numar_outputuri, neuroni_strat_ascuns=30):
        self.costs = []
        #initializare parametri

        #strat intrare -> strat ascuns
        self.conexiuni_W1 = []
        aux = list(2*numpy.random.random((numar_features, neuroni_strat_ascuns)) - 1)
        for i in aux:
             self.conexiuni_W1.append(list(i))

        # strat ascuns -> strat iesire
        self.conexiuni_W2 = []
        aux = list(list(2*numpy.random.random((neuroni_strat_ascuns, numar_outputuri)) - 1))
        for i in aux:
             self.conexiuni_W2.append(list(i))

    def train(self, x, y, max_iter=15, learning_rate=0.1):
        for i in range(len(x)):
            x[i].insert(0, 1)

        y = transform(y)
        for epoch in range(max_iter):
            self.costs.append(0)
            #activare
            #print(epoch)
            valori_activare_layer1 = eval_matrix(x, self.conexiuni_W1)
            # outputs
            outputs_layer_1 = [sigmoid(i) for i in valori_activare_layer1]

            #activare
            valori_activare_layer2 = eval_matrix(outputs_layer_1, self.conexiuni_W2)
            #outputs
            outputs_layer_2 = [sigmoid(i) for i in valori_activare_layer2]
            self.costs[epoch] = entropy(y, outputs_layer_2)


            #erori backward
            errors = [[i - j for i, j in zip(y_, o_)] for (y_, o_) in zip(y, outputs_layer_2)]

            derivata = [[i * (1-i) for i in l] for l in outputs_layer_2]

            #(real - computat) * derivata (sigmoid(L2))
            errors_layer_2 = [[i * j for i, j in zip(error, deriv)] for error, deriv in zip(errors, derivata)]

            errors = eval_matrix(errors_layer_2, transpusa(self.conexiuni_W2))
            derivata = [[i * (1-i) for i in l] for l in outputs_layer_1]

            # eroare_layer_2 * W2 * derivata (sigmoid (L1))
            errors_layer_1 = [[i * j for i, j in zip(error, deriv)] for error, deriv in zip(errors, derivata)]

            # updatare
            update_W2 = eval_matrix(transpusa(outputs_layer_1), errors_layer_2)
            for i in range(len(self.conexiuni_W2)):
                for j in range(len(self.conexiuni_W2[0])):
                    self.conexiuni_W2[i][j] += update_W2[i][j]*learning_rate

            update_W1 = eval_matrix(transpusa(x), errors_layer_1)
            for i in range(len(self.conexiuni_W1)):
                for j in range(len(self.conexiuni_W1[0])):
                    self.conexiuni_W1[i][j] += update_W1[i][j] * learning_rate

        print(self.conexiuni_W2)
        print(self.conexiuni_W1)

    def predict(self, test_norm):
        for i in range(len(test_norm)):
            test_norm[i].insert(0, 1)
        rez = []
        valori_activare_layer1 = eval_matrix(test_norm, self.conexiuni_W1)
        # outputs
        outputs_layer_1 = [sigmoid(i) for i in valori_activare_layer1]

        # activare
        valori_activare_layer2 = eval_matrix(outputs_layer_1, self.conexiuni_W2)
        # outputs
        outputs_layer_2 = [sigmoid(i) for i in valori_activare_layer2]
        for i in outputs_layer_2:
            rez.append(argmax(i))
        return rez


def eval_matrix(m1, m2):
    result = [[0 for j in range(len(m2[0]))] for i in range(len(m1))]

    for i in range(len(m1)):
        for j in range(len(m2[0])):
            for k in range(len(m2)):
                result[i][j] += m1[i][k] * m2[k][j]
    return result


def transform(y):
    rez = []
    for i in range(len(y)):
        if y[i] == 0:
            rez.append([1, 0, 0])
        elif y[i] == 1:
            rez.append([0, 1, 0])
        else:rez.append([0, 0, 1])
    return rez


def transpusa(X):
    rez = [[0 for i in range(len(X))] for j in range(len(X[0]))]
    for i in range(len(X)):
        # iterate through columns
        for j in range(len(X[0])):
            rez[j][i] = X[i][j]
    return rez


def argmax(lista):
    m = max(lista)
    return lista.index(m)
