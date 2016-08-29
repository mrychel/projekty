/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka d2 - operacje 2-wymiarowe
 *
 */


#ifndef D2_INCLUDED
#define D2_INCLUDED

#include <SDL.h>

#include "d3.h"

using namespace std;


struct D2_punkt {

  double x, y;
  // konstruktor do konwersji z 3D
  D2_punkt(D3_punkt p) {  x = p.x;  y = p.y; }
  // bez arg.
  D2_punkt() { x = 0.0;  y = 0.0; }
  D2_punkt(double x, double y) {  this->x = x;  this->y = y; }

};


// rysowanie odcinka - algorytm Bresenhama
void d2_linia(SDL_Surface* screen, D2_punkt p1, D2_punkt p2,  Uint32 color);

// algorytm Cohena-Sutherlanda
bool d2_obciecie_cs(double left, double top, double right, double bottom, D2_punkt &pkt1, D2_punkt &pkt2);

// rysowanie wypelnionego wielokata (algorytm przegladania linii) z obcinaniem (algorytm C-S)
void d2_wypelnienie_wielokata(SDL_Surface *scr, D2_punkt *v, int n, Uint32 c);


#endif
