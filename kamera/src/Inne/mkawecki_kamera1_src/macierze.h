/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka macierze - implementacja potrzebnych operacji macierzowych
 *
 */

#ifndef MACIERZE_INCLUDED
#define MACIERZE_INCLUDED

#include "config.h"
#include "d3.h"


using namespace std;

// macierz jedynkowa
double* macierz_pt();

// macierz rzutu perspektywicznego (wersja 1)
double* macierz_rzut(double d);

// macierz translacji
double* macierz_translacja(double tx, double ty, double tz);

// operacja mnozenia macierzy przez punkt
double* macierz_mnozenie(double *A, D3_punkt p);

// NIEUZYWANE - macierz zlozonych obrotow wokol wszystkich osi
//double* macierz_obrot_xyz(double fi_x, double fi_y, double fi_z);

#endif
