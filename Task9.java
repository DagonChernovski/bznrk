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
        public int[] TreeToArray() {
            int[] ret=new int[16];
            int i=0;
            node current=rootNode;
            Stack<node> nodes=new Stack();
            while (i<elems) {
                nodes.add(current);
                if (current.left!=null) {if (ret[i]<current.left.key)
                    current = current.left; else current.left=null;}
                else {
                    ret[i]=current.key; i++;
                    if (current.right!=null) if (ret[i]<current.right.key)
                        current = current.right;
                    else {nodes.pop();}
                    }
                } return ret;
            }
        }
        public static void main(String[] args) {
            int[] array = new int[16];
            for (int i = 0; i < 16; i++)
                array[i] = (int) (Math.random() * 1000);
            Tree tree = new Tree();
            for (int i = 0; i < 16; i++)
                tree.addElem(array[i]);
            int[] sorted = tree.TreeToArray();
            for (int i=0; i < 16; i++)
                System.out.println(sorted[i]);
        }
    }
