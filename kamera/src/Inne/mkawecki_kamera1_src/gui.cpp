/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka ekran - graficzny interfejs uzytkownika
 *
 */

#include <SDL.h>
#include <SDL_image.h>
#include <SDL_ttf.h>
#include <sstream>
#include <iomanip>
#include <cmath>

#include "sdl_plus.h"
#include "gui.h"
#include "kamera.h"
#include "d3.h"
#include "kwaterniony.h"


using namespace std;

// wskaznik do podstawowej warstwy - caly ekran aplikacji
SDL_Surface* screen;
// wskaznik do warstwy przeznaczonej na wyswietlenie parametrow
SDL_Surface* screen_par;

// zaladowane czcionki
TTF_Font *gui_font = NULL;
TTF_Font *gui_font_m = NULL;

#include <iostream>

/*
Funkcja inicjalizuje tryb graficzny i przygotowuje interfejs graficzny aplikacji
Parametry wejsciowe:
skala - skala osi Y
nr_filtra - nr filtra [1-3]
*/
void gui_init() {


  screen = SDL_SetVideoMode(800, 600, 32, SDL_SWSURFACE | SDL_DOUBLEBUF);

  SDL_EnableKeyRepeat(SDL_DEFAULT_REPEAT_DELAY, SDL_DEFAULT_REPEAT_INTERVAL);
  SDL_EnableUNICODE(1);

  // etykieta i ikona okna
  SDL_WM_SetCaption("Wirtualna kamera", "");
  SDL_Surface *ikona = sdlp_img_load("images/kamera.bmp");
  Uint32 kolorK = SDL_MapRGB( ikona->format, 255, 0, 255 );
  SDL_SetColorKey(ikona, SDL_SRCCOLORKEY, kolorK);
  SDL_WM_SetIcon(ikona, NULL );
  SDL_FreeSurface(ikona);

  // zaladowanie czcionek
  if( TTF_Init() == -1 ) { exit(EXIT_FAILURE); }
  gui_font = TTF_OpenFont("fonts/LiberationSans-Regular.ttf", 12);
  gui_font_m = TTF_OpenFont("fonts/LiberationSans-Regular.ttf", 9);
  if (gui_font==NULL || gui_font_m==NULL)  exit(EXIT_FAILURE);

  SDL_Surface *temp = sdlp_img_load("images/layout.jpg");
  sdlp_apply_surface(0, 0, temp, screen);
  SDL_FreeSurface(temp);

  sdlp_out_text(screen, 350, 15, "A  / D", k_gray4);  sdlp_out_text(screen, 380, 15, " translacja OX", k_gray2);
  sdlp_out_text(screen, 350, 35, "W / S", k_gray4);  sdlp_out_text(screen, 380, 35, " translacja OY", k_gray2);
  sdlp_out_text(screen, 350, 55, "Q / E", k_gray4);  sdlp_out_text(screen, 380, 55, " translacja OZ", k_gray2);

  sdlp_out_text(screen, 500, 15, "Ctrl + A  / D", k_gray4);  sdlp_out_text(screen, 563, 15, " obrót OY", k_gray2);
  sdlp_out_text(screen, 500, 35, "Ctrl + W / S", k_gray4);  sdlp_out_text(screen, 563, 35, " obrót OX", k_gray2);
  sdlp_out_text(screen, 500, 55, "Ctrl + Q / E", k_gray4);  sdlp_out_text(screen, 563, 55, " obrót OZ", k_gray2);

  sdlp_out_text(screen, 650, 15, "PgUp / PgDn", k_gray4);  sdlp_out_text(screen, 725, 15, "  zoom", k_gray2);
  sdlp_out_text(screen, 658, 35, "Backspace", k_gray4);   sdlp_out_text(screen, 725, 35, "  reset", k_gray2);
  sdlp_out_text(screen, 694, 55, "ESC", k_gray4);  sdlp_out_text(screen, 725, 55, " koniec", k_gray2);

  // zapamietanie pola pod lista parametrow
  SDL_Rect r;
  r.x = 19;  r.y = 574;
  r.w = 770; r.h = 25;

  screen_par = sdlp_create_surface(770, 25, screen);
  sdlp_apply_surface(0, 0, screen, screen_par, &r);


  SDL_Flip(screen);

}


/*
Wypisanie parametrow przeksztalcen
Parametry wejsciowe:
d - odleglosc rzutni
fi_x, fi_y, fi_z - kat obrotu w osi Ox, Oy, Oz
t_x, t_y, t_z - wartosc translacji w osi Ox, Oy, Oz
*/
void gui_parametry(double d, Kwaternion q, D3_punkt t) {

  sdlp_apply_surface(19, 574, screen_par, screen);

  string tab = "    ";

  Euler fi = kwaternion_powrot_e(q);


  { stringstream oss;
    oss  << "Parametry:  d = " << fixed << setprecision(2) << d << tab << "φx  = " << fi.x/M_PI << (fi.x!=0 ? " π" : " ")
         << tab << "φy = " << fi.y/M_PI << (fi.y!=0 ? " π" : " ")  << tab << "φz  = " << fi.z/M_PI << (fi.z!=0 ? " π" : " ")
         << tab << "Tx  = " << t.x << tab << "Ty  = " << t.y << tab << "Tz  = " << t.z;
    sdlp_out_text(screen, 20, 575, oss.str(), k_gray3);

  }

  stringstream oss;
  oss << (kamera_pokaz_wsp_pkt ? "ukryj" : "pokaż") << " współrzędne pkt";

  sdlp_out_text(screen, 630, 575, "INS", k_gray4);
  sdlp_out_text(screen, 655, 575, oss.str(), k_gray2);


}




/*
Zwolnienie pamieci i zamkniecie trybu graficznego
*/
void gui_stop() {

  TTF_CloseFont(gui_font);
  TTF_CloseFont(gui_font_m);
  TTF_Quit();

  SDL_FreeSurface(screen_par);

  SDL_Quit();

}

