/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka d2 - operacje 2-wymiarowe
 *
 */


#ifndef D2_INCLUDED
#define D2_INCLUDED

#include "d3.h"

using namespace std;


struct D2_punkt {

  double x, y;
  // konstruktor do konwersji z 3D
  D2_punkt(D3_punkt p) {  x = p.x;  y = p.y; }

};


// algorytm Bresenhama
void d2_linia(SDL_Surface* screen, D2_punkt p1, D2_punkt p2,  Uint32 color);

// algorytm Cohena-Sutherlanda
bool d2_obciecie_cs(double left, double top, double right, double bottom, D2_punkt &pkt1, D2_punkt &pkt2);


#endif
