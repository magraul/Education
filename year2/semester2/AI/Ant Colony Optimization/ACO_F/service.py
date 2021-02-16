from repository import *
from acuUtils import *
import ast


def update_feromon_global(graf, lista_furnici, coeficient):
    for i, linie in enumerate(graf.matrice_feromon):
        for j, coloana in enumerate(linie):
            graf.matrice_feromon[i][j] *= coeficient
            for funrnica in lista_furnici:
                graf.matrice_feromon[i][j] += funrnica.feromon_adaugat[i][j]


def service_rezolva_normal(nr_iteratii, alpha, beta, coeficient):
    #matrice_costuri, nr_orase = read_matrix('input.txt')
    matrice_costuri, nr_orase = read_coordonate('coordonate.txt')
    graf = Graf(matrice_costuri, nr_orase)

    best_cost = float('inf')
    best_path = []

    aux = ''
    for index in range(nr_iteratii):
        if index % 15 == 0:
            if index != 0:
                procentaj = (index / nr_iteratii) * 100
                if str(procentaj) + '% complet...' != aux:
                    print(str(int(procentaj)) + '% complet...')
                    aux = str(procentaj) + '% complet...'
        lista_furnici = [Furnica(alpha, beta, graf) for i in range(nr_orase)]

        for furnica in lista_furnici:
            for i in range(nr_orase - 1):
                furnica.doOneMove()
            ultimul_nod = furnica.vector_solutie[-1]
            furnica.cost_total += graf.matrice_costuri[ultimul_nod][furnica.vector_solutie[0]]
            if furnica.cost_total < best_cost:
                best_cost = furnica.cost_total
                best_path = [] + furnica.vector_solutie

            furnica.update_feromon_cumulat()
        # print('geneeatia {}, best cost: {}, path: {}'.format(index, best_cost, best_path))
        update_feromon_global(graf, lista_furnici, coeficient)
    return best_path, best_cost


def service_rezolva_modificare_cost(nr_iteratii, alpha, beta, coeficient):
    matrice_costuri, nr_orase = read_matrix('input.txt')
    graf = Graf(matrice_costuri, nr_orase)

    best_cost = float('inf')
    best_path = []

    aux = ''
    i_rand = 0
    j_rand = 10
    cost_rand = 10

    for index in range(nr_iteratii):
        adaugat = False
        lista_furnici = [Furnica(alpha, beta, graf) for i in range(nr_orase)]

        for furnica in lista_furnici:
            for i in range(nr_orase - 1):
                furnica.doOneMove()

                if index % 5 == 0:
                    if index != 0 and not adaugat:
                        adaugat = True
                        i_rand = random.randint(0, nr_orase - 1)
                        j_rand = random.randint(0, nr_orase - 1)
                        while j_rand == i_rand:
                            j_rand = random.randint(0, nr_orase)
                        cost_rand = random.randint(1, 10)
                        graf.matrice_costuri[i_rand % nr_orase][j_rand % nr_orase] = cost_rand
                        graf.matrice_costuri[j_rand % nr_orase][i_rand % nr_orase] = cost_rand
                    procentaj = (index / nr_iteratii) * 100
                    if str(procentaj) + '% complet...' != aux:
                        print(str(int(procentaj)) + '% complet...')
                        aux = str(procentaj) + '% complet...'
            ultimul_nod = furnica.vector_solutie[-1]
            furnica.cost_total += graf.matrice_costuri[ultimul_nod][furnica.vector_solutie[0]]
            if furnica.cost_total < best_cost:
                best_cost = furnica.cost_total
                best_path = [] + furnica.vector_solutie

            furnica.update_feromon_cumulat()
        # print('geneeatia {}, best cost: {}, path: {}'.format(index, best_cost, best_path))
        update_feromon_global(graf, lista_furnici, coeficient)
    return best_path, best_cost


def service_rezolva_dinamic(nr_iteratii, alpha, beta, coeficient):
    muchii_scoase = {}

    matrice_costuri, nr_orase = read_matrix('input.txt')
    graf = Graf(matrice_costuri, nr_orase)

    best_cost = float('inf')
    best_path = []

    aux = ''
    i_rand = 0
    j_rand = 10
    cost_rand = 10
    cost_anulat = 0
    for index in range(nr_iteratii):
        if index % 20 == 0 and index != 0:
            k = random.choice(list(muchii_scoase.keys()))
            c = muchii_scoase[k]
            muchie = ast.literal_eval(k)
            del muchii_scoase[k]
            #  cost_anulat = graf.matrice_costuri[i_rand][j_rand]
            graf.matrice_costuri[muchie[0]][muchie[1]] = c
            graf.matrice_costuri[muchie[1]][muchie[0]] = float('inf')
            graf.matrice_feromon[i_rand][j_rand] = 1
            graf.matrice_feromon[j_rand][i_rand] = 1
            print('am adaugat ' + str(muchie))

        if index % 5 == 0:
            if index != 0:
                i_rand = random.randint(0, nr_orase - 1)
                j_rand = random.randint(0, nr_orase - 1)
                while j_rand == i_rand:
                    j_rand = random.randint(0, nr_orase)

                muchii_scoase[str([i_rand, j_rand])] = graf.matrice_costuri[i_rand][j_rand]
                cost_anulat = graf.matrice_costuri[i_rand][j_rand]
                graf.matrice_costuri[i_rand][j_rand] = float('inf')
                graf.matrice_costuri[j_rand][i_rand] = float('inf')
                graf.matrice_feromon[i_rand][j_rand] = 1
                graf.matrice_feromon[j_rand][i_rand] = 1
                print('am scos ' + str([i_rand, j_rand]))

                for i in range(nr_orase - 1):
                    if (i_rand == i and j_rand == i + 1) or (j_rand == i and i_rand == i + 1):
                        # am scos o muchie din best ul curent
                        best_cost = float('inf')
                        best_path = []

            procentaj = (index / nr_iteratii) * 100
            if str(procentaj) + '% complet...' != aux:
                print(str(int(procentaj)) + '% complet...')
                aux = str(procentaj) + '% complet...'

        lista_furnici = [Furnica(alpha, beta, graf) for i in range(nr_orase)]

        for furnica in lista_furnici:
            for i in range(nr_orase - 1):
                furnica.setCostTaiat(cost_anulat)
                furnica.doOneMove()
                #

            ultimul_nod = furnica.vector_solutie[-1]
            furnica.cost_total += graf.matrice_costuri[ultimul_nod][furnica.vector_solutie[0]]
            if furnica.cost_total < best_cost:
                best_cost = furnica.cost_total
                best_path = [] + furnica.vector_solutie

            furnica.update_feromon_cumulat()
        # print('geneeatia {}, best cost: {}, path: {}'.format(index, best_cost, best_path))
        update_feromon_global(graf, lista_furnici, coeficient)
    return best_path, best_cost


def service_rezolva_dinamicv2(nr_iteratii, alpha, beta, coeficient):
    muchii_scoase = {}

    # matrice_costuri, nr_orase = read_matrix('input.txt')
    matrice_costuri, nr_orase = read_coordonate('coordonate.txt')
    graf = Graf(matrice_costuri, nr_orase)

    best_cost = float('inf')
    best_path = []

    aux = ''
    i_rand = 0
    j_rand = 10
    cost_rand = 10
    cost_anulat = 0

    for index in range(nr_iteratii):
        scos = False
        adaugat = False
        lista_furnici = [Furnica(alpha, beta, graf) for i in range(nr_orase)]

        for furnica in lista_furnici:
            for i in range(nr_orase - 1):
                furnica.doOneMove()

                if index % 20 == 0 and index != 0 and not adaugat:
                    adaugat = True
                    k = random.choice(list(muchii_scoase.keys()))
                    c = muchii_scoase[k]
                    muchie = ast.literal_eval(k)
                    del muchii_scoase[k]
                    #  cost_anulat = graf.matrice_costuri[i_rand][j_rand]
                    graf.matrice_costuri[muchie[0]][muchie[1]] = c
                    graf.matrice_costuri[muchie[1]][muchie[0]] = c
                    graf.matrice_feromon[i_rand][j_rand] = 1
                    graf.matrice_feromon[j_rand][i_rand] = 1
                    print('am adaugat ' + str(muchie))

                if index % 5 == 0:
                    if index != 0 and not scos:
                        scos = True
                        i_rand = random.randint(0, nr_orase - 1)
                        j_rand = random.randint(0, nr_orase - 1)
                        while j_rand == i_rand:
                            j_rand = random.randint(0, nr_orase)

                        muchii_scoase[str([i_rand, j_rand])] = graf.matrice_costuri[i_rand][j_rand]
                        graf.matrice_costuri[i_rand][j_rand] = float('inf')
                        graf.matrice_costuri[j_rand][i_rand] = float('inf')
                        graf.matrice_feromon[i_rand][j_rand] = 1
                        graf.matrice_feromon[j_rand][i_rand] = 1
                        print('am scos ' + str([i_rand, j_rand]))

                        procentaj = (index / nr_iteratii) * 100
                        if str(procentaj) + '% complet...' != aux:
                            print(str(int(procentaj)) + '% complet...')
                            aux = str(procentaj) + '% complet...'


            ultimul_nod = furnica.vector_solutie[-1]
            furnica.cost_total += graf.matrice_costuri[ultimul_nod][furnica.vector_solutie[0]]
            if furnica.cost_total < best_cost:
                best_cost = furnica.cost_total
                best_path = [] + furnica.vector_solutie

            furnica.update_feromon_cumulat()
        # print('geneeatia {}, best cost: {}, path: {}'.format(index, best_cost, best_path))
        update_feromon_global(graf, lista_furnici, coeficient)
        index += 1
    return best_path, best_cost
