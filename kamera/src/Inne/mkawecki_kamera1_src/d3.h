/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka d3 - operacje 3-wymiarowe
 *
 */


#ifndef D3_INCLUDED
#define D3_INCLUDED

#include <SDL.h>


using namespace std;



// wpolrzedne punktu w 3D
struct D3_punkt {

  double x,y,z;

};

// struktura opisujaca prostopadloscian na scenie
struct D3_prostopadloscian {

  D3_punkt wierzcholki[6][4];
  D3_punkt wsp_osi;
  Uint32 kolor;

};

// mnozenie wspolrzednych punktow
D3_punkt d3_pkt_pomnoz(D3_punkt p1, D3_punkt p2);

// zmodyfikowany algorytm Cohena-Sutherlanda
bool d3_obciecie_cs(D3_punkt wi1, D3_punkt wi2, D3_punkt &pkt1, D3_punkt &pkt2);

// mnozenie macierz*punkt i normalizacja
D3_punkt d3_pomnoz_normalizuj_pkt(D3_punkt p, double* M);


bool d3_wektor_normal(D3_punkt &w);

#endif
