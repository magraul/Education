class MultiTargetGD:
    def __init__(self):
        self.intercepts_ = []
        self.coefs_ = []

    def train(self, x, y, learningRate=0.05, noEpochs=200):
        self.intercepts_ = [0, 0]
        self.coefs_ = [0, 0]

        # gradients = [0 for _ in range(len(x[0]) + 1)]
        # self.intercept_ = 0

        for epoch in range(noEpochs):
            for i in range(len(x)):
                ycomputed_1, ycomputed_2 = self.eval(x[i])
                crtError1 = ycomputed_1 - y[i][0]
                crtError2 = ycomputed_2 - y[i][1]

                self.coefs_[0] = self.coefs_[0] - learningRate * crtError1*x[i]
                self.coefs_[1] = self.coefs_[1] - learningRate * crtError2*x[i]

                self.intercepts_[0] = self.intercepts_[0] - learningRate * crtError1 * 1
                self.intercepts_[1] = self.intercepts_[1] - learningRate * crtError2 * 1

    def eval(self, xi):
        y_1 = self.intercepts_[0] + xi * self.coefs_[0]
        y_2 = self.intercepts_[1] + xi * self.coefs_[1]
        return [y_1, y_2]

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed

    def model(self):
        rez = 'y1 = ' + str(self.intercepts_[0]) + ' + ' + str(self.coefs_[0]) + ' *x' + '\n' + \
        'y2 = ' + str(self.intercepts_[1]) + ' + ' + str(self.coefs_[1]) + ' *x'

        return rez
