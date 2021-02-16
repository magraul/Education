from keras.models import Model
import keras as keras
from keras.layers import Concatenate, UpSampling2D, Dense, concatenate, Input, MaxPool2D, Conv2D, Conv2DTranspose, \
    BatchNormalization, Dropout
import tensorflow as tf
import tensorflow_addons as tfa
from tensorflow.keras import callbacks
from keras import backend as K
import numpy as np
#from load_data import loadData, loadDataV2, loadDataV3

PHOTO_SIZE = 256
NF = 32

'''
GENERATOR
'''

inputs = Input(shape=(PHOTO_SIZE, PHOTO_SIZE, 3))
c1 = Conv2D(NF, (7, 7), activation=None, padding="same")(inputs)

c2 = Conv2D(NF, (3, 3), activation=None, padding="same", strides=2)(c1)
c2 = tf.nn.leaky_relu(c2)
c2 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c2)
c2 = tf.nn.leaky_relu(c2)

c3 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=2)(c2)
c3 = tf.nn.leaky_relu(c3)
c3 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c3)
c3 = tf.nn.leaky_relu(c3)

c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c3)
c4 = tf.nn.leaky_relu(c4)
c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)

c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)
c4 = tf.nn.leaky_relu(c4)
c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)

c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)
c4 = tf.nn.leaky_relu(c4)
c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)

c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)
c4 = tf.nn.leaky_relu(c4)
c4 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)

c5 = Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c4)
c5 = tf.nn.leaky_relu(c5)

c5 = UpSampling2D()(c5)
c5 = Conv2DTranspose(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c5)
c5 = tf.nn.leaky_relu(c5)

c6 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c5)
c6 = tf.nn.leaky_relu(c6)
c6 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
c6 = tf.nn.leaky_relu(c6)

c6 = UpSampling2D()(c6)
c6 = Conv2DTranspose(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
c6 = tf.nn.leaky_relu(c6)

c7 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
c7 = tf.nn.leaky_relu(c7)

c8 = Conv2D(3, (7, 7), activation=None, padding="same", strides=1)(c7)
c8 = tf.nn.leaky_relu(c8)

model = Model(inputs=inputs, outputs=c8, name="TunMe")

model.compile(optimizer='adam', metrics=["accuracy"], loss="binary_crossentropy")
model.summary()

inputs2 = Input(shape=(PHOTO_SIZE, PHOTO_SIZE, 3))

conv2d = Conv2D(NF, (3, 3), activation=None, padding="same", strides=2)(inputs2)

d1 = tfa.layers.SpectralNormalization(Conv2D(NF, (3, 3), activation=None, padding="same", strides=2))
d1 = d1(inputs2)
d1 = tf.nn.leaky_relu(d1)
d2 = tfa.layers.SpectralNormalization(Conv2D(NF, (3, 3), activation=None, padding="same", strides=1))
d3 = d2(d1)
d3 = tf.nn.leaky_relu(d3)

d4 = tfa.layers.SpectralNormalization(Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=2))
d4 = d4(d3)
d4 = tf.nn.leaky_relu(d4)
d5 = tfa.layers.SpectralNormalization(Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1))
d5 = d5(d4)
d5 = tf.nn.leaky_relu(d5)

d6 = tfa.layers.SpectralNormalization(Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=2))
d6 = d6(d5)
d6 = tf.nn.leaky_relu(d6)
d7 = tfa.layers.SpectralNormalization(Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1))
d7 = d7(d6)
d7 = tf.nn.leaky_relu(d7)

d8 = Conv2D(1, (32, 32), activation="sigmoid")(d7)

model1 = Model(inputs=inputs2, outputs=d8, name="DISCRIMINEAZA-MA")

model1.compile(optimizer='adam', metrics=["accuracy"], loss="binary_crossentropy")
model1.summary()

# trainInputAvatars, trainOutputAvatars = loadDataV2("E:/Facultate/DataSets/Dataset-Avatars/", 1, "png")
# trainInputFaces, trainOutputFaces = loadDataV2("E:/Facultate/DataSets/HumanFaces/", 0, "jpg")

# for inputsica in trainInputFaces:
#     trainInputAvatars.append(inputsica)
#
# for outputsica in trainOutputFaces:
#     trainOutputAvatars.append(outputsica)

# trainInput = np.concatenate(trainInputAvatars, trainInputFaces)
# trainOutput = np.concatenate(trainOutputAvatars, trainOutputFaces)

trainInput, trainOutput = [[1,2]]#loadDataV3("E:/Facultate/DataSets/data/")

# a = trainInput[0]
# print(a.flatten())
# rainInput = [j.flatten() for j in [i.flatten() for i in trainInput]]
# trainInput = [np.asarray(i.flatten()).reshape(100, 1) for i in trainInput]
trainInput = [list(i.flatten()) for i in trainInput]
a = trainInput[0]
# trainInput = np.concatenate(trainInput).tolist()
trainInput = np.asarray(trainInput)
trainInput = np.reshape(trainInput, (200, PHOTO_SIZE, PHOTO_SIZE, 3))
print(trainInput.shape)
trainOutput = np.asarray(trainOutput)

model1.fit(trainInput, trainOutput, epochs=32, batch_size=1)
