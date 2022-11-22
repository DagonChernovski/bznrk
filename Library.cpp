// ConsoleApplication31.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "string"
#include <iostream>
#include <fstream>

bool ReadFrom(const char* filename) {
	ifstream is(filename);
	if (!is) {
		return false;
	}
	char str[100];
	while (is) 
	{
		is.getline(str, 100);
		cout << str << endl; 
	}

	is.close();
	return true;
}

using namespace std;
enum speech { unknown, noun, adj, verb, adverb, interj };

struct Slovo
{
	string word;

};


int _tmain(int argc, _TCHAR* argv[])
{
	ReadFrom("petrov.txt");
	return 0;
}

