from service import *


def run():
    while 1:
        print('alegeti versiunea:')
        print('1: normal')
        print('2: modificare costuri')
        print('3: stergere / adaugare muchii')
        v = int(input('>>> '))

        nr_iter = int(input('dati numarul de iteratii: '))
        alpha = int(input('dati alpha: '))
        beta = int(input('dati beta: '))
        coeficient = float(input('dati coeficientul de evaporare: '))

        if v == 1:
            drum, cost = service_rezolva_normal(nr_iter, alpha, beta, coeficient)
        elif v == 2:
            drum, cost = service_rezolva_modificare_cost(nr_iter, alpha, beta, coeficient)
        else:
            drum, cost = service_rezolva_dinamicv2(nr_iter, alpha, beta, coeficient)

        print('costul: {}, drumul: {}'.format(cost, drum))


run()
