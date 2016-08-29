/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka kwaterniony - czesciowa implementacja kwaternionow
 *
 */


#ifndef KWATERNIONY_INCLUDED
#define KWATERNIONY_INCLUDED


#include "d3.h"


using namespace std;


// wspolrzedne kwaterniona
struct Kwaternion {

  double s, x, y, z;

};


// katy Eulera obrotu
struct Euler {

  double x,y,z;

};


// macierz obrotu z reprezentacji kwaternionowej
double* kwaternion_macierz_obrot_xyz(Kwaternion obr);

// normalizacja kwaterniona
void kwaternion_normalizacja(Kwaternion &q);

// obrot wokol osi Ox, Oy, Oz
void kwaternion_obrotX(Kwaternion &q, double fi);
void kwaternion_obrotY(Kwaternion &q, double fi);
void kwaternion_obrotZ(Kwaternion &q, double fi);
void kwaternion_obrot(Kwaternion &q, Euler fi, double fi_c);
// obrot wektora przy pomocy kwaterniona
void kwaternion_obrot_wektor(Kwaternion q, D3_punkt &w);

// przejscie z katow Eulera do przestrzeni kwaternionow
Kwaternion kwaternion_przejscie_k(double fi_x, double fi_y, double fi_z);

// powrot z przestrzeni kwaternionow  do katow Eulera
Euler kwaternion_powrot_e(Kwaternion q);

Kwaternion kwaternion_odwrotny(Kwaternion q);



#endif

