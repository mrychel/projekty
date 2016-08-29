/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka gui - graficzny interfejs uzytkownika
 *
 */

#ifndef GUI_INCLUDED
#define GUI_INCLUDED

#include <SDL_ttf.h>

#include "d3.h"
#include "kwaterniony.h"

using namespace std;


// definicje kolorow (hex)
const SDL_Color k_white = { 0xFF, 0xFF, 0xFF };
const SDL_Color k_gray1 = { 0xff, 0x60, 0x60 };
const SDL_Color k_gray2 = { 0x77, 0x77, 0x77 };
const SDL_Color k_gray3 = { 0x55, 0x55, 0x55 };
const SDL_Color k_gray4 = { 0xE0, 0xE0, 0xE0 };
const SDL_Color k_red   = { 0x73, 0x00, 0x00 };

// caly ekran aplikacji
extern SDL_Surface* screen;

// czcionki uzywane w aplikacji
extern TTF_Font *gui_font;
extern TTF_Font *gui_font_m;


// wypisanie parametrow przeksztalcen
void gui_parametry(double d, Kwaternion q, D3_punkt t);
// inicjalizacja i przygotowanie interfejsu graficznego
void gui_init();
// zamkniecie trybu graficznego
void gui_stop();

#endif
