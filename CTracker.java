public class CTracker extends EfficientSorting {
    static int[] InsertSortC (item a[]) {
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
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static int[] SelectionSortC(item a[]) {
        int j, k, compare=0,assign=0, n=a.length;
        item x;
        for (int i=0; i<n; i++) {
            k=i; x=a[i]; assign+=3; compare++;
            for (j=i+1; j<n; j++) {
                if (a[j].key<x.key) {k=j; x=a[j]; assign+=2;}
                a[k]=a[i]; a[i]=x; compare+=2; assign+=3;
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
                    assign+=3;
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
                    assign+=4;
                    was=true;
                } while (!was);
            l = k + 1;
            for (int i = l; i < r; i++)
                if (a[i - 1].getKey() > a[i].getKey()) {
                    x = a[i - 1];
                    a[i - 1] = a[i];
                    a[i] = x;
                    k = i;
                    assign+=4; compare++;
                    was=true;
                }
            compare+=2; assign+=5;
            r = k - 1;
        } while (was);
        int c[]=new int[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
    static int[] QuickSortC(item a[], int l, int r) {
        int i=l,j=r,compare=0,assign=0;
        item w;//=new item();
        int middle=l+(r-l)/2;
        item pivot=a[middle];
        assign+=4;
        do {
            while (a[i].getKey()<pivot.getKey()) {i++;}
            while (pivot.getKey()<a[j].getKey()) {j--;}
            compare+=(assign+=(i-l)+(r-j));
            if (i<=j) {
                w = a[i];
                a[i] = a[j];
                a[j] = w;
                i++;
                j--;
                assign+=5; compare++;
            }
        } while (i<j);
        int _c[]=new int[2];
        int c[]=new int[2];
        if (l<j) _c=QuickSortC(a, l, j);
        c[0]+=_c[0]; c[1]+=_c[1];
        if (i<r) _c=QuickSortC(a, i, r);
        c[0]+=_c[0]; c[1]+=_c[1];
        c[0]+=compare;c[1]+=assign;
        return c;
    }
}
