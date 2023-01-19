package com.company;

import java.util.Stack;

public class Main {

    public static class node {
        Integer key;
        node left, right;

        public node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
    public static class Tree {
        private node rootNode;
        int elems;
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
                    if (key == current.key) return;
                    else if (key < current.key) {
                        current = current.left;
                        if (current == null) {
                            parent.left = n;
                            elems++;
                            return;
                        }
                    }
                    else {
                        current = current.right;
                        if (current == null) {
                            parent.right = n;
                            elems++;
                            return;
                        }
                    }
                }
                }
            }
            public void TreeToArray() {
                int[] ret=new int[100000];
                int i=0;
                node current=rootNode;
                node parent;
                while (i<elems) {
                    parent = current;
                    if (current.left!=null) {
                        current = current.left;
                        }
                    else {
                        ret[i]=current.key;
                        i++;
                        if (current.right!=null)
                        current = current.right;
                        if (current == null) {
                            parent.right = n;
                            return;
                        }
                    }
            }
        }
        public static void main(String[] args) {
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++)
            array[i] = (int) (Math.random() * 1000000);
        Tree tree = new Tree();
        for (int i = 0; i < 32; i++)
            tree.addElem(array[i]);
    }
}
