from sklearn.datasets.samples_generator import make_blobs
from SVM_R import *

import matplotlib.pyplot as plt


(inputs, outputs) = make_blobs(n_samples=150, n_features=2, centers=2, cluster_std=1.11, random_state=20)

antrenament_in, antrenament_out = inputs[:int(0.8*len(inputs))], outputs[:int(0.8*len(outputs))]
test_in, test_out = inputs[int(0.8*len(inputs)):], outputs[int(0.8*len(inputs)):]


inputs_full = np.c_[np.ones((antrenament_in.shape[0])), antrenament_in]

plt.scatter(inputs_full[:, 1], inputs_full[:, 2], marker='o', c=antrenament_out)
plt.show()

pozitiv = []
negativ = []
for i, v in enumerate(antrenament_out):
    if v == 0:
        negativ.append(antrenament_in[i])
    else:
        pozitiv.append(antrenament_in[i])

N = np.array(negativ)
P = np.array(pozitiv)


svm = SVM()
svm.train(N, P)
svm.show(inputs_full, N, P, antrenament_out)
o = [-1 if i == 0 else 1 for i in test_out]


#test

computed = svm.predict(test_in)
computed = np.array(computed).astype(int)

print(computed)
print(o)
error = 0
for t1, t2 in zip(computed, o):
    if t1 != t2:
        error += 1
error = error / len(o)
print("classification error (manual): ", error)

