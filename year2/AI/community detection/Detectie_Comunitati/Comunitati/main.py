import networkx as nx
import matplotlib.pyplot as plt
import warnings
from repository import *
from communityUtils import *
import operator


"""

#
# A = np.matrix(network["mat"])
# G = nx.from_numpy_matrix(A)
# pos = nx.spring_layout(G)  # compute graph layout
# plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
# nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu)
# nx.draw_networkx_edges(G, pos, alpha=0.3)
# plt.show(G)

# plot a particular division in communities
communities = [1, 1, 1, 2, 2, 1]


# A = np.matrix(network["mat"])
# G = nx.from_numpy_matrix(A)
# pos = nx.spring_layout(G)  # compute graph layout
# plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
# nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color=communities)
# nx.draw_networkx_edges(G, pos, alpha=0.3)
#

# plt.show(G)

# print(modularity([1, 1, 1, 2, 2, 1], network))







def selectie(pop):
    pop = list(pop)
    ind1 = ast.literal_eval(pop[randrange(len(pop))])
    ind2 = ast.literal_eval(pop[randrange(len(pop))])

    if modularity(ind1, network) < modularity(ind2, network):
        return ind1
    return ind2


def crossover(ind1, ind2):
    rez = [-1] * len(ind1)
    poz = randrange(len(ind2))
    for i in range(poz):
        rez[i] = ind1[i]
    for i in range(poz, len(ind1)):
        rez[i] = ind2[i]
    return rez


def mutatie(ind):
    changes = randint(1, len(ind))
    for i in range(changes):
        a = randrange(len(ind))
        b = randrange(len(ind))
        ind[a], ind[b] = ind[b], ind[a]
    return ind





# genereaza urmatoarea generatie







def findClusters(marimePopulatie, nrIteratii, nr_culori):
    pop = init(marimePopulatie, nr_culori, nr_orase)
    dictionar = dict()
    pop_aux = pop.copy()

    best = bestSol(pop)
    nr_iter = 0
    while nr_iter < nrIteratii:
        populatieNoua = set()

        l = len(pop)
        inde = len(list(pop_aux)) // 4
        while inde > 0:
            ind = bestSol(pop_aux)
            pop_aux.remove(str(ind))
            populatieNoua.add(str(ind))
            dictionar[str(ind)] = modularity(ind, network)
            inde -= 1

        popN = list(populatieNoua)
        for i in range(len(popN) - 1):
            rez = crossover(ast.literal_eval(popN[i]), ast.literal_eval(popN[i + 1]))
            populatieNoua.add(str(rez))
            dictionar[str(rez)] = modularity(rez, network)

        nrIndivid = len(populatieNoua)

        if len(list(populatieNoua)) > 0:
            best = bestSol(populatieNoua)
        while nrIndivid < len(pop):
            nrIndivid += 1
            i1 = selectie(pop)

            rez = crossover(i1, best)
            rez = mutatie(rez)

            ok = True
            for i in populatieNoua:
                if str(rez) == i:
                    ok = False
            if ok == True:
                populatieNoua.add(str(rez))
                dictionar[str(rez)] = modularity(rez, network)
        best = bestSol(populatieNoua)
        print(modularity(best, network))
        pop = populatieNoua
        nr_iter += 1
    best = max(dictionar, key=dictionar.get)
    best = ast.literal_eval(best)
    return best


print('populatie:')
print(init(5, 2, nr_orase))
print(bestSol(init(5, 2, nr_orase)))

best = findCLusters2(4, 400, 2)
print('buna')
print(modularity(best, network))
"""


def run():
    while 1:
        dim_pop = int(input('dati dimensiunea populatiei: '))
        nr_iter = int(input('dati numarul de iteratii: '))

        print('1. kerbs')
        print('2. dolphins')
        print('3. karate')
        print('4. fotball')

        v = int(input())
        crtDir = os.getcwd()
        if v == 1:
            filePathGML = os.path.join(crtDir, 'net.gml')
        elif v == 2:
            filePathGML = os.path.join(crtDir, 'dolphins.gml')
        elif v == 3:
            filePathGML = os.path.join(crtDir, 'karate.gml')
        elif v == 4:
            filePathGML = os.path.join(crtDir, 'forball.gml')
        #filePath = os.path.join(crtDir, 'net.in')


        #nr_orase, network = loadV2(filePath)
        nr_orase, network = load_gml(filePathGML)

       # matrice = nx.to_numpy_matrix(Graf)
       # print(matrice.item(1, 2))


        warnings.simplefilter('ignore')

        bst = findCLusters2(dim_pop,nr_iter,nr_orase, network, nr_orase)
        print('\nCea mai buna este ' + str(bst))
        print('fitness: ' + str(modularity(bst, network)))

        A = np.matrix(network["mat"])
        G = nx.from_numpy_matrix(A)
        pos = nx.spring_layout(G)  # compute graph layout
        plt.figure(figsize=(10, 10))  # image is 8 x 8 inches
        nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color=bst)
        nx.draw_networkx_edges(G, pos, alpha=0.3)
        plt.show(G)


run()









