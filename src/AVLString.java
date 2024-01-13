import java.io.PrintWriter;

public class AVLString {
    NodeString root;

    void updateHeight(NodeString node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    int height(NodeString node) {
        return node == null ? -1 : node.height;
    }

    int getBalance(NodeString node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    NodeString rebalance(NodeString z) {
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

    NodeString rotateRight(NodeString y) {
        NodeString x = y.left;
        NodeString T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    NodeString rotateLeft(NodeString x) {
        NodeString y = x.right;
        NodeString T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    NodeString insert(NodeString root, NodeString node) {
        if (root == null) {
            return node;
        } else if (node.key.compareTo(root.key) < 0) {
            root.left = insert(root.left, node);
        } else if (node.key.compareTo(root.key) > 0) {
            root.right = insert(root.right, node);
        }
        return rebalance(root);
    }

    public void delete(String key) {
        root = deleteRecursive(root, key);
    }

    NodeString deleteRecursive(NodeString node, String key) {
        if (node == null) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = deleteRecursive(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = deleteRecursive(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                NodeString mostLeftChild = mostLeftChild(node.left);
                node.key = mostLeftChild.key;
                node.left = deleteRecursive(node.left, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private NodeString mostLeftChild(NodeString node) {
        NodeString current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    void PrintKLP(NodeString node) {
        if (node != null) {
            String fullKey = node.key;
            String highlightedPart = fullKey.substring(fullKey.length() - 3); // Ostatnie 3 znaki

            String fullReferenceKey = node.reference.key;
            String highlightedReferencePart = fullReferenceKey.substring(fullReferenceKey.length() - 3); // Ostatnie 3 znaki

            System.out.println(fullKey.substring(0, fullKey.length() - 3) +
                    Main.ANSI_YELLOW + highlightedPart + " " +
                    Main.ANSI_RESET + fullReferenceKey.substring(0, fullReferenceKey.length() - 3) +
                    Main.ANSI_YELLOW + highlightedReferencePart + Main.ANSI_RESET);

            PrintKLP(node.left);
            PrintKLP(node.right);
        }
    }



    void SaveKLP(NodeString node, PrintWriter save) {
        if (node != null) {
            save.println(node.key + " " + node.reference.key);
            SaveKLP(node.left, save);
            SaveKLP(node.right, save);
        }
    }

    void getWeightHeight(NodeString node, String key, int height) {
        if (node == null || node.key.equals(key)) {
            if (node == null) {
                System.out.println(Main.ANSI_RED + "Not found" + Main.ANSI_RESET);
            } else {
                System.out.println(Main.ANSI_GREEN + "Waga: " + getBalance(node) + ", Wysokość: " + height + Main.ANSI_RESET);
            }
        } else if (key.compareTo(node.key) < 0) {
            getWeightHeight(node.left, key, height + 1);
        } else {
            getWeightHeight(node.right, key, height + 1);
        }
    }
}
