from Chromosome import *
from random import *
import copy


def init(dim_pop, nr_orase, graf):
    pop = list()
    d= dict()
    problParam = {'noNodes': nr_orase}
    while len(pop) < dim_pop:
        c = Chromosome(problParam)
        c.fitness = get_fitness(c, graf)
        pop.append(c)
    return pop


def bestChromosome(populatieCurenta):
    best = populatieCurenta[0]
    for c in populatieCurenta:
        if c.fitness < best.fitness:
            best = c
    return best


def get_probability_list(dict_fitness):
    fitness = dict_fitness.values()
    total_fit = float(sum(fitness))
    relative_fitness = [f / total_fit for f in fitness]
    probabilities = [sum(relative_fitness[:i + 1]) for i in range(len(relative_fitness))]
    return probabilities


def selection(populatieCurenta, dict_fitness):
    probabilities = get_probability_list(dict_fitness)
    r = random()
    for (i, individual) in enumerate(populatieCurenta):
        if r <= probabilities[i]:
            return individual


def get_fitness(chromosome, graf):
    s = 0
    for i in range(len(chromosome.repres)-1):
        s += graf[chromosome.repres[i]][chromosome.repres[i+1]]
    ii = chromosome.repres[0]
    jj = chromosome.repres[len(graf)-1]
    s += graf[ii][jj]
    return s


def comisVoiajorV(graf, nr_orase, dim_pop, nr_iter):
    d = dict()
    populatieCurenta = init(dim_pop, nr_orase, graf)
    best = bestChromosome(populatieCurenta)
    index = 0
    while index < nr_iter:
        dict_fitness = dict()
        for c in populatieCurenta:
            dict_fitness[str(c)] = c.fitness

        # creem o noua populatie
        populatieViitoare = []
        populatieViitoare.append(bestChromosome(populatieCurenta))

        while len(populatieViitoare) < len(populatieCurenta):
            c1 = selection(populatieCurenta, dict_fitness)
            c2 = selection(populatieCurenta, dict_fitness)

            rez = c1.crossover(c2)
            rez.fitness = get_fitness(rez, graf)

            aux = copy.deepcopy(rez)
            aux.mutation()
            aux.fitness = get_fitness(aux, graf)
            if aux.fitness > rez.fitness:
                populatieViitoare.append(aux)
                d[str(aux)] = aux.fitness
            else:
                populatieViitoare.append(rez)
                d[str(rez)] = rez.fitness
        index += 1
        populatieCurenta = populatieViitoare
        best = bestChromosome(populatieCurenta)
        print(index)
        print(best)
        print('d: ' + str(min(d.values())))
    return best.fitness, best.repres
