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
        public String getPass() //{return this.password;}
        {String ret=""; for (int i = 0; i<50; i++) ret=ret+password[i]; return ret;}
    }
    /*public<Type> String PrintArray(int a, int b, Type arr[]) {\
        String ret;
        for (int i=a; i<b; i++) {
            System.out.print(arr[i]);
        }
    }*/
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
    static void SelectionSort(item a[]) {
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
        for (int i = 1; i < n; i++)
            for (int j = n-1; j > i; j--)
                if (a[j].key < a[j-1].key) {
                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                    //System.out.println("Switches : "+a[j-1]+" and "+a[j]);
                }
        return a;
    }
    void ShakerSort(item a) {}
    /*void QuickSort(item a, int l, int r) {
        int i=l,j=r;
        item b;
        item x=new item();
        x.setItem((a[l+r].getKey()),a.getPass());
        while (a[i].key<x.key) {i=i+1;}
    }*/
}
