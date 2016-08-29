/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka scena_obiekty - definicja obiektow 3D na scenie
 *
 */

#ifndef SCENA_OBIEKTY_INCLUDED
#define SCENA_OBIEKTY_INCLUDED


#include "d3.h"
#include "d3_sciana.h"


using namespace std;


// ilosc obiektow na scenie
const int scena_obiekty_ilosc = 11;



// struktura opisujaca plaszczyzne do podzialu sceny
struct Scena_plaszczyzna {

  double A,B,C,D;
  // wyliczenie wspolczynnikow A,B,C,D np. 3 punktow
  void policz_wspolczynniki(D3_punkt p1, D3_punkt p2, D3_punkt p3);

};



// struktura opisujaca prostopadloscian na scenie
struct Scena_obiekt {

  D3_sciana sciany[6];
  D3_punkt wsp_osi;
  int nr;
  Uint32 kolor;

  void wyswietl_obiekt(SDL_Surface* scr, double *Mt, double *Mo, double *Mr);

};


// definicja obiektow na scenie
// (prostopadlosciany ustawione po dwoch stronach "ulicy")
const Scena_obiekt scena_obiekty[scena_obiekty_ilosc] = {

            {
              // wspolrzedne punktow 3D - krance kolejnych odcinkow
              sciany:   {

                              {{{ -1.0, -1.0, 1.0 }, { -1.0, -1.0, 2.5 }, { 1.0, -1.0, 2.5 }, { 1.0, -1.0, 1.0 }}},
                              {{{ -1.0, 1.0, 1.0 }, { -1.0, 1.0, 2.5 }, { -1.0, -1.0, 2.5 }, { -1.0, -1.0, 1.0 }}},
                              {{{ -1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 2.5 }, { -1.0, 1.0, 2.5 }}},
                              {{{ 1.0, -1.0, 1.0 }, { 1.0, -1.0, 2.5 }, { 1.0, 1.0, 2.5 }, { 1.0, 1.0, 1.0 }}},
                              {{{ -1.0, -1.0, 1.0 }, { -1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, -1.0, 1.0 }}},
                              {{{ 1.0, -1.0, 2.5 }, { 1.0, 1.0, 2.5 }, { -1.0, 1.0, 2.5 }, { -1.0, -1.0, 2.5 }}}

                        },
              // dodatkowe wspolczynniki do przeskalowania wspolrzednych dla poszczegolnych osi
              // dla tej strony zawsze jest z>0, dla drugiej zawsze z<0
              wsp_osi:   {  7.0, 5.0, 7.0 },
              nr: 1,
              // kolor bryly
              kolor: 0x87a0e3 },

            { sciany:   {

                              {{{ -2.0, -1.0, 1.0 }, { -2.0, -1.0, 1.9 }, { -1.1, -1.0, 1.9 }, { -1.1, -1.0, 1.0 }}},
                              {{{ -2.0, 2.1, 1.0 }, { -2.0, 2.1, 1.9 }, { -2.0, -1.0, 1.9 }, { -2.0, -1.0, 1.0 }}},
                              {{{ -2.0, 2.1, 1.0 }, { -1.1, 2.1, 1.0 }, { -1.1, 2.1, 1.9 }, { -2.0, 2.1, 1.9 }}},
                              {{{ -1.1, -1.0, 1.0 }, { -1.1, -1.0, 1.9 }, { -1.1, 2.1, 1.9 }, { -1.1, 2.1, 1.0 }}},
                              {{{ -2.0, -1.0, 1.0 }, { -2.0, 2.1, 1.0 }, { -1.1, 2.1, 1.0 }, { -1.1, -1.0, 1.0 }}},
                              {{{ -1.1, -1.0, 1.9 }, { -1.1, 2.1, 1.9 }, { -2.0, 2.1, 1.9 }, { -2.0, -1.0, 1.9 }}}

                        },
              wsp_osi:   {  9.0, 6.5, 12.0 },
              nr: 2,
              kolor: 0xdd5a59 },

            { sciany:   {

                              {{{ -3.5, -1.0, 1.0 }, { -3.5, -1.0, 1.5 }, { -2.6, -1.0, 1.5 }, { -2.6, -1.0, 1.0 }}},
                              {{{ -3.5, 0.4, 1.0 }, { -3.5, 0.4, 1.5 }, { -3.5, -1.0, 1.5 }, { -3.5, -1.0, 1.0 }}},
                              {{{ -3.5, 0.4, 1.0 }, { -2.6, 0.4, 1.0 }, { -2.6, 0.4, 1.5 }, { -3.5, 0.4, 1.5 }}},
                              {{{ -2.6, -1.0, 1.0 }, { -2.6, -1.0, 1.5 }, { -2.6, 0.4, 1.5 }, { -2.6, 0.4, 1.0 }}},
                              {{{ -3.5, -1.0, 1.0 }, { -3.5, 0.4, 1.0 }, { -2.6, 0.4, 1.0 }, { -2.6, -1.0, 1.0 }}},
                              {{{ -2.6, -1.0, 1.5 }, { -2.6, 0.4, 1.5 }, { -3.5, 0.4, 1.5 }, { -3.5, -1.0, 1.5 }}}

                        },

              wsp_osi:   {  8.0, 6.0, 8.5 },
              nr: 3,
              kolor: 0xbfbfbf },


            { sciany:  {

                              {{{ 1.0, -1.0, 1.0 }, { 1.0, -1.0, 2.3 }, { 3.5, -1.0, 2.3 }, { 3.5, -1.0, 1.0 }}},
                              {{{ 1.0, 2.0, 1.0 }, { 1.0, 2.0, 2.3 }, { 1.0, -1.0, 2.3 }, { 1.0, -1.0, 1.0 }}},
                              {{{ 1.0, 2.0, 1.0 }, { 3.5, 2.0, 1.0 }, { 3.5, 2.0, 2.3 }, { 1.0, 2.0, 2.3 }}},
                              {{{ 3.5, -1.0, 1.0 }, { 3.5, -1.0, 2.3 }, { 3.5, 2.0, 2.3 }, { 3.5, 2.0, 1.0 }}},
                              {{{ 1.0, -1.0, 1.0 }, { 1.0, 2.0, 1.0 }, { 3.5, 2.0, 1.0 }, { 3.5, -1.0, 1.0 }}},
                              {{{ 3.5, -1.0, 2.3 }, { 3.5, 2.0, 2.3 }, { 1.0, 2.0, 2.3 }, { 1.0, -1.0, 2.3 }}}

                        },
              wsp_osi:   {  8.0, 5.5, 8.0 },
              nr: 4,
              kolor: 0xffff55 },

            { sciany:   {
                              {{{ 3.7, -1.0, 1.0 }, { 3.7, -1.0, 2.3 }, { 4.8, -1.0, 2.3 }, { 4.8, -1.0, 1.0 }}},
                              {{{ 3.7, 2.0, 1.0 }, { 3.7, 2.0, 2.3 }, { 3.7, -1.0, 2.3 }, { 3.7, -1.0, 1.0 }}},
                              {{{ 3.7, 2.0, 1.0 }, { 4.8, 2.0, 1.0 }, { 4.8, 2.0, 2.3 }, { 3.7, 2.0, 2.3 }}},
                              {{{ 4.8, -1.0, 1.0 }, { 4.8, -1.0, 2.3 }, { 4.8, 2.0, 2.3 }, { 4.8, 2.0, 1.0 }}},
                              {{{ 3.7, -1.0, 1.0 }, { 3.7, 2.0, 1.0 }, { 4.8, 2.0, 1.0 }, { 4.8, -1.0, 1.0 }}},
                              {{{ 4.8, -1.0, 2.3 }, { 4.8, 2.0, 2.3 }, { 3.7, 2.0, 2.3 }, { 3.7, -1.0, 2.3 }}}
                        },
              wsp_osi:   {  8.5, 6.3, 7.5 },
              nr: 5,
              kolor: 0xff782e },


            { sciany:   {
                              {{{ -2.5, -1.0, 1.0 }, { -2.5, -1.0, 2.5 }, { -1.6, -1.0, 2.5 }, { -1.6, -1.0, 1.0 }}},
                              {{{ -2.5, 1.6, 1.0 }, { -2.5, 1.6, 2.5 }, { -2.5, -1.0, 2.5 }, { -2.5, -1.0, 1.0 }}},
                              {{{ -2.5, 1.6, 1.0 }, { -1.6, 1.6, 1.0 }, { -1.6, 1.6, 2.5 }, { -2.5, 1.6, 2.5 }}},
                              {{{ -1.6, -1.0, 1.0 }, { -1.6, -1.0, 2.5 }, { -1.6, 1.6, 2.5 }, { -1.6, 1.6, 1.0 }}},
                              {{{ -2.5, -1.0, 1.0 }, { -2.5, 1.6, 1.0 }, { -1.6, 1.6, 1.0 }, { -1.6, -1.0, 1.0 }}},
                              {{{ -1.6, -1.0, 2.5 }, { -1.6, 1.6, 2.5 }, { -2.5, 1.6, 2.5 }, { -2.5, -1.0, 2.5 }}}
                        },
              wsp_osi:   {  18.0, 5.0, 7.0 },
              nr: 6,
              kolor: 0xcc0000 },


             //  teraz prostopadlosciany po drugiej stronie "ulicy"

            { sciany:   {
                              {{{ -1.0, -1.0, 1.0 }, { -1.0, -1.0, 1.4 }, { 1.0, -1.0, 1.4 }, { 1.0, -1.0, 1.0 }}},
                              {{{ -1.0, 1.0, 1.0 }, { -1.0, 1.0, 1.4 }, { -1.0, -1.0, 1.4 }, { -1.0, -1.0, 1.0 }}},
                              {{{ -1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.4 }, { -1.0, 1.0, 1.4 }}},
                              {{{ 1.0, -1.0, 1.0 }, { 1.0, -1.0, 1.4 }, { 1.0, 1.0, 1.4 }, { 1.0, 1.0, 1.0 }}},
                              {{{ -1.0, -1.0, 1.0 }, { -1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, -1.0, 1.0 }}},
                              {{{ 1.0, -1.0, 1.4 }, { 1.0, 1.0, 1.4 }, { -1.0, 1.0, 1.4 }, { -1.0, -1.0, 1.4 }}}
                        },
              wsp_osi:   {  4.0, 4.5, -19.0 },
              nr: 7,
              kolor: 0xaa00ff },


            { sciany:   {
                              {{{ -2.0, -1.0, 1.0 }, { -2.0, -1.0, 1.5 }, { -1.1, -1.0, 1.5 }, { -1.1, -1.0, 1.0 }}},
                              {{{ -2.0, 3.4, 1.0 }, { -2.0, 3.4, 1.5 }, { -2.0, -1.0, 1.5 }, { -2.0, -1.0, 1.0 }}},
                              {{{ -2.0, 3.4, 1.0 }, { -1.1, 3.4, 1.0 }, { -1.1, 3.4, 1.5 }, { -2.0, 3.4, 1.5 }}},
                              {{{ -1.1, -1.0, 1.0 }, { -1.1, -1.0, 1.5 }, { -1.1, 3.4, 1.5 }, { -1.1, 3.4, 1.0 }}},
                              {{{ -2.0, -1.0, 1.0 }, { -2.0, 3.4, 1.0 }, { -1.1, 3.4, 1.0 }, { -1.1, -1.0, 1.0 }}},
                              {{{ -1.1, -1.0, 1.5 }, { -1.1, 3.4, 1.5 }, { -2.0, 3.4, 1.5 }, { -2.0, -1.0, 1.5 }}}
                        },
              wsp_osi:   {  7.0, 5.0, -19.5 },
              nr: 8,
              kolor: 0xcc9900 },


            { sciany:   {
                              {{{ 1.3, -1.0, 1.0 }, { 1.3, -1.0, 1.5 }, { 1.9, -1.0, 1.5 }, { 1.9, -1.0, 1.0 }}},
                              {{{ 1.3, 1.9, 1.0 }, { 1.3, 1.9, 1.5 }, { 1.3, -1.0, 1.5 }, { 1.3, -1.0, 1.0 }}},
                              {{{ 1.3, 1.9, 1.0 }, { 1.9, 1.9, 1.0 }, { 1.9, 1.9, 1.5 }, { 1.3, 1.9, 1.5 }}},
                              {{{ 1.9, -1.0, 1.0 }, { 1.9, -1.0, 1.5 }, { 1.9, 1.9, 1.5 }, { 1.9, 1.9, 1.0 }}},
                              {{{ 1.3, -1.0, 1.0 }, { 1.3, 1.9, 1.0 }, { 1.9, 1.9, 1.0 }, { 1.9, -1.0, 1.0 }}},
                              {{{ 1.9, -1.0, 1.5 }, { 1.9, 1.9, 1.5 }, { 1.3, 1.9, 1.5 }, { 1.3, -1.0, 1.5 }}}
                        },
              wsp_osi:   {  6.0, 5.0, -18.5 },
              nr: 9,
              kolor: 0x59cfdd },

            { sciany:   {
                              {{{ 2.2, -1.4, 1.0 }, { 2.2, -1.4, 1.5 }, { 3.1, -1.4, 1.5 }, { 3.1, -1.4, 1.0 }}},
                              {{{ 2.2, 1.0, 1.0 }, { 2.2, 1.0, 1.5 }, { 2.2, -1.4, 1.5 }, { 2.2, -1.4, 1.0 }}},
                              {{{ 2.2, 1.0, 1.0 }, { 3.1, 1.0, 1.0 }, { 3.1, 1.0, 1.5 }, { 2.2, 1.0, 1.5 }}},
                              {{{ 3.1, -1.4, 1.0 }, { 3.1, -1.4, 1.5 }, { 3.1, 1.0, 1.5 }, { 3.1, 1.0, 1.0 }}},
                              {{{ 2.2, -1.4, 1.0 }, { 2.2, 1.0, 1.0 }, { 3.1, 1.0, 1.0 }, { 3.1, -1.4, 1.0 }}},
                              {{{ 3.1, -1.4, 1.5 }, { 3.1, 1.0, 1.5 }, { 2.2, 1.0, 1.5 }, { 2.2, -1.4, 1.5 }}}
                        },
              wsp_osi:   {  6.0, 3.0, -18.7 },
              nr: 10,
              kolor: 0x009010 },


            { sciany:   {
                              {{{ -2.5, -1.5, 1.1 }, { -2.5, -1.5, 1.4 }, { -1.6, -1.5, 1.4 }, { -1.6, -1.5, 1.1 }}},
                              {{{ -2.5, 1.0, 1.1 }, { -2.5, 1.0, 1.4 }, { -2.5, -1.5, 1.4 }, { -2.5, -1.5, 1.1 }}},
                              {{{ -2.5, 1.0, 1.1 }, { -1.6, 1.0, 1.1 }, { -1.6, 1.0, 1.4 }, { -2.5, 1.0, 1.4 }}},
                              {{{ -1.6, -1.5, 1.1 }, { -1.6, -1.5, 1.4 }, { -1.6, 1.0, 1.4 }, { -1.6, 1.0, 1.1 }}},
                              {{{ -2.5, -1.5, 1.1 }, { -2.5, 1.0, 1.1 }, { -1.6, 1.0, 1.1 }, { -1.6, -1.5, 1.1 }}},
                              {{{ -1.6, -1.5, 1.4 }, { -1.6, 1.0, 1.4 }, { -2.5, 1.0, 1.4 }, { -2.5, -1.5, 1.4 }}}
                        },
              wsp_osi:   {  12.0, 4.5, -21.0 },
              nr: 11,
              kolor: 0x88aa00 }


        };


#endif
