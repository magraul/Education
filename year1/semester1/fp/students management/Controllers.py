from Student import Student
from Disciplina import Disciplina
from Asignare import Asignare
from Errors import *
import operator
from Validator import StudentValidator, DisciplinaValidator


class StudentiController(object):
    def __init__(self, repoStudenti, validStudenti):
        self.repoStudenti = repoStudenti
        self.validStudenti = validStudenti

    def adaugaStudent(self, id, nume):
        """
        funcite ce prea datele pentru unn student si creeaza acest student dupo care il valideaza iar daca trece testul,
        il adauga in repo
        :param id: id-ul studentului
        :param nume: numele studentului
        :return:
        """
        s = Student(id, nume)
        self.validStudenti.valideazaStudent(s)
        self.repoStudenti.adauga(s)

    def getAllStudenti(self):
        """
        functie ce returneaza toti studentii aflati in Repository
        :return: lisat ce cuprinde toti studentii creati
        """
        return self.repoStudenti.getAll()

    def updateStudent(self, de_inlocuit, inlocuitor):
        """
        functie ce inlocuieste un student dat cu un ca prim parametru cu un alt student
        :param de_inlocuit: studentul care trebuie inlocuit
        :param inlocuitor: studentul cu care se va inlocuii
        :return:
        """
        self.validStudenti.valideazaStudent(inlocuitor)
        self.repoStudenti.update(de_inlocuit, inlocuitor)


class DisciplineController(object):
    def __init__(self, repoDisciplina, validDisciplina):
        self.repoDisciplina = repoDisciplina
        self.validDisciplina = validDisciplina

    def adaugaDisciplina(self, id, nume, prof):
        """
        funcite ce priea datele pentru o disciplina si creeaza acesta disciplina dupa care o valideaza iar daca trece testul,
        o adauga in repo
        :param id: id-ul disciplinei
        :param nume: numele disciplinei
        :param prof: numele profesorului
        :return:
        """
        d = Disciplina(id, nume, prof)
        self.validDisciplina.valideazaDisciplina(d)
        self.repoDisciplina.adauga(d)

    def getAllDiscipline(self):
        """
        functie ce returneaza ca si lista toate disciplinele existente
        :return: o lista cu toate disciplinele
        """
        return self.repoDisciplina.getAll()

    def updateDisciplina(self, deinlocuit, inlocutor):
        """
        functie ce inlocuieste o disciplina cu o alta disciplina
        :param deinlocuit: disciplina pe care dorim sa o inlocuim
        :param inlocutor: dinsiplina cu care oorim sa inlocuim
        :return:
        """
        self.validDisciplina.valideazaDisciplina(inlocutor)
        self.repoDisciplina.update(deinlocuit, inlocutor)


class AsignController(object):
    def __init__(self, repoStudenti, repoDiscipline, repoAsign, validator):
        self.repoStudenti = repoStudenti
        self.repoDiscipline = repoDiscipline
        self.repoAsign = repoAsign
        self.deschidere_asignari = False
        self.validator = validator
        self.studentValidator = StudentValidator()
        self.disciplinaValidator = DisciplinaValidator()

    def updateStudent(self, de_inlocuit, inlocuitor):
        """
        functie care inlocuieste un obiect de tip student cu un altul atat in repo-ul de asignare
        cat si in cel de studenti
        :param de_inlocuit:obiect care va aparea dupa executie
        :param inlocuitor:obiect care va fi inlocuit
        :return:
        """
        exceptie = False
        self.repoStudenti.cauta(de_inlocuit)
        try:
            self.repoStudenti.cauta(inlocuitor)
        except:
            exceptie = True
        if exceptie:
            self.studentValidator.valideazaStudent(inlocuitor)
            self.repoStudenti.update(de_inlocuit, inlocuitor)
            self.repoAsign.updateStudent(de_inlocuit.get_id(), inlocuitor.get_id())
        else:
            raise RepoError('Id-ul inlocuitor exista deja!')

    def updateDisciplina(self, de_inlocuit, inlocuitor):
        """
        functie care inlocuieste o disciplina cu alta in repoul de asignari si in repo-ul de discipline
        :param de_inlocuit: disciplina care trebuie inlocuita
        :param inlocuitor: disciplina cu care se va inlocui
        :return:
        """
        exceptie = False
        self.repoDiscipline.cauta(de_inlocuit)
        try:
            self.repoDiscipline.cauta(inlocuitor)
        except:
            exceptie = True
        if exceptie:
            self.disciplinaValidator.valideazaDisciplina(inlocuitor)
            self.repoDiscipline.update(de_inlocuit, inlocuitor)
            self.repoAsign.updateDisciplina(de_inlocuit.get_id(), inlocuitor.get_id())
        else:
            raise RepoError('Id-ul inlocuitor exista deja!')

    def adaugaAsignare(self, student, disciplina, nota):
        """
        fucntie ca adauga o noua asignare
        :param id_student: id-ul studentului caruia i se asigneaza
        :param id_disciplina: id-ul disciplineu de folosit
        :param nota: nota ce va fi asignata
        :return:
        """
        asignare = Asignare(student, disciplina, nota)
        self.repoStudenti.cauta(student)
        self.repoDiscipline.cauta(disciplina)
        self.validator.ValideazaAsignare(asignare)
        self.repoAsign.adauga(asignare)

    def getAllAsignari(self):
        """
        functie ce returneaza toate asignarile curente
        :return: lista de obiecte de tip asignare
        """
        rez = []
        linii = self.repoAsign.getAll()
        ###for linie in linii:
           # elemente = linie.strip().split(' ')
            #rez.append(Asignare(self.repoStudenti.cauta(Student(int(elemente[0]), None)), self.repoDiscipline.cauta(Disciplina(int(elemente[1]), None, None)), int(elemente[2])))
        return linii

    def raport1(self, modul_de_sortare, id_disciplina):
        """
        functie care creeaza lista de studenți și notele lor la o disciplină dată, ordonat: alfabetic după nume, după notă.
        :param modul_de_sortare:criteriul dupa care dorim sa sortam
        :param id_disciplina:disciplina la care dorim sa facem sortarea
        :return:
        """
        if modul_de_sortare != 'alfabetic' and modul_de_sortare != 'dupa_nota':
            raise ValidError('Comanda invalida!')
        try:
            id_disciplina = int(id_disciplina)
        except:
            raise ValueError('Id invalid(string)!')
        if modul_de_sortare == 'alfabetic':
            print('')
            studenti_discip = []
            studenti = []
            for i in self.getAllAsignari():
                if i.get_disciplina_id() == int(id_disciplina):
                    studenti_discip.append(i)
            for i in studenti_discip:
                toate = self.repoStudenti.getAll()
                self.mergeSort(toate, key=lambda x:x.get_id(), reverse=True)
                #toate.sort(key=lambda x: x.get_id(), reverse = False)
                s = self.repoStudenti.cauta(toate, 0, len(toate)-1, i.get_student())
                studenti.append(s)
            self.insertion_sort(studenti, key=lambda x:x.get_nume, reverse=False)
            #studenti.sort(key=lambda x: x.get_nume(), reverse=False)
            for i in studenti:
                aux = self.repoAsign.cauta(str(i.get_id()) + ' ' + str(id_disciplina))
                print(i.get_nume(), '  ', aux.get_nota())
        else:
            print('')
            studenti_discip = []
            for i in self.getAllAsignari():
                if i.get_disciplina_id() == int(id_disciplina):
                    studenti_discip.append(i)
            self.insertion_sort(studenti_discip, key=lambda x:x.get_nota(), reverse=True)
            for i in reversed(studenti_discip):
                toate = self.repoStudenti.getAll()
                self.comb_sort(toate, key=lambda  x:x.get_id(), reverse = True)
                #toate.sort(key=lambda x: x.get_id(), reverse=False)
                st = self.repoStudenti.cauta(toate, 0, len(toate)-1, i.get_student())
                print(st.get_nume(), '    ', i.get_nota())

    def raport2(self):
        """
        functie care returneaza primi 20% din studenți ordonati dupa media notelor la toate disciplinele (nume și notă)
        :return:
        """
        asignari = []
        studenti = []
        lungime_dorita = round(20 / 100 * len(self.getAllAsignari()) + 1)
        for i in range(lungime_dorita):
            asignari.append(self.getAllAsignari()[i])
        # ordonam asignarile in functie de media notelor la toate mateiile
        for i in asignari:
            toate = self.repoStudenti.getAll()
            self.mergeSort(toate, key=lambda x:x.get_id(), reverse=True)
            #toate.sort(key=lambda x: x.get_id(), reverse=False)
            st = self.repoStudenti.cauta(toate, 0, len(toate)-1, i.get_student())
            studenti.append(st)
        medii_studenti = {}
        for student in studenti:
            suma = 0
            numar = 0
            for asignare in asignari:
                if asignare.get_student_id() == student.get_id():
                    numar += 1
                    suma += asignare.get_nota()
            medie = suma / numar
            medii_studenti.update({student.get_id(): medie})
        # sortam dictionarul
        sortat = sorted(medii_studenti.items(), key=operator.itemgetter(1))
        print('Primii 20% din studenti oridonati dupa media la toate disciplinele:')
        for i in reversed(sortat):
            toate = self.repoStudenti.getAll()
            self.comb_sort(toate, key=lambda  x:x.get_id(), reverse = True)
            #toate.sort(key=lambda x: x.get_id(), reverse=False)
            print(self.repoStudenti.cauta(toate, 0, len(toate)-1, Student(int(i[0]), None)).get_nume(), '  ', i[1])

    def raport3(self):
        """
        functie care returneaza o lista cu toti profesorii imoreuna cu numarul de restantieri pentru fiecare
        lista este ordonata in functie de numarul de restantieri
        :return:
        """
        discipline = self.repoDiscipline.getAll()
        profesori = []
        for disciplina in discipline:
            profesori.append(disciplina.get_prof())
        numar_restantieri_profesori = dict()
        for profesor in profesori:
            numar_restantieri_profesori.update({profesor: 0})
            for asignare in self.getAllAsignari():
                toate = self.repoDiscipline.getAll()
                self.comb_sort(toate, key=lambda x:x.get_id(), reverse = True)
                #toate.sort(key=lambda x: x.get_id(), reverse=False)
                discip = self.repoDiscipline.cauta(toate, 0, len(toate)-1, asignare.get_disciplina())
                if profesor == discip.get_prof() and asignare.get_nota() < 5:
                    numar_restantieri_profesori[profesor] += 1

        # avem dictionarul cu toti profesorii si cu numarul de restantieri penru fiecare
        # sortam dictionarul
        sortat = sorted(numar_restantieri_profesori.items(), key=operator.itemgetter(1))
        for i in reversed(sortat):
            print(i[0], ': ', i[1])

    def insertion_sort(self, arr, key, reverse):
        """
        functie care se foloseste pentru sortarea listelor de asignari dupa atributul nota
        :param arr: lista de asignari
        :return:
        """
        if reverse == True:
            for i in range(1, len(arr)):
                aux = arr[i]
                j = i - 1
                while j >= 0 and key(aux) < key(arr[j]):
                    arr[j + 1] = arr[j]
                    j -= 1
                arr[j + 1] = aux
        else:
            for i in range(len(arr), 1):
                aux = arr[i]
                j = i - 1
                while j >= 0 and key(aux) < key(arr[j]):
                    arr[j + 1] = arr[j]
                    j -= 1
                arr[j + 1] = aux

    def comb_sort(self, arr, key, reverse):
        """
        functie care sorteqaza o lista de obiecte dupa id
        :return:
        """
        if reverse == True:

            n = len(arr)
            decalaj = n
            swapped = True
            while decalaj != 1 or swapped == True:
                decalaj = self.getNextDecalaj(decalaj)
                swapped = False
                for i in range(n - decalaj):
                    if key(arr[i]) > key(arr[i + decalaj]):
                        arr[i], arr[i + decalaj] = arr[i + decalaj], arr[i]
                        swapped = True
        else:
            n = len(arr)
            decalaj = n
            swapped = True
            while decalaj != 1 or swapped == True:
                decalaj = self.getNextDecalaj(decalaj)
                swapped = False
                for i in range(n - decalaj):
                    if key(arr[i]) > key(arr[i + decalaj]):
                        arr[i], arr[i + decalaj] = arr[i + decalaj], arr[i]
                        swapped = True

    def getNextDecalaj(self, decalaj):
        """
        functie auxiliara pentru functia comb_sort
        :param decalaj: decalajul curent
        :return: decalajul viitor
        """
        decalaj = (decalaj * 10) // 13
        if decalaj < 1:
            return 1
        return decalaj

    def  mergeSort(self, arr, key, reverse):
        """
        Functie care ordoneaza o lista cu metoda merge sort
         input-lista
         preconditii-lista-lista
         output- lista ordonata folosind merge sort
         postconditii-lista-lista

         Complexitatea:
         TIMP:
             ALL CASE: Complexitatea este ϴ(n logn)
         SPATIU:
            O(n)
        :param arr: lista de obiecte ce va fi sortata
        :return:
        """
        if reverse == True:
            if len(arr) > 1:
                mid = len(arr) // 2
                L = arr[:mid]
                R = arr[mid:]

                self.mergeSort(L, key, reverse)
                self.mergeSort(R, key, reverse)

                i = j = k = 0

                while i < len(L) and j < len(R):
                    if key(L[i]) < key(R[j]):
                        arr[k] = L[i]
                        i += 1
                    else:
                        arr[k] = R[j]
                        j += 1
                    k += 1

                while i < len(L):
                    arr[k] = L[i]
                    i += 1
                    k += 1

                while j < len(R):
                    arr[k] = R[j]
                    j += 1
                    k += 1
        else:
            if len(arr) > 1:
                mid = len(arr) // 2
                L = arr[:mid]
                R = arr[mid:]

                self.mergeSort(L, key, reverse)
                self.mergeSort(R, key, reverse)

                i = j = k = 0

                while i < len(L) and j < len(R):
                    if key(L[i]) > key(R[j]):
                        arr[k] = L[i]
                        i += 1
                    else:
                        arr[k] = R[j]
                        j += 1
                    k += 1

                while i < len(L):
                    arr[k] = L[i]
                    i += 1
                    k += 1

                while j < len(R):
                    arr[k] = R[j]
                    j += 1
                    k += 1




