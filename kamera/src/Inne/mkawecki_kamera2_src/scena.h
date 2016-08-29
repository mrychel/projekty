/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka scena - budowa sceny
 *
 */


#ifndef SCENA_INCLUDED
#define SCENA_INCLUDED

#include "d3.h"
#include "drzewo.h"
#include "config.h"


using namespace std;


// punkty krancowe widocznego fragmentu przestrzeni 3D do wstepnego obciecia,
// wyznaczone (eksperymentalnie) tak, aby wszystkie sciany bryl (przy odpowiednim obrocie)
// miescily sie w polu widzenia przy maksymalnej translacji we wszystkich osiach i ogniskowej d=1
const D3_punkt scena_obciecie_wierzcholki[2] = { {MIN_T*2.1, MIN_T*2.1, 0.0}, {MAX_T*2.1, MAX_T*2.1, MAX_T*2.1} };

// relacje pomiedzy scianami 3D
bool scena_sciana_blizej(D3_sciana sciana1, D3_sciana sciana2);
// posortowanie tablicy scian
void scena_sciany_sortuj(D3_sciana* sciana, int n);
//  przygotowuje obiekty i dodaje je do drzewa BSP
void scena_zbuduj_drzewo(Drzewo_wezel* &w);


#endif
