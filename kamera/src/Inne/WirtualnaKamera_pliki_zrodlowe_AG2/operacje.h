#ifndef OPERACJE_H_INCLUDED
#define OPERACJE_H_INCLUDED

// deklaracja funkcji

void MacierzObrotuX (float obrot);
void MacierzObrotuY (float obrot);
void MacierzObrotuZ (float obrot);
int* RzutujPunkt (float *P);
void RysujOs (int okno[], HWND hwnd);
void RysujBrzeg (int brzeg[], HWND hwnd);
float Odleglosc (float ***PPP, int ib, int *w);
int* WierzcholkiSciany (int sc);
void RysujProstopadloscianZ (POINT rzut[], int *polozenie, float ***PPP, int kolor[][3], int ib, int okno[], HWND hwnd);
bool ObetnijKrawedzZ (POINT rzut[], int *polozenie, float &nowex, float &nowey, int &obciete, int start, int koniec);
void Przeun (float ***PPP, int x, int y, int z, int ilbud);
void Home (float ***PPP, int O[][9][3], int ilbud);
void Obroc (float ***PPP, float obrot, int ilbud);
void Pochyl (float ***PPP, float obrot, int ilbud);
void Skrec (float ***PPP, float obrot, int ilbud);
float* Wymnoz (float *P, float M[][4]);
void SortujObiekty (float ***PPP, int nrbud);
void Rysuj (float ***PPP, int kolor[][3], int okno[], int ilbud, HWND hwnd);

#endif // OPERACJE_H_INCLUDED

