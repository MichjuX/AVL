public class NodeString {
    String key;
    int height;
    NodeString left;
    NodeString right;

    // For dictionary
    int plate;
    int pesel;

    public NodeString(String key) {
        this.key= String.valueOf(key);
    }

}
