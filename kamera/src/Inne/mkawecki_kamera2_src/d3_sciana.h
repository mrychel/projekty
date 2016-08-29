/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka d3_sciana - struktura sciany obiektu 3D
 *
 */

#ifndef D3_SCIANA_INCLUDED
#define D3_SCIANA_INCLUDED

#include "d3.h"

using namespace std;


// sciana skladajaca sie z 4 wierzcholkow
struct D3_sciana {

  // wspolrzedne wierzcholkow
  D3_punkt wierzcholki[4];
  // kolejny nr sciany (1-6) i nr obiektu
  int nr_sciany;
  // wspolczynniki plaszczyzny
  double A, B, C, D;
  // wyliczenie wspolczynnikow plaszczyzny
  void policz_wspolczynniki();
  // odleglosc plaszczyzny od punktu p
  double odleglosc_pkt(D3_punkt p);
  // rysowanie sciany (odcinkow)
  void wyswietl_sciane(SDL_Surface* scr, Uint32 kolor, double *Mt, double *Mo, double *Mr);

};





#endif
