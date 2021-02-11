from comis_voiajor import *


def load_from_file(nume):
    f = open(nume, "r")

    nr_orase = int(f.readline())
    distante = []
    for i in range(nr_orase):
        distante.append(f.readline().strip().split(","))

    for i in range(nr_orase):
        distante[i] = [int(x) for x in distante[i]]

    return distante, nr_orase


def service_run(filePath, dim_pop, nr_iter):
    graf, nr_orase = load_from_file(filePath)
    for_return = ''
    for_return += str(nr_orase) + '\n'

    cost, path = comisVoiajorV(graf, nr_orase, dim_pop, nr_iter)

    path_for_add = ''
    for i in path:
        path_for_add += str(i+1) + ','
    path_for_add = path_for_add[:-1]
    for_return += path_for_add + '\n' + str(cost) + '\n'
    return for_return
