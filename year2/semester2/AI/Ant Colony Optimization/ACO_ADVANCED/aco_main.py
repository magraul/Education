
from aco import ACO, Graph
from repository import *


def run():

    cost_matrix, nr_orase = read_matrix('input.txt')
    #updatare feromon: ant_density
    # nr_furnici
    # nr_generatii
    # alpha
    # betha
    # coeficient evaporare
    aco = ACO(nr_orase, 100, 1.0, 30.0, 0.5)


    graph = Graph(cost_matrix, nr_orase)
    path, cost = aco.solve(graph)
    print('cost: {}, path: {}'.format(cost, path))


run()
