/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka d3 - operacje 3-wymiarowe
 *
 */

#include "d3.h"
#include "macierze.h"

using namespace std;


/*
Mnozenie wspolrzednych 2 punktow 3D
Parametry wejsciowe: p1, p2 - punkty, zwracany wynikowy punkt 3D
*/
D3_punkt d3_pkt_pomnoz(D3_punkt p1, D3_punkt p2) {

  D3_punkt tmp = {p1.x*p2.x, p1.y*p2.y, p1.z*p2.z };
  return tmp;

}


/*
Funkcja zwraca kod regionu dla algorytmu Cohena-Sutherlanda w 3D
Parametry wejsciowe:
left, top, near, right, bottom, far - krancowe wspolrzedne obszaru 3D
pkt - sprawdzany punkt
*/
unsigned char d3_obciecie_cs_region(double left, double top,  double near, double right, double bottom, double far, D3_punkt pkt) {

  unsigned char r = 0;

  if (pkt.x < left)   r |= 0x1;
  if (pkt.x > right)  r |= 0x2;
  if (pkt.y > bottom) r |= 0x4;
  if (pkt.y < top)    r |= 0x8;
  if (pkt.z < near)   r |= 0x10;
  if (pkt.z > far)    r |= 0x20;

  return r;

}


/*
Obcinanie odcinkow 3D przy pomocy zmodyfikowanego algorytmu Cohena-Sutherlanda
Zwracana wartosc: true jezeli odcinek(rowniez po obcieciu) miesci sie w danym obszarze, false jezeli nie
Parametry wejsciowe:
left, top, near, right, bottom, far - krancowe wspolrzedne obszaru 3D
pkt1, pkt2 - referencje do punktow krancowych sprawdzanego odcinka
*/
bool d3_obciecie_cs(double left, double top, double near, double right, double bottom, double far, D3_punkt &pkt1, D3_punkt &pkt2) {

  unsigned char rn;
  double x,y,z;

  unsigned char r1 = d3_obciecie_cs_region(left, top, near, right, bottom, far, pkt1);
  unsigned char r2 = d3_obciecie_cs_region(left, top, near, right, bottom, far, pkt2);

  if ((r1 & r2) != 0)  return false;
  if ((r1 | r2) == 0)  return true;

  do {

     if (r1 != 0) {  rn = r1; x=pkt1.x; y=pkt1.y; z=pkt1.z; }
     else { rn = r2;  x=pkt2.x;  y=pkt2.y; z=pkt2.z; }

     if (rn & 1) {

       y = pkt1.y + ((left-pkt1.x)*(pkt2.y-pkt1.y)/(pkt2.x-pkt1.x));
       z = pkt1.z + ((left-pkt1.x)*(pkt2.z-pkt1.z)/(pkt2.x-pkt1.x));
       x = left;

     }

     else if (rn & 0x2) {

       y = pkt1.y + ((right-pkt1.x)*(pkt2.y-pkt1.y)/(pkt2.x-pkt1.x));
       z = pkt1.z + ((right-pkt1.x)*(pkt2.z-pkt1.z)/(pkt2.x-pkt1.x));
       x = right;

     }

     else if (rn & 0x4) {

       x = pkt1.x + ((bottom-pkt1.y)*(pkt2.x-pkt1.x)/(pkt2.y-pkt1.y));
       z = pkt1.z + ((bottom-pkt1.y)*(pkt2.z-pkt1.z)/(pkt2.y-pkt1.y));
       y = bottom;

     }

     else if (rn & 0x8) {

       x = pkt1.x + ((top-pkt1.y)*(pkt2.x-pkt1.x)/(pkt2.y-pkt1.y));
       z = pkt1.z + ((top-pkt1.y)*(pkt2.z-pkt1.z)/(pkt2.y-pkt1.y));
       y = top;

     }

     else if (rn & 0x10) {

       x = pkt1.x + ((near-pkt1.z)*(pkt2.x-pkt1.x)/(pkt2.z-pkt1.z));
       y = pkt1.y + ((near-pkt1.z)*(pkt2.z-pkt1.z)/(pkt2.z-pkt1.z));
       z = near;

     }

     else if (rn & 0x20) {

       x = pkt1.x + ((far-pkt1.z)*(pkt2.x-pkt1.x)/(pkt2.z-pkt1.z));
       y = pkt1.y + ((far-pkt1.z)*(pkt2.z-pkt1.z)/(pkt2.z-pkt1.z));
       z = far;

     }

     if (rn == r1) {

        pkt1.x = x;  pkt1.y = y;  pkt1.z = z;
        r1 = d3_obciecie_cs_region(left, top, near, right, bottom, far, pkt1);

     }

     else {

        pkt2.x = x;  pkt2.y = y;  pkt2.z = z;
        r2 = d3_obciecie_cs_region(left, top, near, right, bottom, far, pkt2);

     }


   } while ((r1 & r2) == 0 && (r1 | r2) != 0);

   if ((r1 & r2) != 0)  return false;

   return true;


}


/*
Wywolanie obciecia Cohena-Sutherlanda z parametrami spakowanymi w punkty 3D
(wi1, wi2 - punkty krancowe obszaru)
*/
bool d3_obciecie_cs(D3_punkt wi1, D3_punkt wi2, D3_punkt &pkt1, D3_punkt &pkt2) {

  return d3_obciecie_cs(wi1.x, wi1.y, wi1.z, wi2.x, wi2.y, wi2.z, pkt1, pkt2);

}



/*
Przemnozenie macierzy przez punkt(jako wektor) i normalizacja
Parametry wejsciowe:
p - punkt, M - macierz (w formie 1-wym. tablicy dyn.)
Zwracana wartosc - wynikowy znormalizowany punkt
*/
D3_punkt d3_pomnoz_normalizuj_pkt(D3_punkt p, double* M) {

   double *V = macierz_mnozenie(M, p);
   D3_punkt tmp;

   // normalizacja, dla wektorow juz znormalizowanych bedzie V[3]=1
   tmp.x = V[0]/V[3];
   tmp.y = V[1]/V[3];
   tmp.z = V[2]/V[3];

   delete []V;

   return tmp;

}


