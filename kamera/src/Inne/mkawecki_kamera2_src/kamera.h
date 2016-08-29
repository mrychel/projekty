/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka kamera - parametry przeksztalcen, budowa sceny i obrazu na kamerze
 *
 */


#ifndef KAMERA_INCLUDED
#define KAMERA_INCLUDED

#include "d2.h"
#include "drzewo.h"
#include "kwaterniony.h"


using namespace std;

struct Kamera {

  // ogniskowa
  double d;
  // czy pokazywac wspolrzedne wierzcholkow
  bool pokaz_wsp_pkt;

  Kamera();
  void inicjalizuj();
  // wyswietlenie gotowego obrazu
  void wyswietl_widok(Drzewo_wezel* w);
  // zmiana ogniskowej
  bool zoom(double krok);

};

extern Kamera kamera;


// przeksztalcenie wspolrzednych punktu do widoku,
void kamera_przelicz_wsp_pkt(D2_punkt &p, double szerokosc, double wysokosc);

// wypisanie obok punktu jego wspolrzednych w widoku kamery.
void kamera_widok_wypisz_wsp(SDL_Surface* scr, D3_punkt p_s, D2_punkt p_k);


#endif
