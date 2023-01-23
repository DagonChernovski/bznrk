package com.company;

import java.util.Stack;

public class Main {
    static int ln=10000000;
    public static class node {
        Integer key, balance;
        node left, right, parent;

        public node(int key) {
            this.key = key;
            this.balance=0;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
        public void countBalance() {
            node current = this;
            while (current.parent != null) {
                current.balance++;
                current = current.parent;
            }
        }
        public void RotateLeft(node l) {
            node rbranch=right;
            node lbranch=left;
            left=this;
            key=right.key;
            right=rbranch.right;
        }
        public int[] TreeToArr() {
            int aa[]=null, bb[]=null, a=0, b=0;
            if (this.left!=null) {
                aa = this.left.TreeToArr();
                a=aa.length;
            }
            if (this.right!=null) {
                bb = this.right.TreeToArr();
                b=bb.length;
                }
            int ret[]=new int[a+b+1];
            for (int i=0; i<a; i++) {
                ret[i]=aa[i];
            }
            ret[a]=key;
            for (int i=0; i<b; i++)
                ret[i+a+1]=bb[i];
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
                    //if (key == current.key) return; else
                    if (key < current.key) {
                        current = current.left;
                        if (current == null) {
                            parent.left = n;
                            parent.left.parent = parent;
                            parent.left.countBalance();
                            return;
                        }
                    }
                    else {
                        current = current.right;
                        if (current == null) {
                            parent.right = n;
                            parent.right.parent = parent;
                            parent.right.countBalance();
                            return;
                        }
                    }
                }
            }
        }
        public void Delete(node el) {
            node q=el;
            if (el.right!=null) Delete(el.right);
            else {q.key=el.key; //? needed or not
            q=el; el=el.left;}
            if (el==null) return;
            else if (el.left!=null) Delete(el.left);
            else {q.key=el.key; //? needed or not
                q=el; el=el.right;}
        }
    }
    public static void main(String[] args) {
        int[] array = new int[ln];
        for (int i = 0; i < ln; i++)
            array[i] = (int) (Math.random() * 1000000);
        Tree tree = new Tree();
        for (int i = 0; i < ln; i++)
            tree.addElem(array[i]);
        int[] sorted = tree.rootNode.TreeToArr();
        for (int i=0; i < sorted.length; i++)
            System.out.println(sorted[i]);
    }
}
