/*
Creati o aplicatie care permite gestiunea ofertelor de la o agentie de turism.

Fiecare oferta are: tip (munte,mare, citybreak), destinatie, data plecare, pret

Aplicatia permite:
 a) Adaugarea de noi oferte.
 b) Actualizare oferte
 c) Stergere oferta
 d) Vizualizare oferete ordonat dupa pret, destinatie (crescator/descrescator)
 e) Vizualizare oferta filtrate dupa un criteriu (destinatie, tip, pret)
*/
#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include "Lista.h"
#include "service.h"
#include <assert.h>

/*
*Valideaza o posibila oferta
*int: tip,dest - char*, int zi,luna,an,p
*ies: 1 - daca oferta este valida, 0 - daca oferta nu este valida
*/
int validOferta(char* tip, char* dest, int zi, int luna, int an, int p)
{
	int ok = 1;
	if ((strcmp(tip, "munte") != 0) && (strcmp(tip, "citybreak") != 0) && (strcmp(tip, "mare") != 0))
	{
		ok = 0;
		printf("Tipul poate sa fie:munte, citybreak ,mare\n");
	}
	if (strcmp(dest, " ") == 0)
	{
		ok = 0;
		printf("Destinatia nu poate fi vida\n");
	}
	if (zi > 31)
	{
		ok = 0;
		printf("Ziua nu poate fi mai mare ca 31\n");
	}
	if (luna > 31)
	{
		ok = 0;
		printf("Luna nu poate fi mai mare ca 12\n");
	}
	if (an < 2000 && an>3000)
	{
		ok = 0;
		printf("An invalid\n");
	}
	if (p < 0)
	{
		ok = 0;
		printf("Pret invalid\n");
	}
	return ok;
}

void test_validare() {
	assert(validOferta("mare", "dest", 12, 3, 2021, 444) == 1);
	assert(validOferta("dsds", "fdfd", 22, 2, 2012, 4343) == 0);
	assert(validOferta("mare", "roma", 12, 13, 2012, 2222) == 1); //despre asta am zis in review, aici ar trebuii ca rezultatul validarii sa fie 0, luna este 13
}

/*
* Se citesc componentele unei oferte si se apeleaza validarea si adaugarea
* int: agent
* post: daca oferta este valida se adauga, altfel se afis mesaj
*/
void adaugaOferta(Agentie* agent) {

	int ok = 1;
	while (ok) {
		printf("Tip:");
		char tip[30];
		tip[0] = '\0';
		scanf("%s", tip);
		printf("Destinatie:");
		char dest[30];
		dest[0] = '\0';
		scanf("%s", dest);
		printf("Zi:");
		int zi;
		scanf("%d", &zi);
		printf("Luna:");
		int luna;
		scanf("%d", &luna);
		printf("An:");
		int an;
		scanf("%d", &an);

		int p;
		printf("Pret:");
		scanf("%d", &p);

		if (validOferta(tip, dest, zi, luna, an, p))
		{
			ok = 0;
			adaugOferta(agent, tip, dest, zi, luna, an, p);
			printf("Oferta adaugata\n");
		}
		else
			printf("Oferta invalida\n");
	}
}

/*
*Verifica validitatea pozitiei si apeleaza stergerea
*int:agent , poz
*post:daca pozitia este valida se sterge elem, altfel se afis mesaj
*/

void stergereOferta(Agentie* agent, int poz)
{
	Lista *to_undo = copieLista(agent->Oferte, copyOferta);

	if (poz < 0 || poz >= lung(agent->Oferte))
		printf("Pozitie invalida\n");
	else
	{
		stergeOferta(agent->Oferte, poz);
		printf("Oferta a fost stearsa!\n");
		adaug(agent->undolist, to_undo);
	}
}

/*
*Verifica validitatea pozitiei si apeleaza modificarea
*int:agent , poz
*post:daca pozitia este valida se modifica elem, altfel se afis mesaj
*/
void modificareOferta(Agentie* agent, int poz)
{	
	Lista *to_undo = copieLista(agent->Oferte, copyOferta);
	if (poz < 0 || poz >= lung(agent->Oferte))
		printf("Pozitie invalida\n");
	else
	{
		stergeOferta(agent->Oferte, poz);
		adaugaOferta(agent);
		adaug(agent->undolist, to_undo);
	}
}

/*
*Afiseaza elem din lista 
*int: l - Lista
*post:se afis. elem din lista
*/
void printOferte(Lista* l) {
	printf("Oferte:\n");
	for (int i = 0; i < lung(l); i++) {
		Oferta *o = elem(l, i);
		printf("Tip:%s Dest:%s Zi:%d Luna:%d An:%d Pret:%d\n", o->tip, o->dest, o->data.zi, o->data.luna, o->data.an, o->pret);
	}
}
/*
*Afiseaza ofertele din agentie
*int:agent - Agentie
*post:se afis ofertele din agentie si dealocarea memoriei
*/
void afisOferte(Agentie* agent) {
	Lista *Oferte = FiltOferteTip(agent, NULL);
	printOferte(Oferte);
	destroyList(Oferte);
}

/*
*Sortare oferte dupa pret, chemare afisare si dealocarea memoriei
*int:agent - Agentie
*post:se afiseaza lista sortata dupa pret
*/
void sortarePret(Agentie* agent) {
	Lista *Oferte = sortPret(agent);
	printOferte(Oferte);
	destroyList(Oferte);
}

/*
*Sortare oferte dupa dest, chemare afisare si dealocarea memoriei
*int:agent - Agentie
*post:se afiseaza lista sortata dupa dest
*/
void sortareDest(Agentie* agent) {
	Lista* Oferte = sortDest(agent);
	printOferte(Oferte);
	destroyList(Oferte);
}

/*
*Filtrare agentie dupa un tip citit de la tastatura
*int:agent - Agentie
*post:se afis elem din lista care au tipul egal cu cel dat
*/
void filtrareTip(Agentie* agent) {
	printf("Dati tipul(munte,mare,citybreak):\n");
	char tip[30];
	scanf_s("%s", tip, sizeof(tip));
	Lista *rez = FiltOferteTip(agent, tip);
	printOferte(rez);
	destroyList(rez);
}

/*
*Filtrare agentie dupa o dest citit de la tastatura
*int:agent - Agentie
*post:se afis elem din lista care au dest egala cu cea data
*/
void filtrareDest(Agentie* agent) {
	printf("Dati destinatia:\n");
	char dest[30];
	scanf_s("%s", dest, sizeof(dest));
	Lista *rez = FiltOferteDest(agent, dest);
	printOferte(rez);
	destroyList(rez);
}

/*
*Filtrare agentie dupa pret citit de la tastatura
*int:agent - Agentie
*post:se afis elem din lista care au pretul mai mic decat cel dat
*/
void filtrarePret(Agentie* agent) {
	printf("Dati pret:\n");
	int pret;
	scanf("%d", &pret);
	Lista *rez = FiltOfertePret(agent, pret);
	printOferte(rez);
	destroyList(rez);
}

// ========================================================================================

/*
	filtrare oferte a caror prima litera a destinatiei este o litera data de la tastatura
	se afiseaza aceste oferte
*/

void filtrare_prima_litera(Agentie *agent) {
	char prima_litera;
	printf("Introduceti litera dupa care filtrati: "); scanf(" %c", &prima_litera);
	Lista *rez;
	rez= Filtrare_oferte_care_incep_cu_o_litera(agent, prima_litera);
	printOferte(rez);
	destroyList(rez);
}

 // ========================================================================================

void run() {
	printf("1 - Adaugare oferta\n");
	printf("2 - Actualizare oferta\n");
	printf("3 - Sterge oferta\n");
	printf("4 - Ordonare\n");
	printf("5 - Filtrare oferte\n");
	printf("6 - Afis Oferte\n7 - Undo\n");
	printf("0 - Iesire\n");
	int ruleaza = 1,poz,cmd2,cmd3;
	Agentie agent = creeazaAgent();

	while (ruleaza) {
		printf("Dati comanda:\n");
		int cmd = 0;
		scanf_s("%d", &cmd);
		switch (cmd) {
		case 1:
		{
			adaugaOferta(&agent);
			break;
		}
		case 3:
		{
			printf("Dati pozitie:\n");
			scanf("%d", &poz);
			stergereOferta(&agent, poz);
			break;
		}
		case 2:
		{
			printf("Dati pozitie:\n");
			scanf("%d", &poz);
			modificareOferta(&agent, poz);
			break;
		}
		case 4:
		{
			printf("1 - Ordonare cresc dupa pret\n");
			printf("2 - Ordonare descresc dupa destinatie\n");
			scanf("%d", &cmd2);
			if (cmd2 == 1)
				sortarePret(&agent);
			else if (cmd2 == 2)
				sortareDest(&agent);
			else
				printf("Comanda invalida\n");
			break;
		}
		case 5:
		{
			printf("1 - Filtrare dupa tip\n");
			printf("2 - Filtrare dupa destinatie\n");
			printf("3 - Filtrare dupa pret\n");
			printf("4 - Filtrare dupa prima litera a destinatiei");
			scanf("%d", &cmd3);
			if (cmd3 == 1)
				filtrareTip(&agent);
			else if (cmd3 == 2)
				filtrareDest(&agent);
			else if (cmd3 == 3)
				filtrarePret(&agent);
			else if (cmd3 == 4) {
				filtrare_prima_litera(&agent);
			}
			else
				printf("Comanda invalida\n");
			break;
		}
		case 6:
		{
			afisOferte(&agent);
			break;
		}
		case 7:
		{
			if (undo(&agent))
				printf("Nu se mai poate face undo!\n");
			break;
		}
		/*case 8:
		{
			if(redo(&agent))
				printf("Nu se mai poate face redo!\n");
			break;
		}*/
		case 0:
			ruleaza = 0;
			distrugeAgent(&agent);
			break;
		default:
			printf("Comanda invalida!\n");
		}
	}
}

void testTot()
{
	testAdaug();
	testSort();
	testCreeazaElimin();
	testCreeare();
	testIterator();
	testCopie();
	testLung();
	test_undo();
	test_validare();
}

int main() {
	
	testTot();
	//run();
	_CrtDumpMemoryLeaks();
	system("pause");
	
	return 0;
}