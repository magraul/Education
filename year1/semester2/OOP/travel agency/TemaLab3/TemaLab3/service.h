#pragma once

#include "Lista.h"

typedef struct {
	Lista *Oferte;
	Lista *undolist;
	Lista *redolist;
	int dim_undo;
} Agentie;

/*
  create a petstore
*/
Agentie creeazaAgent();

void distrugeAgent(Agentie* agent);
/*
Add a pet to the store
*/
int adaugOferta(Agentie* agent, char* tip, char* dest,int zi,int luna,int an ,int price);

int redo(Agentie *);
void stergeOferta(Lista* l, int poz);
/*
  Filter pets in the store
  typeSubstring - cstring
  return all pets where typeSubstring is a substring of the type
*/
Lista *FiltOferteTip(Agentie* agent, char* tip);
Lista *FiltOferteDest(Agentie* agent, char* dest);
Lista *FiltOfertePret(Agentie* agent, int pret);

int cmpDest(Oferta* p1, Oferta* p2);
int cmpPret(Oferta* p1, Oferta* p2);
/*
Sort ascending by dest
*/
Lista *sortDest(Agentie* agent);

/*
Sort ascending by pret
*/
Lista *sortPret(Agentie* agent);


void testAdaug();
void testSort();

Lista *Filtrare_oferte_care_incep_cu_o_litera(Agentie*, char);

int undo(Agentie *agent);

void test_undo();

Lista *get_all(Agentie*);