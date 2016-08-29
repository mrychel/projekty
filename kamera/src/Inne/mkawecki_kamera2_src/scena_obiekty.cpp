/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka scena_obiekty - definicja obiektow 3D na scenie
 *
 */


#include "d3.h"
#include "d3_sciana.h"
#include "scena_obiekty.h"
#include "scena.h"
#include "obserwator.h"

using namespace std;

/*
Wyliczenie wspolczynnikow A, B, C, D plaszczyzny
dla parametrow wejsciowych (punkty 3D) : p1, p2 i p3
*/
void Scena_plaszczyzna::policz_wspolczynniki(D3_punkt p1, D3_punkt p2, D3_punkt p3) {

  D3_sciana s;
  s.wierzcholki[0] = p1;
  s.wierzcholki[1] = p2;
  s.wierzcholki[2] = p3;
  s.policz_wspolczynniki();

  this->A = s.A; this->B = s.B;
  this->C = s.C; this->D = s.D;

}



/*
Metoda sortuje sciany obiektu w kolenosci od najdalszej od obserwatora (algorytm malarza)
i wywoluje rysowanie scian w odpowiedniej kolejnosci
Parametry wejsciowe:
scr - wskaznik do tymczasowej warstwy ekranu
Mt - macierz translacji (tablica dynamiczna)
Mo - macierz obrotow (tab.dyn.)
Mr - macierz rzutowania (tab.dyn.)
*/
void Scena_obiekt::wyswietl_obiekt(SDL_Surface* scr, double *Mt, double *Mo, double *Mr) {

  D3_sciana sciana[6];
  int j;

  for (j=0;j<6;j++) sciana[j]=this->sciany[j];

  scena_sciany_sortuj(sciana, 6);

  // porzadek sortowania w zaleznosci od strony ulicy
  if (this->wsp_osi.z<0)
    for (j=0;j<6;j++)
      sciana[j].wyswietl_sciane(scr, this->kolor, Mt, Mo, Mr);

  else

  for (j=5;j>=0;j--)
      sciana[j].wyswietl_sciane(scr, this->kolor, Mt, Mo, Mr);

}

