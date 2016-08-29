/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 2a
 * "Wirtualna kamera" z eliminacja elementow zaslonietych
 *
 * Biblioteka d2 - operacje 2-wymiarowe
 *
 */


#include <cmath>

#include "sdl_plus.h"
#include "d2.h"


using namespace std;



/*
Rysowanie wypelnionego wielokata z krawedziami
Wypelnienie przy pomocy algorytmu przeglądania linii (scanline algorithm)
Parametry wejsciowe:
scr - wskaznik do warstwy obrazu
v - tablica zawierajaca kolejne pary wierzcholkow wielokata
n - ilosc wierzcholkow
c - kolor wypelnienia
obcinaj - dla true obciecie przy pomocy algorytmu Cohena-Sutherlanda
*/
void d2_wypelnienie_wielokata(SDL_Surface *scr, D2_punkt *v, int n, Uint32 c, bool obcinaj) {

  if (n<3) return;

  int nxs;
  double *xs = new double[n];
  int i, j, k;
  double y, y0 = v[0].y, y1 = y0;

  for (i=0;i<n;i++) {

      y= v[i].y;
      if (y<y0) { y0= y; }
      if (y>y1) { y1= y;  }

  }

  if (y0<1.0) y0 = 1.0;
  if (y1>scr->h-1) y1 = static_cast<double>(scr->h-1);

  for (y=y0;y<=y1;++y) {

      nxs = 0;  j = n-1;
      for (i=0; i<n; j=i++)  {

	    if ((v[i].y<y && y<=v[j].y) || (v[j].y<y && y<=v[i].y))  {

	      xs[nxs++] = v[i].x + (y-v[i].y) / (v[j].y-v[i].y) * (v[j].x-v[i].x);
	      for (k=nxs-1; k && xs[k-1] > xs[k]; --k)   swap(xs[k-1], xs[k]);

	    }

	  }

      for (i= 0; i < nxs; i+=2)  {

         D2_punkt pp(xs[i], y);
         D2_punkt pp2(xs[i+1], pp2.y = y);
         // obciecie odcinka do wsp. ekranu przy pomocy algorytmu Cohena-Sutherlanda
         if (!obcinaj || ((d2_obciecie_cs(1, 1, scr->w-1, scr->h-1, pp, pp2))
              && (pp.x>0 && pp.y>0 && pp2.x>0 && pp2.y>0 && pp.x<scr->w && pp.y<scr->h && pp2.x<scr->w && pp2.y<scr->h))) {

               d2_linia(scr, pp, pp2, c);

         }


      }

   }

   delete []xs;

   // rysowanie krawedzi
   for (i=0;i<4;i++) {

     D2_punkt pp(v[i].x, v[i].y);
     int i2 = (i<3) ? i+1 : 0;
     D2_punkt pp2(v[i2].x, pp2.y = v[i2].y);
     // obciecie odcinka do wsp. ekranu przy pomocy algorytmu Cohena-Sutherlanda
     if (!obcinaj || ((d2_obciecie_cs(1, 1, scr->w-1, scr->h-1, pp, pp2))
           && (pp.x>0 && pp.y>0 && pp2.x>0 && pp2.y>0 && pp.x<scr->w && pp.y<scr->h && pp2.x<scr->w && pp2.y<scr->h))) {

         d2_linia(scr, pp, pp2, 0xf0f0f0);

      }

   }


}


/*
Wywolania rysowanie wypelnionego wielokata z wlaczonym obcinaniem C-S
*/
void d2_wypelnienie_wielokata(SDL_Surface *s, D2_punkt *v, int n, Uint32 c) {

  d2_wypelnienie_wielokata(s, v, n, c, true);

}




/*
Funkcja rysuje odcinek przy pomocy algorytmu Bresenhama
Parametry wejsciowe:
screen - wskaznik do warstwy obrazu
x1,y1,x2,y2 - wspolrzedne krancow odcinka
kolor - kolor pikseli (HEX)
*/
void d2_linia(SDL_Surface* screen, Sint32 x1, Sint32 y1, Sint32 x2, Sint32 y2,  Uint32 color) {

     // zmienne pomocnicze
     int d, dx, dy, ai, bi, xi, yi;
     int x = x1, y = y1;
     // ustalenie kierunku rysowania
     if (x1 < x2)  {
         xi = 1;  dx = x2 - x1;
     }
     else  {
         xi = -1;  dx = x1 - x2;
     }

     if (y1 < y2) {
         yi = 1;  dy = y2 - y1;
     }
     else   {
         yi = -1;  dy = y1 - y2;
     }

     sdlp_put_pixel(screen, x, y, color);

     // os wiodaca OX
     if (dx > dy)
     {
         ai = (dy - dx) * 2;
         bi = dy * 2;
         d = bi - dx;
         // petla po kolejnych x
         while (x != x2)
         {
             // test wspolczynnika
             if (d >= 0) {
                 x += xi; y += yi; d += ai;
             } else {
                 d += bi; x += xi;
             }
             sdlp_put_pixel(screen, x, y, color);
         }
     }
     // os wiodaca OY
     else
     {
         ai = ( dx - dy ) * 2;
         bi = dx * 2;
         d = bi - dy;
         // petla po kolejnych y
         while (y != y2)
         {
             // test wspolczynnika
             if (d >= 0) {
                 x += xi; y += yi; d += ai;
             } else {
                 d += bi;  y += yi;
             }
             sdlp_put_pixel(screen, x, y, color);
         }
     }
}


/*
Wywolanie rysowania linii za pomoca algorytmu Bresenhama z parametrami spakowanymi w punkty 2D
(p1, p2 - punkty krancowe odcinka)
*/
void d2_linia(SDL_Surface* screen, D2_punkt p1, D2_punkt p2,  Uint32 color) {

  if(SDL_MUSTLOCK(screen)) SDL_LockSurface(screen);

  d2_linia(screen, static_cast<Sint32>(p1.x), static_cast<Sint32>(p1.y),
           static_cast<Sint32>(p2.x), static_cast<Sint32>(p2.y), color);

  if(SDL_MUSTLOCK(screen)) SDL_UnlockSurface(screen);

}





/*
Funkcja zwraca kod regionu dla algorytmu Cohena-Sutherlanda
Parametry wejsciowe:
left, top, right, bottom - krancowe wspolrzedne obszaru 2D
pkt - sprawdzany punkt (uwzglednia tylko wspolrzedne odpowiednie dla przestrzeni 2-wymiarowej)
*/
unsigned char d2_obciecie_cs_region(double left, double top, double right, double bottom, D2_punkt pkt) {

  unsigned char r = 0;

  if (pkt.x < left)   r |= 0x1;
  if (pkt.x > right)  r |= 0x2;
  if (pkt.y > bottom) r |= 0x4;
  if (pkt.y < top)    r |= 0x8;

  return r;

}


/*
Obcinanie odcinkow 2D przy pomocy algorytmu Cohena-Sutherlanda
Zwracana wartosc: true jezeli odcinek(rowniez po obcieciu) miesci sie w danym obszarze, false jezeli nie
Parametry wejsciowe:
left, top, right, bottom - krancowe wspolrzedne obszaru 2D
pkt1, pkt2 - krance sprawdzanego odcinka (uwzglednia tylko wspolrzedne odpowiednie dla przestrzeni 2-wymiarowej)
*/
bool d2_obciecie_cs(double left, double top, double right, double bottom, D2_punkt &pkt1, D2_punkt &pkt2) {

  unsigned char rn;
  double x,y;

  unsigned char r1 = d2_obciecie_cs_region(left, top, right, bottom, pkt1);
  unsigned char r2 = d2_obciecie_cs_region(left, top, right, bottom, pkt2);

  if ((r1 & r2) != 0)  return false;
  if ((r1 | r2) == 0)  return true;

  do {

     if (r1 != 0) {  rn = r1; x=pkt1.x; y=pkt1.y; }
     else { rn = r2;  x=pkt2.x;  y=pkt2.y; }

     if (rn & 0x1) {

       y = pkt1.y+(pkt2.y-pkt1.y)*(left-pkt1.x)/(pkt2.x-pkt1.x);
       x = left;

     }

     else if (rn & 0x2) {

       y = pkt1.y+(pkt2.y-pkt1.y)*(right-pkt1.x)/(pkt2.x-pkt1.x);
       x = right;

     }

     else if (rn & 0x4) {

       x = pkt1.x+(pkt2.x-pkt1.x)*(bottom-pkt1.y)/(pkt2.y-pkt1.y);
       y = bottom;

     }

     else if (rn & 0x8) {

      x = pkt1.x+(pkt2.x-pkt1.x)*(top-pkt1.y)/(pkt2.y-pkt1.y);
      y = top;

     }


     if (rn == r1) {

        pkt1.x = x;  pkt1.y = y;
        r1 = d2_obciecie_cs_region(left, top, right, bottom, pkt1);

     }

     else {

        pkt2.x = x;  pkt2.y = y;
        r2 = d2_obciecie_cs_region(left, top, right, bottom, pkt2);

     }


   } while ((r1 & r2) == 0 && (r1 | r2) != 0);

   if ((r1 & r2) != 0)  return false;

   return true;

}




