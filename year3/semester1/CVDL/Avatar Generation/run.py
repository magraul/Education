import os
import numpy as np
import random as rnd
import tensorflow as tf
from matplotlib import pyplot
from discriminator import get_discriminator_model
from gan import get_gan_model
from generator import get_generator_model
from load_data import load_images
from keras.preprocessing.image import save_img
import matplotlib.pyplot as plt

os.environ['TF_FORCE_GPU_ALLOW_GROWTH'] = 'true'
config = tf.compat.v1.ConfigProto()
config.gpu_options.allow_growth = True
config.gpu_options.per_process_gpu_memory_fraction = 0.5
session = tf.compat.v1.Session(config=config)


PHOTO_SIZE = 256
INPUT_SIZE = 200

#train_input_avatar, train_output_avatar = load_images("E:/Facultate/DataSets/output/", "jpg", 1)
#train_input_avatar, train_output_avatar = load_images("E:/Facultate/DataSets/IIIT-CFW1.0/cartoonFaces/", "jpeg", 1)
#train_input_avatar, train_output_avatar = load_images("E:/Facultate/DataSets/photoToCartoon/trainB/", "png", 1)
#train_input_avatar, train_output_avatar = load_images("E:/Facultate/DataSets/Dataset-Avatars/", "png", 1)
train_input_avatar, train_output_avatar = load_images("D:/Facultate/Semestrul 5/Computer Vision and Deep Learning/Laborator/White-box-Cartoonization-master - Copy/dataset/avatars_white/", "png", 1)
#train_input_avatar, train_output_avatar = load_images("E:/Facultate/DataSets/avatars_black_background/", "png", 1)
train_input_avatar = [list(i.flatten()) for i in train_input_avatar]
train_input_avatar = np.asarray(train_input_avatar)
train_input_avatar = np.reshape(train_input_avatar, (INPUT_SIZE, PHOTO_SIZE, PHOTO_SIZE, 3))
train_output_avatar = np.asarray(train_output_avatar)

#train_input_faces, train_output_faces = load_images("E:/Facultate/DataSets/input/", "jpg", 0)
#train_input_faces, train_output_faces = load_images("E:/Facultate/DataSets/HumanFaces_resized/", "jpg", 0)
train_input_faces, train_output_faces = load_images("D:/Facultate/Semestrul 5/Computer Vision and Deep Learning/Laborator/White-box-Cartoonization-master - Copy/dataset/faces/", "png", 0)
train_input_faces = [list(i.flatten()) for i in train_input_faces]
train_input_faces = np.asarray(train_input_faces)
train_input_faces = np.reshape(train_input_faces, (INPUT_SIZE, PHOTO_SIZE, PHOTO_SIZE, 3))
train_output_faces = np.asarray(train_output_faces)

generator = get_generator_model()
discriminator = get_discriminator_model()


train_input = np.vstack((train_input_avatar, train_input_faces))
train_output = np.vstack((train_output_avatar, train_output_faces))

try:
    session.run(discriminator.fit(train_input, train_output, epochs=10, batch_size=2))
except Exception as e:
    print(str(e))

gan = get_gan_model(generator, discriminator)

def save_plot(examples, epoch):
    # plot images
    for i in range(2):
        # define subplot
        pyplot.subplot(2, 2, 1 + i)
        # turn off axis
        pyplot.axis('off')
        # plot raw pixel data
        pyplot.imshow(examples[i, :, :, 0], cmap='gray_r')
    # save plot to file
    filename = 'generated_plot_e%03d.png' % (epoch + 1)
    pyplot.savefig(filename)
    pyplot.close()

def summarize_performance(epoch, g_model, d_model, half_batch):
    # prepare real samples
    input_real = []
    output_real = []
    for image_index in range(half_batch):
        image_positon = rnd.randrange(0, INPUT_SIZE - 1, 1)
        input_real.append(train_input_avatar[image_positon])
        output_real.append(train_output_avatar[image_positon])
    input_real = np.asarray(input_real)
    output_real = np.asarray(output_real)

    # evaluate discriminator on real examples
    _, acc_real = d_model.evaluate(input_real, output_real, verbose=0)
    # prepare fake examples
    input_fake = []
    output_fake = []
    for image_index in range(half_batch):
        # get face image and feed it to generator
        image_positon = rnd.randrange(0, INPUT_SIZE - 1, 1)
        image_face = []
        image_face.append(train_input_faces[image_positon])
        image_face = np.asarray(image_face)

        # get the prediction from generator
        image_fake = generator.predict(image_face)
        input_fake.append(image_fake[0])
        output_fake.append(train_output_faces[image_positon])
    input_fake = np.asarray(input_fake)
    output_fake = np.asarray(output_fake)
    # evaluate discriminator on fake examples
    _, acc_fake = d_model.evaluate(input_fake, output_fake, verbose=0)
    # summarize discriminator performance
    print('>Accuracy real: %.0f%%, fake: %.0f%%' % (acc_real * 100, acc_fake * 100))
    # save plot
    save_plot(input_fake, epoch)
    # save the generator model tile file
    filename = 'generator_model_%03d.h5' % (epoch + 1)
    g_model.save(filename)


def train(generator, discriminator, gan, n_epochs=30, n_batch=8):
    bat_per_epo = int(INPUT_SIZE / n_batch)
    half_batch = int(n_batch / 2)
    xaxis = []
    dyaxis = []
    gyaxis = []
    for i_epoch in range(n_epochs):

        print(str(i_epoch) + "/" + str(n_epochs))
        sumd = 0
        sumg = 0
        for i_bat_per_epo in range(bat_per_epo):

            # get real avatars for discriminator
            input_real = []
            output_real = []
            for image_index in range(half_batch):
                image_positon = rnd.randrange(0, INPUT_SIZE - 1, 1)
                input_real.append(train_input_avatar[image_positon])
                output_real.append(train_output_avatar[image_positon])
            input_real = np.asarray(input_real)
            output_real = np.asarray(output_real)

            # generate fake avatars for discriminator
            input_fake = []
            output_fake = []
            for image_index in range(half_batch):
                # get face image and feed it to generator
                image_positon = rnd.randrange(0, INPUT_SIZE - 1, 1)
                image_face = []
                image_face.append(train_input_faces[image_positon])
                image_face = np.asarray(image_face)

                # get the prediction from generator
                image_fake = generator.predict(image_face)
                input_fake.append(image_fake[0])
                output_fake.append(train_output_faces[image_positon])
            input_fake = np.asarray(input_fake)
            output_fake = np.asarray(output_fake)

            # put the real avatars and the fake ones in the same variables
            input, output = np.vstack((input_real, input_fake)), np.vstack((output_real, output_fake))

            # feed the data to discriminator
            d_loss, _ = discriminator.train_on_batch(input, output)

            # get real faces
            input_gan = []
            output_gan = []
            for image_index in range(n_batch):
                image_positon = rnd.randrange(0, INPUT_SIZE - 1, 1)

                input_gan.append(train_input_faces[image_positon])
                #input_gan.append(train_input_avatar[image_positon])
                output_gan.append([1])
            input_gan = np.asarray(input_gan)
            output_gan = np.asarray(output_gan)

            g_loss = gan.train_on_batch(input_gan, output_gan)

            print(g_loss)
            sumd += d_loss
            sumg += g_loss
        if (i_epoch + 1) % 10 == 0:
            summarize_performance(i_epoch, generator, discriminator, half_batch)
        dyaxis.append(sumd)
        gyaxis.append(sumg)
        xaxis.append(i_epoch)
    plt.plot(xaxis, dyaxis)
    plt.plot(xaxis, gyaxis)
    plt.show()

try:
    session.run(train(generator, discriminator, gan))
except Exception as e:
    print(str(e))




for index_image in range(0, 30):
    input_gan = []
    image_positon = index_image

    input_gan.append(train_input_faces[image_positon])
    input_gan = np.asarray(input_gan)

    try:
        output = generator.predict(input_gan)
        save_img("C:/Users/Stefan/Desktop/CVDL_proiect3/results/farabatchnormalization" + str(index_image) + ".png", output[0])
    except Exception as e:
        print(str(e))