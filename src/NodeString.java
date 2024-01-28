public class NodeString {
    String key;
    int weight;
    NodeString left;
    NodeString right;

    // Referencje do innych obiektów NodeString
    NodeString reference;

    public NodeString(String key) {
        this.key = key;

        // Inicjalizacja referencji
        this.reference = null;
    }

}
