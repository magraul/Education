using System;
using System.Net.Http;
using System.Threading.Tasks;
using System.Net.Http.Headers;
using Newtonsoft.Json;
using System.Text;
using System.Security.Cryptography;

namespace RestClient
{
    class Program
    {
        static HttpClient client = new HttpClient();
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
			RunAsync().Wait();
		}


		static async Task RunAsync()
		{
			client.BaseAddress = new Uri("http://localhost:8080/teledon/cazuri");
			client.DefaultRequestHeaders.Accept.Clear();
			client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            String id = "3";
			CazCaritabil result = await GetCazAsync("http://localhost:8080/teledon/cazuri/"+id);
			Console.WriteLine("Am primit {0}", result);
			Console.WriteLine("get all:");
			Console.ReadLine();

			var cazuri = await GetCazuriAsync("http://localhost:8080/teledon/cazuri");

			foreach(var caz in cazuri)
				Console.WriteLine(caz);

			Console.WriteLine("add");
			Console.ReadLine();
			Add("bun", "http://localhost:8080/teledon/cazuri");

			
			Console.ReadLine();
				Update("17", "update", "http://localhost:8080/teledon/cazuri");

			Delete();

		}

		static async Task<CazCaritabil> GetCazAsync(string path)
		{
			CazCaritabil caz = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				caz = await response.Content.ReadAsAsync<CazCaritabil>();
			}
			return caz;
		}

		static async Task<CazCaritabil[]> GetCazuriAsync(string path)
		{
			CazCaritabil[] cazuri = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
				cazuri = await response.Content.ReadAsAsync<CazCaritabil[]>();
			return cazuri;
		}

		static async void Add(string desrieree, string path)
		{
			CazCaritabil c = new CazCaritabil(desrieree);
			var json = JsonConvert.SerializeObject(c);
			var data = new StringContent(json, Encoding.UTF8, "application/json");

			var url = path;

			var response = await client.PostAsync(url, data);

			string result = response.Content.ReadAsStringAsync().Result;
			
		}

		static async void Update(string id, string desrieree, string path)
		{
			CazCaritabil c = new CazCaritabil(id, desrieree);
			var json = JsonConvert.SerializeObject(c);
			var data = new StringContent(json, Encoding.UTF8, "application/json");

			var url = path;

			var response = await client.PutAsync(url, data);

			string result = response.Content.ReadAsStringAsync().Result;
			Console.WriteLine(result);
		}

		static async void Delete()
		{
			var url = "http://localhost:8080/teledon/cazuri/5";
			
			var response = await client.DeleteAsync(url);

			string result2 = response.Content.ReadAsStringAsync().Result;
		}

	}

	public class CazCaritabil
    {
        public string id { get; set; }
        public string description { get; set; }

		public CazCaritabil(string descriere)
		{
			this.description = descriere;
		}

		public CazCaritabil()
		{
			
		}

		public CazCaritabil(string id, string desc)
		{
			this.id = id;
			this.description = desc;
		}

		public override string ToString()
		{
			return "{" +
				"\"id\" : \"" + id + "\","+
				"\"description\" : \"" + description + "\"}";
		}
	}
}
