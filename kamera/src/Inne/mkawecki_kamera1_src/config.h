/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Konfiguracja
 *
 */


#ifndef CONFIG_INCLUDED
#define CONFIG_INCLUDED

#include <cmath>

// minimalna i maksymalna wartosc odleglosci rzutni (ogniskowej)
const double MIN_D = 1.0;
const double MAX_D = 10.0;

// minimalna i maksymalna wartosc translacji
const double MIN_T = -100.0;
const double MAX_T = 100.0;

// wartosci poczatkowe (ogniskowa, katy obrotu, translacja)
const double D_START = 1.0;
const double FI_START = 0.0;
const double T_START = 0.0;
const double T_Z_START = 8.0;

// czy domyslnie wyswietlac wspolrzedne punktow
const bool WSP_PKT_START = false;

// krok translacji
const double KROK_T = 0.25;
// krok obrotu
const double KROK_FI = M_PI/90;
// krok zmiany ogniskowej
const double KROK_D = 0.01;


#endif
