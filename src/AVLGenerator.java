import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AVLGenerator{
    static void generate(int min, int max, int n, AVL avl) throws FileNotFoundException {
        PrintWriter save = new PrintWriter("OutTest2.txt");
        for(int i=0;i<n-1;i++){
            int rand=(int)(Math.random()*(max-min+1)+min);
            save.print(rand+" ");
            avl.root = avl.insert(avl.root, rand);
        }
        save.close();
    }
}
