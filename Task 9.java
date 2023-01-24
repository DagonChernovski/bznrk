package com.company;

public class Main {
    static int ln=10000;
    private static class node extends Tree {
        Integer key;
        node left, right;

        public node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
        public int[] TreeToArr() {
            int aa[] = null, bb[] = null, a = 0, b = 0;
            if (this.left != null) {
                aa = this.left.TreeToArr();
                a = aa.length;
            }
            if (this.right != null) {
                bb = this.right.TreeToArr();
                b = bb.length;
            }
            int ret[] = new int[a + b + 1];
            for (int i = 0; i < a; i++) {
                ret[i] = aa[i];
            }
            ret[a] = key;
            for (int i = 0; i < b; i++)
                ret[i + a + 1] = bb[i];
            return ret;
        }
    }
    public static class Tree {
        private node rootNode;
        public Tree() {
            rootNode = null;
        }
        public void addElem(int key) {
            node n=new node(key);
            if (rootNode==null) rootNode=n;
            else {
                node current=rootNode;
                node parent;
                while (true) {
                    parent = current;
                    if (key < current.key) {
                        current = current.left;
                        if (current == null) {
                            parent.left = n;
                            return;
                        }
                    }
                    else {
                        current = current.right;
                        if (current == null) {
                            parent.right = n;
                            return;
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        int[] array = new int[ln];
        for (int i = 0; i < ln; i++) array[i] = (int) (Math.random() * ln*5);
        Tree tree = new Tree();
        for (int i = 0; i < ln; i++) tree.addElem(array[i]);
        int[] sorted = tree.rootNode.TreeToArr();
        for (int i=0; i < sorted.length; i++)
            System.out.println(sorted[i]);
    }
}
