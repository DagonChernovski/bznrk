// WerdFinder.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include "cstdlib"
#include "string"
#include <fstream>

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
	unsigned int length=word.length();
	speech part;
};

extern Slovo words[100000];
int word_count()
{
	int i = 0; while (words[i].word!="") i++;
	return i;
}

void ReadFrom(const char* filename[], speech what) {
	printf("oblast: %s\n", *filename);
	ifstream is(*filename);
	if (!is) {
		
	}
	char str[100];
	char *output[100000];
	int i = word_count();
	while (is && i<100000)
	{
		is.getline(str, 100);
		words[i].word = str;
		words[i].part = what;
		i++;
		//cout << str << endl;
	}
	is.close();
	return;
}

int main(int argc, char* argv[])
{
	setlocale(LC_CTYPE, "RUSSIAN");
	const char *filename = "c:\\Users\\denpo\\source\\repos\\WerdFinder\\Debug\\petrov.txt";
	printf("Reading files from: %s", filename);
	ReadFrom(&filename, noun);
	int w=word_count();
	for (int i = 0; i < 10000; i++)
	{
		cout << words[i].word << endl; if (i % 10 == 0) cout << endl;
	}
	//if (!ReadFrom(&filename)) printf("Error: Input file not found");
	//while (text)
	return 0;
}
