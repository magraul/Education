from Errors import ValidError, RepoError
from Student import Student
from Disciplina import Disciplina
from ciorna import *


class Console(object):
    def __init__(self, ctrlStud, ctrlDiscip, ctrlAsign):
        self.ctrlStud = ctrlStud
        self.ctrlDiscip = ctrlDiscip
        self.ctrlAsign = ctrlAsign
        self.deschidere_stud = False
        self.deschidere_discip = False

    def __uiAddStudent(self, params):
        """
        functie care adauga un nou student cu datele continute in lista "params"
        :param params: lista de stringuri trebuie sa contina id-ul unui student care va fi adaugat, si numele acestuia
        ca si al doilea parametru, ambele ca si stringuri
        :return:
        """
        if len(params) != 2:
            print('numar de parametrii invalid! ')
            return
        id = int(params[0])
        nume = params[1]
        self.ctrlStud.adaugaStudent(id, nume)

    def __uiPrintStudenti(self, params):
        """
        functie care afiseaza toti studentii pe care ii contine catalogul in momentul apelarii
        :param params:
        :return:
        """
        if len(params)>0:
            print('numar parametri invalid!')
            return
        studenti = self.ctrlStud.getAllStudenti()
        if len(studenti):
            for stud in studenti:
                print(stud)

    def __uiAddDisciplina(self, params):
        """
        fucntie care creeaza o nou disciplina cu datele continute ca si stringuri in lista "params"
        :param params: pe prima pozitie contine id-ul noii discipline, pe a doua pozitie contine numele disciplinei
        si pe a treia contine numele prifesorului
        :return:
        """
        if len(params) != 3:
            print('numar invalid de parametri!')
            return
        id = int(params[0])
        nume_disciplina = params[1]
        nume_prof_disciplina = params[2]
        self.ctrlDiscip.adaugaDisciplina(id, nume_disciplina, nume_prof_disciplina)

    def __uiprintDiscipline(self, params):
        """
        functie care afiseaza toate disciplinele pe care le contine catalogul in prezent
        :param params:
        :return:
        """
        if len(params):
            print('numar invalid de parametrii!')
            return
        discipline = self.ctrlDiscip.getAllDiscipline()
        if len(discipline):
            for disp in discipline:
                print(disp)
        else:
            print('fisier gol!')


    def __uiupdateStudent(self, params):
        """
        functie ce cauta un student in catalog cu id-ul continut in params[0] si daca este gasit creeaza un student cu datele
        din params[1] si params[2] si il inlocuieste cu studentul cautat
        :param params: o lista de stringuri ce contine parametrii obiectelor ce vor fi creeate
        :return:
        """
        if len(params) != 3:
            print('numar invalid de parametri!')
            return
        de_inlocuit = Student(int(params[0]), None)
        inlocuitor = Student(int(params[1]), params[2])
        self.ctrlAsign.updateStudent(de_inlocuit, inlocuitor)

    def __uiupdateDisciplina(self, params):
        """
        fucnte care cauta o disciplina ce are id-ul continut ca si string in params[0] si creeaza o noua disciplina cu ceilalti
        parametrii si o sa apeleze functia de update pentru a inlocuii primul oiect cu al doilea
        :param params: lista de stringuri ce contine parametrii cu care vor fi create discipline, ca si string-uri
        :return:
        """
        if len(params) != 4:
            print('numar invalid de parametri!')
            return
        deinlocuit = Disciplina(int(params[0]), None, None)
        inlocuitor = Disciplina(int(params[1]), params[2], params[3])
        self.ctrlAsign.updateDisciplina(deinlocuit, inlocuitor)

    def __uideleteStudent(self, params):
        """
        fucntie care creeaza un nou student cu elementul din params si il trimite spre stergere cu funcia specifica repo-ului
        :param params: lista de string-uri ce contine id-ul studentului ce se creeaza pentru a fi sters din repo
        :return:
        """
        if len(params) != 1:
            print('numar invalid de parametrii!')
            return
        new = Student(int(params[0]), None)
        self.ctrlStud.repoStudenti.sterge(new)

    def __uideleteDisciplina(self, params):
        """
        fucntie care sterge din repo un student ce are id-ul egal cu continutul listei params
        :param params: lista ce contine un string ce seminfica id-ul disciplinei de sters
        :return:
        """
        if len(params)!= 1:
            print('numar invalid de parametri!')
            return

        new = Disciplina(int(params[0]), None, None)
        self.ctrlDiscip.repoDisciplina.sterge(new)

    def __uicautaStudent(self, params):
        """
        functie care cauta un student cu id-ul egal cu continutul listei params, creeand un nou student cu id-ul respectiv
        si foloseste functia  de cautare din repo
        :param params: lista ce contine id-ul unui student care trebuie cautat ca si string
        :return: mesaj corespunzator daca a fost gasit sau nu
        """
        if len(params) != 1:
            print('numar de parametri invalid!')
            return
        lista = self.ctrlStud.repoStudenti.getAll()
        lista.sort(key=lambda x: x.get_id(), reverse = False)
        a = self.ctrlStud.repoStudenti.cauta(lista, 0, len(lista)-1, Student(int(params[0]), None))
        if a:
            print('studentul a fost gasit!')

    def __uicautaDisciplina(self, params):
        """
        functie care cauta o disciplina cu id-ul egal cu continutul listei params, creeand o noua disciplina cu id-ul respectiv
        si foloseste functia  de cautare din repo
        :param params: lista ce contine id-ul unuei discipline care trebuie cautata ca si string
        :return: mesaj corespunzator daca a fost gasita sau nu
        """

        if len(params) != 1:
            print('numar invalid de parametri!')
            return
        lista = self.ctrlDiscip.repoDisciplina.getAll()
        lista.sort(key=lambda x: x.get_id(), reverse = False)
        de_cautat = Disciplina(int(params[0]), None, None)
        a = self.ctrlDiscip.repoDisciplina.cauta(lista, 0, len(lista)-1, de_cautat)
        if a:
            print('disciplina a fost gasita!')

    def __randomstud(self, params):
        """
        fucntie care creeaza n obiecte de tip student cu date generate random iar n, numarul lor, este continut in lista
        params ca si string
        :param params:lista de string-uri ce contine numarul de obiecte ce vor fi adaugate
        :return:
        """
        if len(params)!= 1:
            print('numar invalid de parametri!')
            return
        try:
            params = int(params[0])
        except:
            print('numar invalid')
            return
        if params < 0:
            print('numar invalid!')
            return
        i = 0
        while i < params:
            id = random.randint(100, 500)
            lungime = random.randint(2, 10)
            numee = randomword(lungime)
            try:
                self.ctrlStud.adaugaStudent(id, numee)
            except RepoError:
                i -= 1
            i += 1

    def __randomdiscip(self, params):
        """
        fucntie care creeaza n obiecte de tip disciplina cu date generate random iar n, numarul lor, este continut in lista
        params ca si string
        :param params: lista de string-uri ce contine numarul de discipline ce vor fi creeate si adaugate
        :return:
        """
        if len(params)!= 1:
            print('numar invalid de parametri!')
            return
        try:
            params = int(params[0])
        except:
            print('numar invalid de parametri!')
            return
        if params < 0:
            print('numar invalid de parametri!')
            return
        i = 0
        while i < params:
            id = random.randint(100, 500)
            lungime_nume_disciplina = random.randint(2, 10)
            nume_disciplina = randomword(lungime_nume_disciplina)
            lungime_nume_profesor = random.randint(2, 10)
            nume_profesor = randomword(lungime_nume_profesor)
            try:
                self.ctrlDiscip.adaugaDisciplina(id, nume_disciplina, nume_profesor)
            except RepoError:
                i -= 1
            i += 1

    def __Asignare(self, params):
        """
        fucnte care creeaza noi obiecte ce contin id-ul unui student id-ul unei discipline si o nota
        aceasta reprezinta o asignare de nota la un anumit student la o anume disciplina
        :param params: lista de parametrii ce contine:
                       id student
                       id disciplina
                       nota de asignat
        :return:
        """
        if len(params) != 3:
            print('nuamr invalid de parametri!')
            return
        self.ctrlAsign.adaugaAsignare(Student(params[0], None), Disciplina(params[1], None, None), params[2])

    def __printAsignari(self, params):
        """
        functie ce afiseaza in consola asignarile existente
        :param params: lista vida de parametri, are rol in verificarea corectitudinii la introducere
        :return:
        """
        if len(params) > 0:
            print('numar parametri invalid!')
            return
        asignari = self.ctrlAsign.getAllAsignari()
        if len(asignari):
            for asign in asignari:
                print(asign)
        else:
            print('lista goala!')

    def __raport1(self, params):
        """
        functie care ordoneaza asignarile curente in functie de optiunea UI-ului prin lista de optiune "params"si afiseaza pe ecran aceasta ordonare
        :param params:lista ce contine disciplina la care se face ordonarea, si criteriul dupa care se face ordonarea
        :return:
        """
        if len(params)!= 2:
            print('numar de parametri invalid!')
            return
        self.ctrlAsign.raport1(params[0], params[1])

    def __raport2(self, params):
        """
        lista ce preia din asignarile totale doar primele 20% din ele si face media la toate disciplinele si ordoneaza
        crescator in functie de medie lista de studenti
        :param params:lista vida care verifica corectitudinea introducerii comenzii de catre UI
        :return:
        """
        if len(params):
            print('numar invalid de parametri!')
            return
        self.ctrlAsign.raport2()

    def __raport3(self, params):
        if len(params):
            print('numar invalid de parameri!')
            return
        self.ctrlAsign.raport3()
    def run(self):
        """
        functie principala de UI care care afiseaza meniul si cere comenzile de la utilizator trimitand pe baza acestora la functia
        corespunzatoare
        :return:
        """
        comenzi = {'addstudent': self.__uiAddStudent, 'adddisciplina': self.__uiAddDisciplina, 'printstudenti': self.__uiPrintStudenti, 'printdiscipline': self.__uiprintDiscipline,
                   'deletestudent': self.__uideleteStudent, 'deletedisciplina': self.__uideleteDisciplina, 'updatestudent': self.__uiupdateStudent,
                   'updatedisciplina': self.__uiupdateDisciplina, 'cautadisciplina': self.__uicautaDisciplina, 'cautastudent': self.__uicautaStudent,
                   'randomstud': self.__randomstud, 'randomdiscip': self.__randomdiscip, 'asignare': self.__Asignare, 'printasignari': self.__printAsignari, 'ordonare': self.__raport1,
                   'ordonare_medie_generala': self.__raport2, 'ordonare_restantieri': self.__raport3}

        while True:
            print('Alegeti o optiune:\naddstudent id nume\nprintstudenti\nadddisciplina id nume_disp nume_prof\nprintdiscipline\n'
                  'deletestudent id\ndeletedisciplina id\nupdatestudent id_de_inlocuit id_inlocuitor nume_inlocuitor\nupdatedisciplina id_de_inlocuit id_inlocuitor nume_materie nume_prof\n'
                  'cautastudent id\ncautadisciplina id\nrandomstud numar\nrandomdiscip numar\nasignare id_student id_disciplina nota\nprintsignari\nordonare criteriu_de_ordonare(alfabetic sau dupa_nota) disciplina(id)\n'
                  'ordonare_medie_generala\nordonare_restantieri')
            cmd = input('>>>')
            params = cmd.split(' ')
            if cmd == 'exit':
                exit('Iesire comandata din aplicatie')
            elif params[0] in comenzi:
                try:
                    comenzi[params[0]](params[1:])
                except ValueError as ve:
                    print('tip de data invalid!')
                    print(ve)
                except ValidError as ve:
                    print('Validation error:')
                    print(ve)
                except RepoError as re:
                    print('Repo error:')
                    print(re)
            else:
                print('comanda invalida!')
