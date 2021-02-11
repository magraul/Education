from Errors import ValidError


class StudentValidator(object):
    def valideazaStudent(self, student):
        """
        fucntie ce valideaza un student in fucntie de standarde prestabilite
        :param student: obiect de tip student
        :return: statusul de validaitate al studentului
        """
        erori = ""
        if student.get_id() < 0:
            erori += "id invalid!\n"
        if invalid(student.get_nume()) or student.get_nume() == "":
            erori += "nume invalid!"
        if len(erori) > 0:
            raise ValidError(erori)


class DisciplinaValidator(object):
    def valideazaDisciplina(self, disciplina):
        """
        fucntie care valideaza o disciplina in fucntie standardele stabilite
        :param disciplina: obiectd e tip disciplina
        :return: eroare ce caracterizeaza validitatea parametrului
        """
        erori = ""
        if disciplina.get_id() < 0:
            erori += "id invalid!\n"
        if invalid(disciplina.get_nume()):
            erori += "nume invalid!\n"
        if invalid(disciplina.get_prof()):
            erori += "nume prof imvalid!"
        if len(erori) > 0:
            raise ValidError(erori)


class AsignariValidator(object):
    def ValideazaAsignare(self, asignare):
        erori = ""
        if not 1 < int(asignare.get_nota()) < 11:
            erori += "nota invalida!"
        if len(erori):
            raise ValidError(erori)


def invalid(x):
    """
    functie pentru validarea datelor de tip string
    :param x: string ce trebuie verificat daca incepe cu litera mare si nu contine in el cifre sau caractere interzise
    :return: True sau False in functie de validitatea stringului
    """
    if len(x) == 0:
        return True
    lisa = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
    if x[0] == x[0].lower():
        return True
    for i in range(1, len(x)):
        if x[i] != x[i].lower() or x[i] in lisa or x[i] == ' ':
            return True
    return False
