/******************************************************************************

                              Online C++ Compiler.
               Code, Compile, Run and Debug C++ program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <iostream>
#include <stdio.h>
#include <math.h>
#include <limits.h>
#define INT_LIMIT 2147483647;

class longerint {
	int mult, tail;
	public:
	longerint(int i) {
		tail=i;
	}
	longerint operator + (const int);
	longerint operator + (const longerint);
	longerint copy(longerint a) {
	    mult=a.mult;
	    tail=a.tail;
	}
};

int main()
{
	//printf("%d", numeric_limits<int>::max());
	return 0;
}

longerint operator + (const int a) {
    try {
        if ((a+tail)>INT_LIMIT || (a+tail)<INT_LIMIT) throw runtime_error();
    }
    catch {
        if (a>0 && tail>0) {a-=INT_LIMIT; mult++};
        if (a<0 && tail<0) {a+=INT_LIMIT; mult--};
    }
    tail+=a;
}

longerint operator + (const longerint a) {
    mult+=a.mult;
    try {
        if ((a.tail+tail)>INT_LIMIT || (a.tail+tail)<INT_LIMIT) throw runtime_error();
    }
    catch {
        if (a.tail>0 && tail>0) {a.tail-=INT_LIMIT; mult++};
        if (a.tail<0 && tail<0) {a+=INT_LIMIT; mult--};
    }
    tail+=a.tail;
}

ostream& operator << (ostream& os, const longerint a) {
    std::string s;
    int buf=a.tail;
    e=1;
    while (summation) {
        INT_LIMIT/e%10
    }
}
