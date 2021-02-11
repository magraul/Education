import ast
from random import *
import numpy as np


def modularity(communities, param):
    noNodes = param['noNodes']
    mat = param['mat']
    degrees = param['degrees']
    noEdges = param['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if (communities[i] == communities[j]):
                Q += (mat[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M


def init(size, nr_culori, nr_orase, network):
    pop = set()
    nrs = [i for i in range(1, nr_culori + 1)]

    while pop.__len__() < size:
        distr = []
        for i in range(nr_orase):
            poz = randint(0, nr_culori - 1)
            distr.append(nrs[poz])
        se_poate = True
        for i in range(1, nr_culori + 1):
            if distr.count(i) == len(distr):
                se_poate = False
                break

        if se_poate:
            pop.add(str(distr))
    return pop


def bestSol(popSet, network):
    pop = list(popSet)
    best = pop[0]
    best = ast.literal_eval(best)
    for c in pop:
        c = ast.literal_eval(c)
        if modularity(c, network) > modularity(best, network):
            best = c
    return best


def get_probability_list(dict_fitness):
    fitness = dict_fitness.values()
    total_fit = float(sum(fitness))
    relative_fitness = [f / total_fit for f in fitness]
    probabilities = [sum(relative_fitness[:i + 1]) for i in range(len(relative_fitness))]
    return probabilities


def selection(generatie, dict_fitness):
    generatie = list(generatie)
    probabilities = get_probability_list(dict_fitness)

    r = random()
    for (i, individual) in enumerate(generatie):
        if r <= probabilities[i]:
            return ast.literal_eval(individual)


def crossoverv2(mama, tata, network):
    # Get length of chromosome
    chromosome_length = len(mama)

    # Pick crossover point, avoding ends of chromsome
    crossover_point = randint(1, chromosome_length - 1)

    # Create children. np.hstack joins two arrays
    child_1 = np.hstack((mama[0:crossover_point],
                         tata[crossover_point:]))

    child_2 = np.hstack((tata[0:crossover_point],
                         mama[crossover_point:]))
    child_1 = list(child_1)
    child_2 = list(child_2)
    if modularity(child_1, network) > modularity(child_2, network):
        return child_1
    return child_2


def doubleCrossOver(mama, tata, network):
    # Get length of chromosome
    chromosome_length = len(mama)

    # Pick crossover point, avoding ends of chromsome
    crossover_point1 = randint(1, chromosome_length - 1)
    crossover_point2 = randint(1, chromosome_length - 1)
    minn, maxx = min(crossover_point1, crossover_point2), max(crossover_point1, crossover_point2)

    # Create children. np.hstack joins two arrays
    child_1 = np.hstack((mama[0:minn],
                         tata[minn:maxx], mama[maxx:]))
    child_2 = np.hstack((tata[0:minn],
                         mama[minn:maxx], tata[maxx:]))
    child_1 = list(child_1)
    child_2 = list(child_2)
    if modularity(child_1, network) > modularity(child_2, network):
        return child_1
    return child_2


def mutatiev2(copil):
    p1 = randint(0, len(copil) - 1)
    p2 = randint(0, len(copil) - 1)
    copil[p1], copil[p2] = copil[p2], copil[p1]
    return copil


def findCLusters2(marimePopulatie, nrIteratii, nr_culori, network, nr_orase):
    pop = init(marimePopulatie, nr_culori, nr_orase, network)
    best = bestSol(pop, network)
    index = 0
    while index < nrIteratii:
        dict_fitness = dict()
        for p in pop:
            dict_fitness[p] = modularity(ast.literal_eval(p), network)

        # creem o noua populatie
        populatieNoua = set()

        # ne asiguram ca cel mai bun cromozom este in ea
        populatieNoua.add(str(bestSol(pop, network)))

        while populatieNoua.__len__() < pop.__len__():
            i1 = selection(pop, dict_fitness)
            i2 = selection(pop, dict_fitness)

            # rez = crossoverv2(i1, i2, network)
            rez = doubleCrossOver(i1, i2, network)
            rez_mutat = mutatiev2(rez)

            populatieNoua.add(str(rez_mutat))
        # am obtinut noua generatie
        index += 1
        pop = populatieNoua
        best = bestSol(pop, network)
        print(best)
        print(modularity(best, network))
    return best
