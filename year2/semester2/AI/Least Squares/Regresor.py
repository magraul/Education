import numpy
import statistics


def cov(X, Y):

    xmean = sum(X) / len(X)
    ymean = sum(Y) / len(Y)

    numarator = 0.0
    for i in range(len(X)):
        numarator += (X[i] - xmean) * (Y[i] - ymean)

    return numarator / (len(X)-1)


class Regresor:
    def __index__(self):
        self.intercept = 0.0
        self.m1 = 0.0
        self.m2 = 0.0

    # traininputs: matrice n*2
    def train(self, trainInputs, trainOutputs):
        X2 = [i[0] for i in trainInputs]
        X1 = [i[1] for i in trainInputs]
        Y = trainOutputs

        x1mean = sum(X1) / len(X1)
        x2mean = sum(X2) / len(X2)
        ymean = sum(Y) / len(Y)

        # cov(Y, X1) = m1 cov(X1, X1) + m2 cov(X2, X1)
        # cov(Y, X2) = m1 cov (X1 ,X2) + m2 cov (X2, X2)

        C1 = cov(Y, X1)
        C2 = cov(X1, X1)
        C3 = cov(X2, X1)

        C4 = cov(Y, X2)
        C5 = cov(X1, X2)
        C6 = cov(X2, X2)

        # l1 = [cov_x1_x1, cov_x2_x1]
        # l2 = [cov_x1_x2, cov_x2_x2]
        # a = numpy.array([l1, l2])
        # b = numpy.array([cov_y_x1, cov_y_x2])
        # rez = numpy.linalg.solve(a, b)

        self.m1 = (C2*C4 - C5*C1) / (C2*C6-C5*C3)
        self.m2 = (C1-self.m1*C3) / C2

        #self.m1 = rez.item(1)
        #self.m2 = rez.item(0)
        # y - ymean = m1(x1 - x1_mean) + m2 (x2 - x2_mean)

        self.intercept = ymean - self.m2*x1mean - self.m1 * x2mean

    def model(self):
        return 'y = ' + str(self.intercept) + ' + ' + str(self.m1) + ' * x1 + ' + str(self.m2) + ' * x2'

    def test(self, inputsTest):
        return [self.intercept + self.m1 * elem[0] + self.m2 * elem[1] for elem in inputsTest]
