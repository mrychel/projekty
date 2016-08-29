#ifndef OPERACJE_H_INCLUDED
#define OPERACJE_H_INCLUDED

// deklaracja funkcji

void MacierzObrotuX (double obrot);
void MacierzObrotuY (double obrot);
void MacierzObrotuZ (double obrot);
int* RzutujPunkt (double *P);
int* RzutujPunkt2 (double *P);
void RysujOs (int okno[], HWND hwnd);
void RysujBrzeg (int brzeg[], HWND hwnd);
void RysujProstopadloscian (int **PPR, int okno[], HWND hwnd);
void RysujProstopadloscianO (int **PPR, int okno[], HWND hwnd);
void TrzyKrawedzie (int **PPR, int w0, int w1, int w2, int w3, HPEN kolor1, HPEN kolor2, HWND hwnd);
void Przeun (double ***PPP, int x, int y, int z, int ilbud);
double* Wymnoz (double *P, double M[][4]);
void Obroc (double ***PPP, double obrot, int ilbud);
void Pochyl (double ***PPP, double obrot, int ilbud);
void Skrec (double ***PPP, double obrot, int ilbud);
void Rysuj (double ***PPP, int okno[], int ilbud, HWND hwnd);
bool ObetnijKrawedz (int **PPR, int &nowex, int &nowey, int start, int koniec);


#endif // OPERACJE_H_INCLUDED

