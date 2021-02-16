from repository import *
from sklearn import linear_model
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import accuracy_score
import matplotlib.pyplot as plt
from clasifier2 import *
import math


def getTrainData(inputs):
    return inputs[:int(0.8 * (len(inputs)))]


def getTestData(inputs):
    return inputs[int(0.8 * (len(inputs))):]


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


# cu tool
def run_tool():
    # impartire
    inputs_setosa, inputs_versicolor, inputs_virginica = load_data()

    setosa_antrenament = getTrainData(inputs_setosa)
    versicolor_antrenament = getTrainData(inputs_versicolor)
    virginica_antrenament = getTrainData(inputs_virginica)

    setosa_test = getTestData(inputs_setosa)
    versicolor_test = getTestData(inputs_versicolor)
    virginica_test = getTestData(inputs_virginica)


    inputs_antrenament = []
    inputs_test = []
    outputs_antrenament = []
    outputs_test = []
    for i in range(len(setosa_antrenament)):
        inputs_antrenament.append(setosa_antrenament[i])
        outputs_antrenament.append(0)
        inputs_antrenament.append(versicolor_antrenament[i])
        outputs_antrenament.append(1)
        inputs_antrenament.append(virginica_antrenament[i])
        outputs_antrenament.append(2)

    for i in range(len(setosa_test)):
        inputs_test.append(setosa_test[i])
        outputs_test.append(0)
        inputs_test.append(versicolor_test[i])
        outputs_test.append(1)
        inputs_test.append(virginica_test[i])
        outputs_test.append(2)

    antrenament_norm, test_norm = normalisation(inputs_antrenament, inputs_test)

    # antrenament
    classifier = linear_model.LogisticRegression(multi_class='ovr', max_iter=100)
    classifier.fit(antrenament_norm, outputs_antrenament)
    print(classifier.intercept_)
    print(classifier.coef_)

    # test
    computedTestOutputs = classifier.predict(test_norm)
    error = 1 - accuracy_score(outputs_test, computedTestOutputs)
    print("classification error (tool): ", error)


run_tool()


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
    inputs_setosa, inputs_versicolor, inputs_virginica = load_data()

    setosa_antrenament = getTrainData(inputs_setosa)
    versicolor_antrenament = getTrainData(inputs_versicolor)
    virginica_antrenament = getTrainData(inputs_virginica)

    setosa_test = getTestData(inputs_setosa)
    versicolor_test = getTestData(inputs_versicolor)
    virginica_test = getTestData(inputs_virginica)

    inputs_antrenament = []
    inputs_test = []
    outputs_antrenament = []
    outputs_test = []
    for i in range(len(setosa_antrenament)):
        inputs_antrenament.append(setosa_antrenament[i])
        outputs_antrenament.append(0)
        inputs_antrenament.append(versicolor_antrenament[i])
        outputs_antrenament.append(1)
        inputs_antrenament.append(virginica_antrenament[i])
        outputs_antrenament.append(2)

    for i in range(len(setosa_test)):
        inputs_test.append(setosa_test[i])
        outputs_test.append(0)
        inputs_test.append(versicolor_test[i])
        outputs_test.append(1)
        inputs_test.append(virginica_test[i])
        outputs_test.append(2)

    antrenament_norm, test_norm = normalizare(inputs_antrenament, inputs_test)

    # antrenament
    classifier = Classifier2()

    classifier.fit(antrenament_norm, outputs_antrenament)
    print(classifier.coeficienti)
    print('costuri')
    print('entropy')
    print(classifier.costuri_entropy)
    plt.plot(classifier.costuri_CrossEntropy)
    plt.plot(classifier.costuri_entropy)
    plt.show()
    print('cross entropy')
    print(classifier.costuri_CrossEntropy)

    # test
    computedTestOutputs = classifier.predict(test_norm)
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, outputs_test):
        if t1 != t2:
            error += 1
    error = error / len(outputs_test)
    print("classification error (manual): ", error)

run_manual()