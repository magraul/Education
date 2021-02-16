def load_data():
    #happy, sad, surprise, disgust, anger, fear, shame
    rez = dict()
    clase = set()

    f = open('propozitii.txt', 'r')
    for line in f:
        if line != '':
            elems = line.split('>')
            clasa = elems[0][1:]
            prop = elems[1].split('<')[0]
            prop = prop.replace(":", "")
            prop = prop.replace(",", "")
            prop = prop.replace(".", "")
            prop = prop.replace("!", "")
            prop = prop.replace("?", "")
            prop = prop.replace("--", "")


            clase.add(clasa)
            if not clasa in rez:
                rez[clasa] = [prop]
            else:
                rez[clasa].append(prop)
    return rez


