import java.util.Stack;

public class EfficientSorting {
    public static class item {
        Integer key=new Integer(0);
        Character[] password =new Character[50];
        public item() {this.key=0; this.password=new Character[50];}
        public void setItem(Integer key, Character[] password) {
            this.key=key; for (int i = 0; i<50; i++) this.password[i]=password[i];
            //for (int i=0; i<50; i++) System.out.println("| "+this.password[i]+" | ");
        }
        public int getKey() {return this.key;}
        public Character[] getPass()
        {Character[] ret = new Character[50];for (int i = 0; i<50; i++) ret[i]=password[i]; return ret;}
    }

    static void InsertSort(item a[]) {
        int j, n=a.length;
        item x;
        for (int i=2; i<n; i++) {
            x=a[i]; a[0]=x; j=i-1;
            while (x.key<a[j].key) {
                a[j+1]=a[j];
                j=j-1;
            }
            a[j+1]=x;
        }
    }
    static int SelectionSort(item a[]) {
        int j, k, c=0, n=a.length;
        item x;
        for (int i=0; i<n; i++) {
            k=i; x=a[i];
            for (j=i+1; j<n; j++) {
                if (a[j].key<x.key) {k=j; x=a[j]; }
                a[k]=a[i]; a[i]=x; c++;
            }
        }
        return c;
    }
    static void BubbleSort(item a[]) {
        item x;
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = n - 1; j > i; j--)
                if (a[j].key < a[j - 1].key) {

                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
    }
    static void ShakerSort(item a[]) {
        int k=0, l=0, r=a.length;
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
    }
    static void QuickSort(item a[], int l, int r) {
        if (a==null || a.length==0) return;
        if (l>r) return;
        int i=l,j=r;
        item w;//=new item();
        int middle=l+(r-l)/2;
        item pivot=a[middle];
        do {
            while (a[i].getKey()<pivot.getKey()) i++;
            while (pivot.getKey()<a[j].getKey()) j--;
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
    }
}
