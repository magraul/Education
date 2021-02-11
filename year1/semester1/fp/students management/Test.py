from Student import Student
from Disciplina import Disciplina
from Validator import *
from Repository import Repository, RepoError


class Test(object):

    def __init__(self):
        self.__sid = 34
        self.__nume = 'Raul'
        self.__student = Student(self.__sid, self.__nume)
        self.__bsid = -30
        self.__bnume = ''
        self.__bstudent = Student(self.__bsid, self.__bnume)
        self.__did = 50
        self.__dnume = 'Mate'
        self.__prof = 'Vancea'
        self.__disciplina = Disciplina(self.__did, self.__dnume, self.__prof)
        self.__bdid = -50
        self.__bdnume = ''
        self.__bprof = ''
        self.__bdisciplina = Disciplina(self.__bdid, self.__bdnume, self.__bprof)
        self.__pbdid = 50
        self.__pbdnume = 'mat5e'
        self.__pbprof = 'vanc6ea'
        self.__pbdisciplina = Disciplina(self.__pbdid, self.__pbdnume, self.__pbprof)

        self.__validator_student = StudentValidator()
        self.__validator_disciplina = DisciplinaValidator()
        self.__repo = Repository()

    def __testModel(self):
        assert self.__student.get_nume() == self.__nume

    def __testValid_stud(self):
        try:
            self.__validator_student.valideazaStudent(self.__student)
            assert True
        except ValidError:
            assert False
        try:
            self.__validator_student.valideazaStudent(self.__bstudent)
            assert False
        except ValidError as ve:
            assert str(ve) == "id invalid!\nnume invalid!"

    def __testValid_disciplina(self):
        try:
            self.__validator_disciplina.valideazaDisciplina(self.__disciplina)
            assert True
        except ValidError:
            assert False
        try:
            self.__validator_disciplina.valideazaDisciplina(self.__bdisciplina)
            assert False
        except ValidError as ve:
            assert str(ve) == "id invalid!\nnume invalid!\nnume prof imvalid!"
        try:
            self.__validator_disciplina.valideazaDisciplina(self.__pbdisciplina)
            assert False
        except ValidError as ve:
            assert str(ve) == 'nume invalid!\nnume prof imvalid!'

    def __testRepo(self):
        assert len(self.__repo) == 0
        try:
            self.__repo.cauta(self.__bstudent)
            assert False
        except RepoError as re:
            assert  str(re) == 'element inexistent!'
        self.__repo.adauga(self.__student)
        assert len(self.__repo) == 1
        cheieStudent = Student(self.__sid, None)
        student = self.__repo.cauta(cheieStudent)
        assert student == self.__student
        try:
            self.__repo.adauga(self.__student)
            assert False
        except RepoError as re:
            assert str(re) == "element existent!"

        assert len(self.__repo) == 1
        try:
            self.__repo.sterge(cheieStudent)
            assert True
        except RepoError as re:
            assert str(re) == "element inexistent!"
        assert len(self.__repo) == 0
        try:
            self.__repo.cauta(self.__bdisciplina)
            assert False
        except RepoError as re:
            assert str(re) == 'element inexistent!'
        self.__repo.adauga(self.__disciplina)
        assert len(self.__repo) == 1
        cheieDisciplina = Disciplina(self.__did, None, None)
        disp = self.__repo.cauta(cheieDisciplina)
        assert disp == self.__disciplina
        try:
            self.__repo.adauga(self.__disciplina)
            assert False
        except RepoError as re:
            assert str(re) == 'element existent!'

    def ruleazaTeste(self):
        self.__testModel()
        self.__testValid_stud()
        self.__testValid_disciplina()
        self.__testRepo()
