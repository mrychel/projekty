/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka SDL_plus - funkcje pomocniczne dla operacji w SDL
 *
 */

#include <sstream>
#include <iostream>
#include <SDL.h>
#include <SDL_ttf.h>
#include <SDL_image.h>

#include "gui.h"


using namespace std;

/*
Zapalenie jednego piksela
Parametry wejsciowe:
screen - wskaznik do warstwy obrazu
x,y - wspolrzedne piksela
kolor - kolor piksela (HEX)
*/
void sdlp_put_pixel(SDL_Surface* screen, Sint32 x, Sint32 y, Uint32 color)
{
  * ((Uint32 *)screen->pixels + y * screen->w + x) = color;
}


Uint32 sdlp_get_pixel(SDL_Surface* screen, Sint32 x, Sint32 y) {

  return * ((Uint32 *)screen->pixels + y * screen->w + x);

}




/*
Funkcja przygotowuje nowa warstwe obrazu
Parametry wejsciowe:
width, height - dlugosc, szerokosc
display - warstwa na podstawie ktorej ustalany jest format wyswietlania
Zwracana wartosc: wskaznik do utworzonej warstwy
*/
SDL_Surface* sdlp_create_surface(int width, int height, const SDL_Surface* display) {

  const SDL_PixelFormat& fmt = *(display->format);
  return SDL_CreateRGBSurface(0, width, height, fmt.BitsPerPixel, fmt.Rmask,fmt.Gmask,fmt.Bmask,fmt.Amask );

}


/*
Funkcja naklada warstwe source na warstwe destination
Parametry wejsciowe:
source, destination - wskazniki do warstw
x, y - wspolrzedne gornych wierzcholkow miejsca nalozenia
clip - koordynaty obciecia nakladanej warstwy (opcjonalnie)
*/
void sdlp_apply_surface( int x, int y, SDL_Surface* source, SDL_Surface* destination, SDL_Rect* clip = NULL) {

    SDL_Rect offset;
    offset.x = x;
    offset.y = y;
    SDL_BlitSurface( source, clip, destination, &offset );

}



/*
Funkcja do wykreslania tekstu
Parametry wejsciowe:
scr - warstwa obrazu
x,y - wspolrzedne poczatku tekstu
oss - tekst do wykreslenia
kolor - kolor tekstu
fmaly - jezeli true, to bedzie uzyta mniejsza czcionka (opcjonalnie)
*/
void sdlp_out_text(SDL_Surface *scr, int x, int y, string oss, SDL_Color kolor, bool fmaly=false) {

    SDL_Surface* msg = TTF_RenderUTF8_Solid(fmaly?gui_font_m:gui_font, oss.c_str(), kolor);
    sdlp_apply_surface(x, y, msg, scr);

    SDL_FreeSurface(msg);

}


/*
Funkcja laduje obrazek z pliku do warstwy
Parametry wejsciowe:
fname - nazwa pliku
Zwracana wartosc - wskaznik do warstwy z zaladowanym obrazkiem
*/
SDL_Surface* sdlp_img_load(const char* fname) {

  SDL_Surface* temp = IMG_Load(fname);
  if (temp == NULL) {
      cerr << "Nie mozna wczytac obrazka: " << SDL_GetError() << endl;
      exit(EXIT_FAILURE);
  }

  return temp;

}



