from sklearn import linear_model
import math
from sklearn.metrics import mean_squared_error
from Regresor import *
from repo import *


def error(real, computed):
    # return math.sqrt(
    #         sum((r - c) ** 2 for r, c in zip(real, computed)) / len(
    #             real))

    return sum((r - c) ** 2 for r, c in zip(real, computed)) / len(real)


#
# XX = numpy.array(X)
#
# transpusa = XX.transpose()
# print(numpy.matmul(numpy.matmul(numpy.linalg.inv(numpy.matmul(transpusa, XX)), transpusa), Y))


def getTrainData(input1, input2):
    rez = [[i1, i2] for i1, i2 in zip(input1, input2)]
    return rez[:int(0.8*(len(rez)))]


def getTrainResults(results):
    return results[:int(0.8*(len(results)))]


def getTestData(input1, input2):
    rez = [[i1, i2] for i1, i2 in zip(input1, input2)]
    return rez[int(0.8 * (len(rez))):]


def getTestResults(results):
    return results[int(0.8 * (len(results))):]


def runManual():
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')
    r = Regresor()

    X = getTrainData(produs_intern_brut, freedom)
    Y = getTrainResults(happiness)

    r.train(X, Y)
    print(r.model())

    X = getTestData(produs_intern_brut, freedom)
    Y = getTestResults(happiness)

    outTest = r.test(X)

    print('eroarea: ')
    print(error(Y, outTest))


def runTool():
    print('\ntool')
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom',
                                                      'Happiness.Score')

    X = getTrainData(produs_intern_brut, freedom)
    Y = getTrainResults(happiness)

    regressor = linear_model.LinearRegression()
    regressor.fit(X, Y)
    print('model')
    print('intercept: ' + str(regressor.intercept_))
    print('coef: ' + str(regressor.coef_))

    X = getTestData(produs_intern_brut, freedom)
    Y = getTestResults(happiness)

    rez = regressor.predict(X)

    print('error:')
    print(mean_squared_error(Y, rez))


runManual()
runTool()


