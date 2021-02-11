using System;
using Domain_Repo.repositories;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {

            DonatoriDBRepository repo = new DonatoriDBRepository();
            
            Console.WriteLine(repo.findDonatoriDupaNume("Manuela")[0].Name);
        }
    }
}
