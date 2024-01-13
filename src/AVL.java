import java.io.PrintWriter;

public class AVL {
    Node root;

    void updateHeight(Node node) {
        // Wysokość węzła to maksimum wysokości lewego i prawego poddrzewa + 1 (wzór z wykładu)
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    int height(Node node) {
        return node == null ? -1 : node.height;
    }

    int getBalance(Node node) {
        // Wysokość lewego poddrzewa minus wysokość prawego poddrzewa (wzór z wykładu)
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    Node rebalance(Node node) {
        // Aktualizuj wysokość węzła
        updateHeight(node);
        // Oblicz balans węzła
        int balance = getBalance(node);

        // Jeżeli balans jest większy niż 1, znaczy to, że lewe poddrzewo jest zbyt wysokie
        if (balance > 1) {
            // Jeżeli balans lewego poddrzewa jest mniejszy niż 0, wykonaj rotację w lewo
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            // Wykonaj rotację w prawo dla węzła
            return rotateRight(node);
        }
        // Jeżeli balans jest mniejszy niż -1, znaczy to, że prawe poddrzewo jest zbyt wysokie
        else if (balance < -1) {
            // Jeżeli balans prawego poddrzewa jest większy niż 0, wykonaj rotację w prawo
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            // Wykonaj rotację w lewo dla węzła
            return rotateLeft(node);
        }

        // Jeśli jest balans
        return node;
    }

    Node rotateRight(Node y) {
        // Przypisz zmienne pomocnicze
        Node x = y.left;
        Node z = x.right;
        // Wykonaj rotację w prawo
        x.right = y;
        y.left = z;
        // Aktualizuj wysokości węzłów
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    Node rotateLeft(Node x) {
        // Przypisz zmienne pomocnicze
        Node y = x.right;
        Node z = y.left;
        // Wykonaj rotację w lewo
        y.left = x;
        x.right = z;
        // Aktualizuj wysokości węzłów
        updateHeight(x);
        updateHeight(y);
        return y;
    }
    Node insert(Node root, int key) {
        // Jeżeli węzeł jest pusty, zwróć nowy węzeł
        if (root == null) {
            return new Node(key);
        }
        // Jeżeli klucz do wstawienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (root.key > key) {
            root.left = insert(root.left, key);
        }
        // Jeżeli klucz do wstawienia jest większy niż klucz w bieżącym węźle, idź w prawo
        else if (root.key < key) {
            root.right = insert(root.right, key);
        }
        return rebalance(root);
    }
    public void delete(int key) {
        root = deleteRecursive(root, key);
    }
    Node deleteRecursive(Node node, int key) {
        // Jeżeli węzeł jest pusty nie usuwaj niczego
        if (node == null) {
            return node;
        }
        // Jeżeli klucz do usunięcia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (node.key > key) {
            node.left = deleteRecursive(node.left, key);
        }
        // Jeżeli klucz do usunięcia jest większy niż klucz w bieżącym węźle, idź w prawo
        else if (node.key < key) {
            node.right = deleteRecursive(node.right, key);
        }
        // Jeżeli klucz do usunięcia jest równy kluczowi w bieżącym węźle
        else {
            // Jeżeli jeden z potomków jest pusty, zastąp bieżący węzeł niepustym potomkiem
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            }
            // Jeżeli węzeł ma 2 potomków, znajdź najbardziej prawy element lewego poddrzewa
            else {
                Node mostLeftChild = mostLeftChild(node.left);
                // Zastąp klucz bieżącego węzła najbardziej "lewym" kluczem
                node.key = mostLeftChild.key;
                // Usuń najbardziej  prawy element lewego poddrzewa
                node.left = deleteRecursive(node.left, node.key);
            }
        }
        // rebalance
        if (node != null) {
            node = rebalance(node);
        }
        // Zwróć zmodyfikowany węzeł
        return node;
    }
    private Node mostLeftChild(Node node) {
        Node current = node;
        // Przechodź w prawo aż do końca
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
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
