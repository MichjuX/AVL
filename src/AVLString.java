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

    NodeString rebalance(NodeString node) {
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

    NodeString rotateRight(NodeString y) {
        // Przypisz zmienne pomocnicze
        NodeString x = y.left;
        NodeString z = x.right;
        // Wykonaj rotację w prawo
        x.right = y;
        y.left = z;
        // Aktualizuj wysokości węzłów
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    NodeString rotateLeft(NodeString x) {
        // Przypisz zmienne pomocnicze
        NodeString y = x.right;
        NodeString z = y.left;
        // Wykonaj rotację w lewo
        y.left = x;
        x.right = z;
        // Aktualizuj wysokości węzłów
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    NodeString insert(NodeString root, NodeString node) {
        // Jeżeli węzeł jest pusty, zwróć nowy węzeł
        if (root == null) {
            return node;
        }
        // Jeżeli klucz do wstawienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (node.key.compareTo(root.key) < 0) {
            root.left = insert(root.left, node);
        }
        // Jeżeli klucz do wstawienia jest większy niż klucz w bieżącym węźle, idź w prawo
        else if (node.key.compareTo(root.key) > 0) {
            root.right = insert(root.right, node);
        }
        return rebalance(root);
    }

    public void delete(String key, AVLString avl) {
        root = deleteRecursive(root, key, avl);
    }

    NodeString deleteRecursive(NodeString node, String key, AVLString avl) {
        // Jeżeli węzeł jest pusty nie usuwaj niczego
        if (node == null) {
            return node;
        }
        // Jeżeli klucz do usunięcia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (key.compareTo(node.key) < 0) {
            node.left = deleteRecursive(node.left, key, avl);
        }
        // Jeżeli klucz do usunięcia jest większy niż klucz w bieżącym węźle, idź w prawo
        else if (key.compareTo(node.key) > 0) {
            node.right = deleteRecursive(node.right, key, avl);
        }
        // Jeżeli klucz do usunięcia jest równy kluczowi w bieżącym węźle
        else {
            // Usuń z drugiego drzewa
            if(node.reference != null){
                avl.delete(node.reference.key, avl);
            }
            // Jeżeli jeden z potomków jest pusty, zastąp bieżący węzeł niepustym potomkiem
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            }
            // Jeżeli węzeł ma 2 potomków, znajdź najbardziej prawy element lewego poddrzewa
            else {
                NodeString mostLeftChild = precestor(node.left);
                // Zastąp klucz bieżącego węzła najbardziej "lewym" kluczem
                node.key = mostLeftChild.key;
                // Usuń najbardziej  prawy element lewego poddrzewa
                node.left = deleteRecursive(node.left, node.key, avl);
            }
        }
        // rebalance
        if (node != null) {
            node = rebalance(node);
        }
        // Zwróć zmodyfikowany węzeł
        return node;
    }


    private NodeString precestor(NodeString node) {
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

    void savePlate(NodeString node, PrintWriter save) {
        if (node != null) {
            save.println(node.reference.key + " " + node.key);
            savePlate(node.left, save);
            savePlate(node.right, save);
        }
    }
    void savePesel(NodeString node, PrintWriter save) {
        if (node != null) {
            save.println(node.key + " " + node.reference.key);
            savePesel(node.left, save);
            savePesel(node.right, save);
        }
    }
    void search(String key){
        searchRecursive(root, key);
    }
    void searchRecursive(NodeString node, String key){
        // Jeżeli węzeł jest pusty albo już znaleziono węzeł wypisz albo nie wypisz
        if (node == null || node.key.equals(key)) {
            if (node == null) {
                System.out.println(Main.ANSI_RED + "Not found" + Main.ANSI_RESET);
            } else {
                System.out.println(Main.ANSI_GREEN + node.reference.key + Main.ANSI_RESET);
            }
        }
        // Jeśli klucz do znalezienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (key.compareTo(node.key) < 0) {
            searchRecursive(node.left, key);
        }
        // Jeśli klucz do znalezienia jest większy niż klucz w bieżącym węźle, idź w prawo
        else {
            searchRecursive(node.right, key);
        }
    }

//    void getWeightHeight(NodeString node, String key, int height) {
//        if (node == null || node.key.equals(key)) {
//            if (node == null) {
//                System.out.println(Main.ANSI_RED + "Not found" + Main.ANSI_RESET);
//            } else {
//                System.out.println(Main.ANSI_GREEN + "Waga: " + getBalance(node) + ", Wysokość: " + height + Main.ANSI_RESET);
//            }
//        } else if (key.compareTo(node.key) < 0) {
//            getWeightHeight(node.left, key, height + 1);
//        } else {
//            getWeightHeight(node.right, key, height + 1);
//        }
//    }
}
