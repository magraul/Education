import numpy as np
from PIL import Image

INPUT_SIZE = 200
PHOTO_SIZE = 256

def load_images(folder_path, extension, output_value):
    trainInput = []
    trainOutput = []
    prefix = folder_path

    for i in range(INPUT_SIZE):
        filenameInput = prefix + "img (" + str(int(i + 1)) + ")." + extension
        imInput = Image.open(filenameInput, 'r')
        #if extension == "png":
        imInput = imInput.resize((256, 256))
        pixel_valuesInput = list(imInput.getdata())

        img = []
        for i in range(len(pixel_valuesInput)):
            ceva = []
            ceva.append(pixel_valuesInput[i][0])
            ceva.append(pixel_valuesInput[i][1])
            ceva.append(pixel_valuesInput[i][2])
            img.append(ceva)

        pixel_valuesInput = img

        inputCube = []
        inputCube.append(pixel_valuesInput)

        trainInput.append(inputCube)
        trainOutput.append([[output_value]])

    trainInput = np.reshape(trainInput, (INPUT_SIZE, PHOTO_SIZE, PHOTO_SIZE, 3))
    return trainInput, trainOutput


def get_image(folder_path, img_index, extension, output_value):
    trainInput = []
    trainOutput = []
    prefix = folder_path

    filenameInput = prefix + "img (" + str(int(img_index + 1)) + ")." + extension
    imInput = Image.open(filenameInput, 'r')
    imInput = imInput.resize((256, 256))

    pixel_valuesInput = list(imInput.getdata())

    img = []
    for i in range(len(pixel_valuesInput)):
        ceva = []
        ceva.append(pixel_valuesInput[i][0])
        ceva.append(pixel_valuesInput[i][1])
        ceva.append(pixel_valuesInput[i][2])
        img.append(ceva)

    pixel_valuesInput = img


    inputCube = []
    inputCube.append(pixel_valuesInput)
    trainInput.append(inputCube)

    trainOutput.append(output_value)

    trainInput = np.reshape(trainInput, (1, PHOTO_SIZE, PHOTO_SIZE, 3))

    trainInput = [list(i.flatten()) for i in trainInput]
    trainInput = np.asarray(trainInput)
    trainInput = np.reshape(trainInput, (1, PHOTO_SIZE, PHOTO_SIZE, 3))
    trainOutput = np.asarray(trainOutput)

    return trainInput, trainOutput
