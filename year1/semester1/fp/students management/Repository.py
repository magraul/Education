from Errors import RepoError
from Asignare import Asignare
from Student import Student
from Disciplina import Disciplina


class Repository(object):

    def __init__(self):
        self.__elems = []

    def cauta(self, elem):
        """
        functie care cauta un anumit element in repository
        :param elem: elementuyl de cautat
        :return: elementul daca a fost gasit sau eroare in caz contrat
        """
        pos = -1
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                pos = i
                break
        if pos == -1:
            raise RepoError("element inexistent!")
        return self.__elems[pos]

    def adauga(self, elem):
        """
        fucntie care adauga un element in repo
        :param elem: obiectul pe care dorim sa il adaugam
        :return: eroare in caz ca elementul exista deja sau succes
        """
        if elem in self.__elems:
            raise RepoError('element existent!')
        self.__elems.append(elem)

    def __len__(self):
        return len(self.__elems)

    def getAll(self):
        """
        functie care returneaza toate elementele din repo
        :return:
        """
        return self.__elems[:]

    def sterge(self, elem):
        """
        fucntie care sterge un element dein reoo
        :param elem: elementul ep care dorim sa il stergem
        :return: eroare in caz ca elementul de sters nu exista
        """
        pos = -1
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                pos = i
                break
        if pos == -1:
            raise RepoError('element inexistent!')
        del self.__elems[pos]

    def update(self, elem, inlocuitor):
        """
        fucnte care inlocuieste un element cu un alt element
        :param elem: elementul pe care dorim sa il inlocuim
        :param inlocuitor: elementul cu care dorim sa inlocuim
        :return: eroare in cazul incare lementul de inlocuit nu exista
        """
        pos = -1
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                pos = i
                break
        if pos == -1:
            raise RepoError('element inexistent!')
        self.__elems[pos] = inlocuitor

    def free(self):
        self.__elems = []


class RepositoryFileStudent(object):
    def __init__(self):
        self.deschidere_stud = False

    def adauga(self, stud):
        """
        functie care adayuga un student nou in repoul de studenti
        :param stud: studentul care trebuie adaugat
        :return:
        """
        cuv = str(stud.get_id()) + ' ' + stud.get_nume() + '\n'
        exista = False
        studentii = open('studenti.txt', 'r')
        for line in studentii:
            elems = line.strip().split(' ')
            if elems[0] == str(stud.get_id()):
                exista = True
        studentii.close()
        if exista:
            raise RepoError('element exitstent!')
        else:
            if not self.deschidere_stud:
                studentii = open('studenti.txt', 'w')
                self.deschidere_stud = True
            else:
                studentii = open('studenti.txt', 'a')
            studentii.write(cuv)
        studentii.close()

    def getAll(self):
        """
        functie care returneaza toti studentii existenti
        :return:lista de obiecte de tip student
        """
        l = []
        studentii = open('studenti.txt', 'r')
        for line in studentii:
            elems = line.strip().split(' ')
            l.append(Student(int(elems[0]), elems[1]))
        studentii.close()
        return l[:]

    def update(self, de_inlocuit, inlocuitor):
        """
        functie care inlocuieste in repoul de studenti un student cu altul
        :param de_inlocuit: studentull care va fi inlocuit
        :param inlocuitor: studentul cu care se va inlocui
        :return:
        """
        cuv_inlocuitor = str(inlocuitor.get_id()) + ' ' + str(inlocuitor.get_nume()) + '\n'

        aux = open('temp.txt', 'w')
        studentii = open('studenti.txt', 'r')
        for line in studentii:
            elems = line.strip().split(' ')
            if elems[0] == str(de_inlocuit.get_id()):
                aux.write(cuv_inlocuitor)
            else:
                aux.write(line)
        aux.close()
        studentii.close()
        aux = open('temp.txt', 'r')
        studentii = open('studenti.txt', 'w')
        for line in aux:
            studentii.write(line)

        aux.close()
        studentii.close()

    def sterge(self, de_sters):
        """
        functie care sterge un student din repoul de studenti
        :param de_sters: studentul care trebuie sters
        :return:
        """
        cuv = str(de_sters.get_id()) + ' ' + str(de_sters.get_nume())
        aux = open('temp.txt', 'w')
        studentii = open('studenti.txt', 'r')
        for line in studentii:
            elems = line.strip().split(' ')
            if elems[0] != str(de_sters.get_id()):
                aux.write(line)
        aux.close()
        studentii.close()
        aux = open('temp.txt', 'r')
        studentii = open('studenti.txt', 'w')
        for line in aux:
            studentii.write(line)

        aux.close()
        studentii.close()

    def cauta(self, lista, l, r, de_cautat):
        """
        functie care cauta un student intr-o lista data de studenti
        :param lista: lista actuala de studenti
        :param l: indicele de la care se cauta
        :param r: indicele pana la care se cauta
        :param de_cautat: studentul care se cauta
        :return:
        """
        if r >= l:
            mid = l + (r-l)//2
            if lista[mid] == de_cautat:
                return lista[mid]
            elif lista[mid] > de_cautat:
                return self.cauta(lista, l, mid-1, de_cautat)
            else:
                return self.cauta(lista, mid+1, r, de_cautat)
        else:
            raise RepoError('Id student nu a fost gasit!')


class RepositoryFileDisciplina(object):
    def __init__(self):
        self.deschidere_discip = False

    def adauga(self, discip):
        """
        functie care adayuga o disciplina noua in repoul de descipline
        :param discip: disciplina care trebuie adaugata
        :return:
        """
        cuv = str(discip.get_id()) + ' ' + discip.get_nume() + ' ' + discip.get_prof() + '\n'
        exista = False
        discipline = open('discipline.txt', 'r')
        for line in discipline:
            elems = line.strip().split(' ')
            if elems[0] == str(discip.get_id()):
                exista = True
        discipline.close()
        if exista:
            raise RepoError('element exitstent!')
        else:
            if not self.deschidere_discip:
                discipline = open('discipline.txt', 'a')
                self.deschidere_discip = True
            else:
                discipline = open('discipline.txt', 'a')
            discipline.write(cuv)
        discipline.close()

    def getAll(self):
        """
        fucntie care returneaza toate obiectele de tip disciplina existente
        :return: lista de obiecte
        """
        l = []
        discipline = open('discipline.txt', 'r')
        for line in discipline:
            elems = line.strip().split(' ')
            l.append(Disciplina(int(elems[0]), elems[1], elems[2]))
        discipline.close()
        return l

    def update(self, de_inlocuit, inlocuitor):
        """
        functie care inlocuieste in repo-ul de disipline o disciplina cu alta
        :param de_inlocuit: disciplina care trebuiie inlocuita
        :param inlocuitor: disciplina cu care se va inlocui
        :return: 
        """""
        cuv_inlocuitor = str(inlocuitor.get_id()) + ' ' + str(inlocuitor.get_nume()) + ' ' + str(inlocuitor.get_prof()) + '\n'
        aux = open('temp.txt', 'w')
        discipline = open('discipline.txt', 'r')
        for line in discipline:
            elems = line.strip().split(' ')
            if elems[0] == str(de_inlocuit.get_id()):
                aux.write(cuv_inlocuitor)
            else:
                aux.write(line)
        aux.close()
        discipline.close()
        aux = open('temp.txt', 'r')
        discipline = open('discipline.txt', 'w')
        for line in aux:
            discipline.write(line)

        aux.close()
        discipline.close()

    def sterge(self, de_sters):
        """
        funcite care srterge un obiect de tip disciplina di repoul de discipline
        :param de_sters: obiectul care trebuie sters
        :return:
        """
        cuv = str(de_sters.get_id()) + ' ' + str(de_sters.get_nume())
        aux = open('temp.txt', 'w')
        discipline = open('discipline.txt', 'r')
        for line in discipline:
            elems = line.strip().split(' ')
            if elems[0] != str(de_sters.get_id()):
                aux.write(line)
        aux.close()
        discipline.close()
        aux = open('temp.txt', 'r')
        discipline = open('discipline.txt', 'w')
        for line in aux:
            discipline.write(line)

        aux.close()
        discipline.close()

    def cauta(self, lista, l, r, de_cautat):
        """
        functie care cauta o disciplina intr-o lista data de obiecte de tip disciplina
        :param lista: lista in care se cauta
        :param l: indicele de la care se incepe cautarea
        :param r: indicele pana la care se cauta
        :param de_cautat: disciplina care se cauta
        :return: exceptie daca nu s-a gasit sau obiectul care trebuia cautat
        """
        if r >= l:
            mid = l + (r-l)//2
            if lista[mid] == de_cautat:
                return lista[mid]
            elif lista[mid] > de_cautat:
                return self.cauta(lista, l, mid-1, de_cautat)
            else:
                return self.cauta(lista, mid+1, r, de_cautat)
        else:
            raise RepoError('Id disciplina nu a fost gasit!')


class RepositoryFileAsignare(object):
    def __init__(self):
        self.deschidere_asignari = False

    def adauga(self, nou):
        """
        functie care adauga o noua asignare in fisierul de asignari
        :param nou: asignare care trebuie adaugata
        :return:
        """
        cuv = str(nou.get_student_id()) + ' ' + str(nou.get_disciplina_id()) + ' ' + str(nou.get_nota()) + '\n'
        asign = open('asignari.txt', 'r')
        for l in asign:
            if l == cuv:
                raise RepoError('asignare existenta!')
        if not self.deschidere_asignari:
            asign = open('asignari.txt', 'w')
            self.deschidere_asignari = True
        else:
            asign = open('asignari.txt', 'a')
        asign.write(cuv)
        asign.close()

    def getAll(self):
        """
        functie care returneaza o lista de asignari cu toate asignarile existente
        :return:
        """
        l = []
        asignari = open('asignari.txt', 'r')
        for line in asignari:
            elemente = line.strip().split(' ')
            l.append(Asignare(Student(int(elemente[0]), None), Disciplina(int(elemente[1]), None, None), int(elemente[2])))
        asignari.close()
        return l

    def cauta_student(self, asignare):
        """
        functe care cauta un student intre asignari;le curente
        :param asignare: asignare
        :return:
        """
        asignari = open('asignari.txt', 'r')
        gasit = False
        fine = 0
        for line in asignari:
            elems = line.strip().split(' ')
            if elems[0] == str(asignare.get_student_id()):
                gasit = True
                fine = line.strip()
                break
        if not gasit:
            raise RepoError('ID student sau disciplina invalide!')
        elems = fine.split(' ')
        asignari.close()
        return Asignare(Student(elems[0], None), Disciplina(elems[1], None, None), elems[2])

    def updateStudent(self, id_de_inlocuit, id_inlocuitor):
        """
        functie care inlocuieste id-ul studentului dintr-o anume asinganere din fiusierul de asignari
        :param id_de_inlocuit: de de inlocuit
        :param id_inlocuitor: id cu care se inlocuieste
        :return:
        """
        cuv_inlocuitor = str(id_inlocuitor) + ' '
        aux = open('temp.txt', 'w')
        asignari = open('asignari.txt', 'r')
        for line in asignari:
            elems = line.strip().split(' ')
            if elems[0] == str(id_de_inlocuit):
                aux.write(cuv_inlocuitor + elems[1] + ' ' + elems[2] + '\n')
            else:
                aux.write(line)
        aux.close()
        asignari.close()
        aux = open('temp.txt', 'r')
        asignari = open('asignari.txt', 'w')
        for line in aux:
            asignari.write(line)

        aux.close()
        asignari.close()

    def updateDisciplina(self, id_de_inlocuit, id_inlocuitor):
        """
        functie care inlocuieste id-ul unei discupline date cu un alt id in fisieul de asignari
        :param id_de_inlocuit: id care trebueoi inlocuit
        :param id_inlocuitor: id care va aparea
        :return:
        """
        cuv_inlocuitor = str(id_inlocuitor)
        aux = open('temp.txt', 'w')
        asignari = open('asignari.txt', 'r')
        for line in asignari:
            elems = line.strip().split(' ')
            if elems[1] == str(id_de_inlocuit):
                aux.write(elems[0] + ' ' + cuv_inlocuitor + ' ' + elems[2] + '\n')
            else:
                aux.write(line)
        aux.close()
        asignari.close()
        aux = open('temp.txt', 'r')
        asignari = open('asignari.txt', 'w')
        for line in aux:
            asignari.write(line)

        aux.close()
        asignari.close()

    def cauta(self, de_cautat):
        """
        functe care cauta o asignare data
        :param de_cautat: asignare
        :return:
        """
        comp = de_cautat.split(' ')
        asignari = open('asignari.txt', 'r')
        gasit = False
        fine = 0
        for line in asignari:
            elems = line.strip().split(' ')
            if elems[0] == comp[0] and elems[1] == comp[1]:
                gasit = not False
                fine = line.strip()
                break
        if not gasit:
            raise RepoError('asignarea nu exista')
        elems = fine.split(' ')
        asignari.close()
        return Asignare(Student(int(elems[0]), None), Disciplina(int(elems[1]), None, None), elems[2])
