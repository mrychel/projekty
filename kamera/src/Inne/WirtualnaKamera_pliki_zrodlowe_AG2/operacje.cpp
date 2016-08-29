#include <windows.h>
#include <math.h>
#include "operacje.h"

extern int szer, wys, zerox, zeroy, d;

float MOX[4][4] = {{1,0,0,0}, {0,2,2,0}, {0,2,2,0}, {0,0,0,1}}; // obrot wokol osi X = {{1,0,0,0}, {0,cosinus,-sinus,0}, {0,sinus,cosinus,0}, {0,0,0,1}}
float MOY[4][4] = {{2,0,2,0}, {0,1,0,0}, {2,0,2,0}, {0,0,0,1}}; // obrot wokol osi Y = {{cosinus,0,sinus,0}, {0,1,0,0}, {-sinus,0,cosinus,0}, {0,0,0,1}}
float MOZ[4][4] = {{2,2,0,0}, {2,2,0,0}, {0,0,1,0}, {0,0,0,1}}; // obrot wokol osi Z = {{cosinus,-sinus,0,0}, {sinus,-cosinus,0,0}, {0,0,1,0}, {0,0,0,1}}


// implementacja funkcji

// wyliczenie transponowanych macierzy przeksztalcen
void MacierzObrotuX (float obrot){
	float cosinus = cos(obrot);
	float sinus = sin(obrot);
	MOX[1][1] = cosinus;
	MOX[2][1] = -sinus;
	MOX[1][2] = sinus;
	MOX[2][2] = cosinus;
}
void MacierzObrotuY (float obrot){
	float cosinus = cos(obrot);
	float sinus = sin(obrot);
	MOY[0][0] = cosinus;
	MOY[2][0] = sinus;
	MOY[0][2] = -sinus;
	MOY[2][2] = cosinus;
}
void MacierzObrotuZ (float obrot){
	float cosinus = cos(obrot);
	float sinus = sin(obrot);
	MOZ[0][0] = cosinus;
	MOZ[1][0] = -sinus;
	MOZ[0][1] = sinus;
	MOZ[1][1] = cosinus;
}

// rzutowanie na plaszczyzne rzutni z=0 -> obserwator na osi "z" w punkcie {x=y=0,z=-d}
// float macierzRzutowania [4][4] = {{1,0,0,0}, {0,1,0,0}, {0,0,0,0}, {0,0,1/d,1}};
int* RzutujPunkt (float *P){
	int *RzutujPunkt = new int[4];
	RzutujPunkt[0] = (P[0] * d) / (P[2] + d) + 0.5;
	RzutujPunkt[1] = (P[1] * d) / (P[2] + d) + 0.5;
	RzutujPunkt[2] = 0;
	// oznaczenie punktow rzutowanych poza obszar rzutni - algorytm Sutherlanda-Hodgmana
	RzutujPunkt[3] = 0;
	if (RzutujPunkt[0] < -szer) RzutujPunkt[3] += 1;
	else if (RzutujPunkt[0] > szer) RzutujPunkt[3] += 2;
	if (RzutujPunkt[1] < -wys) RzutujPunkt[3] += 4;
	else if (RzutujPunkt[1] > wys) RzutujPunkt[3] += 8;
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

// odleglosc srodka ciezkosci prostopadloscianu od kamery
float Odleglosc (float ***PPP, int ib, int *w){
	float srx, sry, srz;	
	srx = (PPP[ib][w[0]][0]+PPP[ib][w[1]][0]+PPP[ib][w[2]][0]+PPP[ib][w[3]][0])/4;
	sry = (PPP[ib][w[0]][1]+PPP[ib][w[1]][1]+PPP[ib][w[2]][1]+PPP[ib][w[3]][1])/4;
	srz = (PPP[ib][w[0]][2]+PPP[ib][w[1]][2]+PPP[ib][w[2]][2]+PPP[ib][w[3]][2])/4 + d;
	return sqrt(srx * srx + sry * sry + srz * srz);
}

// wierzcholki sciany
int* WierzcholkiSciany (int sc){
	int *w = new int [4];
	switch (sc){
	case 0: // sciana Frontowa - wierzcholki 0, 1, 2, 3
		w[0] = 0; w[1] = 1; w[2] = 2; w[3] = 3;
	break;
	case 1: // sciana Tylna - wierzcholki 4, 5, 6, 7
		w[0] = 4; w[1] = 5; w[2] = 6; w[3] = 7;
	break;
	case 2: // sciana Prawa - wierzcholki 1, 2, 6, 5
		w[0] = 1; w[1] = 2; w[2] = 6; w[3] = 5;
	break;
	case 3: // sciana Lewa - wierzcholki 0, 3, 7, 4
		w[0] = 0; w[1] = 3; w[2] = 7; w[3] = 4;
	break;
	case 4: // sciana Gorna - wierzcholki 3, 2, 6, 7
		w[0] = 3; w[1] = 2; w[2] = 6; w[3] = 7;
	break;
	case 5: // sciana Dolna - wierzcholki 0, 1, 5, 4
		w[0] = 0; w[1] = 1; w[2] = 5; w[3] = 4;
	break;
	}
	return w;
}

// rysowanie rzutu prostopadloscianu z zaslanianiem - funkcja z obcinaniem
void RysujProstopadloscianZ (POINT rzut[], int *polozenie, float ***PPP, int kolor[][3], int ib, int okno[], HWND hwnd){
	bool rysuj, rysuj0;
	int nr, sciana, nr_wierzch, obciete_ostatnie, tempodl;
	float nowex, nowey;
	int obciete = 0;
	int nr_obiektu = PPP[ib][8][0];
	int *wierzcholki = new int [4];
	POINT wielokat[8];
	HDC hdc = GetDC (hwnd);
	HBRUSH pedzel;
	pedzel = CreateSolidBrush (RGB(kolor[nr_obiektu][0], kolor[nr_obiektu][1], kolor[nr_obiektu][2]));
	SelectObject( hdc, pedzel );
	int *temp = new int [2];
	int **sc = new int *[6];
	for (nr = 0; nr < 6; nr++){
		sc[nr] = new int [2];
		sc[nr][1] = nr;
	}
	
	// sortowanie scian wedlug odleglosci od kamery - od najblizszej do najdalszej
	wierzcholki = WierzcholkiSciany (0);
	sc[0][0] = Odleglosc (PPP, ib, wierzcholki) + 0.5; // sciana Frontowa
	wierzcholki = WierzcholkiSciany (1);
	sc[1][0] = Odleglosc (PPP, ib, wierzcholki) + 0.5; // sciana Tylna
	if (sc[1][0] < sc[0][0])
	{
		tempodl = sc[1][0];
		sc[1][0] = sc[0][0];
		sc[1][1] = 0;
		sc[0][0] = tempodl;
		sc[0][1] = 1;
	}

	// sciany Prawa, Lewa, Gora, Dol
	for (sciana = 2; sciana < 6; sciana++){
		wierzcholki = WierzcholkiSciany (sciana);
		sc[sciana][0] = Odleglosc (PPP, ib, wierzcholki) + 0.5;
		tempodl = sc[sciana][0];
		nr = sciana;
		while (nr > 0 && tempodl < sc[nr-1][0]){
			sc[nr][0] = sc[nr-1][0];
			sc[nr][1] = sc[nr-1][1];
			nr--;
		}
		sc[nr][0] = tempodl;
		sc[nr][1] = sciana;
	}
	// wyswietlanie scian od najdalszej
	for (nr = 5; nr >= 0; nr--){
		for (nr_wierzch = 0; nr_wierzch < 8; nr_wierzch++){
			wielokat[nr_wierzch].x = 0;
			wielokat[nr_wierzch].y = 0;
		}
		nr_wierzch = 0;
		wierzcholki = WierzcholkiSciany (sc[nr][1]);
		// krawedz pomiedzy wierzcholkami 3-0
		rysuj0 = rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[0], wierzcholki[3]);
		if (rysuj){
			wielokat[nr_wierzch].x = zerox + nowex + 0.5;
			wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
			nr_wierzch++;
			rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[3], wierzcholki[0]);
			wielokat[nr_wierzch].x = zerox + nowex + 0.5;
			wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
			nr_wierzch++;
		}
		else {
			obciete = polozenie[wierzcholki[0]];
		}
		for (int iw = 0; iw < 3; iw++){
			obciete_ostatnie = obciete;
			switch (obciete_ostatnie){
				case 0:
					rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw], wierzcholki[iw+1]);
					wielokat[nr_wierzch].x = zerox + nowex + 0.5;
					wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
					nr_wierzch++;
				break;
				case 1:
					rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw+1], wierzcholki[iw]);
					if (rysuj){
						if (nowex == -szer){
							wielokat[nr_wierzch].x = zerox + nowex + 0.5;
							wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
							nr_wierzch++;
						}
						else {
							if (nowey == wys){
								if (nr_wierzch != 0)
								{
									wielokat[nr_wierzch].x = zerox - szer;
									wielokat[nr_wierzch].y = zeroy - wys;
									nr_wierzch++;
								}
								wielokat[nr_wierzch].x = zerox + nowex + 0.5;
								wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
								nr_wierzch++;
							}
							else {
								if (nowey == -wys){
									if (nr_wierzch != 0)
									{
										wielokat[nr_wierzch].x = zerox - szer;
										wielokat[nr_wierzch].y = zeroy + wys;
										nr_wierzch++;
									}
									wielokat[nr_wierzch].x = zerox + nowex + 0.5;
									wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
									nr_wierzch++;
								}
							}
						}
						rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw], wierzcholki[iw+1]);
						wielokat[nr_wierzch].x = zerox + nowex + 0.5;
						wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
						nr_wierzch++;
					}
				break;
				case 2:
					rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw+1], wierzcholki[iw]);
					if (rysuj){
						if (nowex == szer){
							wielokat[nr_wierzch].x = zerox + nowex + 0.5;
							wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
							nr_wierzch++;
						}
						else {
							if (nowey == wys){
								if (nr_wierzch != 0)
								{
									wielokat[nr_wierzch].x = zerox + szer;
									wielokat[nr_wierzch].y = zeroy - wys;
									nr_wierzch++;
								}
								wielokat[nr_wierzch].x = zerox + nowex + 0.5;
								wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
								nr_wierzch++;
							}
							else {
								if (nowey == -wys){
									if (nr_wierzch != 0)
									{
										wielokat[nr_wierzch].x = zerox + szer;
										wielokat[nr_wierzch].y = zeroy + wys;
										nr_wierzch++;
									}
									wielokat[nr_wierzch].x = zerox + nowex + 0.5;
									wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
									nr_wierzch++;
								}
							}
						}
						rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw], wierzcholki[iw+1]);
						wielokat[nr_wierzch].x = zerox + nowex + 0.5;
						wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
						nr_wierzch++;
					}
				break;
				case 4:
					rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw+1], wierzcholki[iw]);
					if (rysuj){
						if (nowey == -wys){
							wielokat[nr_wierzch].x = zerox + nowex + 0.5;
							wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
							nr_wierzch++;
						}
						else {
							if (nowex == szer){
								if (nr_wierzch != 0)
								{
									wielokat[nr_wierzch].x = zerox + szer;
									wielokat[nr_wierzch].y = zeroy + wys;
									nr_wierzch++;
								}
								wielokat[nr_wierzch].x = zerox + nowex + 0.5;
								wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
								nr_wierzch++;
							}
							else {
								if (nowex == -szer){
									if (nr_wierzch != 0)
									{
										wielokat[nr_wierzch].x = zerox - szer;
										wielokat[nr_wierzch].y = zeroy + wys;
										nr_wierzch++;
									}
									wielokat[nr_wierzch].x = zerox + nowex + 0.5;
									wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
									nr_wierzch++;
								}
							}
						}
						rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw], wierzcholki[iw+1]);
						wielokat[nr_wierzch].x = zerox + nowex + 0.5;
						wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
						nr_wierzch++;
					}
				break;
				case 8:
					rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw+1], wierzcholki[iw]);
					if (rysuj){
						if (nowey == wys){
							wielokat[nr_wierzch].x = zerox + nowex + 0.5;
							wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
							nr_wierzch++;
						}
						else {
							if (nowex == szer){
								if (nr_wierzch != 0)
								{
									wielokat[nr_wierzch].x = zerox + szer;
									wielokat[nr_wierzch].y = zeroy - wys;
									nr_wierzch++;
								}
								wielokat[nr_wierzch].x = zerox + nowex + 0.5;
								wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
								nr_wierzch++;
							}
							else {
								if (nowex == -szer){
									if (nr_wierzch != 0)
									{
										wielokat[nr_wierzch].x = zerox - szer;
										wielokat[nr_wierzch].y = zeroy - wys;
										nr_wierzch++;
									}
									wielokat[nr_wierzch].x = zerox + nowex + 0.5;
									wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
									nr_wierzch++;
								}
							}
						}
						rysuj = ObetnijKrawedzZ (rzut, polozenie, nowex, nowey, obciete, wierzcholki[iw], wierzcholki[iw+1]);
						wielokat[nr_wierzch].x = zerox + nowex + 0.5;
						wielokat[nr_wierzch].y = zeroy - nowey + 0.5;
						nr_wierzch++;
					}
				break;
				default:
					nr_wierzch = 0;
				} // end switch
				if (rysuj) obciete_ostatnie = obciete;
				if (!rysuj || !rysuj0){
					if (((wielokat[0].y == zeroy-wys || wielokat[0].y == zeroy+wys) && (wielokat[0].x != zerox-szer && wielokat[0].x != zerox+szer)) && 
						((wielokat[nr_wierzch-1].x == zerox-szer || wielokat[nr_wierzch-1].x == zerox+szer) && (wielokat[nr_wierzch-1].y != zeroy-wys && wielokat[nr_wierzch-1].y != zeroy+wys)))
					{
							wielokat[nr_wierzch].x = wielokat[nr_wierzch-1].x;
							wielokat[nr_wierzch].y = wielokat[0].y;
							nr_wierzch++;
					}
					else {
						if (((wielokat[0].x == zerox-szer || wielokat[0].x == zerox+szer) && (wielokat[0].y != zeroy-wys && wielokat[0].y != zeroy+wys)) &&
							((wielokat[nr_wierzch-1].y == zeroy-wys || wielokat[nr_wierzch-1].y == zeroy+wys) && (wielokat[nr_wierzch-1].x != zerox-szer && wielokat[nr_wierzch-1].x != zerox+szer)))
						{
								wielokat[nr_wierzch].y = wielokat[nr_wierzch-1].y;
								wielokat[nr_wierzch].x = wielokat[0].x;
								nr_wierzch++;
						}
					}
				}
		}
		Polygon (hdc, wielokat, nr_wierzch);
	}

	// kasowanie
	for (nr = 0; nr < 6; nr++){
		delete [] sc[nr];
	}
	delete [] sc;
	//delete [] temp;
	delete [] wierzcholki;
	DeleteObject (pedzel);
	ReleaseDC (hwnd, hdc);
	
}

// obcinanie krawedzi do rozmiarow okna (obcina koniec)
bool ObetnijKrawedzZ (POINT rzut[], int *polozenie, float &nowex, float &nowey, int &obciete, int start, int koniec){
	bool rysuj = true;
	if ((polozenie[start] & polozenie[koniec]) == 0)
	{
		switch (polozenie[koniec])
		{
			case 0: // srodek
				nowex = rzut[koniec].x;
				nowey = rzut[koniec].y;
				obciete = 0;
			break;
		
			case 1: // lewy bok
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowex = -szer;
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(-szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys) rysuj = false;
					else obciete = 1;
				}
			break;
		
			case 2: // prawy bok
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowex = szer;
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys) rysuj = false;
					else obciete = 2;
				}
			break;
		
			case 4: // dol
				if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
				else {
					nowey = -wys;
					nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(-wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
					if (nowex > szer || nowex < -szer) rysuj = false;
					else obciete = 4;
				}
			break;
			
			case 5: // lewy dol
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(-szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys){
						if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
						else {
							nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(-wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
							if(nowex > szer || nowex < - szer ) rysuj = false;
							else {
								nowey = -wys;
								obciete = 4;
							}
						}
					}
					else {
						nowex = -szer;
						obciete = 1;
					}
				}
			break;
		
			case 6: // prawy dol
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys){
						if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
						else {
							nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(-wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
							if(nowex > szer || nowex < - szer ) rysuj = false;
							else {
								nowey = -wys;
								obciete = 4;
							}
						}
					}
					else {
						nowex = szer;
						obciete = 2;
					}
				}
			break;
		
			case 8: // gora
				if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
				else {
					nowey = wys;
					nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
					if (nowex > szer || nowex < -szer) rysuj = false;
					else obciete = 8;
				}
			break;
		
			case 9: // lewa gora
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(-szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys){
						if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
						else {
							nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
							if(nowex > szer || nowex < - szer ) rysuj = false;
							else {
								nowey = wys;
								obciete = 8;
							}
						}
					}
					else {
						nowex = -szer;
						obciete = 1;
					}
				}
			break;	
		
			case 10: // prawa gora
				if (rzut[koniec].x-rzut[start].x == 0) rysuj = false;
				else {
					nowey = rzut[start].y + 1.0*(rzut[koniec].y-rzut[start].y)*(szer-rzut[start].x)/(rzut[koniec].x-rzut[start].x);
					if (nowey > wys || nowey < -wys){
						if (rzut[koniec].y-rzut[start].y == 0) rysuj = false;
						else {
							nowex = rzut[start].x + 1.0*(rzut[koniec].x-rzut[start].x)*(wys-rzut[start].y)/(rzut[koniec].y-rzut[start].y);
							if(nowex > szer || nowex < - szer ) rysuj = false;
							else {
								nowey = wys;
								obciete = 8;
							}
						}
					}
					else {
						nowex = szer;
						obciete = 2;
					}
				}
			break;
		
		}
	}
	else rysuj = false;
	return rysuj;
}

// przesuniecie wzdluz osi X, Y, Z
void Przeun (float ***PPP, int x, int y, int z, int ilbud){
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			PPP[ib][iw][0] += x;
			PPP[ib][iw][1] += y;
			PPP[ib][iw][2] += z;
		}
	}	
}

// powrot do pozycji startowej
void Home (float ***PPP, int O[][9][3], int ilbud){
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			for (int ik = 0; ik < 3; ik++){
				PPP[ib][iw][ik] = O[ib][iw][ik];
			}
		}
		SortujObiekty (PPP, ib);
	}

}

// obrot wokol osi Z = 0
void Obroc (float ***PPP, float obrot, int ilbud){
	float *P = new float [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		PPP[ib][8][2] = 0;
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2];
			MacierzObrotuZ(obrot);
			P = Wymnoz(P, MOZ);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2];
			PPP[ib][8][2] += PPP[ib][iw][2];
		}
		SortujObiekty (PPP, ib);
	}
	delete [] P;
}

// obrot wokol osi X = -d
void Pochyl (float ***PPP, float obrot, int ilbud){
	float *P = new float [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		PPP[ib][8][2] = 0;
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2] + d;
			MacierzObrotuX(obrot);
			P = Wymnoz(P, MOX);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2] - d;
			PPP[ib][8][2] += PPP[ib][iw][2];
		}
		SortujObiekty (PPP, ib);
	}
	delete [] P;
}

// obrot wokol osi Y = -d
void Skrec (float ***PPP, float obrot, int ilbud){
	float *P = new float [4];
	P[4] = 1;
	for (int ib = 0; ib < ilbud; ib++){
		PPP[ib][8][2] = 0;
		for (int iw = 0; iw < 8; iw++){
			P[0] = PPP[ib][iw][0];
			P[1] = PPP[ib][iw][1];
			P[2] = PPP[ib][iw][2] + d;
			MacierzObrotuY(obrot);
			P = Wymnoz(P, MOY);
			PPP[ib][iw][0] = P[0];
			PPP[ib][iw][1] = P[1];
			PPP[ib][iw][2] = P[2] - d;
			PPP[ib][8][2] += PPP[ib][iw][2];
		}
		SortujObiekty (PPP, ib);
	}
	delete [] P;
}

// mnozenie macierzy dwuwymiarowej 4x4 przez wektor o d³ugosci 4  +  normalizacja
float* Wymnoz (float *P, float M[][4]){
	float *Mnoz = new float[4];
	float normalizacja = 0;
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

// sortowanie obiektow w kolejnosci malejacej
void SortujObiekty (float ***PPP, int nrbud){
	float tempodl;
	PPP[nrbud][8][2] = PPP[nrbud][8][2] / 8;
	if(nrbud > 0)
	{
		float **temp = new float *[9];
		temp = PPP[nrbud];
		tempodl = PPP[nrbud][8][2];
		while (nrbud > 0 && tempodl > PPP[nrbud-1][8][2]){
			PPP[nrbud] = PPP[nrbud-1];
			nrbud--;
		}
		PPP[nrbud] = temp;
		//delete [] temp;
	}
}

// rysowanie figur po ruchu
void Rysuj (float ***PPP, int kolor[][3], int okno[], int ilbud, HWND hwnd){
	bool rys = true;
	float *P = new float [4];
	int *PR = new int [4];
	POINT rzut[8];
	int *polozenie = new int [8];
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			P[2] = PPP[ib][iw][2];
			if (P[2] > 0){
				P[0] = PPP[ib][iw][0];
				P[1] = PPP[ib][iw][1];
				PR = RzutujPunkt (P);
				rzut[iw].x = PR[0];
				rzut[iw].y = PR[1];
				polozenie[iw] = PR[3];
			}
			else rys = false;
		}
		if (rys) RysujProstopadloscianZ (rzut, polozenie, PPP, kolor, ib, okno, hwnd);
	}
	delete [] P;
	delete [] PR;
	delete [] polozenie;
}

