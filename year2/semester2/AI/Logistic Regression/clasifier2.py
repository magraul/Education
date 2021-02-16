from math import exp, log
from math import log


def sigmoid(z):
    return [1 / (1 + exp(-i)) for i in z]


def entropy(y, sig):
    m = len(y)
    t1 = [-i * log(j) for i, j in zip(y, sig)]
    t2 = [(1 - i) * log(1 - j) for i, j in zip(y, sig)]
    t3 = [i - j for i, j in zip(t1, t2)]
    return 1 / m * sum(t3)


def crossEntropy(probabilitatiReale, probabilitatiComputate):
    rez = 0.0
    for i, val in enumerate(probabilitatiReale):
        if probabilitatiComputate[i] == 0 and val != 0:
            return float('inf')
        rez += probabilitatiReale[i] * log(probabilitatiComputate[i])
    return -rez


class Classifier2:
    def __init__(self):
        self.coeficienti = []
        self.classes = []
        self.costuri_CrossEntropy = []
        self.costuri_entropy = []

    def cost(self, w, x, y):
        y_value_computed = [i[0] for i in eval_matrix(x, [[i] for i in w])]
        sig = sigmoid(y_value_computed)
        m = len(y)

        cost1 = crossEntropy(y, sig)
        cost2 = entropy(y, sig)

        crtError = [i - j for i, j in zip(y, sig)]

        rez = eval_matrix([crtError], x)[0]
        gradienti = [1/m * i for i in rez]
        return cost1, cost2, gradienti

    def fit(self, x, y, max_iter=400, learning_rate=0.1):
        for i in range(len(x)):
           x[i].insert(0, 1)
        W = []
        classes = list(set(y))
        costs_crossEntropy = [0 for i in range(max_iter)]
        costs_Entropy = [0 for i in range(max_iter)]

        for c in classes:
            # one vs rest
            binary_y = imparte(y, c)

            Wi = [0 for i in range(len(x[0]))]
            for epoch in range(max_iter):
                costs_crossEntropy[epoch], costs_Entropy[epoch], gradienti = self.cost(Wi, x, binary_y)
                for j in range(1, len(x[0])):
                    Wi[j] = Wi[j] + learning_rate * gradienti[j]
                Wi[0] = Wi[0] +learning_rate * gradienti[0]


            W.append(Wi)
        self.coeficienti = W
        self.classes = classes
        self.costuri_CrossEntropy = costs_crossEntropy
        self.costuri_entropy = costs_Entropy

    def predict(self, x):
        for i in range(len(x)):
           x[i].insert(0, 1)

        rez = []
        for xi in x:
            aux = []
            for Wi in self.coeficienti:
                y = eval_matrix([xi], [[i] for i in Wi])
                y = sigmoid(y[0])[0]
                aux.append(y)
            rez.append(argmax(aux))
        return [self.classes[i] for i in rez]


def eval_matrix(m1, m2):
    result = [[0 for j in range(len(m2))] for i in range(len(m1))]

    for i in range(len(m1)):
        for j in range(len(m2[0])):
            for k in range(len(m2)):
                result[i][j] += m1[i][k] * m2[k][j]
    return result


def imparte(y, c):
    return [1 if i == c else 0 for i in y]


def argmax(lista):
    m = max(lista)
    return lista.index(m)
