/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka kamera - wyswietlanie obrazu na kamerze
 *
 */

#include <SDL.h>
#include <sstream>

#include "kamera.h"
#include "gui.h"
#include "scena.h"
#include "d2.h"
#include "d3.h"
#include "sdl_plus.h"
#include "macierze.h"
#include "drzewo.h"
#include "obserwator.h"
#include "kwaterniony.h"
#include "config.h"


using namespace std;


Kamera kamera;

// konstruktor
Kamera::Kamera() {

  this->inicjalizuj();

}

/*
Inicjalizacja zmiennych - poczatkowe ustawienia
*/
void Kamera::inicjalizuj() {

  this->d = D_START;
  this->pokaz_wsp_pkt = WSP_PKT_START;

}



/*
Zmiana ogniskowej kamery
Zwraca true jezeli zmiana zostala wykonana
Parametr wejsciowy
krok - wartosc zmiany
*/
bool Kamera::zoom(double krok) {

   if ((krok<0.0 && d>MIN_D) || (krok>0.0 && d<MAX_D)) {

     this->d += krok;
     return true;

   }

   return false;

}



/*
Przygotowanie i wyswietlenie gotowego obrazu
Parametry wejsciowe:
w - wskaznik do korzenia drzewa
*/
void Kamera::wyswietl_widok(Drzewo_wezel* w) {

   // warstwa ekranu dla widoku kamery
   SDL_Surface* screen_tmp = sdlp_create_surface(757, 480, screen);

   // macierz translacji, odwrocony
   double *Mt = macierz_translacja(obserwator.t.x, -obserwator.t.y, obserwator.t.z);

   // macierz rzutowania
   double *Mr = macierz_rzut(this->d);

   // macierz obrotow
   double *Mo = kwaternion_macierz_obrot_xyz(obserwator.obrot);


   // wyswietlenie scian z drzewa BSP w porzadku in-order
   drzewo_wyswietl(w, screen_tmp, Mt, Mo, Mr);


   delete []Mt;
   delete []Mr;
   delete []Mo;


   // nalozenie gotowej warstwy na ekran
   sdlp_apply_surface(21, 86, screen_tmp, screen);
   // zwolnienie pamieci
   SDL_FreeSurface(screen_tmp);

}



/*
Przeksztalcenie wspolrzednych punktu w ukladzie kamery(po rzutowaniu, juz 2D) do widoku,
tj na wspolrzedne warstwy ekranu. Przesuniecie na srodek widoku.
Parametry wejsciowe:
p - referencja do punktu (uwzgledniane tylko wspolrzedne dla przestrzeni 2D)
szerokosc, wysokosc - szerokosc i wysokosc widoku kamery
*/
void kamera_przelicz_wsp_pkt(D2_punkt &p, double szerokosc, double wysokosc) {

    // zadane krancowe wspolrzedne w ukladzie kamery
    const double x_min = -1.0, x_max = 1.0, y_min = -1.0, y_max = 1.0;

    p.x = ((p.x - x_min) * szerokosc / (x_max - x_min));
    // odwrocenie kierunku w osi OY
    p.y = wysokosc - ((p.y - y_min) * wysokosc / (y_max - y_min));


}


/*
Wypisanie obok punktu jego wspolrzednych (w ukladzie swiata) w widoku kamery.
Parametry wejsciowe:
scr - wskaznik do warstwy ekranu
p_s - wspolrzedne punktu w ukladzie swiata (3D)
p_k - wspolrzedne punktu w widoku kamery (2D)
*/
void kamera_widok_wypisz_wsp(SDL_Surface* scr, D3_punkt p_s, D2_punkt p_k) {

  if (p_k.x<1 || p_k.y<1 || p_k.x>scr->w || p_k.y>scr->h)  return;

  stringstream oss;
  oss  << "(" << p_s.x << ";" << p_s.y << ";"
       << p_s.z << ")";
  sdlp_out_text(scr, p_k.x-3, p_k.y+3, oss.str(), k_gray3, true);

}




