import os
from networkx import *


# read the network details
def readNet(fileName):
    f = open(fileName, "r")
    net = {}
    n = int(f.readline())
    net['noNodes'] = n
    mat = []
    for i in range(n):
        mat.append([])
        line = f.readline()
        elems = line.split(" ")
        for j in range(n):
            mat[-1].append(int(elems[j]))
    net["mat"] = mat
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if (mat[i][j] == 1):
                d += 1
            if (j > i):
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees
    f.close()
    return n, net


def loadV2(fileName):
    f = open(fileName, "r")
    net = {}
    n = 0
    for line in f:
        e = line.strip().split(" ")
        n = max(int(e[0]), int(e[1]))

    net['noNodes'] = n
    mat = []
    for i in range(n):
        mat.append([])
        for j in range(n):
            mat[i].append(0)

    f.seek(0, 0)
    for i in range(n):
        line = f.readline()
        elems = line.strip().split(" ")
        mat[int(elems[0]) - 1][int(elems[1]) - 1] = 1
        mat[int(elems[1]) - 1][int(elems[0]) - 1] = 1

    net["mat"] = mat
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if (mat[i][j] == 1):
                d += 1
            if (j > i):
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees
    f.close()
    return n, net


def load_gml(fileName):
    net = {}
    n = 0
    Graf = read_gml(fileName, "id")
    matrice = nx.to_numpy_matrix(Graf)
    n = len(matrice)
    net['noNodes'] = n
    mat = []

    for i in range(n):
        mat.append([])
        for j in range(n):
            mat[i].append(0)
    for i in range(n):
        for j in range(n):
            mat[i][j] = matrice.item(i, j)

    net['mat'] = mat
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if (mat[i][j] == 1):
                d += 1
            if (j > i):
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees
    return n, net
