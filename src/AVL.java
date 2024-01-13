import java.io.PrintWriter;

public class AVL {
    Node root;

    void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    int height(Node node) {
        return node == null ? -1 : node.height;
    }

    int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (getBalance(z.left) < 0) {
                z.left = rotateLeft(z.left);
            }
            return rotateRight(z);
        } else if (balance < -1) {
            if (getBalance(z.right) > 0) {
                z.right = rotateRight(z.right);
            }
            return rotateLeft(z);
            }
        return z;
    }
//    Node rebalance(Node z) {
//        updateHeight(z);
//        int balance = getBalance(z);
//        if (balance < -1) {
//            if (height(z.right.right) > height(z.right.left)) {
//                z = rotateLeft(z);
//            } else {
//                z.right = rotateRight(z.right);
//                z = rotateLeft(z);
//            }
//        } else if (balance > 1) {
//            if (height(z.left.left) > height(z.left.right))
//                z = rotateRight(z);
//            else {
//                z.left = rotateLeft(z.left);
//                z = rotateRight(z);
//            }
//        }
//        return z;
//    }
    Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
    Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        } else if (root.key > key) {
            root.left = insert(root.left, key);
        } else if (root.key < key) {
            root.right = insert(root.right, key);
        }
        return rebalance(root);
    }
    public void delete(int key) {
        root = deleteRecursive(root, key);
    }
//    Node deleteRecursive(Node node, int key) {
//        if (node == null) {
//            return node;
//        }
//        else if (node.key > key) {
//            node.left = deleteRecursive(node.left, key);
//        }
//        else if (node.key < key) {
//            node.right = deleteRecursive(node.right, key);
//        }
//        else {
//            if (node.left == null && node.right == null) {
//                return null;
//            } else if(node.left == null) {
//                node = node.right;
//            } else if(node.right == null) {
//                node = node.left;
//            } else {
//                Node mostLeftChild = mostLeftChild(node.left);
//                node.key = mostLeftChild.key;
//                node.left = deleteRecursive(node.left, node.key);
//            }
//        }
//        return rebalance(node);
//    }
    Node deleteRecursive(Node node, int key) {
        if (node == null) {
            return node;
        }
        else if (node.key > key) {
            node.left = deleteRecursive(node.left, key);
        }
        else if (node.key < key) {
            node.right = deleteRecursive(node.right, key);
        }
        else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            }
            else {
                Node mostLeftChild = mostLeftChild(node.left);
                node.key = mostLeftChild.key;
                node.left = deleteRecursive(node.left, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }
    private Node mostLeftChild(Node node) {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    // avl print in order KLP which means Root Left Right
    void PrintKLP(Node node) {
        if (node != null) {
            System.out.print(Main.ANSI_GREEN+node.key+" "+Main.ANSI_RESET);
            PrintKLP(node.left);
            PrintKLP(node.right);
        }
    }
    void SaveKLP(Node node, PrintWriter save) {
        if (node != null) {
            save.print(node.key+"("+getBalance(node)+") ");
            SaveKLP(node.left, save);
            SaveKLP(node.right, save);
        }
    }

    void getWeightHeight(Node node, int key, int height) {
        if (node == null || node.key == key) {
//            return node == null ? "Not found" : "Weight: " + getBalance(node) + ", Height: " + height;
            if(node==null){
                System.out.println(Main.ANSI_RED + "Not found"  + Main.ANSI_RESET);
            }
            else{
                System.out.println(Main.ANSI_GREEN + "Waga: " + getBalance(node) + ", Wysokość: " + height + Main.ANSI_RESET);
            }
        }
        else if (node.key > key) {
            getWeightHeight(node.left, key, height+1);
        }
        else {
            getWeightHeight(node.right, key, height+1);
        }
    }
}
