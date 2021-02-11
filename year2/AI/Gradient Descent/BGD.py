

class BGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    def train(self, x, y, learningRate=0.05, noEpochs=200):
        m = len(x)

        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]
        gradients = [0 for _ in range(len(x[0]) + 1)]
        self.intercept_ = 0
        for epoch in range(noEpochs):

            for feature in range(len(x[0])):
                gradients.append(1.0 / m * sum([(self.eval(x[valoare]) - y[valoare]) * x[valoare][feature] for valoare in range(m)]))
            gradients.append(1.0 / m * sum([(self.eval(x[input]) - y[input]) * 1 for input in range(m)]))

            for j in range(0, len(x[0])):
                self.coef_[j] = self.coef_[j] - learningRate * gradients[j]
            self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * gradients[-1]
            gradients = []

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]



    def eval(self, xi):
        yi = self.coef_[-1]
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed

    def model(self):
        rez = 'y = ' + str(self.intercept_) + ' + '
        for i in range(len(self.coef_)):
            rez += str(self.coef_[i]) + '*x' + str(i+1) + ' + '
        return rez[:-2]






















# def statisticalNormalisation(features):
#     # meanValue = sum(features) / len(features)
#     meanValue = mean(features)
#     # stdDevValue = (1 / len(features) * sum([ (feat - meanValue) ** 2 for feat in features])) ** 0.5
#     stdDevValue = stdev(features)
#     normalisedFeatures = [(feat - meanValue) / stdDevValue for feat in features]
#     return normalisedFeatures
