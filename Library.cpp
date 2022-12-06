// WerdFinder.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include "stdafx.h"
#include <iostream>
#include "cstdlib"
#include "string"
#include <fstream>
#include <vector>
#include <set>

//список команд
/*
1. Введите директорию для ввода слов (файл с расширением txt)
2. Ввод слова
3. Удаление слова
4. Сортировать список слов (желательно!)
5. !!!Поиск слов по размеру и маске
*/

using namespace std;
enum speech { unknown, noun, adj, verb, adverb, interj };

struct Slovo
{
	string word;
	unsigned int length = word.length();
	speech part;
};

int word_count(vector<Slovo> w)
{
	int i = 0; while (w[i].word != "") i++;
	return i;
}

void ReadFrom(const char* filename[], vector<Slovo> *w, speech what) {
	printf("oblast: %s\n", *filename);
	ifstream is(*filename);
	if (!is) {

	}
	string str;
	char *output[100000];
	//int i = w.size();
	while (is && w.size()<100000)
	{
		is.getline(str, 100);
		w.push_back(str, what);
		//i++;
		//cout << str << endl;
	}
	is.close();
	return;
}

int main(int argc, char* argv[])
{
	vector<Slovo> words(10000);
	setlocale(LC_CTYPE, "RUSSIAN");
	const char *filename = "Words.txt";
	printf("Reading files from: %s", filename);
	ReadFrom(&filename, &words, noun);
	int w = word_count(words);
	for (int i = 0; i < 10000; i++)
	{
		cout << words[i].word << endl; if (i % 10 == 0) cout << endl;
	}
	//if (!ReadFrom(&filename)) printf("Error: Input file not found");
	//while (text)
	return 0;
}
