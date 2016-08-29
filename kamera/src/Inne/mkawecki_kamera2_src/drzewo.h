/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka drzewo - implementacja drzewa BSP
 */


#ifndef DRZEWO_INCLUDED
#define DRZEWO_INCLUDED

#include <SDL.h>

#include "d3_sciana.h"
#include "d3.h"
#include "scena_obiekty.h"


using namespace std;


// struktura wezla drzewa BSP
// relacje definiowane w oparciu o polozenie wzgledem okreslonej plaszczyzny (jesli sektor = false)
// lub wzgledem plaszczyzny zawierajacej tylna sciane danego obiektu (jesli sektor = true)
// przod, tyl - wskazniki do innych wezlow (w dol)
struct Drzewo_wezel {

  union {
    Scena_obiekt obiekt;
    Scena_plaszczyzna plaszczyzna;
  };

  bool sektor;

  struct Drzewo_wezel *przod;
  struct Drzewo_wezel *tyl;

  // konstruktory
  Drzewo_wezel(Scena_obiekt obiekt);
  Drzewo_wezel(Scena_plaszczyzna p);

};


// wykreslenie scian (wezlow drzewa) w porzadku in-order
void drzewo_wyswietl(Drzewo_wezel *w, SDL_Surface* scr, double *Mt, double *Mo, double *Mr);
// usuniecie calego drzewa
void drzewo_usun(Drzewo_wezel* &w);



#endif
