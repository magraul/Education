class Student:
    def __init__(self, __id, __nume):
        self.__IDStudent = __id
        self.__nume = __nume

    def __str__(self):
        return str(self.__IDStudent) + " ->" + str(self.__nume)

    def get_id(self):
        """
        functie ce returneaza id-ul studentului cu care este apelata
        :return: id de student
        """
        return self.__IDStudent

    def get_nume(self):
        """
        fucntie ce returneaza numele studentului cu care ste apelata
        :return: nume de student
        """
        return self.__nume

    def __eq__(self, value):
        return self.__IDStudent == value.__IDStudent

    def __gt__(self, other):
        return self.get_id() > other.get_id()

    def __lt__(self, other):
        return self.get_id() < other.get_id()
