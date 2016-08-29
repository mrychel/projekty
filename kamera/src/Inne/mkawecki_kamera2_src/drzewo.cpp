/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka drzewo - implementacja drzewa BSP
 */

#include <SDL.h>

#include "drzewo.h"
#include "scena.h"
#include "obserwator.h"



using namespace std;


/*
Konstruktor wezla drzewa dla danego obiektu
*/
Drzewo_wezel::Drzewo_wezel(Scena_obiekt obiekt) {

    this->obiekt = obiekt;
    this->przod = NULL;
    this->tyl = NULL;
    this->sektor = true;

}

/*
Konstruktor wezla drzewa dla danej plaszczyzny p
*/
Drzewo_wezel::Drzewo_wezel(Scena_plaszczyzna p) {

    this->plaszczyzna = p;
    this->przod = NULL;
    this->tyl = NULL;
    this->sektor = false;

}


/*
Wyswietlenie scian (wezlow drzewa) w porzadku INORDER (z uwzglednieniem pozycji obserwatora), rekurencja
Parametr wejsciowy:
w - wskaznik do wezla od ktorego zaczyna sie wyswietlanie
scr - wskaznik do tymczasowej warstwy obrazu
Mt - macierz translacji (tablica dynamiczna)
Mo - macierz obrotow (tab.dyn.)
Mr - macierz rzutowania
*/
void drzewo_wyswietl(Drzewo_wezel *w, SDL_Surface* scr, double *Mt, double *Mo, double *Mr)   {

  // porzadek INORDER
  if (w!=NULL)  {

    bool przod = w->sektor ?
            (w->obiekt.sciany[3].A*obserwator.t.x + w->obiekt.sciany[3].B*obserwator.t.y
             + w->obiekt.sciany[3].C*obserwator.t.z < w->obiekt.sciany[3].D) :
            (w->plaszczyzna.A*obserwator.t.x + w->plaszczyzna.B*obserwator.t.y
             + w->plaszczyzna.C*obserwator.t.z<w->plaszczyzna.D);

     // obserwator z przodu plaszczyzny
     if (przod) {

       drzewo_wyswietl(w->przod, scr, Mt, Mo, Mr);
       if (w->sektor) w->obiekt.wyswietl_obiekt(scr, Mt, Mo, Mr);
       drzewo_wyswietl(w->tyl, scr, Mt, Mo, Mr);

     }

     // obserwator z tylu plaszczyzny
     else {

       drzewo_wyswietl(w->tyl, scr, Mt, Mo, Mr);
       if (w->sektor) w->obiekt.wyswietl_obiekt(scr, Mt, Mo, Mr);
       drzewo_wyswietl(w->przod, scr, Mt, Mo, Mr);

     }

  }

}



/*
Usuniecie calego drzewa, rekurencja
Parametr wejsciowy:
w - referencja do wskaznika do korzenia drzewa
*/
void drzewo_usun(Drzewo_wezel* &w) {

   Drzewo_wezel *tmp;
   if (w!=NULL)  {

       drzewo_usun(w->tyl);
       drzewo_usun(w->przod);
       tmp=w; w=NULL;
       delete tmp;

   }

}




