using System;
using System.Collections.Generic;
using System.Text;


namespace Lab9
{

    class Ui
    {

        private Service service;

        public Ui(Service service)
        {
            this.service = service;
        }

        internal void run()
        {
            int optiune;
            for (; ; )
            {
              
            Console.WriteLine("alegeti o optiune:");
            Console.WriteLine("1: adaugare elev ca si jucator");
            Console.WriteLine("2: adauga meci");
            Console.WriteLine("3: adauga jucator activ");
            Console.WriteLine("4: toti jucatorii unei echipe date");
            Console.WriteLine("5: toti jucatorii activi ai unei echipe de la un anumit meci");
            Console.WriteLine("6 : toate meciurile dintr-o anumita perioada calendaristica");
            Console.WriteLine("7:  scorul de la un anumit meci");
            optiune = Convert.ToInt32(Console.ReadLine());

                try
                {
                    if (optiune == 1)
                    {
                        Console.WriteLine("dati id ul elevului pe care il puneti ca si jucator: ");
                        string idElev = Console.ReadLine();
                        Console.WriteLine("Echipele de la scoala elevului sunt");
                        Console.Write(service.echideleScolii(idElev));
                        Console.WriteLine("dati numele echipei");
                        string echipa = Console.ReadLine();
                        service.addJucator(idElev, echipa);
                    }
                    else if (optiune == 2)
                    {
                        Console.WriteLine(service.getAllEchipe());
                        Console.WriteLine("dati numele echipei 1");
                        string echipa1 = Console.ReadLine();
                        Console.WriteLine("dati numele echipei 2");
                        string echipa2 = Console.ReadLine();
                        Console.WriteLine("dati data in care va alea loc meciul");
                        string data = Console.ReadLine();
                        service.addMeci(echipa1, echipa2, data);
                    }
                    else if (optiune == 3)
                    {
                        Console.WriteLine("jucatorii:");
                        Console.WriteLine(service.getAllJucatori());
                        Console.WriteLine("dati numele jucatorului:");
                        string nume = Console.ReadLine();

                        Console.WriteLine("meciuri disponibile");
                        Console.WriteLine(service.getMeciurileEchipeiJucatorului(nume));
                        Console.WriteLine("dati id ul meciului:");
                        string idMeci = Console.ReadLine();

                        Console.WriteLine("dati cate puncte a inscris:");
                        string puncte = Console.ReadLine();

                        Console.WriteLine("dati tipul jucatorului(rezerva/participant):");
                        string tip = Console.ReadLine();

                        service.addJucatorActiv(nume, idMeci, puncte, tip);

                    }
                    else if (optiune == 4)
                    {
                        Console.WriteLine("echipele existente:");
                        Console.WriteLine(service.getAllEchipe());
                        Console.WriteLine("dati numele echipei");
                        string echipa = Console.ReadLine();
                        Console.WriteLine(service.GetJucatoriiEchipei(echipa));
                    }
                    else if (optiune == 5)
                    {
                        Console.WriteLine("echipele sunt:");
                        Console.WriteLine(service.getAllEchipe());
                        Console.WriteLine("alegeti echipa:");
                        string echipa = Console.ReadLine();

                        Console.WriteLine("meciurule sunt: ");
                        Console.WriteLine(service.getLinesMeciuri());
                        Console.WriteLine("dati id ul meciului");
                        string idMeci = Console.ReadLine();
                        Console.WriteLine(service.raport2(echipa, idMeci));
                    }
                    else if (optiune == 6)
                    {
                        Console.WriteLine("dati perioada de start:");
                        string data1 = Console.ReadLine();
                        DateTime data11 = DateTime.Parse(data1);

                        Console.WriteLine("dati perioada de final:");
                        string data2 = Console.ReadLine();
                        DateTime data22 = DateTime.Parse(data2);

                        Console.WriteLine(service.meciuriInPerioada(data11, data22));

                    }
                    else if (optiune == 7)
                    {
                        Console.WriteLine(service.getLinesMeciuri());
                        Console.WriteLine("dati id ul meciului:");
                        string idMeci = Console.ReadLine();
                        Console.WriteLine("scorul este:");
                        Console.WriteLine(service.getScorLaMeciul(idMeci));
                    }
                    else if (optiune == 0)
                        break;

                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                }
            }
        }
    }
}
