package com.company;

public class CTracker extends EfficientSorting {
    static long[] InsertSortC (item a[]) {
        int j, n=a.length,assign=0,compare=0;
        item x;
        for (int i=2; i<n; i++) {
            x=a[i]; a[0]=x; j=i-1;
            while (x.key<a[j].key) {
                a[j+1]=a[j];
                j=j-1;
                compare++; assign+=2;
            }
            a[j+1]=x;
            compare++; assign+=4;
        }
        long c[]=new long[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static long[] SelectionSortC(item a[]) { //без барьера
        int j, k, compare=0,assign=0, n=a.length;
        item x;
        for (int i=0; i<n; i++) {
            k=i; x=a[i]; assign++;
            for (j=i+1; j<n; j++) {
                if (a[j].key<x.key) {k=j; x=a[j]; assign+=2;}
                a[k]=a[i]; a[i]=x; compare++; assign+=3;
            }
            //System.out.printf("A %d C %d\n",assign,compare);
        }
        long c[]=new long[2];
        c[0]=compare; c[1]=assign;
        return c;
    }
    static long[] BubbleSortC(item a[]) {
        item x;
        int n=a.length, compare=0,assign=0;
        for (int i = 1; i < n; i++) {
            for (int j = n - 1; j >= i; j--) {
                if (a[j].key < a[j - 1].key) {
                    x = a[j - 1]; a[j - 1] = a[j]; a[j] = x;
                    assign += 3;
                }
                compare++;
            }
        }
        long c[]=new long[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static long[] ShakerSortC(item a[]) {
        int k=0, l=0, compare=0,assign=0, r=a.length-1;
        item x;
        boolean was;
        do {
            was=false;
            for (int i = r; i > l; i--)
                if (a[i - 1].key > a[i].key) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    assign += 4;
                    compare++;
                    was = true;
                }
            l = k+1;
            for (int i = l; i <= r; i++)
                if (a[i - 1].key > a[i].key) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    assign += 4;
                    compare++;
                    was = true;
                }
            compare+=2; assign+=5;
            r = k;
        } while (was);
        long c[]=new long[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static long[] QuickSortC(item a[], int l, int r) {
        int i=l,j=r,compare=0,assign=0;
        item w;//=new item();
        int middle=l+(r-l)/2;
        item pivot=a[middle];
        assign+=4;
        do {
            while (a[i].key<pivot.key) {i++;}
            while (pivot.key<a[j].key) {j--;}
            compare+=(i-l)+(r-j);
            if (i<=j) {
                w = a[i];
                a[i] = a[j];
                a[j] = w;
                i++;
                j--;
                assign+=3;
            }
        } while (i<j);
        long _c[]=new long[2];
        long c[]=new long[2];
        if (l<j) _c=QuickSortC(a, l, j);
        c[0]+=_c[0]; c[1]+=_c[1];
        if (i<r) _c=QuickSortC(a, i, r);
        c[0]+=_c[0]; c[1]+=_c[1];
        c[0]+=compare;c[1]+=assign;
        return c;
    }
    static long[] siftC(item a[], int n, int i) {
        int max = i,compare=0,assign=0;
        int l = 2*i + 1;
        int r = 2*i + 2;
        if (l < n && a[l].key > a[max].key)
        {max = l; assign++;}
        if (r < n && a[r].key > a[max].key)
        { max = r; assign++;}
        long _c[]=new long[2];
        if (max != i)
        {
            item swap = a[i];
            a[i] = a[max];
            a[max] = swap;
            _c=siftC(a, n, max);
        }
        compare+=5;
        long[] c =new long[2];
        c[0]+=_c[0]; c[1]+=_c[1];
        c[0]+=compare;c[1]+=assign;
        return c;
    }
    static long[] PyramidalSortC(item a[]) {
        item temp;
        int size = a.length-1;
        long[] c =new long[2];
        long[] _c;
        for (int i = (a.length / 2); i >= 0; i--) {
            _c=siftC(a, size,i);
            c[0]+=_c[0]; c[1]+=_c[1];
            c[0]++; c[1]++;
        };
        for(int i=size; i>=0; i--) {
            temp = a[0];
            a[0] = a[size];
            a[size] = temp;
            size--;
            _c=siftC(a, size,0);
            c[0]+=_c[0]; c[1]+=_c[1];
            c[0]++; c[1]+=5;
        }
        return c;
    }
}
