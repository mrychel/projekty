#include <windows.h>
#include "operacje.h"

LPCWSTR nazwaOkna = L"MojeOkno";

//-----------------------------------------------------------------------------------------------------------------------------------------------
// DEFINICJE PARAMETROW OKNA, ODLEGLOSCI OD RZUTNI ORAZ ODLEGLOSCI DO OBIEKTOW
//-----------------------------------------------------------------------------------------------------------------------------------------------

// definicja rozmiarow okna
int const rozm_okna_x = 1300; // poczatkowo ustawione na 1300
int const rozm_okna_y = 600;  // poczatkowo ustawione na 600
int const brzegx = 50;		  // poczatkowo ustawione na 50
int const brzegy = 50;		  // poczatkowo ustawione na 50
// definicja polozenia obserwatora na osi z (odleglosci od rzutni - srodek rzutni w srodku wspolrzednych)
int dd = 300;				  // poczatkowo ustawione na 300
// definicja odleglosci na osi z linii obiektow od srodka wspolrzednych (odleglosc od rzutni)
int zz = 1000;
// definicja skokow
int skok = 10;  // dla translacji po osiach - skok przesuniecia w stosunku wielkosci okna
int skoko = 24; // dla obrotow - skok kata w stosunku do kata pelnego
// definicja iloœci, polozenia i rozmiarow budynkow
//									 polozenie			   rozmiar
int const ilbud = 4; //             x     y     z        x     y     z
int const bud[ilbud][2][3] =  {{{  500,    0,  500}, {  200,  500,  100}},
							   {{ -700,    0,  500}, {  300,  500,  100}},
							   {{ -100,    0,  500}, {  200,  700,  200}},
							   {{ -500,    0,  300}, {  200,  400,  100}},
							  };

//-----------------------------------------------------------------------------------------------------------------------------------------------
// DEFINICJE PARAMETROW OKNA, ODLEGLOSCI OD RZUTNI ORAZ ODLEGLOSCI DO OBIEKTOW
//-----------------------------------------------------------------------------------------------------------------------------------------------



// obliczenie parametrow okna oraz poczatkowych danych
int szer = rozm_okna_x/2 - brzegx;
int wys = rozm_okna_y/2 - brzegy;
int zerox = rozm_okna_x/2;
int zeroy = rozm_okna_y/2;
int okno[4] = {brzegx, rozm_okna_x-brzegx, brzegy, rozm_okna_y-brzegy};
int x = 0;
int y = 0;
int z = 0;
int d = dd;
double kat = (2*3.1415926535)/skoko; // skok kata w radianach
double obrot;
bool zmiana = false;
int wart;
int O[ilbud][8][3]; // deklaracja tablic na wierzcholki budynkow
double ***PPP = new double **[ilbud];

MSG Komunikat;
RECT okienko;

LRESULT CALLBACK WndProc( HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam );

int WINAPI WinMain( HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow )
{

// wypelnienie tablicy wierzcholkow budynkow
for (int ib = 0; ib < ilbud; ib++){
	O[ib][0][0] = O[ib][3][0] = O[ib][4][0] = O[ib][7][0] = bud[ib][0][0]; // wspolrzedna x
	O[ib][1][0] = O[ib][2][0] = O[ib][5][0] = O[ib][6][0] = bud[ib][0][0] + bud[ib][1][0]; // wspolrzedna x
	O[ib][0][1] = O[ib][1][1] = O[ib][4][1] = O[ib][5][1] = bud[ib][0][1]; // wspolrzedna y
	O[ib][2][1] = O[ib][3][1] = O[ib][6][1] = O[ib][7][1] = bud[ib][0][1] + bud[ib][1][1]; // wspolrzedna y
	O[ib][0][2] = O[ib][1][2] = O[ib][2][2] = O[ib][3][2] = bud[ib][0][2] + zz; // wspolrzedna z
	O[ib][4][2] = O[ib][5][2] = O[ib][6][2] = O[ib][7][2] = bud[ib][0][2] + bud[ib][1][2] + zz; // wspolrzedna z

}
// format danych budynkow
// {{{x1, y1, z1}, {x1+sz1, y1, z1}, {x1+sz1, y1+wys1, z1}, {x1, y1+wys1, z1}, {x1, y1, z1+gl1}, {x1+sz1, y1, z1+gl1}, {x1+sz1, y1+wys1, z1+gl1}, {x1, y1+wys1, z1+gl1}},
//  {{x2, y2, z2}, {x2+sz2, y2, z2}, {x2+sz2, y2+wys2, z2}, {x2, y2+wys2, z2}, {x2, y2, z2+gl2}, {x2+sz2, y2, z2+gl2}, {x2+sz2, y2+wys2, z2+gl2}, {x2, y2+wys2, z2+gl2}}};

// deklaracja tablic dynamicznych i przepisanie danych poczatkowych
for (int ib = 0; ib < ilbud; ib++){
		PPP[ib] = new double *[8];
		for (int iw = 0; iw < 8; iw++){
			PPP[ib][iw] = new double [3];
			for (int ik = 0; ik < 3; ik++){
				PPP[ib][iw][ik] = O[ib][iw][ik];
				wart = PPP[ib][iw][ik]; // testowe
			}
		}
	}

    // WYPE£NIANIE STRUKTURY OKNA
    WNDCLASSEX wc;
    wc.cbSize = sizeof( WNDCLASSEX );
    wc.style = CS_DBLCLKS;
    wc.lpfnWndProc = WndProc;
    wc.cbClsExtra = 0;
    wc.cbWndExtra = 0;
    wc.hInstance = hInstance;
    wc.hIcon = LoadIcon( NULL, IDI_APPLICATION );
    wc.hCursor = LoadCursor( NULL, IDC_ARROW );
    wc.hbrBackground =( HBRUSH )( COLOR_WINDOW + 1 );
    wc.lpszMenuName = NULL;
    wc.lpszClassName = nazwaOkna;
    wc.hIconSm = LoadIcon( NULL, IDI_APPLICATION );
    
    // REJESTROWANIE KLASY OKNA
    if( !RegisterClassEx( & wc ) )
    {
		MessageBox( NULL, L"Okno nie przesz³o przegl¹du technicznego!", L"Niestety...", MB_ICONEXCLAMATION | MB_OK );
        return 1;
    }
    
    // TWORZENIE OKNA
    HWND hwnd;
    
    hwnd = CreateWindowEx( WS_EX_CLIENTEDGE, nazwaOkna, L"Kamera wirtualna - projekt z Grafiki Komputerowej - PW OKNO AG", WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, rozm_okna_x, rozm_okna_y, NULL, NULL, hInstance, NULL );
   
	if( hwnd == NULL )
    {
        MessageBox( NULL, L"Okno siê zbuntowa³o!", L"Nie ma okna :-(", MB_ICONEXCLAMATION );
        return 1;
    }
    
    ShowWindow( hwnd, nCmdShow );
    UpdateWindow( hwnd );
	
	// przepisanie poczatkowych prostopadloscianow do tablicy dynamicznej
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			for (int ik = 0; ik < 3; ik++){
				PPP[ib][iw][ik] = O[ib][iw][ik];
				wart = PPP[ib][iw][ik];
			}
		}	
	}
	SetRect( & okienko, 0, 0, rozm_okna_x, rozm_okna_y);	
	RysujOs (okno, hwnd);
	Rysuj (PPP, okno, ilbud, hwnd); // rysowanie prostopadloscianu
	
	// petla komunikatow
    while( GetMessage( & Komunikat, NULL, 0, 0 ) )
    {
        TranslateMessage( & Komunikat );
        DispatchMessage( & Komunikat );
    }
    
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	// kasowanie zmiennych dynamicznych
	// ---------------------------------------------------------------------------------------------------------------------------------------------------     
   
	for (int ib = 0; ib < ilbud; ib++){
		for (int iw = 0; iw < 8; iw++){
			delete [] PPP[ib][iw];
		}
		delete [] PPP[ib];
	}
	delete [] PPP;
	    
	return Komunikat.wParam;
}

// OBSLUGA ZDARZEN
LRESULT CALLBACK WndProc( HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam )
{
    switch( msg )
    {
    case WM_CLOSE:
    	DestroyWindow( hwnd );
    break;
        
    case WM_DESTROY:
    	PostQuitMessage( 0 );
    break;
	
	// kontrola nacisniecia klawiszy
	case WM_KEYDOWN:
	{
    	switch(( int ) wParam )
    	{
    	
		case VK_HOME: // powrot do ustawien poczatkowych
        	d = dd;
			for (int ib = 0; ib < ilbud; ib++){
				for (int iw = 0; iw < 8; iw++){
					for (int ik = 0; ik < 3; ik++){
				PPP[ib][iw][ik] = O[ib][iw][ik];
					}
				}
			}
			zmiana = true;
        break;

		case VK_LEFT: // przemieszczenie w lewo
        	x = szer/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;
        
        case VK_RIGHT: // przemieszczenie w prawo
        	x = - szer/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;
        
        case VK_UP: // przemieszczenie w gore
        	y = - wys/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;
        
        case VK_DOWN: // przemieszczenie w dol
        	y = wys/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;

		case VK_PRIOR: // zblizenie do obiektu
        	z = - zz/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;
        
        case VK_NEXT: // oddalenie od obiektu
        	z = zz/skok;
        	Przeun (PPP, x, y, z, ilbud);
			zmiana = true;
        break;
        
		case VK_ADD: // zoom in
        	d = d + dd/skok;
			zmiana = true;
        break;
        
        case VK_SUBTRACT: // zoom out
        	if (dd/skok < d) d = d - dd/skok;
			zmiana = true;
        break;

		case 0x45: // obrot pionowy aparatu - przeciwnie do zegara = obrot wokol osi "z" - klawisz "E"
        	obrot = kat;
			Obroc(PPP, obrot, ilbud);
			zmiana = true;
        break;

		case 0x51: // obrot pionowy aparatu - przeciwnie do zegara = obrot wokol osi "z" - klawisz "Q"
        	obrot = -kat;
			Obroc(PPP, obrot, ilbud);
			zmiana = true;
        break;

		case 0x57: // przechylenie aparatu w gore - "spojrzenie w strone nieba" = obrot wokol osi "x+d" - klawisz "W"
        	obrot = kat;
			Pochyl(PPP, obrot, ilbud);
			zmiana = true;
        break;

		case 0x53: // pochylenie aparatu w dol - "spojrzenie w strone ziemi" = obrot wokol osi "x+d" - klawisz "S"
        	obrot = -kat;
			Pochyl(PPP, obrot, ilbud);
			zmiana = true;
        break;

		case 0x44: // obrot aparatu w prawo = obrot wokol osi "y+d" - klawisz "D"
        	obrot = kat;
			Skrec(PPP, obrot, ilbud);
			zmiana = true;
        break;

		case 0x41: // obrot aparatu w lewo = obrot wokol osi "y+d" - klawisz "A"
        	obrot = -kat;
			Skrec(PPP, obrot, ilbud);
			zmiana = true;
        break;

    	}
    	if (zmiana){
			HDC hdc = GetDC( hwnd );
			FillRect( hdc, & okienko,( HBRUSH ) GetStockObject( LTGRAY_BRUSH ) );
			ReleaseDC( hwnd, hdc );
			Rysuj (PPP, okno, ilbud, hwnd);
			RysujBrzeg (okno, hwnd);
			RysujOs (okno, hwnd);
		}
    	x = y = z = 0;
		zmiana = false;
	}
	break;
	
	case WM_PAINT: // na wypadek minimalizacji okna lub przykrycia go innym oknem ...
	{
    	PAINTSTRUCT ps; // deklaracja struktury
    	HDC hdc = BeginPaint( hwnd, & ps );
    	Rysuj (PPP, okno, ilbud, hwnd);
		RysujBrzeg (okno, hwnd);
		RysujOs (okno, hwnd);
    	EndPaint( hwnd, & ps ); // zwalniamy hdc
	}
	break;
	
	case VK_ESCAPE:
       	DestroyWindow( hwnd );
    break;
	
    default:
    return DefWindowProc( hwnd, msg, wParam, lParam );
    }
    
    return 0;
}
