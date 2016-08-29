/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka d3 - operacje na strukturach 3-wymiarowych
 *
 */


#ifndef D3_INCLUDED
#define D3_INCLUDED

#include <SDL.h>


using namespace std;




// wspolrzedne punktu w 3D
struct D3_punkt {

  double x,y,z;

};

// pelna deklaracja w d3_sciana.h
struct D3_sciana;


// mnozenie wspolrzednych punktow
D3_punkt d3_pkt_pomnoz(D3_punkt p1, D3_punkt p2);

// wymnozenie sciany przez wsp.punktu
D3_sciana d3_sciana_pkt_pomnoz(D3_sciana sciana, D3_punkt p);

// zmodyfikowany algorytm Cohena-Sutherlanda
bool d3_obciecie_cs(D3_punkt wi1, D3_punkt wi2, D3_punkt &pkt1, D3_punkt &pkt2);

// mnozenie macierz*punkt i normalizacja
D3_punkt d3_pomnoz_normalizuj_pkt(D3_punkt p, double* M);


#endif
