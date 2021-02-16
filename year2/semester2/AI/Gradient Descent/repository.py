import csv


def loadData(fileName, input1VariabName, input2VariabName, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    selectedVariable1 = dataNames.index(input1VariabName)
    selectedVariable2 = dataNames.index(input2VariabName)

    inputs1 = [float(data[i][selectedVariable1]) for i in range(len(data))]
    inputs2 = [float(data[i][selectedVariable2]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs1, inputs2, outputs

import os

crtDir =  os.getcwd()
filePath = os.path.join(crtDir, 'data', 'data.csv')

