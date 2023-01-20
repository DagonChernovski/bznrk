// GottaLetEmGo.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <time.h>

struct node {
	std::string data;
	struct node* next;
};

class list {
	node *head, *tail;
	int size;
public:
	list() : head(NULL), tail(NULL), size(0) {};
	void Add(std::string x);
	void Delete(std::string x);
	void Print();
};

void list::Add(std::string x) {
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

void list::Delete(std::string x) {
	node* temp = new node;
	while ()
}

void list::Print() {
	node* p = head;
	for (int i=0; i<size; i++) {
		printf("%s ", p->data);
		p = p->next;
	}
}

int main()
{
	setlocale(LC_ALL, "Russian");
	srand(time(NULL));
	std::string soldiers[6] = {
		"Миндаль Иванович","Курага Михайловна", "Кокос Сидорович",
		"Морковь Патрикеевна", "Изюм Вениаминович", "Розмарин Великий" };
	printf("Init attempt:");
	list *a = new list(); a->Add("Фундук Петрович");
	printf("INIT SUCCESS");
	list* b;
	int r;
	for (int i = 0; i < 6; i++)
		a->Add(soldiers[i]);
	printf("Изначальный список солдат:");
	a->Print();
	for (int i = 0; i < 5; i++) {
		r = 13 * rand() / int(RAND_MAX);
		b = a->next;
		for (int j = 0; j < r; j++) {
			b = b->next;
		}
		a->Delete(b);
		printf("\n\nСолдат %d выходит из строя!\nТекущий список солдат\n:", b->data);
		listprint(a);
	}
}
