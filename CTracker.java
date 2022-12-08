package com.company;

public class CTracker extends EfficientSorting {
    static int[] InsertSortC(item a[]) {
        int j, n=a.length,assign=0,compare=0;
        item x;
        for (int i=2; i<n; i++) {
            x=a[i]; a[0]=x; j=i-1;
            while (x.key<a[j].key) {
                a[j+1]=a[j];
                j=j-1;
            }
            a[j+1]=x;
        }
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static int[] SelectionSortC(item a[]) {
        int j, k, compare=0,assign=0, n=a.length;
        item x;
        for (int i=0; i<n; i++) {
            k=i; x=a[i];
            for (j=i+1; j<n; j++) {
                if (a[j].key<x.key) {k=j; x=a[j]; }
                a[k]=a[i]; a[i]=x; compare++;
            }
        }
        int c[]=new int[2];
        c[0]=compare; c[1]=assign;
        return c;
    }
    static int[] BubbleSortC(item a[]) {
        item x;
        int n=a.length, compare=0,assign=0;
        for (int i = 0; i < n; i++)
            for (int j = n-1; j > i; j--)
                if (a[j].key < a[j-1].key) {

                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static int[] ShakerSortC(item a[]) {
        int k=0, l=0, compare=0,assign=0, r=a.length;
        item x;
        boolean was;
        do {
            was = false;
            for (int i = r - 1; i > l; i--)
                if (a[i - 1].getKey() > a[i].getKey()) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was=true;
                }
            l = k + 1;
            for (int i = l; i < r; i++)
                if (a[i - 1].getKey() > a[i].getKey()) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    was=true;
                }
            r = k - 1;
        } while (was);
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static int[] QuickSortC(item a[], int l, int r) {
        int i=l,j=r,compare=0,assign=0;
        item w;//=new item();
        item x=new item();
        x.setItem((a[l+r].getKey()/2),a[l+r].getPass());
        do {
            while (a[i].key<x.key) i++; compare+=(assign+=i);
            while (x.key<a[j].key) j--; compare+=(assign+=i);
            if (i<=j) {
                w = a[i] = a[j];
                a[j] = w;
                i++;
                j--;
            }
        } while (i<=j);
        if (l<j) QuickSortC(a, l, j);
        if (i<r) QuickSortC(a, i,r);
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
}
