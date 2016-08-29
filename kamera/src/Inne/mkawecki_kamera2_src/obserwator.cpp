/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka obserwator - pozycja obserwatora, zmiany pozycji
 *
 */

#include "obserwator.h"
#include "d3.h"
#include "kwaterniony.h"
#include "config.h"


using namespace std;

Obserwator obserwator;


// konstruktor
Obserwator::Obserwator() {

   this->inicjalizuj();

}

/*
Inicjalizacja zmiennych - poczatkowe polozenie
*/
void Obserwator::inicjalizuj() {

   this->t.x = this->t.y = this->t.z = 0.0;
   this->obrot = kwaternion_przejscie_k(FI_START, FI_START, FI_START);
   this->translacja(T_START, T_START, T_Z_START);
   this->obrotXYZ(FI_START, FI_START, FI_START, FI_START);

}

/*
Wykonanie obrotu (na kwaternionowej reprezentacji)
uwzgledniajace biezaca orientacje (definicja kierunkow)
Zwracana wartosc: czy zostala wykonana zmiana (true / false)
Parametry wejsciowe:
fx, fy, fz - wspolrzedne wektora obrotu (katy Eulera)
f_c - kat obrotu w zadanej osi
*/
bool Obserwator::obrotXYZ(double fx, double fy, double fz, double f_c) {

   if (f_c==0.0) return false;

   D3_punkt tr = { fx, fy, fz };
   kwaternion_obrot_wektor(this->obrot, tr);
   Euler fi = { tr.x, tr.y, tr.z };

   kwaternion_obrot(this->obrot, fi, f_c);
   kwaternion_normalizacja(this->obrot);

   return true;

}


/*
Obroty wokol osi Ox, Oy, Oz
Zwracana wartosc: czy zostala wykonana zmiana (true / false)
Parametry wejsciowe:
krok - kat Eulera obrotu w danej osi
*/
bool Obserwator::obrotX(double krok) {

  return this->obrotXYZ(krok, 0.0, 0.0, krok);

}

bool Obserwator::obrotY(double krok) {

  return this->obrotXYZ(0.0, krok, 0.0, krok);

}


bool Obserwator::obrotZ(double krok) {

  return this->obrotXYZ(0.0, 0.0, krok, krok);

}



/*
Wykonanie translacji uwzgledniajace biezaca orientacje
Zwracana wartosc: czy zostala wykonana zmiana (true / false)
Parametry wejsciowe:
tx, ty, tz - wspolrzedne wektora translacji
*/
bool Obserwator::translacja(double tx, double ty, double tz) {

   D3_punkt tr = { tx, ty, tz };
   kwaternion_obrot_wektor(this->obrot, tr);

   tx = this->t.x + tr.x;
   ty = this->t.y + tr.y;
   tz = this->t.z + tr.z;

   // sprawdzenie czy translacja nie przekracza ustalonego przedzialu
   if (((tr.x>=0 && tx<=MAX_T) || (tr.x<0 && tx>=MIN_T))
     && ((tr.y>=0 && ty<=MAX_T) || (tr.y<0 && ty>=MIN_T))
     && ((tr.z>=0 && tz<=MAX_T) || (tr.z<0 && tz>=MIN_T))) {

      this->t.x = tx;  this->t.y = ty;  this->t.z = tz;

      return true;

   }

   return false;

}



/*
Translacje w osiach Ox, Oy, Oz
Zwracana wartosc: czy zostala wykonana zmiana (true / false)
Parametr wejsciowy:
krok - wartosc translacji w danej osi
*/
bool Obserwator::translacjaX(double krok) {

  return Obserwator::translacja(krok, 0.0, 0.0);

}


bool Obserwator::translacjaY(double krok) {

  return Obserwator::translacja(0.0, krok, 0.0);

}


bool Obserwator::translacjaZ(double krok) {

  return Obserwator::translacja(0.0, 0.0, krok);

}


