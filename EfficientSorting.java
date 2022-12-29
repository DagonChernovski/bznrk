package com.company;

public class EfficientSorting {
    public static class item {
        Integer key=new Integer(0);
        Character[] password =new Character[50];
        public item() {this.key=0; this.password=new Character[50];}
        public void setItem(Integer key, Character[] password) {
            this.key=key; for (int i = 0; i<50; i++) this.password[i]=password[i];
            //for (int i=0; i<50; i++) System.out.println("| "+this.password[i]+" | ");
        }
    }

    static long InsertSort(item a[]) {
        long startTime = System.nanoTime();
        int j, l, r, m, n = a.length;
        item x;
        for (int i = 1; i < n; i++) {
            x=a[i]; l=0; r=i-1;
            while (l<=r) {
                m = (l + r) / 2;
                if (x.key < a[m].key) r = m - 1;
                else l = m + 1;
            }
            for (j = i - 1; j > l; j--) {
                a[j+1] = a[j - 1];}
            a[l] = x;
        }
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
    static long SelectionSort(item a[]) {
        long startTime=System.nanoTime();
        int jj, n=a.length;
        item x;
        for (int i=0; i<n; i++) {
            x=a[i]; jj=i;
            for (int j=i+1; j<n; j++)
                if (a[j].key<a[i].key) {x=a[j]; jj=j;}
            if (jj!=i) {a[jj]=a[i]; a[i]=x;}
        }
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
    static long BubbleSort(item a[]) {
        long startTime=System.nanoTime();
        item x;
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = n - 1; j > i; j--)
                if (a[j].key < a[j - 1].key) {
                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
    static long ShakerSort(item a[]) {
        long startTime=System.nanoTime();
        int k=0, l=0, r=a.length-1;
        item x;
        boolean was;
        do {
            was = false;
            for (int i = r; i > l; i--)
                if (a[i - 1].key > a[i].key) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was=true;
                }
            l = k + 1;
            for (int i = l; i < r; i++)
                if (a[i - 1].key > a[i].key) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was=true;
                }
            r = k - 1;
        } while (was);
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
    static long QuickSort(item a[], int l, int r) {
        if (a==null || a.length==0) return 0;
        if (l>r) return 0;
        long startTime=System.nanoTime();
        int i=l,j=r;
        item w;//=new item();
        item pivot=a[l+(r-l)/2];
        do {
            while (a[i].key<pivot.key) i++;
            while (pivot.key<a[j].key) j--;
            if (i<=j) {
                w = a[i];
                a[i] = a[j];
                a[j] = w;
                i++;
                j--;
            }
        } while (i<j);
        if (l<j) QuickSort(a, l, j);
        if (i<r) QuickSort(a, i, r);
        long endTime=System.nanoTime();
        return endTime-startTime;
    }

    static void sift(item a[], int n, int i) {
        int max = i;
        int l = 2*i + 1;
        int r = 2*i + 2;
        if (l < n && a[l].key > a[max].key) max = l;
        if (r < n && a[r].key > a[max].key) max = r;
        if (max != i)
        {
            item temp = a[i];
            a[i] = a[max];
            a[max] = temp;
            sift(a, n, max);
        }
    }
    static long PyramidalSort(item a[]) {
        item temp;
        int size = a.length;
        long startTime=System.nanoTime();
        for (int i = (a.length / 2)-1; i >= 0; i--) sift(a, size,i);
        for(int i= size-1; i>=0; i--) {
            temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            sift(a, i,0);
        }
        long endTime=System.nanoTime();
        return endTime-startTime;
    }
}
