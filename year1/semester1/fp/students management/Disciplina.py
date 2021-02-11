class Disciplina:
    def __init__(self, __id, __nume, __prof):
        self.__idDisciplina = __id
        self.__nume = __nume
        self.__profesor = __prof

    def get_id(self):
        """
        fucntie ce returneaza id-ul disciplinei
        :return: id de disciplina
        """
        return self.__idDisciplina

    def get_nume(self):
        """
        fucntie ce rturneza numele unei discipline
        :return: nume disciplina
        """
        return self.__nume

    def get_prof(self):
        """
        fucntie ce returneaza numele profesorului
        :return: nume profesor
        """
        return self.__profesor

    def __eq__(self, other):
        return self.__idDisciplina == other.__idDisciplina or self.__profesor == other.__profesor

    def __str__(self):
        return str(self.__idDisciplina) + "->" + str(self.__nume) + "->" + str(self.__profesor)

    def __gt__(self, other):
        return self.get_id() > other.get_id()

    def __lt__(self, other):
        return self.get_id() < other.get_id()
