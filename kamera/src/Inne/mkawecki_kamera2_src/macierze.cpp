/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
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


