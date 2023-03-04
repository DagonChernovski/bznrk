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
#include <sstream>
#define LIM 2147483648 //why the fuck I need that?
using namespace std;

template <typename T>std::string toString(T val)
{
	std::ostringstream oss;
	oss << val;
	return oss.str();
}

class longestint {
protected:
	//char* mynumber; //WTF why not std string? I needcha try both though.
	//mynumber=(char*)calloc(10, sizeof(char)) ; //stored invertedly no worries
	std::string mynumber;
	size_t length = 0;
public:
	longestint(int i) { //specific constructor
		mynumber.clear();
		std::string c;
		mynumber = toString(i);
		std::reverse(mynumber.begin(), mynumber.end());
		//int ii = i;
		//do {
		//	c=char(ii % 10 + 48);
		//	mynumber.append(c);
		//	//cout << c << "   " << c << endl; //debug
		//	ii /= 10; length += sizeof(char);
		//} while (ii != 0);
	}
	longestint(char t[])
	{
		//if (sizeof(t) > sizeof(mynumber)) {int abs = sizeof(t) - sizeof(mynumber);
		mynumber = t;
	}
	longestint() { //default constructor
		mynumber.append(" ");
		//возможно придется юзать null-озаменитель но хер знает как это в плюсах будет работать
	}
	longestint operator + (const longestint);
	longestint operator * (const longestint);
	longestint operator / (const longestint);
	friend ostream& operator << (ostream& os, const longestint a) {
		std::string copy = a.mynumber;
		std::reverse(copy.begin(), copy.end());
		os << copy;
		return os;
	}
};

int main()
{
	longestint a = 123466666;
	longestint b = 636271263;
	longestint c = a + b;
	cout << a << " " << b << " " << c  << endl;
	c = c + c;
	cout << c << endl;
	c = c + c + c;
	cout << c << endl;
	return 0;
}

longestint longestint::operator + (const longestint a) {
	longestint ret;
	std::string b = this->mynumber; //easier to use #define tbh.
	size_t max, min; //also maybe use pointers
	if (a.length > b.length())
	{
		min = b.length(); max = a.mynumber.length();
	}
	else
	{
		max = b.length(); min = a.mynumber.length();
	}
	bool next = 0;
	short a_int, b_int;
	std::string s;
	for (int i = 0; i < min; i++) {
		a_int = (short)a.mynumber[i] - 48;
		b_int = (short)b[i] - 48;
		s = toString((char)((a_int + b_int + next) % 10 + 48)); ret.mynumber.append(s); ret.length++; //maybe easier to rewrite using an array of byte numbers idk
		next = (a_int + b_int) / 10;
		printf("ITER %d --- %d %d %d\n", i, a_int, b_int, next);
	}
	if (a.length > b.length()) b = a.mynumber;
	for (int i = min; i < max; i++) {
		a_int = (short)b[i] - 48;
		s = toString((char)(a_int % 10));
		ret.mynumber.append(s);
		if (next) a_int++; ret.mynumber[i] = a_int % 10; if (a_int < 10) next = false;
	}
	if (next) ret.mynumber.append(toString(next));
	return ret;
}
longestint longestint::operator * (const longestint a) {
	longestint ret;
	std::string b = this->mynumber; //easier to use #define tbh.
	size_t max, min; //also maybe use pointers
	bool next = 0;
	short a_int, b_int;
	std::string s;
	for (int i = 0; i < a.length; i++) {
		for (int j = 0; j < b.length(); j++) {
			a_int = (short)a.mynumber[i] - 48;
			b_int = (short)b[j] - 48;
			s = toString((char)(a_int * b_int + next) % 10 + 48); ret.mynumber.append(s); ret.length++; //maybe easier to rewrite using an array of byte numbers idk
			next = (a_int * b_int) / 10;
			printf("ITER %d --- %d %d %d\n", i, a_int, b_int, next);
		}
	}
	if (a.length > b.length()) b = a.mynumber;
	if (next) ret.mynumber.append(toString(next));
	return ret;
}
/*longestint longestint::operator / (const int a) {
	longestint ret;
	
	return ret;
}*/
