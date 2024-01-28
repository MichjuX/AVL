import java.io.PrintWriter;

public class AVLString {
    NodeString root;

    public NodeString insert(NodeString node, NodeString toInsert) {
        // Jeżeli węzeł jest pusty, zwróć nowy węzeł
        if (node == null) {
            return toInsert;
        }
        // Jeżeli klucz do wstawienia jest mniejszy niż klucz w bieżącym węźle, idź w lewo
        else if (toInsert.key.compareTo(node.key) < 0) {
            node.left = insert(node.left, toInsert);
        }
        // Jeżeli klucz do wstawienia jest większy niż klucz w bieżącym węźle, idź w prawo
        else if (toInsert.key.compareTo(node.key) > 0) {
            node.right = insert(node.right, toInsert);
        }
        return rebalance(node);
    }

    public void delete(String key, AVLString avl) {
        root = deleteRecursive(root, key, avl);
    }

    public void printKLP(NodeString node) {
        if (node != null) {
            String fullKey = node.key;
            String highlightedPart = fullKey.substring(fullKey.length() - 3); // Ostatnie 3 znaki

            String fullReferenceKey = node.reference.key;
            String highlightedReferencePart = fullReferenceKey.substring(fullReferenceKey.length() - 3); // Ostatnie 3 znaki

            System.out.println(fullKey.substring(0, fullKey.length() - 3) +
                    Main.ANSI_YELLOW + highlightedPart + " " +
                    Main.ANSI_RESET + fullReferenceKey.substring(0, fullReferenceKey.length() - 3) +
                    Main.ANSI_YELLOW + highlightedReferencePart + Main.ANSI_RESET);

            printKLP(node.left);
            printKLP(node.right);
        }
    }

    public void savePlate(NodeString node, PrintWriter save) {
        if (node != null) {
            save.println(node.reference.key + " " + node.key);
            savePlate(node.left, save);
            savePlate(node.right, save);
        }
    }

    public void savePesel(NodeString node, PrintWriter save) {
        if (node != null) {
            save.println(node.key + " " + node.reference.key);
            savePesel(node.left, save);
            savePesel(node.right, save);
        }
    }

    public void search(String key){
        searchRecursive(root, key);
    }

    private NodeString rebalance(NodeString node) {
        // Aktualizuj poziom węzła
        updateLevel(node);
        // Oblicz balans węzła
        int weight = calculateWeight(node);

        // Jeżeli waga jest większa niż 1, znaczy to, że lewe poddrzewo jest zbyt wysokie
        if (weight > 1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w lewo (będzie podwójna LR)
            if (calculateWeight(node.left) < 0) {
                node.left = LL(node.left);
            }
            // Wykonaj rotację w prawo dla węzła
            return RR(node);
        }
        // Jeżeli waga jest mniejsza niż -1, znaczy to, że prawe poddrzewo jest zbyt wysokie
        if (weight < -1) {
            // Jeżeli waga jest innego znaku, wykonaj rotację w prawo (będzie podwójna RL)
            if (calculateWeight(node.right) > 0) {
                node.right = RR(node.right);
            }
            // Wykonaj rotację w lewo dla węzła
            return LL(node);
        }
        // Jeśli jest balans
        return node;
    }

    private int calculateWeight(NodeString node) {
        if (node == null) {
            return 0;
        }
        else { // Wysokość lewego poddrzewa minus wysokość prawego poddrzewa (wzór z wykładu)
            return getNodeLevel(node.left) - getNodeLevel(node.right);
        }
    }

    private void updateLevel(NodeString node) {
        node.level = Math.max(getNodeLevel(node.left), getNodeLevel(node.right)) + 1;
    }
    private void updateLevel(NodeString A, NodeString B) {
        A.level = Math.max(getNodeLevel(A.left), getNodeLevel(A.right)) + 1;
        B.level = Math.max(getNodeLevel(B.left), getNodeLevel(B.right)) + 1;
    }

    private int getNodeLevel(NodeString node) {
        if (node != null) {
            return node.level;
        }

        return -1;
    }
    private NodeString LL(NodeString A) {
        // Wykonaj rotację w lewo
        NodeString B = A.right;
        A.right = B.left;
        B.left = A;
        // Aktualizuj poziomy węzłów
        updateLevel(A,B);
        return B;
    }

    private NodeString RR(NodeString A) {
        // Wykonaj rotację w prawo
        NodeString B = A.left;
        A.left = B.right;
        B.right = A;
        // Aktualizuj poziomy węzłów
        updateLevel(A,B);
        return B;
    }

    private NodeString deleteRecursive(NodeString node, String key, AVLString avl) {
        // Szukamy elementu do usunięcia:
        // Jeżeli klucz do usunięcia jest równy kluczowi w bieżącym węźle
        if (key.equals(node.key)){
            // Usuń z drugiego drzewa
            if(node.reference != null){
                avl.delete(node.reference.key, avl);
            }
            if (node.right != null && node.left != null) { // Jeśli ma 2 potomków znajdź następnika
                // Zastąp klucz korzenia kluczem następnikia
                node.key = precestor(node.left);
                // Usuń następnik
                node.left = deleteRecursive(node.left, node.key, avl);
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
            if (key.compareTo(node.key) < 0) {
                node.left = deleteRecursive(node.left, key, avl);
            }
            // Jeżeli klucz do usunięcia jest większy niż klucz w bieżącym węźle, idź w prawo
            if (key.compareTo(node.key) > 0) {
                node.right = deleteRecursive(node.right, key, avl);
            }
        }
        // rebalance
        if (node != null) {
            node = rebalance(node);
        }
        // Zwróć zmodyfikowany węzeł
        return node;
    }

    private String precestor(NodeString node) {
        NodeString temp = node;
        // Przechodź w prawo aż do końca
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.key;
    }

    private void searchRecursive(NodeString node, String key){
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

}
