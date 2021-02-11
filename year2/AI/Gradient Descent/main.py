from repository import *
from sklearn.preprocessing import StandardScaler
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
import math
from BGD import *
import statistics
from multiOutut import *

produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')


def error_f(real, computed):
    # return math.sqrt(
    #         sum((r - c) ** 2 for r, c in zip(real, computed)) / len(
    #             real))

    return sum((r - c) ** 2 for r, c in zip(real, computed)) / len(real)


def errorRegresirMultiTarget(matriceOutputuriReale, matriceOutputuriComputate):
    erori = []
    for i in range(len(matriceOutputuriComputate)):
        erori.append(math.sqrt(
            sum((r - c) ** 2 for r, c in zip(matriceOutputuriReale[i], matriceOutputuriComputate[i])) / len(
                matriceOutputuriReale[i])))
    return statistics.mean(erori)


def getTrainData(input1, input2):
    rez = [[i1, i2] for i1, i2 in zip(input1, input2)]
    return rez[:int(0.8 * (len(rez)))]


def getTrainResults(results):
    return results[:int(0.8 * (len(results)))]


def getTestData(input1, input2):
    rez = [[i1, i2] for i1, i2 in zip(input1, input2)]
    return rez[int(0.8 * (len(rez))):]


def getTestResults(results):
    return results[int(0.8 * (len(results))):]


def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]

        scaler.fit(trainData)
        normalisedTrainData = scaler.transform(trainData)
        normalisedTestData = scaler.transform(testData)

        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)
        normalisedTrainData = scaler.transform(trainData)
        normalisedTestData = scaler.transform(testData)
    return normalisedTrainData, normalisedTestData


def run_cu_tool():
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom',
                                                      'Happiness.Score')

    features = [produs_intern_brut, freedom]
    # impartire
    X_train = getTrainData(produs_intern_brut, freedom)
    Y_train = getTrainResults(happiness)
    nrEpoci = len(X_train)

    X_test = getTestData(produs_intern_brut, freedom)
    Y_test = getTestResults(happiness)

    # normalizare
    train_normalised, test_normalised = normalisation(X_train, X_test)
    train_output_normalised, test_output_normalised = normalisation(Y_train, Y_test)

    # print(train_normalised)
    # print(train_output_normalised)

    # antrenament
    regressor = linear_model.SGDRegressor(alpha=0.01)
    #regressor.fit(train_normalised, train_output_normalised)
    lr = 0.01
    m = len(X_train)
    for i in range(nrEpoci):
        # regressor.partial_fit(train_normalised, train_output_normalised)
        # rez = regressor.predict(train_normalised)
        # crt_error = mean_squared_error(train_output_normalised, rez)
        #
        # for i in range(len(regressor.coef_)):
        #     regressor.coef_[i] = regressor.coef_[i] - lr * crt_error * statistics.mean(features[i])
        # regressor.intercept_ = regressor.intercept_ - lr * crt_error

        gradients = []
        regressor.partial_fit(train_normalised, train_output_normalised)
        rez = regressor.predict(train_normalised)
        for feature in range(len(train_normalised[0])):
            gradients.append(1.0 / m * sum(
                [(rez[valoare] - train_output_normalised[valoare]) * train_normalised[valoare][feature] for valoare in
                range(len(train_normalised))]))

            gradients.append(
                1.0 / m * sum([(rez[valoare] - train_output_normalised[valoare]) * 1 for valoare in range(len(train_normalised))]))

            for i in range(len(regressor.coef_)):
                regressor.coef_[i] = regressor.coef_[i] - lr * gradients[i]
            regressor.intercept_ = regressor.intercept_ - lr * gradients[-1]

            # regressor.fit(train_normalised, train_output_normalised)

    print(str(regressor.intercept_) + ' ' + str(regressor.coef_))

    # test
    rez = regressor.predict(test_normalised)

    error = mean_squared_error(test_output_normalised, rez)
    print('eroarea: ')
    print(error)


def normalizare(trainData, testData):
    deviatii = []
    medii = []
    if not isinstance(trainData[0], list):
        # un singur feature
        medii.append(sum(trainData) / len(trainData))
        deviatii.append(math.sqrt(1 / len(trainData) * sum([(i - medii[0]) ** 2 for i in trainData])))
        normalised_train_Data = [(i - medii[0]) / deviatii[0] for i in trainData]
        normalised_test_Data = [(i - medii[0]) / deviatii[0] for i in testData]

        return normalised_train_Data, normalised_test_Data

    for feature in range(len(trainData[0])):
        medii.append(sum([lista[feature] for lista in trainData]) / len(trainData))
        deviatii.append(
            math.sqrt(1 / len(trainData) * sum([(lista[feature] - medii[feature]) ** 2 for lista in trainData])))

    normalised_train_Data = [[(lista[i] - medii[i]) / deviatii[i] for i in range(len(lista))] for lista in trainData]
    normalised_test_Data = [[(lista[i] - medii[i]) / deviatii[i] for i in range(len(lista))] for lista in testData]

    return normalised_train_Data, normalised_test_Data


def run_manual():
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom',
                                                      'Happiness.Score')

    # impartire
    X_train = getTrainData(produs_intern_brut, freedom)
    Y_train = getTrainResults(happiness)

    X_test = getTestData(produs_intern_brut, freedom)
    Y_test = getTestResults(happiness)

    # normalizare
    train_normalised, test_normalised = normalizare(X_train, X_test)
    train_output_normalised, test_output_normalised = normalizare(Y_train, Y_test)

    # antrenament

    # print(train_normalised)
    # print(train_output_normalised)

    regressor = BGDRegression()
    regressor.train(train_normalised, train_output_normalised)
    print('model: ')
    print(regressor.model())

    #test
    rez = regressor.predict(test_normalised)
    er = error_f(test_output_normalised, rez)
    print('eroarea: ')
    print(er)


# GD NON LINEAR
def gd_non_linear():
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom',
                                                      'Happiness.Score')

    input1 = freedom
    input2 = [i ** 2 for i in freedom]
    # Y = t0 + t1* x1 + t2* x1^2


    # impartire
    X_train = getTrainData(input1, input2)
    Y_train = getTrainResults(happiness)

    X_test = getTestData(input1, input2)
    Y_test = getTestResults(happiness)

    # normalizare
    train_normalised, test_normalised = normalizare(X_train, X_test)
    train_output_normalised, test_output_normalised = normalizare(Y_train, Y_test)

    # antrenament
    regressor = BGDRegression()
    regressor.train(train_normalised, train_output_normalised)
    print('model: ')
    print(regressor.model())

    # test
    rez = regressor.predict(test_normalised)
    er = error_f(test_output_normalised, rez)
    print('eroarea: ')
    print(er)


def multi_output():
    produs_intern_brut, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom',
                                                      'Happiness.Score')

    input = freedom
    # d1: y = t0 + t1* x
    # d2: y = t2 + t3* x

    # impartire
    X_train = getTestResults(input)
    Y_train = getTestData(produs_intern_brut, happiness)

    X_test = getTestResults(input)
    Y_test = getTestData(produs_intern_brut, happiness)

    # normalizare
    train_normalised, test_normalised = normalizare(X_train, X_test)
    train_output_normalised, test_output_normalised = normalizare(Y_train, Y_test)

    # antrenament
    regressor = MultiTargetGD()
    regressor.train(train_normalised, train_output_normalised)
    print('model: ')
    print(regressor.model())

    # test
    rez = regressor.predict(test_normalised)
    er = errorRegresirMultiTarget(test_output_normalised, rez)
    print('eroarea: ')
    print(er)



print('tool')
run_cu_tool()


print('\nmanual')
run_manual()


#print('non linear')
#gd_non_linear()

print('\nmulti output')
multi_output()
