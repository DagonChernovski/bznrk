// LongerInt.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

/******************************************************************************

							  Longer Int Counter
						Создает переменную longerint.
Размер переменной составляет 4,61*10^18 значений (примерно 46116860198000000000).

*******************************************************************************/

#include <iostream>
#include <stdio.h>
#include <math.h>
#include <stdexcept>
#include <limits.h>
using namespace std;

class longerint {
public:
	int mult, tail;
	longerint(int i) {
		mult = 0;
		tail = i;
	}
	longerint operator + (const longerint);
	longerint copy(longerint a) {
		mult = a.mult;
		tail = a.tail;
	}
	friend ostream& operator << (ostream& os, const longerint a) {
		std::string s;
		std::string output;
		int n, n2 = 0, buf = a.tail;
		unsigned int buf2 = 2147483648;
		cout << "Current value is: " << a.mult << "__" << a.tail << endl;
		while (buf2>0 && buf!=0) {
			n = (buf2 % 10) * a.mult + buf%10 + n2;
			s.append(1, char(n % 10));
			n2 = n / 10;
			buf /= 10;
			buf2 /= 10;
			cout << "Iter1: " << n << "__" << buf << endl;
		} //my string literally works like a stack, tbh. I should maybe use stack instead
		for (int i = s.length(); i > 0; i--)
			output.append(1, s[i]);
		os << s;
		return os;
	}
};

int main()
{
	int a = 156;
	cout << a << endl;
	longerint b = 352161616;
	longerint c = 321717373;
	longerint d = 15365165;
	longerint bc = b + c;
	cout << d << endl;
	b = b + 1512;
	cout << bc << endl;
	b = b + a;
	cout << b << endl;
	return 0;
}

longerint longerint::operator + (const longerint a) {
	mult += a.mult;
	try {
			if ((2147483648 - abs(mult)) < 2) throw runtime_error("Out of range");
			if ((a.tail + tail) >= 2147483648) { tail -= 2147483648; mult++; };
			if ((a.tail + tail) < 0) { tail += 2147483648; mult--; };
			tail += a.tail;
		}
		catch (runtime_error e) {
			printf(e);
		}
}
	/*if (
			((a.tail + tail) >= 2147483648) ||
			((a.tail + tail) < 0)
			) throw runtime_error("1");
	}*/
