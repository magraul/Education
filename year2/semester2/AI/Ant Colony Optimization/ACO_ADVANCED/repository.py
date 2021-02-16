def read_matrix(nume):
    f = open(nume, "r")

    nr_orase = int(f.readline())
    distante = []
    for i in range(nr_orase):
        distante.append(f.readline().strip().split(","))

    for i in range(nr_orase):
        distante[i] = [int(x) for x in distante[i]]
    f.close()
    return distante, nr_orase
