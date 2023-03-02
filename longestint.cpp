// LongerInt.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

/******************************************************************************

							  Longer Int Counter
						Создает переменную longerint.
Размер переменной составляет 4,61*10^18 значений (примерно 46116860198000000000).

*******************************************************************************/

/*
Method 1:
Make a longer int of int*int size. Hence I should make 2 variables for smaller and bigger number.
Output will include a lot of useless multiplication if I don't use a log10 number as a limit for the next one. 
So in output I will just write a bigger number and this will be efficient ( I DO CARE OF EFFICIENCY you fucks).
So the max number I could use is 10^18.
Method 2:
Make every variable a vector or array of chars. Or basically reallocate a string, that's why CPUs love C++.

*/
#include <iostream>
#include <stdio.h>
#include <math.h>
#include <stdexcept>
#include <limits.h>
#define LIM 2147483648 //why the fuck I need that?
using namespace std;

class longestint {
protected:
	//char* mynumber; //WTF why not std string? I needcha try both though.
	//mynumber=(char*)calloc(10, sizeof(char)) ; //stored invertedly no worries
	std::string mynumber;
	size_t length=0;
public:
	longestint(int i) { //specific constructor
		do {
			mynumber[length] = i % 10; i /= 10; length+=sizeof(char);
		} while (i != 0);
	}
	longestint(char t[]) 
	{
		//if (sizeof(t) > sizeof(mynumber)) {int abs = sizeof(t) - sizeof(mynumber);
		mynumber = t;
	}
	longestint() { //default constructor
		mynumber[0] = 0;
		//возможно придется юзать null-озаменитель но хер знает как это в плюсах будет работать
	}
	longestint operator + (const longestint);
	longestint operator * (const longestint);
	longestint operator / (const int a);
	friend ostream& operator << (ostream& os, const longestint a) {
		for (int i = a.length - 1; 
			j = 0; 
			i >= 0; 
			i--; 
			j++;)
			os[j] = a[i];
		return os;
	}
};

int main()
{
	int a = 156;
	cout << a << endl;
	longestint b = 352161616;
	longestint c = 321717373;
	longestint d = 15365165;
	longestint bc = b + c;
	cout << d << endl;
	b = b + 1512;
	cout << bc << endl;
	b = b + a;
	cout << b << endl;
	longestint aa = 1000000;
	longestint bb = 0;
	bb = aa * 1000000;
	cout << bb << endl;
	return 0;
}

longestint longestint::operator + (const longestint a) {
	longestint ret;
	std::string b=this->mynumber; //easier to use #define tbh.
	size_t max, min; //also maybe use pointers
	if (a.length > b.length) 
	{ min = b.length; max = a.mynumber.length; }
	else 
	{ max = b.length; min = a.mynumber.length; }
	bool next = 0;
	short a_int, b_int;
	for (int i = 0; i < min; i++) {
		a_int = (short)a.mynumber[i] - 48;
		b_int = (short)b[i] - 48;
		ret.mynumber[i] = (char)((a_int + b_int) % 10+48); ret.length++; //maybe easier to rewrite using an array of byte numbers idk
		next = (a_int + b_int) / 10;
	}
	if (a.length > b.length) b = a.mynumber;
	for (int i = min + 1; i < max; i++) {
		a_int = (short)b[i]-48;
		if (next) a_int++; ret.mynumber[i] = a_int%10; if (a_int < 10) next = false;
	}
	return ret;
}
longestint longestint::operator * (const longestint a) {
	longestint ret;
	std::string b = this->mynumber; //easier to use #define tbh.
	size_t max, min; //also maybe use pointers
	bool avb;
	if (a.length > b.length)
	{
		avb = true;  min = b.length; max = a.mynumber.length;
	}
	else
	{
		avb = false; max = b.length; min = a.mynumber.length;
	}
	bool next = 0;
	short a_int, b_int;
	for (int i = 0; i < min; i++) {
		a_int = (short)a.mynumber[i] - 48;
		b_int = (short)b[i] - 48;
		ret.mynumber[i] = (char)((a_int * b_int) % 10 + 48); ret.length++; //maybe easier to rewrite using an array of byte numbers idk
		next = (a_int * b_int) / 10;
	}
	for (int i = min + 1; i < max; i++) {
		a_int = (short)a.mynumber[i] - 48;
		if (next) a_int++; if (a_int > 10) ret.mynumber[i] = 0; else next = false;
	}
	return ret;
}
/*longestint longestint::operator / (const int a) {
	longestint ret;
	ret = copy(*this);
	ret.mult /= a;
	int b = mult % a;
	try {
		if ((LIM - abs(ret.mult)) < 2) throw runtime_error("Out of range"); //Actual (real).
		while (b > 0) {
			if (ret.tail + tail > LIM) {
				ret.tail -= LIM; b--;
				if ((ret.tail + tail) > LIM) { tail -= LIM; mult++; };
				ret.tail += tail;
			}
		}
	}
	catch (runtime_error e) {
		cout << "Exception: " << endl << e.what();
	}
	return ret;
}*/
