#include <windows.h>
#include <math.h>
#include "operacje.h"

extern int szer, wys, zerox, zeroy, d;

double MOX[4][4] = {{1,0,0,0}, {0,2,2,0}, {0,2,2,0}, {0,0,0,1}}; // obrot wokol osi X = {{1,0,0,0}, {0,cosinus,-sinus,0}, {0,sinus,cosinus,0}, {0,0,0,1}}
double MOY[4][4] = {{2,0,2,0}, {0,1,0,0}, {2,0,2,0}, {0,0,0,1}}; // obrot wokol osi Y = {{cosinus,0,sinus,0}, {0,1,0,0}, {-sinus,0,cosinus,0}, {0,0,0,1}}
double MOZ[4][4] = {{2,2,0,0}, {2,2,0,0}, {0,0,1,0}, {0,0,0,1}}; // obrot wokol osi Z = {{cosinus,-sinus,0,0}, {sinus,-cosinus,0,0}, {0,0,1,0}, {0,0,0,1}}


// implementacja funkcji

// wyliczenie transponowanych macierzy przeksztalcen
void MacierzObrotuX (double obrot){
	double cosinus = cos(obrot);
	double sinus = sin(obrot);
	MOX[1][1] = cosinus;
	MOX[2][1] = -sinus;
	MOX[1][2] = sinus;
	MOX[2][2] = cosinus;
}
void MacierzObrotuY (double obrot){
	double cosinus = cos(obrot);
	double sinus = sin(obrot);
	MOY[0][0] = cosinus;
	MOY[2][0] = sinus;
	MOY[0][2] = -sinus;
	MOY[2][2] = cosinus;
}
void MacierzObrotuZ (double obrot){
	double cosinus = cos(obrot);
	double sinus = sin(obrot);
	MOZ[0][0] = cosinus;
	MOZ[1][0] = -sinus;
	MOZ[0][1] = sinus;
	MOZ[1][1] = cosinus;
}

// rzutowanie na plaszczyzne rzutni z=0 -> obserwator na osi "z" w punkcie {x=y=0,z=-d}
// double macierzRzutowania [4][4] = {{1,0,0,0}, {0,1,0,0}, {0,0,0,0}, {0,0,1/d,1}};
int* RzutujPunkt (double *P){
	int *RzutujPunkt = new int[4];
	RzutujPunkt[0] = (P[0] * d) / (P[2] + d);
	RzutujPunkt[1] = (P[1] * d) / (P[2] + d);
	RzutujPunkt[2] = 0;
	// oznaczenie punktow rzutowanych poza obszar rzutni - algorytm Sutherlanda-Hodgmana
	RzutujPunkt[3] = 0;
	if (RzutujPunkt[0] < -szer) RzutujPunkt[3] += 1;
	else if (RzutujPunkt[0] > szer) RzutujPunkt[3] += 2;
	if (RzutujPunkt[1] < -wys) RzutujPunkt[3] += 4;
	else if (RzutujPunkt[1] > wys) RzutujPunkt[3] += 8;
	return RzutujPunkt;
}

// rzutowanie na plaszczyzne rzutni z=d -> obserwator w srodku ukladu wspolrzednych x=y=z=0
// double macierzRzutowania2 [4][4] = {{1,0,0,0}, {0,1,0,0}, {0,0,1,0}, {0,0,1/d,0}};
int* RzutujPunkt2 (double *P){
	int *RzutujPunkt = new int[4];
	RzutujPunkt[0] = (P[0] * d) / (P[2]);
	RzutujPunkt[1] = (P[1] * d) / (P[2]);
	RzutujPunkt[2] = d;
	// oznaczenie punktow rzutowanych poza obszar rzutni - algorytm Sutherlanda-Hodgmana
	RzutujPunkt[3] = 0;
	if (P[0] < -szer) RzutujPunkt[3] += 1;
	else if (P[0] > szer) RzutujPunkt[3] += 2;
	if (P[1] < -wys) RzutujPunkt[3] += 8;
	else if (P[1] > wys) RzutujPunkt[3] += 4;
	return RzutujPunkt;
}

// rysowanie osi
void RysujOs (int okno[], HWND hwnd){
	HDC hdc = GetDC( hwnd );
	POINT stary_punkt;
	MoveToEx( hdc, okno[0], zeroy, & stary_punkt );
	LineTo( hdc, okno[1], zeroy );
	MoveToEx( hdc, zerox, okno[2], & stary_punkt );
	LineTo( hdc, zerox, okno[3] );	
	ReleaseDC( hwnd, hdc );
}

// rysowanie obramowania okna i menu
void RysujBrzeg (int brzeg[], HWND hwnd){
	HDC hdc = GetDC( hwnd );
	POINT stary_punkt;
	MoveToEx( hdc, brzeg[0], brzeg[2], & stary_punkt );
	LineTo( hdc, brzeg[0], brzeg[3] );
	LineTo( hdc, brzeg[1], brzeg[3] );
	LineTo( hdc, brzeg[1], brzeg[2] );
	LineTo( hdc, brzeg[0], brzeg[2] );
	TextOut( hdc, 10, 5, L"KLAWISZE STERUJ¥CE   pozycja startowa = <Home>    lewo, prawo, góra, dó³ = <strza³ki>     przód, ty³ = <PageUp PageDown>", 120);  
	TextOut( hdc, 10, 25, L"               obroty zegaowe = <Q E>     pochylanie, podnoszenie = <S W>     skrêcanie prawo lewo = <A D>    zoom <+ ->", 120 );
	ReleaseDC( hwnd, hdc );
}

// rysowanie 3 krawedzi z jednego wierzcholka
void TrzyKrawedzie (int **PPR, int w0, int w1, int w2, int w3, HPEN kolor1, HPEN kolor2, HWND hwnd){
	int nowex, nowey;
	bool rysuj;
	HDC hdc = GetDC( hwnd );
	POINT stary_punkt;
	SelectObject( hdc, kolor1 );
	if (PPR[w0][3] == 0){
		rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w1);
		MoveToEx( hdc, zerox+PPR[w0][0], zeroy-PPR[w0][1], & stary_punkt );
		LineTo( hdc, zerox+nowex, zeroy-nowey );
		rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w2);
		MoveToEx( hdc, zerox+PPR[w0][0], zeroy-PPR[w0][1], & stary_punkt );
		LineTo( hdc, zerox+nowex, zeroy-nowey );
		rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w3);
		SelectObject( hdc, kolor2 );
		MoveToEx( hdc, zerox+PPR[w0][0], zeroy-PPR[w0][1], & stary_punkt );
		LineTo( hdc, zerox+nowex, zeroy-nowey );
	}
	else {
		if ((PPR[w0][3] & PPR[w1][3]) == 0) {
			SelectObject( hdc, kolor1 );
			rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w1);
			if (rysuj){
				MoveToEx( hdc, zerox+nowex, zeroy-nowey, & stary_punkt );
				rysuj = ObetnijKrawedz (PPR, nowex, nowey, w1, w0);
				LineTo( hdc, zerox+nowex, zeroy-nowey );
			}
		}
		if ((PPR[w0][3] & PPR[w2][3]) == 0) {
			SelectObject( hdc, kolor1 );
			rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w2);
			if (rysuj){
				MoveToEx( hdc, zerox+nowex, zeroy-nowey, & stary_punkt );
				rysuj = ObetnijKrawedz (PPR, nowex, nowey, w2, w0);
				LineTo( hdc, zerox+nowex, zeroy-nowey );
			}
		}
		if ((PPR[w0][3] & PPR[w3][3]) == 0) {
			SelectObject( hdc, kolor2 );
			rysuj = ObetnijKrawedz (PPR, nowex, nowey, w0, w3);
			if (rysuj){
				MoveToEx( hdc, zerox+nowex, zeroy-nowey, & stary_punkt );
				rysuj = ObetnijKrawedz (PPR, nowex, nowey, w3, w0);
				LineTo( hdc, zerox+nowex, zeroy-nowey );
			}
		}
	}
	ReleaseDC( hwnd, hdc );
}


// rysowanie rzutu prostopadloscianu - funkcja z obcinaniem
void RysujProstopadloscianO (int **PPR, int okno[], HWND hwnd){
	HDC hdc = GetDC( hwnd );
	HPEN zielony, niebieski, inny;
	zielony = CreatePen( PS_SOLID, 1, RGB( 0, 255, 0 ) );
	niebieski = CreatePen( PS_SOLID, 1, RGB( 0, 0, 255 ) );
	inny = CreatePen( PS_SOLID, 1, RGB( 255, 0, 255 ) );
	// krawedzie z wierzcholka 0 do wierzcholkow 1, 3, 4
	TrzyKrawedzie (PPR, 0, 1, 3, 4, niebieski, inny, hwnd);
	// krawedzie z wierzcholka 2 do wierzcholkow 1, 3, 6
	TrzyKrawedzie (PPR, 2, 1, 3, 6, niebieski, inny, hwnd);
	// krawedzie z wierzcholka 5 do wierzcholkow 4, 6, 1
	TrzyKrawedzie (PPR, 5, 4, 6, 1, zielony, inny, hwnd);
	// krawedzie z wierzcholka 7 do wierzcholkow 4, 6, 3
	TrzyKrawedzie (PPR, 7, 4, 6, 3, zielony, inny, hwnd);
	DeleteObject( zielony );
	DeleteObject( niebieski );
	DeleteObject( inny );
}


// obcinanie krawedzi do rozmiarow okna
bool ObetnijKrawedz (int **PPR, int &nowex, int &nowey, int start, int koniec){
	bool rysuj = true;
	switch (PPR[koniec][3])
	{
		case 0: // srodek
			nowex = PPR[koniec][0];
			nowey = PPR[koniec][1];
		break;
		
		case 1: // lewy bok
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowex = -szer;
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(-szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey > wys || nowey < -wys) rysuj = false;
			}
		break;
		
		case 2: // prawy bok
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowex = szer;
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey > wys || nowey < -wys) rysuj = false;
			}
		break;
		
		case 4: // dol
			if (PPR[koniec][1]-PPR[start][1] == 0) rysuj = false;
			else {
				nowey = -wys;
				nowex = PPR[start][0] + (PPR[koniec][0]-PPR[start][0])*(-wys-PPR[start][1])/(PPR[koniec][1]-PPR[start][1]);
				if (nowex > szer || nowex < -szer) rysuj = false;
			}
		break;
			
		case 5: // lewy dol
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(-szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey < -wys){
					if (PPR[koniec][1]-PPR[start][1] == 0) rysuj = false;
					else {
						nowex = PPR[start][0] + (PPR[koniec][0]-PPR[start][0])*(-wys-PPR[start][1])/(PPR[koniec][1]-PPR[start][1]);
						if(nowex > szer || nowex < - szer ) rysuj = false;
						else nowey = -wys;
					}
				}
				else nowex = -szer;
			}
		break;
		
		case 6: // prawy dol
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey < -wys){
					nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
					if(nowex > szer || nowex < - szer ) rysuj = false;
					else nowey = -wys;
				}
				else nowex = szer;
			}
		break;
		
		case 8: // gora
			if (PPR[koniec][1]-PPR[start][1] == 0) rysuj = false;
			else {
				nowey = wys;
				nowex = PPR[start][0] + (PPR[koniec][0]-PPR[start][0])*(wys-PPR[start][1])/(PPR[koniec][1]-PPR[start][1]);
				if (nowex > szer || nowex < -szer) rysuj = false;
			}
		break;
		
		case 9: // lewa gora
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(-szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey > wys){
					if (PPR[koniec][1]-PPR[start][1] == 0) rysuj = false;
					else {
						nowex = PPR[start][0] + (PPR[koniec][0]-PPR[start][0])*(wys-PPR[start][1])/(PPR[koniec][1]-PPR[start][1]);
						if(nowex > szer || nowex < - szer ) rysuj = false;
						else nowey = wys;
					}
				}
				else nowex = -szer;
			}
		break;	
		
		case 10: // prawa gora
			if (PPR[koniec][0]-PPR[start][0] == 0) rysuj = false;
			else {
				nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
				if (nowey > wys){
					nowey = PPR[start][1] + (PPR[koniec][1]-PPR[start][1])*(szer-PPR[start][0])/(PPR[koniec][0]-PPR[start][0]);
					if(nowex > szer || nowex < - szer ) rysuj = false;
					else nowey = wys;
				}
				else nowex = szer;
			}
		break;
		
	}
	return rysuj;
}


// rysowanie rzutu prostopadloscianu - funkcja bez obcinania
void RysujProstopadloscian (int **PPR, int okno[], HWND hwnd){
	HDC hdc = GetDC( hwnd );
	POINT stary_punkt;
	HPEN ZielonePioro, NiebieskiePioro, InnePioro, Pudelko;
	ZielonePioro = CreatePen( PS_SOLID, 1, RGB( 0, 255, 0 ) );
	NiebieskiePioro = CreatePen( PS_SOLID, 1, RGB( 0, 0, 255 ) );
	InnePioro = CreatePen( PS_SOLID, 1, RGB( 255, 0, 255 ) );
	Pudelko = ( HPEN ) SelectObject( hdc, InnePioro );
	int i=0;
	MoveToEx( hdc, zerox+PPR[i][0], zeroy-PPR[i][1], & stary_punkt );
	i=4;
	LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	i=1;
	MoveToEx( hdc, zerox+PPR[i][0], zeroy-PPR[i][1], & stary_punkt );
	i=5;
	LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	i=2;
	MoveToEx( hdc, zerox+PPR[i][0], zeroy-PPR[i][1], & stary_punkt );
	i=6;
	LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	Pudelko = ( HPEN ) SelectObject( hdc, ZielonePioro );
	i = 7;
	MoveToEx( hdc, zerox+PPR[i][0], zeroy-PPR[i][1], & stary_punkt );
	for (i = 4; i < 8; i++)
		LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	i=3;
	Pudelko = ( HPEN ) SelectObject( hdc, InnePioro );
	LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	Pudelko = ( HPEN ) SelectObject( hdc, NiebieskiePioro );
	for (i = 0; i < 4; i++)
		LineTo( hdc, zerox+PPR[i][0], zeroy-PPR[i][1] );
	SelectObject( hdc, Pudelko );
	DeleteObject( ZielonePioro );
	DeleteObject( NiebieskiePioro );
	DeleteObject( InnePioro );
	DeleteObject( Pudelko );
	ReleaseDC( hwnd, hdc );
}

void Przeun (double ***PPP, int x, int y, int z, int ilbud){
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			PPP[ib][iw][0] += x;
			PPP[ib][iw][1] += y;
			PPP[ib][iw][2] += z;
		}
	}	
}

// mnozenie macierzy dwuwymiarowej 4x4 przez wektor o d³ugosci 4  +  normalizacja
double* Wymnoz (double *P, double M[][4]){
	double *Mnoz = new double[4];
	int normalizacja = 0;
	for (int ik = 0; ik < 4; ik++){
		normalizacja += M[3][ik]*P[ik];
	}
	for (int iw = 0; iw < 3; iw++){
		Mnoz[iw] = 0;
		for (int ik = 0; ik < 4; ik++){
			Mnoz[iw] += M[iw][ik]*P[ik];
		}
	}
	Mnoz[3] = 1;
	return Mnoz;
}

// obrot wokol osi Z = 0
void Obroc (double ***PPP, double obrot, int ilbud){
	double *P = new double [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2];
			MacierzObrotuZ(obrot);
			P = Wymnoz(P, MOZ);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2];
		}
	}
	delete [] P;
}

// obrot wokol osi X = -d
void Pochyl (double ***PPP, double obrot, int ilbud){
	double *P = new double [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2] + d;
			MacierzObrotuX(obrot);
			P = Wymnoz(P, MOX);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2] - d;
		}
	}
	delete [] P;
}

// obrot wokol osi Y = -d
void Skrec (double ***PPP, double obrot, int ilbud){
	double *P = new double [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2] + d;
			MacierzObrotuY(obrot);
			P = Wymnoz(P, MOY);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2] - d;
		}
	}
	delete [] P;
}

// rysowanie figur po ruchu
void Rysuj (double ***PPP, int okno[], int ilbud, HWND hwnd){
	bool rys = true;
	double *P = new double [4];
	int *PR = new int [4];
	int **PPR = new int *[8];
	for (int iw = 0; iw < 8; iw++){
		PPR[iw] = new int [4];
	}
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			P[2] = PPP[ib][iw][2];
			if (P[2] > 0){
				P[0] = PPP[ib][iw][0];
				P[1] = PPP[ib][iw][1];
				PR = RzutujPunkt (P);
				PPR[iw][0] = PR[0];
				PPR[iw][1] = PR[1];
				PPR[iw][2] = PR[2];
				PPR[iw][3] = PR[3];
			}
			else rys = false;
		}
		if (rys) RysujProstopadloscianO (PPR, okno, hwnd);
	}
	delete [] P;
	delete [] PR;
	for (int iw = 0; iw < 8; iw++){
		delete [] PPR[iw];
	}
	delete [] PPR;
}
