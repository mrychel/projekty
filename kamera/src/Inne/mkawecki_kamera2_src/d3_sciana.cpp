/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka d3_sciana - struktura sciany obiektu 3D
 *
 */


#include <cmath>
#include <iostream>

#include "d2.h"
#include "d3.h"
#include "scena.h"
#include "kamera.h"


using namespace std;



// wyznaczenie wspolczynnikow plaszczyzny przechodzacej przez 3 wierzcholki sciany
void D3_sciana::policz_wspolczynniki() {

  double x1=this->wierzcholki[0].x, x2=this->wierzcholki[1].x, x3=this->wierzcholki[2].x,
          y1=this->wierzcholki[0].y, y2=this->wierzcholki[1].y, y3=this->wierzcholki[2].y,
          z1=this->wierzcholki[0].z, z2=this->wierzcholki[1].z, z3=this->wierzcholki[2].z;

  this->A = y1*z2 - y1*z3 - y2*z1 + y2*z3 + y3*z1 - y3*z2;
  this->B = -x1*z2 + x1*z3 + x2*z1 - x2*z3 - x3*z1 + x3*z2;
  this->C = x1*y2 - x1*y3 - x2*y1 + x2*y3 + x3*y1 - x3*y2;
  this->D = -x1*y2*z3 + x1*y3*z2 + x2*y1*z3 - x2*y3*z1 - x3*y1*z2 + x3*y2*z1;

}

// odleglosc sciany (plaszczyzny) od punktu
double D3_sciana::odleglosc_pkt(D3_punkt p) {

   return (this->A*p.x + this->B*p.y + this->C*p.z + this->D)
          / sqrt(this->A*this->A+this->B*this->B+this->C*this->C);

}


/*
Metoda struktury D3_sciana - rysowanie sciany
Wykonanie przeksztalcen punktu 3D, rzutowanie, przeksztalcenia 2D, wykreslenie odcinka
Parametry wejsciowe:
scr - wskaznik do tymczasowej warstwy ekranu
kolor - kolor obiektu sceny
Mt - gotowa macierz translacji (tablica dynamiczna)
Mo - gotowa macierz obrotow (tablica dynamiczna)
Mr - gotowa macierz rzutowania (tablica dynamiczna)
*/

void D3_sciana::wyswietl_sciane(SDL_Surface* scr, Uint32 kolor, double *Mt, double *Mo, double *Mr) {

  int k;
  D2_punkt sc[4];

  for (k=0;k<4;k+=2) {

     // pobranie wspolrzednych krancow kolejnego odcinka
     D3_punkt pkt1 = this->wierzcholki[k];
     D3_punkt pkt2 = this->wierzcholki[k+1];

     // najpierw translacje
     pkt1 = d3_pomnoz_normalizuj_pkt(pkt1, Mt);
     pkt2 = d3_pomnoz_normalizuj_pkt(pkt2, Mt);

     // obroty juz po przesunieciu (w nowym odniesieniu)
     pkt1 = d3_pomnoz_normalizuj_pkt(pkt1, Mo);
     pkt2 = d3_pomnoz_normalizuj_pkt(pkt2, Mo);


     // obciecie odcinkow w 3D w nieco szerszym planie (zmod. alg. Cohena-Sutherlanda)
     // przy okazji wyeliminowanie niewidocznych elementow)
     // sprawdzenie czy punkt miesci sie w rozszerzonym planie 3D
     if (d3_obciecie_cs(scena_obciecie_wierzcholki[0], scena_obciecie_wierzcholki[1], pkt1, pkt2)) {

        // rzutowanie i konwersja typu na punkt 2D
        D2_punkt p1 = D2_punkt(d3_pomnoz_normalizuj_pkt(pkt1, Mr));
        D2_punkt p2 = D2_punkt(d3_pomnoz_normalizuj_pkt(pkt2, Mr));

        // przeksztalcenie wspolrzednych (po rzutowaniu i normalizacji) do widoku kamery
        kamera_przelicz_wsp_pkt(p1, scr->w, scr->h);
        kamera_przelicz_wsp_pkt(p2, scr->w, scr->h);

        // zapamietanie wspolrzednych wierzcholkow sciany
        sc[k].x = p1.x;  sc[k].y = p1.y;
        sc[k+1].x = p2.x;  sc[k+1].y = p2.y;


        // ewentualne opisanie wspolrzednych punktow
        if (kamera.pokaz_wsp_pkt) {

           kamera_widok_wypisz_wsp(scr, this->wierzcholki[k], p1);
           kamera_widok_wypisz_wsp(scr, this->wierzcholki[k+1], p2);

        }
      }

      // jezeli jakis wierzcholek nie miesci sie w szerokim planie 3D - nie rysujemy sciany w ogole
      else return;

   }

   // rysowanie wypelnionego czworokata - sciany, kazda sciana ma byc w innym kolorze
   d2_wypelnienie_wielokata(scr, sc, 4, kolor*pow(1.2, this->nr_sciany-1));

   SDL_Flip(scr);

}



