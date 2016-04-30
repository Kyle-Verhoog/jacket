package jacket;

import java.util.Scanner;
import static jacket.list.*;


public class repl {
    public static void main(String[] args) {
        /*
        Scanner scan  = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scan.next();
            System.out.println(input);
        }*/
        pprint(cons(1, empty));
    }


    public static list genlst(int n) {
        if (equal(n, 0))
            return empty;
        else
            return cons(n, genlst(n - 1));
    }
}
