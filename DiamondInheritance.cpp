// Diamond Inheritance.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <cstdlib>
#include <stdexcept>

class MyVector {
protected:
    int length = 0;
public:
    int* arr;
    MyVector() {
        arr=(int*)(malloc(sizeof(int)));
    }
    void push_back(int a) {
        arr[length] = a;
        length++;
        arr=(int*)(realloc(arr,(length+1)*sizeof(int)));
    }
    void pop_back(int a)
    {
        //arr[length] = 0;
        length--;
        arr = (int*)(realloc(arr, length * sizeof(int)));
    }
    void resize(int len)
    {
        length = len;
        arr = (int*)(realloc(arr, length * sizeof(int)));
    }
    void freeVector() {
        free(arr);
    }
    void printVector(int begin, int end) {
        if (end >= length || begin < 0)
        {
            printf("Error: index end out of range %d", end, length);
            return;
        }
        for (int i = begin; i <= end; i++) {
            printf("%d ", arr[i]);
        }
    }
    void printVector() {
        printVector(0, length-1);
    }
    void printlnVector() {
        printVector();
        printf("\n");
    }
};

class DexatedVector: public MyVector{
protected:
    int maxIndex = 0;
public:
    DexatedVector(int len) { //вектор объявляется сразу с заданным диапазоном
        maxIndex = len;
        arr = (int*)(malloc(maxIndex * sizeof(int)));
    };
    DexatedVector() {
        arr = (int*)(malloc(sizeof(int)));
    }
    void push_back(int a) {
        arr[length] = a;
        length++;
        if (maxIndex==length)
            arr = (int*)(realloc(arr, (maxIndex + 1) * sizeof(int)));
    }
    void pop_back(int a) {
        length--;
    }
    int getElem(int index) {
        try {
            if (index >= length || index <= 0) throw ("Error: out of range");
            else return arr[index];
        }
        catch (_exception e) {
            printf("\n%s", e);
            return 0;
        }
    }
};

class SortedVector : public MyVector {
public:
    SortedVector() {
        arr = (int*)(malloc(sizeof(int)));
    }
    void push_back(int a) {
        length++;
        arr = (int*)(realloc(arr, length * sizeof(int)));
        int pointer=length-1;
        if (pointer>0)
            while (pointer > 0 && a <= arr[pointer-1]) {
                arr[pointer] = arr[pointer - 1];
                pointer--;
            }
        arr[pointer] = a;
    }
};

class ComboVector : public DexatedVector, public SortedVector {
protected:
    int length = 0;
    int maxIndex;
    int* arr;
public:
    ComboVector(int len) {
        maxIndex = len;
        arr = (int*)(malloc(sizeof(int)));
    }
    void push_back(int a) {
        length++;
        if (maxIndex < length)
        {
            printf("Reallocation attempt -- ");
            arr = (int*)(realloc(arr, length * sizeof(int)));
            maxIndex++;
            printf("Reallocated successfully: max index %d\n", maxIndex);
        }
        int pointer = length - 1;
        if (pointer > 0)
            while (pointer > 0 && a <= arr[pointer - 1]) {
                arr[pointer] = arr[pointer - 1];
                pointer--;
                printf("Current pointer is on %d\n",pointer);
            }
        arr[pointer] = a;
        printlnVector();
    }
    void printVector() {
        DexatedVector::printVector();
    }
    void printlnVector() {
        DexatedVector::printlnVector();
    }
};

int main()
{
    MyVector a;
    DexatedVector b(6);
    SortedVector s;
    ComboVector cc(6);
    for (int i = 0; i < 5; i++)
    {
        a.push_back(20 - i * 3);
        b.push_back(30 - i * 4);
        s.push_back(30 - i * 4);
        //cc.push_back(20 - i * 3);
    }
    a.printVector();
    printf("\n");
    b.printVector();
    printf("\n");
    for (int i = 0; i < 5; i++)
    {
        a.push_back(i * 3);
        b.push_back(i * 4);
        s.push_back(i * 4);
        cc.push_back(i * 3);
    }
    a.printVector();
    printf("\nB: ");
    b.printVector();
    printf("\nS: ");
    s.printVector();
    printf("\nCombo: ");
    cc.printVector();
    a.resize(3);
    b.resize(4);
    s.resize(6);
    printf("\n");
    a.printlnVector();
    b.printlnVector(); 
    s.printlnVector();
    cc.printVector();
    a.freeVector();
    std::cout << "Hello World!\n";
}
