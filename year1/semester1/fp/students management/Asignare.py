class Asignare(object):
    def __init__(self, __student, __disciplina, __nota):
        self.__student = __student
        self.__disciplina = __disciplina
        self.__nota = __nota

    def __eq__(self, other):
        return self.__student.get_id() == other.__student.get_id() and self.__disciplina.get_id() == other.__discip.get_id()

    def get_student_id(self):
        """
        functie ce returnea id-ul unui student dintr-o anume asignare
        :return: id de student
        """
        return self.__student.get_id()

    def get_disciplina_id(self):
        """
        fucntie ce returneza id ul unei discipline
        :return: id de disciplina
        """
        return self.__disciplina.get_id()

    def get_nota(self):
        """
        fucntie ce returneaza nota din asignarea cu care este apelata
        :return: nota ca si intreg
        """
        return self.__nota

    def get_student(self):
        return self.__student

    def get_disciplina(self):
        return self.__disciplina

    def __str__(self):
        return str(self.get_student_id()) + ' ' + str(self.get_disciplina_id()) + ' ' + str(self.get_nota())
