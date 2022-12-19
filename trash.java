static long[] siftC(item a[], int l, int r) {
        int i, j;
        long compare = 0, assign = 0;
        item x;
        i = l;
        j = 2 * i;
        x = a[i];
        assign += 3;
        while (j <= r) {
            if (j < r && a[j].getKey() > a[j + 1].getKey()) {
                i = j + 1;
                assign++;
            }
            compare++;
            if (x.getKey() <= a[j].getKey()) {
                compare++;
                break;
            }
            compare++;
            a[i] = a[j];
            i = j;
            j = 2 * i;
            assign += 3;
        }
        a[i] = x;
        compare++; assign++;
        long c[]=new long[2];
        c[0]=compare;c[1]=assign;
        return c;
    }
