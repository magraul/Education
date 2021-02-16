
import warnings

from service import *



def run():

    while 1:
        dim_pop = int(input('dati dimensiunea populatiei: '))
        nr_iter = int(input('dati numarul de iteratii: '))

        warnings.simplefilter('ignore')

        rez = service_run('tsp.in', dim_pop, nr_iter)
        print(rez)



run()




