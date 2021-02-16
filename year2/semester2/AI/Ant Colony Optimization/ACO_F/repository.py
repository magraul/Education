import math

def read_matrix(nume):
    f = open(nume, "r")

    nr_orase = int(f.readline())
    distante = []
    for i in range(nr_orase):
        distante.append(f.readline().strip().split(","))

    for i in range(nr_orase):
        distante[i] = [int(x) for x in distante[i]]
    f.close()
    return distante, nr_orase


def read_coordonate(nume):
    f = open(nume, "r")

    nr_orase = int(f.readline())
    distante = []

    for i in range(nr_orase+1):
        distante.append([0 for i in range(nr_orase)])
    coordonate = {}
    for i in range(nr_orase):
        line = f.readline().strip().split(" ")
        coordonate[int(line[0])-1] = [int(line[1]), int(line[2])]
    for poz, punct1 in enumerate(coordonate.keys()):
        for punct2 in list(coordonate.keys())[poz:]:
            distante[punct1][int(punct2)] = math.sqrt(pow(coordonate[punct1][0] - coordonate[punct2][0], 2) + pow(coordonate[punct1][1] - coordonate[punct2][1], 2))
            distante[int(punct2)][punct1] = math.sqrt(pow(coordonate[punct1][0] - coordonate[punct2][0], 2) + pow(
                coordonate[punct1][1] - coordonate[punct2][1], 2))

    f.close()
    return distante, nr_orase