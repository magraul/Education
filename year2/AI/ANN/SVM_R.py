import numpy as np
np.random.seed(4)
import matplotlib.pyplot as plt


class SVM:
    def __init__(self):
        self.w = []
        self.b = []  # bias

    def train(self, N, P, learning_rate=0.1):
        max_val_feature = float('-inf')

        if np.amax(N) > max_val_feature:
            max_val_feature = np.amax(N)

        if np.amax(P) > max_val_feature:
            max_val_feature = np.amax(P)

        D = [(-1, N), (1, P)]
        lungimi_vectori = {}
        transformari = [[-1, -1], [-1, 1], [1, -1], [1, 1]]

        w_optim = max_val_feature

        self.w = np.array([w_optim, w_optim])
        optim = False
        while not optim:
            for b in np.arange(-1 * (max_val_feature), max_val_feature):
                for tr in transformari:
                    w_t = self.w * tr
                    corect = True
                    for touple in D:
                        for xi in touple[1]:
                            if touple[0] * (np.dot(w_t, xi) + b) < 1:  # corect daca w_t * xi >= 1
                                corect = False

                    if corect:
                        # avem un hiperplan de separare, calculam norma si salvan coeficientii
                        a = np.linalg.norm(w_t)
                        lungimi_vectori[a] = [w_t, b]

            if self.w[0] < 0:
                optim = True
            else:
                self.w = self.w - learning_rate

        norme = sorted([n for n in lungimi_vectori])

        norma_minima = lungimi_vectori[norme[0]]
        self.w = norma_minima[0]
        self.b = norma_minima[1]

        w_optim = self.w[0] + learning_rate

    def show(self, inputs, N, P, o):
        max_val_feature = float('-inf')
        min_val_feature = float('+inf')

        if np.amax(N) > max_val_feature:
            max_val_feature = np.amax(N)

        if np.amax(P) > max_val_feature:
            max_val_feature = np.amax(P)

        if np.amin(N) < min_val_feature:
            min_val_feature = np.amin(N)

        if np.amin(P) < min_val_feature:
            min_val_feature = np.amin(P)

        fig = plt.figure()
        ax = fig.add_subplot(1, 1, 1)
        plt.scatter(inputs[:, 1], inputs[:, 2], marker='o', c=o)


        # vector suport hiperplan pozitiv
        vs1 = self.eval(min_val_feature, self.w, self.b, 1)
        vs2 = self.eval(max_val_feature, self.w, self.b, 1)
        ax.plot([min_val_feature, max_val_feature], [vs1, vs2], 'k')

        # vector suport hiperplan negativ
        vs1 = self.eval(min_val_feature, self.w, self.b, -1)
        vs2 = self.eval(max_val_feature, self.w, self.b, -1)
        ax.plot([min_val_feature, max_val_feature], [vs1, vs2], 'k')

        # vector suport hiperplan central
        vc1 = self.eval(min_val_feature, self.w, self.b, 0)
        vc2 = self.eval(max_val_feature, self.w, self.b, 0)
        ax.plot([min_val_feature, max_val_feature], [vc1, vc2], 'y--')

        plt.show()

    def eval(self, x, w, b, v):
        return (-w[0] * x - b + v) / w[1]

    def predict(self, x):
        rez = []
        for xi in x:
            rez.append(np.sign(np.dot(np.array(xi), self.w) + self.b))
        return rez