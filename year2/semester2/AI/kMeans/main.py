from REPOSITORY import load_data
from kmeans import *
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
from sklearn import linear_model


propozitii = load_data()

def getTrainData(inputs):
    return inputs[:int(0.8 * (len(inputs)))]


def getTestData(inputs):
    return inputs[int(0.8 * (len(inputs))):]

clasee = ['happy', 'sad', 'surprise', 'disgust', 'anger', 'fear', 'shame']


date_antrenament = dict()
for clasa in propozitii:
    date_antrenament[clasa] = getTrainData(propozitii[clasa])

date_test = dict()
for clasa in propozitii:
    date_test[clasa] = getTestData(propozitii[clasa])


inputs_antrenament = []
inputs_test = []
outputs_antrenament = []
outputs_test = []

for clasa in date_antrenament:
    inputs_antrenament += date_antrenament[clasa]
    outputs_antrenament += [clasa for i in range(len(date_antrenament[clasa]))]

for clasa in date_test:
    inputs_test += date_test[clasa]
    outputs_test += [clasa for i in range(len(date_test[clasa]))]


#antrenament
clase = [i for i in propozitii.keys()]
#classifier = KMEANS(clase)
classifier = KMeans(len(clase))
classifier2 = linear_model.LogisticRegression(multi_class='ovr', max_iter=100)
outputs_test = [clasee.index(i) for i in outputs_test]
outputs_antrenament = [clasee.index(i) for i in outputs_antrenament]
vectorizer = TfidfVectorizer(max_features=200)

trainFeatures = vectorizer.fit_transform(inputs_antrenament)

testFeatures = vectorizer.transform(inputs_test)

aux = trainFeatures.toarray()
classifier.fit(trainFeatures.toarray())
classifier2.fit(trainFeatures.toarray(), outputs_antrenament)

# test
computedTestOutputs = classifier.predict(testFeatures.toarray())
#outputs_test = [clasee.index(i) for i in outputs_test]
error = 0.0
for t1, t2 in zip(computedTestOutputs, outputs_test):
    if t1 != t2:
        error += 1
error = error / len(outputs_test)
print("classification error (manual): ", error)

print('computed:')
print(computedTestOutputs)
print('real:')
print(outputs_test)

print('supervizat:')
computedTestOutputs = classifier2.predict(testFeatures.toarray())
error = 0.0
for t1, t2 in zip(computedTestOutputs, outputs_test):
    if t1 != t2:
        error += 1
error = error / len(outputs_test)
print("classification error (manual): ", error)

print('computed:')
print(computedTestOutputs)
print('real:')
print(outputs_test)

