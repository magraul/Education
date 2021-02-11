import random

class Graf:
    def __init__(self, matrice_costuri: list, nr_orase: int):
        self.matrice_costuri = matrice_costuri
        self.nr_orase = nr_orase
        self.matrice_feromon = [[1 for j in range(nr_orase)] for i in range(nr_orase)]


class Furnica:
    def __init__(self, alpha: int, beta: int, graf: Graf):
        self.alpha =alpha
        self.beta = beta
        self.graf = graf
        self.cost_total = 0.0
        self.vector_solutie = []
        self.feromon_adaugat = []
        self.vecini_nod_curent = [i for i in range(graf.nr_orase)]
        self.eta = [[0 if i == j else 1 / graf.matrice_costuri[i][j] for j in range(graf.nr_orase)] for i in range(graf.nr_orase)]
        self.oras_start = random.randint(0, graf.nr_orase-1)
        self.vector_solutie.append(self.oras_start)
        self.oras__curent = self.oras_start
        self.vecini_nod_curent.remove(self.oras_start)
        self.Q = 20
        self.__costAnulat = 0

    def doOneMove(self):
        numitor = 0
        for i in self.vecini_nod_curent:
            numitor += self.graf.matrice_feromon[self.oras__curent][i] ** self.alpha * self.eta[self.oras__curent][i] ** self.beta

        lista_probabilitati = [0 for i in range(self.graf.nr_orase)]
        for i in range(self.graf.nr_orase):
            try:
                self.vecini_nod_curent.index(i)

                # nu o sa se aleaga muchia asta pentru ca eta e 0
                if numitor == 0:
                    numitor = 1
                lista_probabilitati[i] = self.graf.matrice_feromon[self.oras__curent][i] ** self.alpha * self.eta[self.oras__curent][i] ** self.beta / numitor
            except ValueError:
                pass

        nod_viitor = -1
        r = random.random()

        taiata = 0
        for i, prob in enumerate(lista_probabilitati):
            r -= prob
            if r <= 0:
                nod_viitor = i
                break
        if nod_viitor == -1:
            nod_viitor = self.vecini_nod_curent[-1]
            taiata = 1

        self.vecini_nod_curent.remove(nod_viitor)
        self.vector_solutie.append(nod_viitor)
        if taiata == 1:
            # salvam costul muchiei care a fost taiata
            self.cost_total += self.__costAnulat
        else:
            self.cost_total += self.graf.matrice_costuri[self.oras__curent][nod_viitor]
        self.oras__curent = nod_viitor

    def update_feromon_cumulat(self):
        self.feromon_adaugat = [[0 for j in range(self.graf.nr_orase)] for i in range(self.graf.nr_orase)]
        for vecin in range(1,len(self.vector_solutie)):
            i = self.vector_solutie[vecin - 1]
            j = self.vector_solutie[vecin]
            self.feromon_adaugat[i][j] = self.Q / self.graf.matrice_costuri[i][j]

    def setCostTaiat(self, cost_anulat):
        self.__costAnulat = cost_anulat