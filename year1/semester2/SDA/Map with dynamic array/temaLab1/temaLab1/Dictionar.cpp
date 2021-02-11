#include "Dictionar.h"
#include "Iterator.h"
#include <iostream>

// aici implementarea operatiilor din Dictionar.h



Dictionar::Dictionar() {
	vector = (TElem **) malloc(sizeof(TElem *)*capacitate);
}


IteratorDictionar Dictionar::iterator() const {
	IteratorDictionar *it = new IteratorDictionar(*this);
	return *it;
}

Dictionar::~Dictionar() {
	// TBA
	for (int i = 0; i < dimensiune; i++) {
		free(vector[i]);
	}
	free(vector);
	capacitate = 100;
	dimensiune = 0;
}


// restul operatiilor

TValoare Dictionar::adauga(TCheie c, TValoare v) {
	TElem *e = (TElem *)malloc(sizeof(TElem));
	e->first = c;
	e->second = v;
	if (!dimensiune) {
		vector[dimensiune++] = e;
		return NULL_TVALOARE;
	}
	bool gasit = false;
	for (int i = 0; i < dimensiune; i++) {
		if (vector[i]->first == c) {
			gasit = true;
			break;
		}
	}
	if(!gasit){
		//nu mai exista cheia
		if (dimensiune < capacitate) {
			vector[dimensiune++] = e;
		}
		else {
			//redimensionare
			TElem **vect_aux = (TElem **)malloc(sizeof(TElem*)*capacitate * 2);
			for (int i = 0; i < dimensiune; i++) {
				vect_aux[i] = vector[i];
			}
			vect_aux[capacitate] = e;
			free(vector);
			vector = vect_aux;
			capacitate *= 2;
			++dimensiune;
		}

		return NULL_TVALOARE;
	}
	else {
		//duplicat
		int pos = get_pos_cheie(c);
		if (pos != -1) {
			int aux = vector[pos]->second;
			vector[pos]->second = v;
			return aux;
		}
		else {
			printf("eroare la duplicat");
		}
	}
}

int Dictionar::get_pos_cheie(TCheie c) {
	for (int i = 0; i <dimensiune; i++) {
		if (vector[i]->first == c) {
			return i;
		}
	}
	return -1;
}

TValoare Dictionar::cauta(TCheie c) const {
	for (int i = 0; i < dimensiune; i++) {
		TElem *b = vector[i];
		int a = vector[i]->first;
		if (vector[i]->first == c) {

			return vector[i]->second;
		}
	}
	return NULL_TVALOARE;
}

TValoare Dictionar::sterge(TCheie c) {
	bool gasit = false;
	for (int i = 0; i < dimensiune; i++) {
		if (vector[i]->first == c) {
			gasit = true;
			break;
		}
	}
	if (gasit) {
		int index = get_pos_cheie(c);
		TElem *to_delete = vector[index];
		int aux = vector[index]->second;
		for (int j = index; j < dimensiune - 1; j++) {
			vector[j] = vector[j + 1];
		}
		free(to_delete);
		--dimensiune;
		return aux;
	}
	else return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int Dictionar::dim() const {
	return dimensiune;
}

//verifica daca dictionarul e vid
bool Dictionar::vid() const {
	return dimensiune == 0;
}
