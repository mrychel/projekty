/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka kwaterniony - czesciowa implementacja kwaternionow
 *
 */


#include <cmath>

#include "kwaterniony.h"
#include "macierze.h"
#include "d3.h"

using namespace std;


/*
Funkcja zwraca wynik mnozenia dwoch kwaternionow
Parametry: q1, q2 - czynniki mnozenia
*/
Kwaternion kwaternion_mnozenie(Kwaternion q1, Kwaternion q2) {

   Kwaternion q;
   q.s = q1.s*q2.s - q1.x*q2.x - q1.y*q2.y - q1.z*q2.z;
   q.x=  q1.s*q2.x + q1.x*q2.s + q1.y*q2.z - q1.z*q2.y;
   q.y = q1.s*q2.y + q1.y*q2.s + q1.z*q2.x - q1.x*q2.z;
   q.z = q1.s*q2.z + q1.z*q2.s + q1.x*q2.y - q1.y*q2.x;
   return q;

}



/*
Funkcja zwraca kwaternion odwrotny do danego
Parametr wejsciowy:
q - dany kwaternion
*/
Kwaternion kwaternion_odwrotny(Kwaternion q) {

  double dl = sqrt(q.s*q.s + q.x*q.x + q.y*q.y + q.z*q.z);
  q.x = -q.x / dl;
  q.y = -q.y / dl;
  q.z = -q.z / dl;

  return q;

}


/*
Funkcje zmieniajace orientacje - obrot o fi wokol osi Ox, Iy, Oz
Parametry wejsciowe:
q - referencja do kwaterniona reprezentujacego orientacje
fi - wartosc kata
*/

// zmiana orientacji - obrot o fi wokol osi Ox
void kwaternion_obrotX(Kwaternion &q, double fi) {

  fi*=0.5;
  Kwaternion qq = { cos(fi), sin(fi), 0.0, 0.0 };
  q = kwaternion_mnozenie(q, qq);

}


// zmiana orientacji - obrot o fi wokol osi Oy
void kwaternion_obrotY(Kwaternion &q, double fi) {

  fi*=0.5;
  Kwaternion qq = { cos(fi), 0.0, sin(fi), 0.0 };
  q = kwaternion_mnozenie(q, qq);

}

// zmiana orientacji - obrot o fi wokol osi Oz
void kwaternion_obrotZ(Kwaternion &q, double fi) {

  fi*=0.5;
  Kwaternion qq = { cos(fi), 0.0, 0.0, sin(fi) };
  q = kwaternion_mnozenie(q, qq);

}

void kwaternion_obrot(Kwaternion &q, Euler fi, double fi_c) {

  fi.x*=0.5;  fi.y*=0.5;  fi.z*=0.5;
  Kwaternion qq = { cos(fi_c), sin(fi.x), sin(fi.y), sin(fi.z) };
  q = kwaternion_mnozenie(q, qq);

}

/*
Obrot wektora o kwaternion
Parametry wejsciowe:
q - kwaternion reprezentujacy orientacje
w - referencja do obracanego wektora
*/
void kwaternion_obrot_wektor(Kwaternion q, D3_punkt &w) {

   kwaternion_normalizacja(q);
   Kwaternion q2 = kwaternion_odwrotny(q);
   Kwaternion tmp = { 0.0, w.x, w.y, w.z };

   tmp = kwaternion_mnozenie(tmp, q);
   tmp = kwaternion_mnozenie(q2, tmp);

   w.x = tmp.x;
   w.y = tmp.y;
   w.z = tmp.z;

}


/*
Funkcja normalizujaca kwaternion
Parametr wejsciowy: referencja do kwaterniona
*/
void kwaternion_normalizacja(Kwaternion &q) {

	double norma = (q.s*q.s + q.x*q.x + q.y*q.y + q.z*q.z);

    if (norma != 1.0) {
	  q.s /= norma;  q.x /= norma;
      q.y /= norma;  q.z /= norma;
    }

}



/*
Funkcja realizuje przejscie z katow Eulera do przestrzeni kwaternionow
Zwracana wartosc: kwaterion reprezentujacy dany obrot
Parametry wejsciowe:
fi_x, fi_y, fi_z - katy Eulera obrotu
*/
Kwaternion kwaternion_przejscie_k(double fi_x, double fi_y, double fi_z) {

  fi_x*=0.5;  fi_y*=0.5;  fi_z*=0.5;

  double sy = sin(fi_y), cy = cos(fi_y);
  double sx = sin(fi_x), cx = cos(fi_x);
  double sz = sin(fi_z), cz = cos(fi_z);

  Kwaternion tmp;
  tmp.s = cy*cx*cz + sy*sx*sz;
  tmp.x = cy*sx*cz + sy*cx*sz;
  tmp.y = sy*cx*cz - cy*sx*sz;
  tmp.z = cy*cx*sz - sy*sx*cz;

  return tmp;

}


/*
Funkcja realizuje przejcie z przestrzeni kwaternionow  do katow Eulera
Zwracana wartosc: trojka wspolrzednych obrotu wokol kolejnych osi
Parametry wejsciowe:
q - kwaternion reprezentujacy obrot
*/
Euler kwaternion_powrot_e(Kwaternion q) {

  Euler tmp;
  double sp = -2.0 * (q.y*q.z - q.s*q.x);

  if (sp == 1.0) {

    tmp.x = M_PI / 2  * sp;
    tmp.y = atan2(-q.x*q.z + q.s*q.y, 0.5 - q.y*q.y - q.z*q.z);
    tmp.z = 0.0;

  }

  else   {

    tmp.x = asin(sp);
    tmp.y = atan2(q.x*q.z + q.s*q.y, 0.5 - q.x*q.x - q.y*q.y);
    tmp.z = atan2(q.x*q.y + q.s*q.z, 0.5f - q.x*q.x - q.z*q.z);

  }

  return tmp;

}



/*
Funkcja zwraca macierz obrotu z reprezentacji kwaternionowej
Parametry wejsciowe:
q - kwaternion reprezentujacy obrot
*/
double* kwaternion_macierz_obrot_xyz(Kwaternion q) {

    double *M = macierz_pt();


    double sqx = q.x*q.x;
    double sqy = q.y*q.y;
    double sqz = q.z*q.z;

    M[0] = 1.0 - 2.0 * (sqy + sqz);
    M[1] = 2.0 * (q.x * q.y - q.z * q.s);
    M[2] = 2.0 * (q.z * q.x + q.y * q.s);
    M[3] = 0.0;

    M[4] = 2.0 * (q.x * q.y + q.z * q.s);
    M[5]= 1.0 - 2.0 * (sqz + sqx);
    M[6] = 2.0 * (q.y * q.z - q.x * q.s);
    M[7] = 0.0;

    M[8] = 2.0 * (q.z * q.x - q.y * q.s);
    M[9] = 2.0 * (q.y * q.z + q.x * q.s);
    M[10] = 1.0 - 2.0 * (sqy + sqx);
    M[11] = 0.0;

    M[12] = 0.0;
    M[13] = 0.0;
    M[14] = 0.0;
    M[15] = 1.0;


    return M;

}


