import tensorflow as tf
from keras.layers import Input, Conv2D, Conv2DTranspose, UpSampling2D, Concatenate, BatchNormalization, Activation, \
    Dropout, LeakyReLU
from keras.models import Model
from tensorflow.python.keras.initializers.initializers_v1 import RandomNormal

PHOTO_SIZE = 256
NF = 32

def get_generator_model():
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

    c5 = Concatenate()([c5, c3])

    c5 = UpSampling2D()(c5)
    c5 = Conv2DTranspose(NF * 4, (3, 3), activation=None, padding="same", strides=1)(c5)
    c5 = tf.nn.leaky_relu(c5)

    c5 = Concatenate()([c5, c2])

    c6 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c5)
    c6 = tf.nn.leaky_relu(c6)
    c6 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
    c6 = tf.nn.leaky_relu(c6)

    c6 = UpSampling2D()(c6)
    c6 = Conv2DTranspose(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
    c6 = tf.nn.leaky_relu(c6)

    c6 = Concatenate()([c6, c1])

    c7 = Conv2D(NF * 2, (3, 3), activation=None, padding="same", strides=1)(c6)
    c7 = tf.nn.leaky_relu(c7)

    c8 = Conv2D(3, (7, 7), activation=None, padding="same", strides=1)(c7)
    c8 = tf.nn.leaky_relu(c8)

    opt = tf.keras.optimizers.RMSprop()
    generator = Model(inputs=inputs, outputs=c8, name="Generator")
    generator.compile(optimizer=opt, metrics=["accuracy"], loss="binary_crossentropy")

    return generator