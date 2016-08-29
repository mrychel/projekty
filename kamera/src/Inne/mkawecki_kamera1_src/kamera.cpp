/*
 * Maciej Kawecki
 * OKNO PW, Grafika komputerowa, projekt 1
 * "Wirtualna kamera"
 *
 * Biblioteka kamera - wyswietlanie obrazu na kamerze
 *
 */

#include <SDL.h>

#include "gui.h"
#include "scena.h"
#include "d2.h"
#include "d3.h"
#include "sdl_plus.h"
#include "macierze.h"
#include "kwaterniony.h"
#include "config.h"


using namespace std;


// czy wyswietlac wspolrzedne punktow
bool kamera_pokaz_wsp_pkt = WSP_PKT_START;


/*
Przeksztalcenie wspolrzednych punktu w ukladzie kamery(po rzutowaniu, juz 2D) do widoku,
tj na wspolrzedne warstwy ekranu. Przesuniecie na srodek widoku.
Parametry wejsciowe:
p - referencja do punktu (uwzgledniane tylko wspolrzedne dla przestrzeni 2D)
szerokosc, wysokosc - szerokosc i wysokosc widoku kamery
*/
void kamera_przelicz_wsp_pkt(D2_punkt &p, double szerokosc, double wysokosc) {

    // zadane krancowe wspolrzedne w ukladzie kamery
    const double x_min = -1.0, x_max = 1.0, y_min = -1.0, y_max = 1.0;

    p.x = ((p.x - x_min) * szerokosc / (x_max - x_min));
    // odwrocenie kierunku w osi OY
    p.y = wysokosc - ((p.y - y_min) * wysokosc / (y_max - y_min));


}


/*
Wypisanie obok punktu jego wspolrzednych (w ukladzie swiata) w widoku kamery.
Parametry wejsciowe:
scr - wskaznik do warstwy ekranu
p_s - wspolrzedne punktu w ukladzie swiata (3D)
p_k - wspolrzedne punktu w widoku kamery (2D)
*/
void kamera_widok_wypisz_wsp(SDL_Surface* scr, D3_punkt p_s, D2_punkt p_k) {

  stringstream oss;
  oss  << "(" << p_s.x << ";" << p_s.y << ";"
       << p_s.z << ")";
  sdlp_out_text(scr, p_k.x-3, p_k.y+3, oss.str(), k_gray3, true);

}



/*
Przygotowanie i wyswietlenie gotowego obrazu
Parametry wejsciowe:
d - odleglosc rzutni (ogniskowa)
t - wektor translacji w ukladzie kamery
tu - wektor translacji w odniesieniu do poczatku ukladu wsp.
k_obrot - reprezentacja kwaternionowa obrotu
*/
void kamera_widok(double d, D3_punkt t, Kwaternion k_obrot) {

   // warstwa ekranu dla widoku kamery
   SDL_Surface* screen_tmp = sdlp_create_surface(757, 480, screen);


   int i,j,k;

   // kazdorazowa konstrukcja macierzy przeksztalcen
   // w celu minimalizacji bledow wynikajacych z obliczen numerycznych

   // macierz translacji
   // zmiana znaku Ty, bo w osi Oy jest odwrocenie kierunku
   double *Mt = macierz_translacja(t.x, -t.y, t.z);

   // macierz rzutowania
   double *Mr = macierz_rzut(d);

   // macierz obrotow
   double *Mo = kwaternion_macierz_obrot_xyz(k_obrot);

   for (i=0;i<scena_ile_obiektow;i++) {

      for (j=0;j<6;j++) {

        for (k=0;k<2;k++) {

          // pobranie wspolrzednych krancow kolejnego odcinka
          D3_punkt pkt1 = d3_pkt_pomnoz(scena_obiekty[i].wierzcholki[j][k*2], scena_obiekty[i].wsp_osi);
          D3_punkt pkt2 = d3_pkt_pomnoz(scena_obiekty[i].wierzcholki[j][k*2+1], scena_obiekty[i].wsp_osi);
          // zapamietanie wspolrzednych do pozniejszego wyswietlenia
          D3_punkt pkt1_tmp = pkt1;  D3_punkt pkt2_tmp = pkt2;

          pkt1 = d3_pomnoz_normalizuj_pkt(pkt1, Mt);
          pkt2 = d3_pomnoz_normalizuj_pkt(pkt2, Mt);

          pkt1 = d3_pomnoz_normalizuj_pkt(pkt1, Mo);
          pkt2 = d3_pomnoz_normalizuj_pkt(pkt2, Mo);


          // obciecie odcinkow w 3D w nieco szerszym planie (zmod. alg. Cohena-Sutherlanda)
          // przy okazji wyeliminowanie niewidocznych elementow)
          // sprawdzenie czy punkt miesci sie w rozszerzonym planie 3D
          if (d3_obciecie_cs(scena_obciecie_wierzcholki[0], scena_obciecie_wierzcholki[1], pkt1, pkt2)) {

            // rzutowanie i konwersja typu na punkt 2D
            D2_punkt p1 = D2_punkt(d3_pomnoz_normalizuj_pkt(pkt1, Mr));
            D2_punkt p2 = D2_punkt(d3_pomnoz_normalizuj_pkt(pkt2, Mr));

            // przeksztalcenie wspolrzednych (po rzutowaniu i normalizacji) do widoku kamery
            kamera_przelicz_wsp_pkt(p1, screen_tmp->w, screen_tmp->h);
            kamera_przelicz_wsp_pkt(p2, screen_tmp->w, screen_tmp->h);

            // ponowne obciecie odcinkow ale w 2D (alg. Cohena-Sutherlanda)
            // tu scisle do wspolrzednych warstwy ekranu (widoku kamery)
            if (d2_obciecie_cs(1, 1, screen_tmp->w-1, screen_tmp->h-1, p1, p2)) {

              // dodatkowe sprawdzenie na wypadek nie zadzialania obciecia, polepsza to koncowy efekt,
              // bo w niektorych wypadkach algorytm C-S nie jest w 100% skuteczny
              if (p1.x>0 && p1.y>0 && p2.x>0 && p2.y>0 &&
                 p1.x<screen_tmp->w && p1.y<screen_tmp->h && p2.x<screen_tmp->w && p2.y<screen_tmp->h) {

                // wykreslenie odcinka
                d2_linia(screen_tmp, p1, p2, scena_obiekty[i].kolor);

                // ewentualne opisanie wspolrzednych punktow
                if (kamera_pokaz_wsp_pkt) {

                  kamera_widok_wypisz_wsp(screen_tmp, pkt1_tmp, p1);
                  kamera_widok_wypisz_wsp(screen_tmp, pkt2_tmp, p2);

                }

              }

            }

         }

       }

     }

   }

   delete []Mt;
   delete []Mr;
   delete []Mo;

   // nalozenie gotowej warstwy na ekran
   sdlp_apply_surface(21, 86, screen_tmp, screen);
   // zwolnienie pamieci
   SDL_FreeSurface(screen_tmp);

}




