from sklearn import metrics
from PIL import Image
import numpy as np
from repository import load_data
import math
from neural_network import *
import os
import matplotlib.pyplot as plt


def getTrainData(inputs):
    return inputs[:int(0.8 * (len(inputs)))]


def getTestData(inputs):
    return inputs[int(0.8 * (len(inputs))):]


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
    neural_network = NeuralNetwork(5, 3)
    neural_network.train(antrenament_norm, outputs_antrenament)

    # test
    computedTestOutputs = neural_network.predict(test_norm)
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, outputs_test):
        if t1 != t2:
            error += 1
    error = error / len(outputs_test)
    print("classification error (manual): ", error)


#run_manual()

def flatten(mat):
    x = []
    for line in mat:
        for el in line:
            for i in el:
                x.append(i)
    return x


def flatten2(mat):
    x = []
    for line in mat:
        for el in line:
            x.append(el)
    return x


def run_sepia():
    # incarcam datele
    poze = []
    outputs = [1, 0, 1, 1, 1, 0, 0, 0, 1]

    for filename in os.listdir('poze_sepia'):
        image = Image.open('poze_sepia/' + filename)
        image = image.resize((20, 20), Image.ANTIALIAS)
        poze.append(image)

    poze_pixeli = [flatten(np.asarray(i)) for i in poze]

    # impartire
    inputs_antrenament = getTrainData(poze_pixeli)
    outputs_antrenament = getTrainData(outputs)

    inputs_test = getTestData(poze_pixeli)
    outputs_test = getTestData(outputs)

    # antrenament

    antrenament_norm, test_norm = normalizare(inputs_antrenament, inputs_test)

    neural_network = NeuralNetwork(len(poze_pixeli[0]), 2)
    neural_network.train(antrenament_norm, outputs_antrenament)

    # test
    computedTestOutputs = neural_network.predict(test_norm)
    error = 0.0
    print(outputs_test)
    print(computedTestOutputs)
    for t1, t2 in zip(computedTestOutputs, outputs_test):
        if t1 != t2:
            error += 1
    error = error / len(outputs_test)
    print("classification error (manual): ", error)
    print(metrics.classification_report(outputs_test, computedTestOutputs, digits=3))


run_sepia()


def run_fete():
    # incarcam datele
    poze_happy = []
    poze_sad = []

    for filename in os.listdir('happy'):
        image = Image.open('happy/' + filename)
        poze_happy.append(image)

    for filename in os.listdir('sadness'):
        image = Image.open('sadness/' + filename)
        poze_sad.append(image)

    happy_pixels = [flatten2(np.asarray(i)) for i in poze_happy]
    sad_pixels = [flatten2(np.asarray(i)) for i in poze_sad]

    # impartire
    happy_train_in = getTrainData(happy_pixels)
    sad_train_in = getTrainData(sad_pixels)

    happy_test_in = getTestData(happy_pixels)
    sad_test_in = getTestData(sad_pixels)


    inputs_antrenament = []
    inputs_test = []
    outputs_antrenament = []
    outputs_test = []

    #train
    for i in range(len(happy_train_in)):
        inputs_antrenament.append(happy_train_in[i])
        outputs_antrenament.append(1)

    for i in range(len(sad_train_in)):
        inputs_antrenament.append(sad_train_in[i])
        outputs_antrenament.append(0)

    #test
    for i in range(len(happy_test_in)):
        inputs_test.append(happy_test_in[i])
        outputs_test.append(1)

    for i in range(len(sad_test_in)):
        inputs_test.append(sad_test_in[i])
        outputs_test.append(0)

    # antrenament

    antrenament_norm, test_norm = normalizare(inputs_antrenament, inputs_test)

    neural_network = NeuralNetwork(len(inputs_test[0]), 2)
    neural_network.train(antrenament_norm, outputs_antrenament)

    # test
    computedTestOutputs = neural_network.predict(test_norm)
    error = 0.0
    print(outputs_test)
    print(computedTestOutputs)
    for t1, t2 in zip(computedTestOutputs, outputs_test):
        if t1 != t2:
            error += 1
    error = error / len(outputs_test)
    print("classification error (manual): ", error)
    print(metrics.classification_report(outputs_test, computedTestOutputs, digits=3))

    # plt.plot(neural_network.costs)
    # plt.show()


#run_fete()