#pragma once
#include "Iterator.h"

IteratorDictionar::IteratorDictionar(const Dictionar& c) 
	:dict(c)
{
	index_curent = 0;
}

TElem IteratorDictionar::element() const {
	//TBA
	/*for (int i = 0; i < dict.dim(); i++) {
		if ();
	}*/
	return *dict.vector[index_curent];
}

bool IteratorDictionar::valid()const {
	//TBA
	return index_curent < dict.dim();
}

void IteratorDictionar::urmator() {
	//TBA
	++index_curent;
}

void IteratorDictionar::prim() {
	//TBA
	index_curent = 0;
}
