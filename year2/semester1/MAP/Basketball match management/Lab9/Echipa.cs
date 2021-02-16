using System;

namespace Lab9
{
	class Echipa : Entity<string>
	{
		public Echipa(string id, string nume)
		{
			base.Id = id;
			Nume = nume;
		}

		public string Nume { get; set; }
	}
}