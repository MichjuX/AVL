public class Dictionary {
    AVLString avlPesel;
    AVLString avlPlate;

    Dictionary(){
        this.avlPesel = new AVLString();
        this.avlPlate = new AVLString();
    }
    void insert(String pesel, String plate){
        NodeString peselNode = new NodeString(pesel);
        NodeString plateNode = new NodeString(plate);
        // Przypisanie referencji
        peselNode.reference = plateNode;
        plateNode.reference = peselNode;
        // Wstawienie do drzewa
        this.avlPesel.root = this.avlPesel.insert(this.avlPesel.root, peselNode);
        this.avlPlate.root = this.avlPlate.insert(this.avlPlate.root, plateNode);


    }
//    void insertPesel(String key){
//        NodeString peselNode = new NodeString(pesel);
//        this.avlPesel.root = avlPesel.insert(this.avlPesel.root, key);
//    }
//    void insertPlate(String key){
//        this.avlPlate.root = this.avlPlate.insert(avlPlate.root, key);
//    }
    void printPesel(){
        this.avlPesel.PrintKLP(this.avlPesel.root);

    }
    void printPlate(){
        this.avlPlate.PrintKLP(this.avlPlate.root);
    }
}
