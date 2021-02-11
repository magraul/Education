from Test import Test
from Repository import RepositoryFileStudent, Repository, RepositoryFileDisciplina, RepositoryFileAsignare
from Validator import StudentValidator, DisciplinaValidator, AsignariValidator
from Controllers import *
from Console import Console
t = Test()
t.ruleazaTeste()
validator = AsignariValidator()
repoStudenti = RepositoryFileStudent()
repoDiscipline = RepositoryFileDisciplina()
repoAsign = RepositoryFileAsignare()
validStudent = StudentValidator()
validDisciplina = DisciplinaValidator()
CtrlStudenti = StudentiController(repoStudenti, validStudent)
CtrlDiscipline = DisciplineController(repoDiscipline, validDisciplina)
CtrlAsign = AsignController(repoStudenti, repoDiscipline, repoAsign, validator)
cons = Console(CtrlStudenti, CtrlDiscipline, CtrlAsign)
cons.run()
