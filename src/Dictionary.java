public class Dictionary {
    AVLString avlPesel;
    AVLString avlPlate;

    Dictionary(){
        this.avlPesel = new AVLString();
        this.avlPlate = new AVLString();
    }
    void insert(String pesel, String plate){
        this.avlPesel.root = this.avlPesel.insert(this.avlPesel.root, pesel, );
        this.avlPlate.root = this.avlPlate.insert(this.avlPlate.root, plate);


    }
    void insertPesel(String key){
        this.avlPesel.root = avlPesel.insert(this.avlPesel.root, key);
    }
    void insertPlate(String key){
        this.avlPlate.root = this.avlPlate.insert(avlPlate.root, key);
    }
    void printPesel(){
        this.avlPesel.PrintKLP(this.avlPesel.root);

    }
    void printPlate(){
        this.avlPlate.PrintKLP(this.avlPlate.root);
    }
}
