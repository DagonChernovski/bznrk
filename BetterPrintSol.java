package com.company;

public class Main {
    static int[] all = new int[]{1,1,2,4,5,6,7,8,4,5,9,6,2,6,7,2,6,3,3,5,6,3,5,11,14};
    static int weight=42,solutions=0;
    static int[] kratn =new int[weight];
    static int[] sol = new int[weight];
    public static void printSol() {
        solutions++;
        System.out.print("Solution #"+solutions+": ");
        for (int i = 0; i <= weight-1; i++) {
            if (sol[i]>0) for (int j=0; j<sol[i]; j++) System.out.print((i+1)+" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
	// write your code here
        for (int i=0; i<weight; i++)
        {kratn[i]=0; sol[i]=0;};
        for (int i=0; i<all.length; i++)
            kratn[all[i]-1]++;
        nabor(weight,weight);
        System.out.println("All solutions: "+solutions);
        }
    private static void nabor(int V, int g) {
        for (int i = g; i > 0; i--) {
            if (kratn[i-1] > 0 && i <= V) {
                kratn[i-1]--;
                sol[i-1]++;
                int V1 = V - i;
                if (V1 == 0) printSol();
                else nabor(V1,i);
                kratn[i-1]++;
                sol[i-1]--;
            }
        }
    }
}
