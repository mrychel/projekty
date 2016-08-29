/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka scena - budowa sceny
 *
 */

#include <algorithm>
#include <cmath>

#include "scena.h"
#include "d3.h"
#include "obserwator.h"


using namespace std;


/* Funkcja okresla relacje pomiedzy scianami 3D
Zwracana wartosc: true jezeli sciana1 jest blizej obserwatora, false jezeli nie
Parametry wejsciowe:
sciana1, sciana2 - porownywane sciany
*/
bool scena_sciana_blizej(D3_sciana sciana1, D3_sciana sciana2) {

   // odwrocenie wspolrzednych  polozenia obserwatora
   D3_punkt t = obserwator.t;
   t.y = -t.y;
   t.z = -t.z;

   // dla scian wyznaczajacych rownolegle plaszczyzny porownanie odleglosci od obserwatora
   if (sciana1.A==sciana2.A && sciana1.B==sciana2.B && sciana1.C==-sciana2.C) {

      return sciana1.odleglosc_pkt(t) < sciana2.odleglosc_pkt(t);

   }

   // dla pozostalych scian analiza polozenia pierwszej sciany wzgledem obserwatora
  return sciana1.A*t.x+sciana1.B*t.y+sciana1.C*t.z < sciana1.D;


}



/*
Sortowanie scian - algorytm sortowania babelkowego
sortowanie tylko tablic 6-elementowych w wariancie "umiarkowanie optymistycznym" ...
Parametry wejsciowe:
sciana - tablica scian do posortowania
n - ilosc elementow
*/
void scena_sciany_sortuj(D3_sciana* sciana, int n) {

  int i,j;
  bool stop;

  for(j=n-1; j>0; j--) {

	stop = true;

	for(i=0; i<j; i++)
	 if (scena_sciana_blizej(sciana[i], sciana[i+1])) {

	   swap(sciana[i], sciana[i+1]);
	   stop = false;

     }

    if (stop) break;

  }

}




/*
Funkcja przygotowuje obiekty sceny np. stalych i dodaje je do drzewa BSP w ustalonej kolejnosci
Parametr wejsciowy:
w - referencja do wskaznika korzenia drzewa
*/
void scena_zbuduj_drzewo(Drzewo_wezel* &w) {

  int i,j;

  Scena_obiekt *ob = new Scena_obiekt[scena_obiekty_ilosc];

  // przygotowanie obiektow sceny, przeskalowanie wspolczynnikow
  for (i=0;i<scena_obiekty_ilosc;i++)  {

    ob[i] = scena_obiekty[i];
    D3_punkt wsp = {ob[i].wsp_osi.x, ob[i].wsp_osi.y, ob[i].wsp_osi.z};

    for (j=0;j<6;j++)  {

        ob[i].sciany[j] = d3_sciana_pkt_pomnoz(ob[i].sciany[j], wsp);
        ob[i].sciany[j].policz_wspolczynniki();
        ob[i].sciany[j].nr_sciany = j+1;

    }

  }

  // korzen drzewa to plaszczyzna - podzial wzdluz "ulicy"
  Scena_plaszczyzna pl;
  D3_punkt p1 = {0.0, 0.0, 0.0}, p2 = {1.0, 0.0, 0.0}, p3 = {1.0, 1.0, 0.0};
  pl.policz_wspolczynniki(p1, p2, p3);

  w = new Drzewo_wezel(pl);

  // teraz kolejne obiekty sceny (plaszczyzny podzialu wyznaczone przez tylne sciany obiektow)
  // "tyl" - na wprost poczatkowej orientacji obserwatora (tyl plaszczyzny pl)
  w->tyl = new Drzewo_wezel(ob[5]);
  w->tyl->przod = new Drzewo_wezel(ob[2]);
  w->tyl->przod->przod = new Drzewo_wezel(ob[1]);
  w->tyl->przod->przod->przod = new Drzewo_wezel(ob[0]);
  w->tyl->przod->przod->przod->przod = new Drzewo_wezel(ob[3]);
  w->tyl->przod->przod->przod->przod->przod = new Drzewo_wezel(ob[4]);
  // "przod - za poczatkowa orientacja obserwatora (przod plaszczyzny p1)
  w->przod = new Drzewo_wezel(ob[10]);
  w->przod->tyl = new Drzewo_wezel(ob[7]);
  w->przod->tyl->tyl = new Drzewo_wezel(ob[6]);
  w->przod->tyl->tyl->tyl = new Drzewo_wezel(ob[8]);
  w->przod->tyl->tyl->tyl->tyl = new Drzewo_wezel(ob[9]);

  delete []ob;


}




