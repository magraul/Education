import numpy as np
from math import exp
from math import log

def crossEntropy(probabilitatiReale, probabilitatiComputate):
    rez = 0.0
    for i, val in enumerate(probabilitatiReale):
        if probabilitatiComputate[i] == 0 and val != 0:
            return float('inf')
        rez += probabilitatiReale[i] * np.log(probabilitatiComputate[i])
    return -rez


def costCLasificareMultiCLass(real, computed):
    costuri = []
    for i, probab in enumerate(computed):
        costuri.append(crossEntropy(real[i], computed[i]))
    return 1/len(real) * sum(costuri)


def sigmoid_nr(x):
    return  1 / (1 + exp(-x))

def sigmoid(z):
    return [1 / (1 + exp(-i)) for i in z]


class Classifier:
    def __init__(self):
        self.coef = []
        self.classes = []

    # def cost(self, theta, x, y):
    #     pr = self.predict_probabilitati(x)
    #     m = len(y)
    #     cost = costCLasificareMultiCLass(convert(y), pr)
    #     gradients = [1/m * i for i in self.predict_probabilitati()]

    def cost(self, theta, x, y ,clasa):
        h = self.predict_probabilitati(theta, x)
        m = len(y)
        grad = []
        grad.append(1.0 / m * sum([(sigmoid_nr(self.eval(x[input], theta)) - y[input]) * 1 for input in range(m)]))
        for feature in range(len(x[0])):
            grad.append(
                1.0 / m * sum([sigmoid_nr(self.eval(x[valoare], theta)) - y[valoare] * x[valoare][feature] for valoare in range(m)]))

        return grad

    def fit(self, x, y, max_iter=100, learning_rate=0.1):
        #x = np.insert(x, 0, 1, axis=1)
        coefs = []
        classes = list(set(y))

        self.classes = classes
        costs = [0 for i in range(max_iter)]

        for c in classes:
            # one vs rest
            binary_y = imparte(y, c)
            theta_clasa = [0 for i in range(len(x[0]) + 1)]
            for epoch in range(max_iter):
                # TBA: shuffle the trainind examples in order to prevent cycles
                for i in range(len(x)):  # for each sample from the training data
                    ycomputed = sigmoid_nr(self.eval(x[i], theta_clasa))  # estimate the output
                    crtError = ycomputed - binary_y[i]  # compute the error for the current sample
                for j in range(0, len(x[0])):  # update the coefficients
                    theta_clasa[j + 1] = theta_clasa[j + 1] - learning_rate * crtError * x[i][j]
                theta_clasa[0] = theta_clasa[0] - learning_rate * crtError * 1
            self.coef.append(theta_clasa)

    def predict(self, x):
        predicted = []

        for xi in x:
            aux = []
            for c in self.classes:
                stat = self.eval(xi, self.coef[c])
                stat = sigmoid_nr(stat)
                aux.append(stat)
            predicted.append(argmax(aux))

        return [self.classes[j] for j in predicted]

    def eval(self, xi, theta):
        yi = theta[0]
        for j in range(len(xi)):
            yi += theta[j + 1] * xi[j]
        return yi

    def decide(self, xi):
        a = self.eval(xi)
        return argmax(a)

    def eval_probabilitati(self, xi):
        rez = []
        for c in range(len(self.classes)):
            yi = self.coef[c][0]
            for j in range(len(xi)):
                yi += self.coef[c][j + 1] * xi[j]
            rez.append(sigmoid_nr(yi))
        return rez

    def predict_probabilitati(self, theta, x):
        predicted = []
        for xi in x:
            stat = self.eval(xi, theta)
            stat = sigmoid_nr(stat)
            predicted.append(stat)
        return predicted


def argmax(lista):
    m = max(lista)
    return lista.index(m)


def imparte(y, c):
    return [1 if i == c else 0 for i in y]