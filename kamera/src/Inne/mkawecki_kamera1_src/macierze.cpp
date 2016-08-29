/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka macierze - implementacja potrzebnych operacji macierzowych
 *
 */

#include <cstdlib>
#include <cmath>

#include "scena.h"
#include "d3.h"
#include "config.h"



using namespace std;


// Wszystkie macierze przedstawione jako jednowymiarowa tablica dynamiczna
// Dostep do elementu a[i,j] macierzy A[N*M]: a[i*M+j]
// Domyslne wymiary macierzy:
const int wym_i = 4;
const int wym_j = 4;


/*
Funkcja zwraca macierz przeksztalcenia tozsamosciowego (jedynkowa)
w formie jednowymiarowej tablicy dynamicznej.
*/
double* macierz_pt() {

   double *M = new double[wym_i*wym_j];
   int i,j;
   for (i=0;i<wym_i;i++)
     for (j=0;j<wym_j;j++) M[i*wym_j+j] = (i==j)?1.0:0.0;

   return M;

}


/*
Funkcja zwraca macierz rzutu perspektywicznego (wersja 1)
w formie jednowymiarowej tablicy dynamicznej.
Parametr wejsciowy:
d - odleglosc rzutni
*/
double* macierz_rzut(double d) {

  if (d==0)  return NULL;

  double *M = macierz_pt();
  M[15] = 0;
  M[14] = 1/d;

  return M;

}


/*
Funkcja zwraca macierz translacji w formie jednowymiarowej tablicy dynamicznej.
Parametry wejsciowe:
tx, ty, tz - wartosci translacji
*/
double* macierz_translacja(double tx, double ty, double tz) {

  double *M = macierz_pt();
  M[3] = tx;
  M[7] = ty;
  M[11] = tz;

  return M;

}



/*
Operacja mnozenia macierzy, funkcja zwraca wynik:
macierz w formie jednowymiarowej tablicy dynamicznej.
Parametry wejsciowe:
A, B - wskazniki do mnozonych macierzy (tablice dyn. 1-wym)
wym_b - drugi wymiar macierzy B
*/
double* macierz_mnozenie(double *A, double *B, int wym_b) {

  double *M = new double[wym_i*wym_b];
  int i,j,k;

  for (i=0;i<wym_i;i++)
    for (j=0;j<wym_b;j++) {

      double tmp = 0.0;
      for (k=0;k<wym_j;k++) tmp += A[i*wym_j+k] * B[k*wym_b+j];
      M[i*wym_b+j] = tmp;

  }

  return M;

}


//  Wywolanie mnozenia macierzy z domyslnym parametrem wym_b
double* macierz_mnozenie(double *A, double *B) {

  return macierz_mnozenie(A, B, wym_j);

}


/*
Wywolanie mnozenia macierzy A razy wektor wsp. punktu 3D
Parametry wejsciowe:
A - wskaznik do macierzy A (tablica dyn. 1-wym)
pkt - punkt w przestrzeni 3-wymiarowej
*/
double* macierz_mnozenie(double *A, D3_punkt pkt) {

  double *B = new double[4];

  B[0] = pkt.x;  B[1] = pkt.y;
  B[2] = pkt.z;  B[3] = 1;

  B = macierz_mnozenie(A, B, 1);

  return B;

}


// ------------------------------------------
// NIEUZYWANE - kod z wersji bez kwaternionow


enum Osie {O_X, O_Y, O_Z};


/*
Funkcja zwraca macierz obrotu wokol wybranej osi
w formie jednowymiarowej tablicy dynamicznej.
Parametry wejsciowe:
fi - wartosc kata obrotu
os - wybrana os (O_X/O_Y/O_Z)
*/
double* macierz_obrot(double fi, Osie os) {

  double *M = macierz_pt();

  switch (os) {

    default: break;

    case O_X: M[5] = cos(fi);
               M[6] = -sin(fi);
               M[9] = sin(fi);
               M[10] = cos(fi);
               break;

    case O_Y: M[0] = cos(fi);
               M[2] = sin(fi);
               M[8] = -sin(fi);
               M[10] = cos(fi);
               break;

    case O_Z: M[0] = cos(fi);
               M[1] = -sin(fi);
               M[4] = sin(fi);
               M[5] = cos(fi);
               break;

  }

  return M;

}



/*
Funkcja zwraca macierz zlozonych obrotow wokol wszystkich osi
w formie jednowymiarowej tablicy dynamicznej.
Parametry wejsciowe:
fi_x, fi_y, fi_z - wartosc katow obrotu
t_x, t_y, t_z - parametry translacji
*/
double* macierz_obrot_xyz(double fi_x, double fi_y, double fi_z)  {

   double *M = macierz_pt();

   M = macierz_pt();

   if (fi_x>0)
     M = macierz_mnozenie(macierz_obrot(fi_x, O_X), M);

   if (fi_y>0)
     M = macierz_mnozenie(macierz_obrot(fi_y, O_Y), M);

   if (fi_z>0)
     M = macierz_mnozenie(macierz_obrot(fi_z, O_Z), M);

   return M;


}


