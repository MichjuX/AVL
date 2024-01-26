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
    public static final String PROGRAM = "a";
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
                System.out.println(ANSI_CYAN + "WCZYTYWANIE DANYCH Z PLIKU..." + ANSI_RESET);
                try {
                    Scanner file = new Scanner(new File("InTest1.txt"));
                    while (file.hasNext()) {
                        int key = file.nextInt();

                        avl.root = avl.insert(avl.root, key);
                    }
                    System.out.println(ANSI_GREEN + "Pomyślnie wczytano dane z pliku InTest1.txt." + ANSI_RESET);
                } catch (FileNotFoundException e) {
                    System.out.println(ANSI_RED + "Nie znaleziono pliku InTest1.txt." + ANSI_RESET);
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
                    System.out.println(ANSI_YELLOW + "GENEROWANIE LOSOWYCH DANYCH..." + ANSI_RESET);
                    AVLGenerator.generate(MIN, MAX, N, avl);
                    System.out.println(ANSI_GREEN + "Pomyślnie wygenerowano losowe dane." + ANSI_RESET);
                } catch (FileNotFoundException e) {
                    System.out.println(ANSI_RED + "Plik OutTest2.txt nie mógł zostać ztworzony." + ANSI_RESET);
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
            Dictionary dictionary = new Dictionary();

            // menu
            int sw = 0;
            Scanner sc = new Scanner(System.in);

            while (sw == 0) {
                Scanner sc2 = new Scanner(System.in);
                System.out.println("1. Plik -> Wczytaj");
                System.out.println("2. Plik -> Zapisz");
                System.out.println("3. Wstaw -> numer_pesel");
                System.out.println("4. Wstaw -> numer_samochodu");
                System.out.println("5. Wyszukaj -> numer_pesel");
                System.out.println("6. Wyszukaj -> numer_samochodu");
                System.out.println("7. Usuń -> numer_pesel");
                System.out.println("8. Usuń -> numer_samochodu");
                System.out.println("9. Zakończ działanie programu.");
                switch (sc.nextLine()) {
                    case "1":
                        // Plik -> Wczytaj
                        System.out.println(ANSI_CYAN + "WCZYTYWANIE DANYCH Z PLIKU..." + ANSI_RESET);
                        try {
                            Scanner file = new Scanner(new File("InTestB.txt"));
                            while (file.hasNext()) {
                                String keyPesel = file.next();
                                String keyPlate = file.next();
                                dictionary.insert(keyPesel, keyPlate);
                            }

                            System.out.println(ANSI_GREEN + "Pomyślnie wczytano dane z pliku InTestB.txt" + ANSI_RESET);
                        } catch (FileNotFoundException e) {
                            System.out.println(ANSI_RED + "Nie znaleziono pliku InTestB.txt." + ANSI_RESET);
                            return;
                        }
                        break;
                    case "2":
                        // Plik -> Zapisz
                        try {
                            Scanner sc3 = new Scanner(System.in);
                            System.out.println("1. Kolejność względem numeru PESEL");
                            System.out.println("2. Kolejność względem numeru samochodu");
                            switch(sc3.nextLine()){
                                case "1":
                                    // Zapisz elementy drzewa AVL do pliku OutTestB.txt w kolejności KLP
                                    PrintWriter save = new PrintWriter("OutTestB.txt");
                                    dictionary.avlPesel.savePesel(dictionary.avlPesel.root, save);
                                    save.close();
                                    System.out.println(ANSI_GREEN + "Pomyślnie zapisano do pliku OutTestB.txt" + ANSI_RESET);
                                    break;
                                case "2":
                                    // Zapisz elementy drzewa AVL do pliku OutTestB.txt w kolejności KLP
                                    PrintWriter save2 = new PrintWriter("OutTestB.txt");
                                    dictionary.avlPlate.savePlate(dictionary.avlPlate.root, save2);
                                    save2.close();
                                    System.out.println(ANSI_GREEN + "Pomyślnie zapisano do pliku OutTestB.txt" + ANSI_RESET);
                                    break;
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println(ANSI_RED + "Nie znaleziono pliku OutTestB.txt." + ANSI_RESET);
                        }
                        break;
                    case "3":
                        // Wstaw -> numer_pesel
                        System.out.print(ANSI_YELLOW + "Podaj numer PESEL: " + ANSI_RESET);
                        String keyPesel = sc2.next();
                        System.out.print(ANSI_YELLOW + "Podaj numer samochodu: " + ANSI_RESET);
                        String keyPlate = sc2.next();
                        dictionary.insert(keyPesel, keyPlate);
                        System.out.println(ANSI_GREEN + "Dodano element (jeśli wcześniej nie istniał)." + ANSI_RESET);
                        break;
                    case "4":
                        // Wstaw -> numer_samochodu
                        System.out.print(ANSI_YELLOW + "Podaj numer samochodu: " + ANSI_RESET);
                        String keyPlate1 = sc2.next();
                        System.out.print(ANSI_YELLOW + "Podaj numer PESEL: " + ANSI_RESET);
                        String keyPesel1 = sc2.next();
                        dictionary.insert(keyPesel1, keyPlate1);
                        System.out.println(ANSI_GREEN + "Dodano element (jeśli wcześniej nie istniał)." + ANSI_RESET);
                        break;
                    case "5":
                        // Wyszukaj -> numer_pesel
                        System.out.print(ANSI_YELLOW + "Podaj numer PESEL: " + ANSI_RESET);
                        dictionary.avlPesel.search(sc2.next());
                        break;
                    case "6":
                        // Wyszukaj -> numer_pesel
                        System.out.print(ANSI_YELLOW + "Podaj numer samochodu: " + ANSI_RESET);
                        dictionary.avlPlate.search(sc2.next());
                        break;
                    case "7":
                        // Usuń -> numer_pesel
                        System.out.print(ANSI_YELLOW + "Podaj numer PESEL: " + ANSI_RESET);
                        dictionary.avlPesel.delete(sc2.next(), dictionary.avlPlate);
                        System.out.println(ANSI_GREEN + "Usunięto element (jeśli taki istniał)." + ANSI_RESET);
                        break;
                    case "8":
                        // Usuń -> numer_samochodu
                        System.out.print(ANSI_YELLOW + "Podaj numer samochodu: " + ANSI_RESET);
                        dictionary.avlPlate.delete(sc2.next(), dictionary.avlPesel);
                        System.out.println(ANSI_GREEN + "Usunięto element (jeśli taki istniał)." + ANSI_RESET);
                        break;
                    case "9":
                        sw = 1;
                        break;

                }
            }
        }
    }

}