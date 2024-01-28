import java.io.PrintWriter;

public class AVL {
    Node root;

    public Node insert(Node node, int key) {
        // Jeżeli węzeł jest pusty, zwróć nowy węzeł
        if (node == null) {
            return new Node(key);
        }
        // Jeżeli klucz do wstawienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        if (key < node.key) {
            node.left = insert(node.left, key);
        }
        // Jeżeli klucz do wstawienia jest większy niż klucz w bieżącym węźle, idź w prawo
        if (key > node.key) {
            node.right = insert(node.right, key);
        }
        return rebalance(node);
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

    public void getWeightAndLevel(Node node, int key, int height) { // wypisanie wagi i poziomu węzła o podanym kluczu
        if (node == null || node.key == key) {
            if(node==null){
                System.out.println(Main.ANSI_RED + "Not found"  + Main.ANSI_RESET);
            }
            else{
                System.out.println(Main.ANSI_GREEN + "Waga: " + calculateWeight(node) + ", Wysokość: " + height + Main.ANSI_RESET);
            }
        }
        else if (node.key > key) {
            getWeightAndLevel(node.left, key, height+1);
        }
        else {
            getWeightAndLevel(node.right, key, height+1);
        }
    }

    private Node rebalance(Node node) {
        // Aktualizuj poziom węzła
        updateLevel(node);
        // Oblicz wage węzła
        int weight = calculateWeight(node);

        // Jeżeli waga jest większa niż 1, znaczy to, że lewe poddrzewo jest zbyt wysokie
        if (weight > 1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w lewo (będzie podwójna LR)
            if (calculateWeight(node.left) < 0) {
                node.left = LL(node.left);
            }
            // Wykonaj rotację w prawo
            return RR(node);
        }
        // Jeżeli waga jest mniejsza niż -1, znaczy to, że prawe poddrzewo jest zbyt wysokie
        if (weight < -1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w prawo (będzie podwójna RL)
            if (calculateWeight(node.right) > 0) {
                node.right = RR(node.right);
            }
            // Wykonaj rotację w lewo
            return LL(node);
        }
        // Jeśli waga jest ok
        return node;
    }

    private int calculateWeight(Node node) {
        if (node == null) {
            return 0;
        }
        else { // Wysokość lewego poddrzewa minus wysokość prawego poddrzewa (wzór z wykładu)
            return getNodeLevel(node.left) - getNodeLevel(node.right);
        }
    }

    private void updateLevel(Node node) {
        // Wysokość węzła to maksimum wysokości lewego i prawego poddrzewa + 1 (wzór z wykładu)
        node.level = Math.max(getNodeLevel(node.left), getNodeLevel(node.right)) + 1;
    }
    private void updateLevel(Node A, Node B) {
        // Wysokość węzła to maksimum wysokości lewego i prawego poddrzewa + 1 (wzór z wykładu)
        A.level = Math.max(getNodeLevel(A.left), getNodeLevel(A.right)) + 1;
        B.level = Math.max(getNodeLevel(B.left), getNodeLevel(B.right)) + 1;
    }

    private int getNodeLevel(Node node) { // Funkcja, która zapobiega błędom związanym odwołaniem do poziomu pustego węzła
        if (node != null) {
            return node.level;
        }
        return -1;
    }

    private Node LL(Node A) {
        // Wykonaj rotację w lewo
        Node B = A.right;
        A.right = B.left;
        B.left = A;
        // Aktualizuj poziomy węzłów
        updateLevel(A,B);
        return B;
    }

    private Node RR(Node A) {
        // Wykonaj rotację w prawo
        Node B = A.left;
        A.left = B.right;
        B.right = A;
        // Aktualizuj poziomy węzłów
        updateLevel(A,B);
        return B;
    }

    private Node deleteRecursive(Node node, int key) {
        // Szukamy elementu do usunięcia:
        // Jeżeli klucz do usunięcia jest równy kluczowi w bieżącym węźle
        if (key == node.key) {
            if (node.right != null && node.left != null) { // Jeśli ma 2 potomków znajdź następnika
                // Zastąp klucz korzenia kluczem następnikia
                node.key = precestor(node.left);
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
        else {
            // Jeżeli klucz do usunięcia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
            if (key < node.key) {
                node.left = deleteRecursive(node.left, key);
            }
            // Jeżeli klucz do usunięcia jest większy niż klucz w bieżącym węźle, idź w prawo
            if (key > node.key) {
                node.right = deleteRecursive(node.right, key);
            }
        }
        // Zrób rotacje jeśli jest co rotować
        if (node != null) {
            node = rebalance(node);
        }
        // Zwróć zmodyfikowany węzeł
        return node;
    }

    private int precestor(Node node) { // znajdź następnik ( w tym przypadku poprzednik )
        Node temp = node;
        // Przechodź w prawo aż do końca
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.key;
    }

}
