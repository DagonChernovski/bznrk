import java.lang.String;
import java.lang.Math;

public class CompareThemAll {
    public class item {
        public int key;
        public char password[]=new char[50];
    }
    void InsertSort(item a[]) {
        int j, n=10000;
        item x;
        for (int i=2; i<n; i++) {
            x=a[i]; a[0]=x; j=i-1;
            while (x.key<a[j].key) {
                a[j+1]=a[j];
                j=j-1;
            }
            a[j+1]=x;
        }
    };
    void SelectionSort(item a[]) {
        int j, k, n=10000;
        item x;
        for (int i=1; i<n-1; i++) {
            k=i; x=a[i];
            for (j=i+1; j<n; j++) {
                if (a[j].key<x.key) {k=j; x=a[j];}
                a[k]=a[i]; a[i]=x;
            }
        }
    }
    static item[] BubbleSort(item a[]) {
        item x;
        int n=10000;
        for (int i = 2; i < n; i++)
            for (int j = n; j > i; j--)
                if (a[j].key < a[j-1].key) {
                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
        return a;
    }
    void ShakerSort(item a) {}
    void QuickSort(item a) {
        int i,j;
        item b;
    }
    public static void main(String[] args) {
        item sorted[]=new item[10000];
        item random[]=new item[10000];
        item opposite[]=new item[10000];
        //СТАДИЯ 1. ВВОД
        for (int i=0; i<10000; i++)
        {
            sorted[i].key=i;
            System.out.println("works"+i);
            opposite[i].key=10000-i;
            random[i].key=(int)(Math.random()*100000);
            for (int j=0; j<50; j++) {sorted[i].password[j] = (char) (79*Math.random() + 43); }
            opposite[i].password=sorted[i].password;
            random[i].password=sorted[i].password;
        }
        //СТАДИЯ 2. СОРТИРОВКА
        System.out.println(opposite[1].key+" "+opposite[1].password);
        opposite=BubbleSort(opposite);
        System.out.println(opposite[1].key+" "+opposite[1].password);
    }
}
