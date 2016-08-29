/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Program glowny
 *
 */


#include <SDL.h>
#include <cmath>
#include <iostream>

#include "obserwator.h"
#include "gui.h"
#include "kwaterniony.h"
#include "scena.h"
#include "drzewo.h"
#include "kamera.h"
#include "config.h"


using namespace std;


// sprawdzenie stanu klawiatury i podjecie odp. akcji
bool interakcja(bool &koniec);
// odswiezenie widoku kamery
void rysuj();
// czyszczenie pamieci na wyjsciu
void zakonczenie();


// drzewo BSP - sciany obiektow sceny
Drzewo_wezel* korzen = NULL;



int main(int argc, char **argv) {

  bool koniec = false;

  scena_zbuduj_drzewo(korzen);


  if(!SDL_Init(SDL_INIT_EVERYTHING)) {

    atexit(zakonczenie);

    gui_init();
    rysuj();

    // glowna petla programu
    do {

        // sprawdzenie stanu klawiszy, w razie potrzeby odwiezenie rysunku
        if (interakcja(koniec)) rysuj();

    } while (!koniec);


  }

  else cerr << SDL_GetError() << endl;


  return 0;


}



/*
Funkcja sprawdza stan klawiatury i podejmuje odp.akcje
Parametry wejsciowe:
koniec - referencja do informacji o zakonczeniu programu
Zwracana wartosc: true jezeli konieczne jest odswiezenie ekranu,
false w przeciwnym wypadku
*/
bool interakcja(bool &koniec) {

   SDL_Event event;
   bool zmiana = false;


   if (SDL_PollEvent(&event)) {

     koniec = (event.type == SDL_QUIT);

     int mod = SDL_GetModState();

     switch (event.type) {

        default: break;

        // sprawdzenie wcisnietych klawiszy
        case SDL_KEYDOWN:

          switch (event.key.keysym.sym) {

             default:  break;

             case SDLK_ESCAPE:

                koniec = true;
                break;


             // zoom - zmiana ogniskowej
             case SDLK_PAGEDOWN:

                zmiana = kamera.zoom(-KROK_D);
                break;

             case SDLK_PAGEUP:

                zmiana = kamera.zoom(KROK_D);
                break;


             case SDLK_a:

                // jezeli wcisniety Ctrl, to obrot wokol osi OY
                if ((mod & KMOD_CTRL) != 0)  zmiana = obserwator.obrotY(KROK_FI);
                // translacja OX
                else zmiana = obserwator.translacjaX(KROK_T);

                break;

             case SDLK_d:

                // jezeli wcisniety Ctrl, to obrot wokol osi OY
                if ((mod & KMOD_CTRL) != 0) zmiana = obserwator.obrotY(-KROK_FI);
                // translacja OX
                else zmiana = obserwator.translacjaX(-KROK_T);

                break;


             case SDLK_w:

                // jezeli wcisniety Ctrl, to obrot wokol osi OX
                if ((mod & KMOD_CTRL) != 0)  zmiana = obserwator.obrotX(KROK_FI);
                // translacja OY
                else  zmiana = obserwator.translacjaY(KROK_T);

                break;

             case SDLK_s:

                // jezeli wcisniety Ctrl, to obrot wokol osi OX
                if ((mod & KMOD_CTRL) != 0)  zmiana = obserwator.obrotX(-KROK_FI);
                // translacja OY
                else  zmiana = obserwator.translacjaY(-KROK_T);

                break;


             case SDLK_q:

                // jezeli wcisniety Ctrl, to obrot OZ
                if ((mod & KMOD_CTRL) != 0)  zmiana = obserwator.obrotZ(KROK_FI);
                // translacja OZ
                else zmiana = obserwator.translacjaZ(KROK_T);

                break;


             case SDLK_e:

                // jezeli wcisniety Ctrl, to obrot OZ
                if ((mod & KMOD_CTRL) != 0)  zmiana = obserwator.obrotZ(-KROK_FI);
                // translacja OZ
                else  zmiana = obserwator.translacjaZ(-KROK_T);

                break;


             // reset
             case SDLK_BACKSPACE:

                obserwator.inicjalizuj();
                kamera.inicjalizuj();
                zmiana = true;

                break;

             // zmiana wyswietlania wspolrzednych pkt
             case SDLK_INSERT:

                kamera.pokaz_wsp_pkt = !kamera.pokaz_wsp_pkt;
                zmiana = true;
                break;


          }

          break;

     }

   }


   return zmiana;

}


/*
Odswiezenie widoku kamery i wypisanie parametrow
*/
void rysuj() {

  gui_parametry();

  kamera.wyswietl_widok(korzen);

  SDL_Flip(screen);

}


/*
Czyszczenie pamieci na wyjsciu
*/
void zakonczenie() {

  drzewo_usun(korzen);

  gui_stop();

}

