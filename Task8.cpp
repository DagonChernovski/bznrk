// GottaLetEmGo.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <time.h>

struct list {
	int data;
	struct list* next;
};

struct list* init(int a) {
	struct list* lst;
	lst = (struct list*)malloc(sizeof(struct list));
	lst->data = a;
	lst->next = lst;
	return(lst);
}

struct list* addelem(list* lst, std::string n) {
	struct list* temp, * p;
	temp = (struct list*)malloc(sizeof(list));
	p = lst->next;
	lst->next = temp;
	temp->data = n;
	temp->next = p;
	return(temp);
}

struct list* deletelem(list* lst) {
	struct list* temp;
	temp = lst;
	while (temp->next != lst) temp = temp->next;
	temp->next = lst->next;
	free(lst);
	return temp;
}

void listprint(list* lst) {
	struct list* p;
	p = lst;
	do {
		printf("%s ", &p->data);
		p = p->next;
	} while (p != lst);
}

int main()
{
	setlocale(LC_ALL, "Russian");
	srand(time(NULL));
	std::string soldiers[6] = { 
		"Миндаль Иванович","Курага Михайловна", "Кокос Сидорович", 
		"Морковь Патрикеевна", "Изюм Вениаминович", "Розмарин Великий" };
	printf("Init attempt:");
	list *a = init("Фундук Петрович");
	printf("INIT SUCCESS");
	list *b;
	int r;
	for (int i = 0; i < 6; i++)
		a = addelem(a, soldiers[i]);
	printf("Изначальный список солдат:");
	listprint(a);
	for (int i = 0; i < 5; i++) {
		r = 13 * rand() / int(RAND_MAX);
		b = a->next;
		for (int j = 0; j < r; j++) {
			b = b->next;
		}
		deletelem(b);
		printf("\n\nСолдат %d выходит из строя!\nТекущий список солдат\n:", &b->data);
		listprint(a);
	}
}

// Запуск программы: CTRL+F5 или меню "Отладка" > "Запуск без отладки"
// Отладка программы: F5 или меню "Отладка" > "Запустить отладку"

// Советы по началу работы 
//   1. В окне обозревателя решений можно добавлять файлы и управлять ими.
//   2. В окне Team Explorer можно подключиться к системе управления версиями.
//   3. В окне "Выходные данные" можно просматривать выходные данные сборки и другие сообщения.
//   4. В окне "Список ошибок" можно просматривать ошибки.
//   5. Последовательно выберите пункты меню "Проект" > "Добавить новый элемент", чтобы создать файлы кода, или "Проект" > "Добавить существующий элемент", чтобы добавить в проект существующие файлы кода.
//   6. Чтобы снова открыть этот проект позже, выберите пункты меню "Файл" > "Открыть" > "Проект" и выберите SLN-файл.
