package com.company;

import java.util.Stack;

public class Main {
    static int ln=1000;

    private static class node extends Tree {
        Integer key, balance;
        node left, right, parent;

        public node(int key) {
            this.key = key;
            this.balance = 0;
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

        public void Rebalance() {
            node current = this;
            int l = 0, r = 0;
            Boolean LRotPossible = false, RRotPossible = false;
            if (current.left != null) if (current.left.right == null) RRotPossible = true;
            if (current.right != null) if (current.right.left == null) LRotPossible = true;
            while (current.left != null) {
                current = current.left;
                l++;
            }
            System.out.println("got");
            current = this;
            while (current.right != null) {
                current = current.right;
                r++;
            }
            System.out.println("got");
            if (l - r > 1 && RRotPossible) RotateRight(this);
            if (r - l > 1 && LRotPossible) RotateLeft(this);
        }

        public void RotateLeft(node l) {
            node mbranch = l.right.left;
            node rbranch = l.right.right;
            l.key = l.right.key;
            l.right = rbranch;
            l.left = l;
            l.left.right = mbranch;
        }
        public void RotateRight(node l) {
            node mbranch = l.left.right;
            node lbranch = l.left.left;
            l.key = l.left.key;
            l.left = lbranch;
            l.right = l;
            l.right.left = mbranch;
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
            for (int i=0; i < ret.length; i++)
                System.out.print(ret[i]+" ");
            System.out.println();
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
                            parent.left.parent = parent;
                            if (parent.parent!=null) parent.parent.Rebalance();
                            return;
                        }
                    }
                    else {
                        current = current.right;
                        if (current == null) {
                            parent.right = n;
                            parent.right.parent = parent;
                            if (parent.parent!=null) parent.parent.Rebalance();
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
        for (int i = 0; i < ln; i++) array[i] = (int) (Math.random() * 10000);
        Tree tree = new Tree();
        for (int i = 0; i < ln; i++) tree.addElem(array[i]);
        int[] sorted = tree.rootNode.TreeToArr();
        for (int i=0; i < sorted.length; i++)
            System.out.println(sorted[i]);
    }
}
