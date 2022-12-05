public class Main {

    public static void main(String[] args)
    {
        Grammar g =new Grammar("D:\\lftc lab\\parserr\\parser\\g2.txt");

        g.print_non_Nterminals();
        g.print_terminals();
        g.printStartSymbol();
        g.print_productions();
        g.print_productions_forNonTerminal("A S");

        if(g.checkIFCFG())
            System.out.println("Context free grammar!");
        else
            System.out.println("Not a context free grammar!");

    }



}
