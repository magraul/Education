using System;

namespace Lab9
{
    class Program
    {
        static void Main(string[] args)
        {

            ValidatorMeciuri validatorMeciuri = new ValidatorMeciuri();
            ValidatorJucatoriActivi validatorJucatoriActivi = new ValidatorJucatoriActivi();
            ValidatorJucatori validatorJucatori = new ValidatorJucatori();
            ValidatorElevi validatorElevi = new ValidatorElevi();
            ValidatorEchipa validatorEchipe = new ValidatorEchipa();
            RepoMeciuri repoMeciuri = new RepoMeciuri("E:\\An2 Sem 1\\MAP\\Lab9\\meciuri.txt");
            RepoJucatoriActivi repoJucatoriActivi = new RepoJucatoriActivi("E:\\An2 Sem 1\\MAP\\Lab9\\jucatoriActivi.txt");
            RepoJucatori repoJucatori = new RepoJucatori("E:\\An2 Sem 1\\MAP\\Lab9\\jucatori.txt");
            RepoElevi repoElevi = new RepoElevi("E:\\An2 Sem 1\\MAP\\Lab9\\elevi.txt");
            RepoEchipe repoEchipe = new RepoEchipe("E:\\An2 Sem 1\\MAP\\Lab9\\echipe.txt");
            Service service = new Service(repoEchipe, repoElevi, repoJucatori, repoJucatoriActivi, repoMeciuri, validatorEchipe, validatorElevi, validatorJucatori, validatorJucatoriActivi, validatorMeciuri);
            Ui ui = new Ui(service);
            ui.run();
        }
    }
}
