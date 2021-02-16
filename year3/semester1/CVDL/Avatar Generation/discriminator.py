import tensorflow_addons as tfa
import tensorflow as tf
from keras.layers import Input, Conv2D, Dropout, Dense, Flatten, Concatenate, BatchNormalization, Activation
from keras.models import Model
from tensorflow.python.keras.initializers.initializers_v1 import RandomNormal
from torch.optim import Adam

PHOTO_SIZE = 256
NF = 32


def get_discriminator_model():
    inputs = Input(shape=(PHOTO_SIZE, PHOTO_SIZE, 3))

    d1 = tfa.layers.SpectralNormalization(Conv2D(NF, (3, 3), activation=None, padding="same", strides=2))
    d1 = d1(inputs)
    d1 = tf.nn.leaky_relu(d1)
    d1 = Dropout(0.4)(d1)
    d2 = tfa.layers.SpectralNormalization(Conv2D(NF, (3, 3), activation=None, padding="same", strides=1))
    d3 = d2(d1)
    d3 = tf.nn.leaky_relu(d3)
    d3 = Dropout(0.4)(d3)

    d4 = tfa.layers.SpectralNormalization(Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=2))
    d4 = d4(d3)
    d4 = tf.nn.leaky_relu(d4)
    d4 = Dropout(0.4)(d4)
    d5 = tfa.layers.SpectralNormalization(Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1))
    d5 = d5(d4)
    d5 = tf.nn.leaky_relu(d5)
    d5 = Dropout(0.4)(d5)

    d6 = tfa.layers.SpectralNormalization(Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=2))
    d6 = d6(d5)
    d6 = tf.nn.leaky_relu(d6)
    d6 = Dropout(0.4)(d6)
    d7 = tfa.layers.SpectralNormalization(Conv2D(NF * 4, (3, 3), activation=None, padding="same", strides=1))
    d7 = d7(d6)
    d7 = tf.nn.leaky_relu(d7)
    d7 = Dropout(0.4)(d7)
    d7 = Flatten()(d7)

    d8 = Dense(1, activation="sigmoid")(d7)
    #d8 = BatchNormalization()(d8)

    # d8 = Conv2D(1, (32, 32), activation="sigmoid")(d7)

    opt = tf.keras.optimizers.RMSprop()
    discriminator = Model(inputs=inputs, outputs=d8, name="Discriminator")
    discriminator.compile(optimizer=opt, metrics=["accuracy"], loss="binary_crossentropy")

    return discriminator