/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka kamera - parametry przeksztalcen, budowa sceny i obrazu na kamerze
 *
 */


#ifndef KAMERA_INCLUDED
#define KAMERA_INCLUDED

#include "kwaterniony.h"


using namespace std;

// czy wyswietlac wspolrzedne pkt
extern bool kamera_pokaz_wsp_pkt;

// przygotowanie i wyswietlenie gotowego obrazu
void kamera_widok(double d, D3_punkt t, Kwaternion k_obrot);

#endif
