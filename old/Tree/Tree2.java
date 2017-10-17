import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class Tree2 {

    public class Node {
        int key;
        int h = 0;
        int pplength;
        int pathcounter;
        int countmaxh = 1;
        int coutpath = 0;

        boolean isDeleted = false;
        boolean onPath = false;
        boolean isRoot = false;

        Node leftChild;
        Node rightChild;
        Node parent;
    }

    Stack<Node> st = new Stack<>();
    List<Node> l = new ArrayList<Node>();

    private Node root;

    public Tree2() {
        st.push(new Node());
        root = null;
    }

    public void insert(int val) {
        Node node = new Node();
        node.key = val;
        if (root == null) {
            this.root = node;
        } else {
            Node cur = root;
            Node parent;
            while (true) {
                parent = cur;
                if (val < cur.key) {
                    cur = cur.leftChild;
                    if (cur == null) {
                        parent.leftChild = node;
                        return;

                    }
                } else {
                    cur = cur.rightChild;
                    if (cur == null) {
                        parent.rightChild = node;
                        return;
                    }
                }
            }
        }
    }

    public void traverse(Node locRoot) {
        if (locRoot != null) {
            traverse(locRoot.leftChild);
            if (locRoot.onPath && locRoot.coutpath % 2 == 0) {
                l.add(locRoot);
            }
            traverse(locRoot.rightChild);
        }
    }


    public void delete(int key, Node n) {
        Node current = root;
        Node parent = root;
        boolean isLeft = true;

        while (current.key != key) {
            parent = current;
            if (key < current.key) {
                isLeft = true;
                current = current.leftChild;
            } else {
                isLeft = false;
                current = current.rightChild;
            }
            if (current == null)
                return;
        }


        if (current.leftChild == null && current.rightChild == null) {

            if (current == root) {
                root = null;
            } else if (isLeft) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        } else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeft) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } else if (current.leftChild == null) {

            if (current == root)
                root = current.rightChild;
            else if (current.leftChild == null) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }

        } else {

            Node successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeft)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;
            successor.leftChild = current.leftChild;
        }
        return;
    }


    Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;

        Node current = delNode.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;

    }


    void startFromRoot(Node n) {
        int h;
        if (n.rightChild != null && n.leftChild != null) {
            h = n.rightChild.countmaxh;
            n.coutpath = n.coutpath + n.rightChild.countmaxh * n.leftChild.countmaxh;
            n.onPath = true;
            traversePath(n.leftChild, h);
            h = n.leftChild.countmaxh;
            traversePath(n.rightChild, h);
        } else if (n.rightChild != null) {
            n.onPath = true;
            h = 1;
            traversePath(n.rightChild, h);
        } else {
            n.onPath = true;
            h = 1;
            traversePath(n.leftChild, h);
        }


    }

    public void traversePath(Node n, int h) {
        if (n == null) {
            return;
        }
        n.coutpath = n.coutpath + n.countmaxh * h;
        n.onPath = true;
        if (n.leftChild != null && n.rightChild != null && n.leftChild.h == n.h - 1 && n.rightChild.h == n.h - 1) {

            traversePath(n.leftChild, h);
            traversePath(n.rightChild, h);

        } else if (n.rightChild != null && n.rightChild.h == n.h - 1) {
            traversePath(n.rightChild, h);

        } else {
            traversePath(n.leftChild, h);
        }
    }


    int calcHe(Node n) {
        if (n == null) {
            return 0;
        } else {
            n.h = (Math.max(calcHe(n.leftChild), calcHe(n.rightChild)) + 1);

            if (n.leftChild != null && n.rightChild != null) {
                n.pplength = n.rightChild.h + n.leftChild.h + 1;

                if (st.peek().pplength < n.pplength) {
                    st.clear();
                    st.push(n);
                } else if (st.peek().pplength == n.pplength) {
                    st.push(n);
                } else {
                }
                if (n.leftChild.h == n.rightChild.h) {  ///////удалить потом
                    if (n.leftChild.countmaxh == 1) {
                        n.countmaxh = 2;
                    } else {
                        n.countmaxh = n.leftChild.countmaxh + n.rightChild.countmaxh;
                    }
                } else {
                    n.countmaxh = Math.max(n.leftChild.countmaxh, n.rightChild.countmaxh);
                }

            } else if (n.leftChild != null) {
                n.pplength = n.leftChild.h + 1;
                if (st.peek().pplength < n.pplength) {
                    st.clear();
                    st.push(n);

                }
                n.countmaxh = n.leftChild.countmaxh;

            } else if (n.rightChild != null) {
                n.pplength = n.rightChild.h + 1;

                if (st.peek().pplength < n.pplength) {
                    st.clear();
                    st.push(n);

                }

                n.countmaxh = n.rightChild.countmaxh;

            } else {
                n.pplength = 1;
            }
            return n.h;
        }

    }

    public static void main(String[] args) {

        Tree2 t = new Tree2();

        File f = new File("tst.in");
        try {
            FileReader r = new FileReader(f);
            Scanner sc = new Scanner(r);
            while (sc.hasNext()) {
                t.insert(sc.nextInt());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        t.calcHe(t.root);
        t.traverse(t.root);

        while (!t.st.isEmpty()) {

            t.startFromRoot(t.st.pop());
        }

        t.traverse(t.root);

        Node m;
        int sz = t.l.size();
        if (sz % 2 == 1) {
            m = t.l.get(sz / 2);
            t.delete(m.key, m);
        }

        try (FileWriter writer = new FileWriter("tst.out", false)) {

            Stack<Node> stack = new Stack<>();
            Node top = t.root;
            while (top != null || !stack.empty()) {
                if (!stack.empty()) {
                    top = stack.pop();
                }
                while (top != null) {
                    writer.write(Integer.toString(top.key) + "\n");
                    if (top.rightChild != null) stack.push(top.rightChild);
                    top = top.leftChild;
                }
            }
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
