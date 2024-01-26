import java.io.PrintWriter;

public class AVL {
    Node root;

    public Node insert(Node root, int key) {
        // Jeżeli węzeł jest pusty, zwróć nowy węzeł
        if (root == null) {
            return new Node(key);
        }
        // Jeżeli klucz do wstawienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        if (key < root.key) {
            root.left = insert(root.left, key);
        }
        // Jeżeli klucz do wstawienia jest większy niż klucz w bieżącym węźle, idź w prawo
        if (key > root.key) {
            root.right = insert(root.right, key);
        }
        return rebalance(root);
    }

    public void delete(int key) {
        root = deleteRecursive(root, key);
    }

    public void PrintKLP(Node node) {
        if (node != null) {
            System.out.print(Main.ANSI_GREEN+node.key+" "+Main.ANSI_RESET);
            PrintKLP(node.left);
            PrintKLP(node.right);
        }
    }

    public void SaveKLP(Node node, PrintWriter save) {
        if (node != null) {
            save.print(node.key+"("+ calculateWeight(node)+") ");
            SaveKLP(node.left, save);
            SaveKLP(node.right, save);
        }
    }

    public void getWeightHeight(Node node, int key, int height) {
        if (node == null || node.key == key) {
            if(node==null){
                System.out.println(Main.ANSI_RED + "Not found"  + Main.ANSI_RESET);
            }
            else{
                System.out.println(Main.ANSI_GREEN + "Waga: " + calculateWeight(node) + ", Wysokość: " + height + Main.ANSI_RESET);
            }
        }
        else if (node.key > key) {
            getWeightHeight(node.left, key, height+1);
        }
        else {
            getWeightHeight(node.right, key, height+1);
        }
    }

    private Node rebalance(Node node) {
        // Aktualizuj wysokość węzła
        updateNodeHeight(node);
        // Oblicz balans węzła
        int weight = calculateWeight(node);

        // Jeżeli balans jest większy niż 1, znaczy to, że lewe poddrzewo jest zbyt wysokie
        if (weight > 1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w lewo (będzie podwójna LR)
            if (calculateWeight(node.left) < 0) {
                node.left = LL(node.left);
            }
            // Wykonaj rotację w prawo
            return RR(node);
        }
        // Jeżeli balans jest mniejszy niż -1, znaczy to, że prawe poddrzewo jest zbyt wysokie
        if (weight < -1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w prawo (będzie podwójna RL)
            if (calculateWeight(node.right) > 0) {
                node.right = RR(node.right);
            }
            // Wykonaj rotację w lewo
            return LL(node);
        }

        // Jeśli jest balans
        return node;
    }

    private int calculateWeight(Node node) {
        if (node == null) {
            return 0;
        }
        else { // Wysokość lewego poddrzewa minus wysokość prawego poddrzewa (wzór z wykładu)
            return getNodeHeight(node.left) - getNodeHeight(node.right);
        }
    }

    private void updateNodeHeight(Node node) {
        // Wysokość węzła to maksimum wysokości lewego i prawego poddrzewa + 1 (wzór z wykładu)
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }

    private int getNodeHeight(Node node) { // Funkcja, która zapobiega błędom związanym odwołaniem do wysokości pustego węzła
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    private Node LL(Node A) {
        // Wykonaj rotację w lewo
        Node B = A.right;
        A.right = B.left;
        B.left = A;
        // Aktualizuj wysokości węzłów
        updateNodeHeight(A);
        updateNodeHeight(B);
        return B;
    }

    private Node RR(Node A) {
        // Wykonaj rotację w prawo
        Node B = A.left;
        A.left = B.right;
        B.right = A;
        // Aktualizuj wysokości węzłów
        updateNodeHeight(A);
        updateNodeHeight(B);
        return B;
    }

    private Node deleteRecursive(Node node, int key) {
        // Jeżeli klucz do usunięcia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        if (key < node.key) {
            node.left = deleteRecursive(node.left, key);
        }
        // Jeżeli klucz do usunięcia jest większy niż klucz w bieżącym węźle, idź w prawo
        if (key > node.key) {
            node.right = deleteRecursive(node.right, key);
        }
        // Jeżeli klucz do usunięcia jest równy kluczowi w bieżącym węźle
        if (key == node.key) {
            if (node.right != null && node.left != null) { // Jeśli ma 2 potomków znajdź następnika
                // Zastąp klucz następnikiem
                node.key = ancestor(node.left);
                // Usuń następnik
                node.left = deleteRecursive(node.left, node.key);
            }
            else { // Jeżeli jeden z potomków jest pusty, zastąp bieżący węzeł niepustym potomkiem
                if (node.right == null) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }
        // Zrób rotacje jeśli jest co rotować
        if (node != null) {
            node = rebalance(node);
        }
        // Zwróć zmodyfikowany węzeł
        return node;
    }

    private int ancestor(Node node) {
        Node temp = node;
        // Przechodź w prawo aż do końca
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.key;
    }

}
