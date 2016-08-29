/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka SDL_plus - funkcje pomocniczne dla operacji w SDL
 *
 */

#ifndef SDL_PLUS_INCLUDED
#define SDL_PLUS_INCLUDED

#include <sstream>
#include <SDL.h>


using namespace std;

// zapalenie 1 piksela
void sdlp_put_pixel(SDL_Surface * screen, Sint32 x, Sint32 y, Uint32 color);
// zwrocenie koloru 1 piksela
Uint32 sdlp_get_pixel(SDL_Surface* screen, Sint32 x, Sint32 y);

// przygotowanie nowej warstwy obrazu
SDL_Surface* sdlp_create_surface(int width, int height, const SDL_Surface* display);
// nalozenie warstw obrazu
void sdlp_apply_surface( int x, int y, SDL_Surface* source, SDL_Surface* destination, SDL_Rect* clip = NULL);
// wykreslenie tekstu
void sdlp_out_text(SDL_Surface *scr, int x, int y, string oss, SDL_Color kolor, bool fmaly = false);
// zaladowanie obrazka do warstwy
SDL_Surface* sdlp_img_load(const char* fname);

#endif

