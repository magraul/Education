import numpy as np
import math


def norma(l1, l2):
    return math.sqrt(sum([(i - j) ** 2 for i, j in zip(l1, l2)]))


def media (l):
    rez = []
    for i in range(len(l[0])):
        s = sum([t[i] for t in l]) / len(l)
        rez.append(s)
    return rez


class KMEANS:
    def __init__(self, clase, max_iter=20):
        self.nr_clusters = len(clase)
        self.max_iter = max_iter
        self.centroizi = {}
        self.clasificari = {}
        self.clase = clase

    def fit(self, propozitii):
        # INITIALIZAM CENTROIZII
        for i in range(self.nr_clusters):
            self.centroizi[i] = propozitii[i]

        for i in range(self.max_iter):
            # propozitiile pentru fiecare centroid
            self.clasificari = {}

            for i in range(self.nr_clusters):
                self.clasificari[i] = []

            for propozitie in propozitii:
                distante = [norma(propozitie, self.centroizi[centroid]) for centroid in self.centroizi]
                clasificare = distante.index(min(distante))
                self.clasificari[clasificare].append(propozitie)

            # ajustam centroizii
            for clasificare in self.clasificari:
                self.centroizi[clasificare] = media(self.clasificari[clasificare])

    def predict(self, data):
        rez = []
        for propozitie in data:
            distances = [np.linalg.norm(propozitie - self.centroizi[centroid]) for centroid in self.centroizi]
            rez.append(self.clase[distances.index(min(distances))])

        return rez
