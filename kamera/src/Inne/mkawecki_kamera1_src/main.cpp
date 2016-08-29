/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Program glowny
 *
 */


#include <SDL.h>
#include <cmath>

#include "sdl_plus.h"
#include "gui.h"
#include "macierze.h"
#include "kwaterniony.h"
#include "scena.h"
#include "kamera.h"
#include "config.h"



using namespace std;

// sprawdzenie stanu klawiatury i odpowiedz
bool interakcja(bool &koniec);
// odswiezenie widoku
void rysuj();

// wykonanie translacji uwzgledniajace biezaca orientacje
bool translacja(double tx, double ty, double tz);
// wykonanie obrotu uwzgledniajace biezaca orientacje
void obrot(double fx, double fy, double fz, double f_c);


// odleglosc rzutni (ogniskowa)
double d = D_START;

// kwaternionowa reprezentacja obrotu
Kwaternion k_obrot;

// wspolrzedne translacji w ukladzie kamery
D3_punkt t = {0.0, 0.0, 0.0};



int main(int argc, char **argv) {


  bool koniec = false;

  k_obrot = kwaternion_przejscie_k(FI_START, FI_START, FI_START);
  translacja(T_START, T_START, T_Z_START);
  obrot(FI_START, FI_START, FI_START, FI_START);

  if(!SDL_Init(SDL_INIT_EVERYTHING)) {

    atexit(gui_stop);

    gui_init();
    rysuj();

    // glowna petla programu
    do {

        // sprawdzenie stanu klawiszy, w razie potrzeby odwiezenie rysunku
        if (interakcja(koniec)) rysuj();

    } while (!koniec);

  }


  return 0;


}


/*
Wykonanie obrotu (na kwaternionowej reprezentacji)
uwzgledniajace biezaca orientacje (definicja kierunkow)
Parametry wejsciowe:
fx, fy, fz - wspolrzedne wektora obrotu (katy Eulera)
f_c - kat obrotu w zadanej osi
*/
void obrot(double fx, double fy, double fz, double f_c) {

   if (f_c==0.0) return;

   D3_punkt tr = { fx, fy, fz };
   kwaternion_obrot_wektor(k_obrot, tr);
   Euler fi = { tr.x, tr.y, tr.z };

   kwaternion_obrot(k_obrot, fi, f_c);

}


/*
Translacje w osiach Ox, Oy, Oz
Parametr wejsciowy:
krok - kat Eulera obrotu w danej osi
*/
void obrotX(double krok) {

  obrot(krok, 0.0, 0.0, krok);

}

void obrotY(double krok) {

  obrot(0.0, krok, 0.0, krok);

}


void obrotZ(double krok) {

  obrot(0.0, 0.0, krok, krok);

}

/*
Wykonanie translacji uwzgledniajace biezaca orientacje
Zwracana wartosc: czy zostala wykonana zmiana (true / false)
Parametry wejsciowe:
tx, ty, tz - wspolrzedne wektora translacji
*/
bool translacja(double tx, double ty, double tz) {

   D3_punkt tr = { tx, ty, tz };
   kwaternion_obrot_wektor(k_obrot, tr);

   tx = t.x + tr.x;
   ty = t.y + tr.y;
   tz = t.z + tr.z;

   // sprawdzenie czy translacja nie przekracza ustalonego przedzialu
   if (((tr.x>=0 && tx<=MAX_T) || (tr.x<0 && tx>=MIN_T))
     && ((tr.y>=0 && ty<=MAX_T) || (tr.y<0 && ty>=MIN_T))
     && ((tr.z>=0 && tz<=MAX_T) || (tr.z<0 && tz>=MIN_T))) {

      t.x = tx;  t.y = ty;  t.z = tz;

      return true;

   }

   return false;

}



/*
Translacje w osiach Ox, Oy, Oz
Parametr wejsciowy:
krok - wartosc translacji w danej osi
*/
bool translacjaX(double krok) {

  return translacja(krok, 0.0, 0.0);

}


bool translacjaY(double krok) {

  return translacja(0.0, krok, 0.0);

}


bool translacjaZ(double krok) {

  return translacja(0.0, 0.0, krok);

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

             // zoom
             case SDLK_PAGEDOWN:

                if (d>MIN_D) {
                  d -= KROK_D;
                  zmiana = true;
                }
                break;

             case SDLK_PAGEUP:

                if (d<MAX_D) {
                  d += KROK_D;
                  zmiana = true;
                }
                break;


             case SDLK_a:

                // jezeli wcisniety Ctrl, to obrot wokol osi OY
                if ((mod & KMOD_CTRL) != 0) {

                  obrotY(KROK_FI);
                  zmiana = true;

                }
                // translacja OX
                else zmiana =translacjaX(KROK_T);

                break;

             case SDLK_d:

               // jezeli wcisniety Ctrl, to obrot wokol osi OY
                if ((mod & KMOD_CTRL) != 0) {

                  obrotY(-KROK_FI);
                  zmiana = true;

                }
                // translacja OX
                else zmiana = translacjaX(-KROK_T);

                break;


             case SDLK_w:

                // jezeli wcisniety Ctrl, to obrot wokol osi OX
                if ((mod & KMOD_CTRL) != 0)  {

                  obrotX(KROK_FI);
                  zmiana = true;

                }
                // translacja OY
                else  zmiana = translacjaY(KROK_T);

                break;

             case SDLK_s:

                 // jezeli wcisniety Ctrl, to obrot wokol osi OX
                if ((mod & KMOD_CTRL) != 0)  {

                  obrotX(-KROK_FI);
                  zmiana = true;

                }
                // translacja OY
                else  zmiana = translacjaY(-KROK_T);

                break;


             case SDLK_q:

                // jezeli wcisniety Ctrl, to obrot OZ
                if ((mod & KMOD_CTRL) != 0)  {

                   //kwaternion_obrotZ(k_obrot, KROK_FI);
                   obrotZ(KROK_FI);
                   zmiana = true;

                }
                // translacja OZ
                else zmiana = translacjaZ(KROK_T);

                break;


             case SDLK_e:

                // jezeli wcisniety Ctrl, to obrot OZ
                if ((mod & KMOD_CTRL) != 0) {

                   obrotZ(-KROK_FI);
                   zmiana = true;

                }
                // translacja OZ
                else  zmiana = translacjaZ(-KROK_T);


                break;


             // reset
             case SDLK_BACKSPACE:

                t.x = 0.0;  t.y = 0.0;  t.z = 0.0;
                d = D_START;
                k_obrot = kwaternion_przejscie_k(FI_START, FI_START, FI_START);
                translacja(T_START, T_START, T_Z_START);
                obrot(FI_START, FI_START, FI_START, FI_START);
                zmiana = true;
                break;

             // zmiana wyswietlania wspolrzednych pkt
             case SDLK_INSERT:

                kamera_pokaz_wsp_pkt = !kamera_pokaz_wsp_pkt;
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

  kwaternion_normalizacja(k_obrot);

  gui_parametry(d, k_obrot, t);

  kamera_widok(d, t, k_obrot);

  SDL_Flip(screen);

}

