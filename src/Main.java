import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    /////////////////////////////////////////////////
    // CONFIG - configuration of program
    public static final int DATA = 0; // Applies only to AVL
    // 0 - read from file
    // 1 - generate random numbers
    public static final String PROGRAM = "b";
    // A - AVL
    // B - DICTIONARY

    // GENERATOR - configuration of generator
    public static final int MIN = 0;
    // MIN - minimum value of random number
    public static final int MAX = 100;
    // MAX - maximum value of random number
    public static final int N = 20;
    // N - number of random numbers
    /////////////////////////////////////////////////


    // ANSI - colors
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
    /////////////////////////////////////////////////
    // AVL
        if(PROGRAM.equals("A") || PROGRAM.equals("a")) {
            // implement AVL
            AVL avl = new AVL();
            // insert from file InTest1.txt
            if (DATA == 0) {
                System.out.println(ANSI_CYAN + "READING VALUES FROM FILE..." + ANSI_RESET);
                try {
                    Scanner file = new Scanner(new File("InTest1.txt"));
                    while (file.hasNext()) {
                        int key = file.nextInt();

                        avl.root = avl.insert(avl.root, key);
                    }
                    System.out.println(ANSI_GREEN + "Inserted values from file InTest1.txt" + ANSI_RESET);
                } catch (FileNotFoundException e) {
                    System.out.println(ANSI_RED + "File InTest1.txt not found." + ANSI_RESET);
                    return;
                }
//                Scanner file = new Scanner(new File("InTest1.txt"));
//                while (file.hasNext()) {
//                    int key = file.nextInt();
//
//                    avl.root = avl.insert(avl.root, key);
//                }

            }
            // insert from random numbers
            else {
                try {
                    System.out.println(ANSI_YELLOW + "GENERATING RANDOM VALUES" + ANSI_RESET);
                    AVLGenerator.generate(MIN, MAX, N, avl);
                    System.out.println(ANSI_GREEN + "Generated random values." + ANSI_RESET);
                } catch (FileNotFoundException e) {
                    System.out.println(ANSI_RED + "File OutTest2.txt couldn't be created." + ANSI_RESET);
                    return;
                }


            }

            // menu
            int sw = 0;
            Scanner sc = new Scanner(System.in);
            while (sw == 0) {
                Scanner sc2 = new Scanner(System.in);
                System.out.println("1. Zapisz elementy drzewa AVL (wraz z wagami umieszczonymi w nawiasach) do pliku OutTest3.txt w kolejności KLP");
                System.out.println("2. Podaj wagę i poziom (korzeń jest na poziomie 0) wybranego elementu drzewa AVL.");
                System.out.println("3. Dodaj element do drzewa AVL.");
                System.out.println("4. Usuń element z drzewa AVL.");
                System.out.println("5. Wypisz elementy drzewa AVL.");
                System.out.println("6. Zakończ działanie programu.");
                switch (sc.nextLine()) {
                    case "1":
                        // Zapisz elementy drzewa AVL (wraz z wagami umieszczonymi w nawiasach) do pliku OutTest3.txt w
                        // kolejności KLP
                        try {
                            PrintWriter save = new PrintWriter("OutTest3.txt");
                            avl.SaveKLP(avl.root, save);
                            save.close();
                            System.out.println(ANSI_GREEN + "Pomyślnie zapisano do pliku OutTest3.txt" + ANSI_RESET);
                        } catch (FileNotFoundException e) {
                            System.out.println(ANSI_RED + "Nie znaleziono pliku OutTest3.txt." + ANSI_RESET);
                        }
//                        PrintWriter save = new PrintWriter("OutTest3.txt");
//                        avl.SaveKLP(avl.root, save);
//                        save.close();
                        break;
                    case "2":
                        // Podaj wagę i poziom (korzeń jest na poziomie 0) wybranego elementu drzewa AVL.
                        System.out.println(Main.ANSI_GREEN+"Elementy możliwe do wybrania:"+Main.ANSI_RESET);
                        avl.PrintKLP(avl.root);
                        System.out.println();
                        System.out.print(ANSI_YELLOW+"Podaj element: "+ANSI_RESET);
                        avl.getWeightHeight(avl.root, sc2.nextInt(), 0);
                        break;
                    case "3":
                        // Dodaj element do drzewa AVL.
                        System.out.print(ANSI_YELLOW+"Podaj element do dodania: "+ANSI_RESET);
                        avl.insert(avl.root, sc2.nextInt());
                        System.out.println(ANSI_GREEN + "Dodano element (jeśli wcześniej nie istniał)." + ANSI_RESET);
                        break;
                    case "4":
                        // Usuń element z drzewa AVL.
                        if(avl.root==null){
                            System.out.println(Main.ANSI_RED+"Drzewo jest puste."+Main.ANSI_RESET);
                        }
                        else {
                            System.out.println(Main.ANSI_GREEN+"Elementy możliwe do usunięcia:"+Main.ANSI_RESET);
                            avl.PrintKLP(avl.root);
                            System.out.println();
                            System.out.print(ANSI_YELLOW+"Podaj element do usunięcia: "+ANSI_RESET);
                            avl.delete(sc2.nextInt());
                            System.out.println(ANSI_GREEN + "Usunięto element (jeśli taki istniał)." + ANSI_RESET);
                        }
                        break;

                    case "5":
                        // Wypisz elementy drzewa AVL.
                        avl.PrintKLP(avl.root);
                        System.out.println();
                        break;
                    case "6":
                        // Zakończ działanie programu.
                        sw = 1;
                        break;
                }
            }
        }
        // END OF AVL
        /////////////////////////////////////////////////
        // DICTIONARY
        else if (PROGRAM.equals("B") || PROGRAM.equals("b")) {
            // implement DICTIONARY of plates numbers and pesel numbers
            Dictionary dictionary = new Dictionary();
            
            System.out.println(ANSI_CYAN + "READING VALUES FROM FILE..." + ANSI_RESET);
            try {
                Scanner file = new Scanner(new File("InTestB.txt"));
                while (file.hasNext()) {
                    String keyPesel = file.next();
                    dictionary.insertPesel(keyPesel);

                    String keyPlate = file.next();
                    dictionary.insertPlate(keyPlate);
                }

                System.out.println(ANSI_GREEN + "Inserted values from file InTest1.txt" + ANSI_RESET);
            } catch (FileNotFoundException e) {
                System.out.println(ANSI_RED + "File InTest1.txt not found." + ANSI_RESET);
                return;
            }

            dictionary.printPesel();
            System.out.println();
            dictionary.printPlate();

            }

    }

}