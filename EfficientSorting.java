package com.company;

public class EfficientSorting {
    public static class item {
        Integer key = new Integer(0);
        Character[] password = new Character[50];

        public item() {
            this.key = 0;
            this.password = new Character[50];
        }

        public void setItem(Integer key, Character[] password) {
            this.key = key;
            for (int i = 0; i < 50; i++) this.password[i] = password[i];
            //for (int i=0; i<50; i++) System.out.println("| "+this.password[i]+" | ");
        }

        public int getKey() {
            return this.key;
        }

        public Character[] getPass() {
            Character[] ret = new Character[50];
            for (int i = 0; i < 50; i++) ret[i] = password[i];
            return ret;
        }
    }

    static long InsertSort(item a[]) {
        long startTime = System.nanoTime();
        int j, n = a.length;
        item x;
        for (int i = 2; i < n; i++) {
            x = a[i];
            a[0] = x;
            j = i - 1;
            while (x.key < a[j].key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = x;
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    static long SelectionSort(item a[]) {
        long startTime = System.nanoTime();
        int j, k, n = a.length;
        item x;
        for (int i = 0; i < n; i++) {
            k = i;
            x = a[i];
            for (j = i + 1; j < n; j++) {
                if (a[j].key < x.key) {
                    k = j;
                    x = a[j];
                }
                a[k] = a[i];
                a[i] = x;
            }
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    static long BubbleSort(item a[]) {
        long startTime = System.nanoTime();
        item x;
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = n - 1; j > i; j--)
                if (a[j].key < a[j - 1].key) {

                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    static long ShakerSort(item a[]) {
        long startTime = System.nanoTime();
        int k = 0, l = 0, r = a.length - 1;
        item x;
        boolean was;
        do {
            was = false;
            for (int i = r; i > l; i--)
                if (a[i - 1].getKey() > a[i].getKey()) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was = true;
                }
            l = k + 1;
            for (int i = l; i < r; i++)
                if (a[i - 1].getKey() > a[i].getKey()) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was = true;
                }
            r = k - 1;
        } while (was);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    static long QuickSort(item a[], int l, int r) {
        if (a == null || a.length == 0) return 0;
        if (l > r) return 0;
        long startTime = System.nanoTime();
        int i = l, j = r;
        item w;//=new item();
        int middle = l + (r - l) / 2;
        item pivot = a[middle];
        do {
            while (a[i].getKey() < pivot.getKey()) i++;
            while (pivot.getKey() < a[j].getKey()) j--;
            if (i <= j) {
                w = a[i];
                a[i] = a[j];
                a[j] = w;
                i++;
                j--;
            }
        } while (i < j);
        if (l < j) QuickSort(a, l, j);
        if (i < r) QuickSort(a, i, r);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    static void sift(item a[], int l, int r) {
        int i, j;
        //int l = 0, r = a.length;
        item x;
        i = l;
        j = 2 * i;
        x = a[i];
        while (j <= r) {
            if (j < r && a[j].getKey() > a[j + 1].getKey())
                i = j + 1;
            if (x.getKey() <= a[j].getKey()) break;
            a[i] = a[j];
            i = j;
            j = 2 * i;
        }
        a[i] = x;
    }
    static long PyramidalSort(item a[]) {
        item temp;
        int size = a.length-1;
        long startTime=System.nanoTime();
        for (int i = (a.length / 2); i >= 0; i--) {
            sift(a,i,size);
        };
        for(int i= size; i>=0; i--) {
            temp = a[0];
            a[0] = a[size];
            a[size] = temp;
            size--;
            sift(a, 0, size);
        }
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
}
