/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka obserwator - pozycja obserwatora, zmiany pozycji
 *
 */

#ifndef OBSERWATOR_INCLUDED
#define OBSERWATOR_INCLUDED


#include "d3.h"
#include "kwaterniony.h"


using namespace std;

struct Obserwator {

  // wspolrzedne translacji w ukladzie kamery i w ukladzie sceny
  D3_punkt t;

  // kwaternionowa reprezentacja obrotu
  Kwaternion obrot;

  Obserwator();

  // inicjalizacja zmiennych
  void inicjalizuj();

  // Obroty wokol osi Ox, Oy, Oz
  bool obrotXYZ(double fx, double fy, double fz, double f_c);
  bool obrotX(double krok);
  bool obrotY(double krok);
  bool obrotZ(double krok);
  // Translacje w osiach Ox, Oy, Oz
  bool translacja(double tx, double ty, double tz);
  bool translacjaX(double krok);
  bool translacjaY(double krok);
  bool translacjaZ(double krok);

};

extern Obserwator obserwator;



#endif


