// GottaLetEmGo.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <time.h>
#include <stdexcept>
#include <stdlib.h>
#include <limits.h>
using namespace std;

struct node {
	const char* data;
	struct node* next;
};

class list {
public:
	node* head, * tail;
	int size;
	list() : head(NULL), tail(NULL), size(0) {};
	const char* GetHead();
	void Add(const char* x);
	node* FindName(const char* name);
	node* DeleteCount(node *name, int n);
	void Print();
};

const char* list::GetHead() {
	return head->data;
}

void list::Add(const char* x) {
	size++;
	node* temp = new node;
	temp->next = head;
	temp->data = x;
	if (head != NULL) {
		tail->next = temp;
		tail = temp;
	}
	else head = tail = temp;
}

node* list::FindName(const char* name) {
	node* temp = head;
	int i = 0;
	while ((temp->data) != name && i <= size) {
		temp = temp->next; i++;
	}
	if (temp->data != name) throw runtime_error("Error: name not found\n");
	return temp;
}

node* list::DeleteCount(node * name, int n) {
	node* temp = name;
	printf("Счёт ведется начиная с имени %s до %d. \n", name->data, n);
	for (int i = 1; i < n-1; i++) {
		printf("%d - %s\n", i, temp->data);
		temp = temp->next;
	}
	printf("%d - %s\n", n-1, temp->data);
	printf("\n\n%d-ый солдат %s выходит из строя!\n", n, temp->next->data);
	node* del = temp->next;
	temp->next = del->next;
	node* ret = del->next;
	if (del == head) {
		head = del->next; tail = del->next;
	}
	delete del;
	size--;
	return ret;
}

void list::Print() {
	node* p = head;
	for (int i = 0; i < size; i++) {
		printf("%s\n", p->data);
		p = p->next;
	}
}

int main()
{
	setlocale(LC_ALL, "Russian");
	srand(time(NULL));
	const int len = 12; //длина массива
	const char* soldiers[len] = {
	"Фундук Петрович", "Миндаль Иванович", "Розмарин Великий", "Шоколад Горячевский",
	"Анекдот Старомодный", "Зверь Сергеев", "Карл Великий-Второй", "Алексей Веслов", 
	"Василий Корнеев", "Михаил Лобачевский", "Бублик Собачевский", "Ванилий Розмаринович"};
	list* a = new list();
	const char* name = "Шоколад Горячевский";
	for (int i = 0; i < len; i++)
		a->Add(soldiers[i]);
	printf("Изначальный список солдат:\n");
	a->Print();
	int r = 20 * rand() / int(RAND_MAX);
	node* n;
	try {n = a->FindName(name);}
	catch (exception ex) { printf("Exception: %s", ex); n = a->head; }
	for (int i = 1; i < len; i++) {
		n=a->DeleteCount(n, r);
		printf("\nТекущий список солдат:\n");
		a->Print();
	}
	printf("В конце концов %s остался один.\n Спустя некоторое время скука и нехватка еды вынудили его выбежать на поле боя за компанию.\nТак и закончилась история.",
		a->GetHead());
}
