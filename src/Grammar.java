import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

public class Grammar {

    private Set<String>N=new HashSet<>();
    private Set<String>E=new HashSet<>();
    private final HashMap<Set<String>,Set<List<String>>>p=new HashMap<>();
    private String S;

    public Grammar(String filename)
    {
        readFromFile(filename);
    }

    private void readFromFile(String filename)
    {
        try
        {
            BufferedReader r=new BufferedReader(new FileReader(filename));
            String []non_terminals=r.readLine().split(" ");
            this.N=new HashSet<>(Arrays.asList(non_terminals));

            String [] terminals= r.readLine().split(" ");

            this.E=new HashSet<>(Arrays.asList(terminals));

            this.S= r.readLine();

            String line= r.readLine();

            while(line!=null)
            {
                String[] tokens=line.split("->");
                String [] lhsTokens=tokens[0].split(",");
                String [] rhsTokens=tokens[1].split("\\|");

                Set<String>lhs=new HashSet<>();

                for(String l:lhsTokens)
                    lhs.add(l.strip());

                if(!p.containsKey(lhs))
                    p.put(lhs,new HashSet<>());

                for(String rhs:rhsTokens)
                {
                    ArrayList<String>productionElements=new ArrayList<>();
                    String[] rhsTokenElement=rhs.strip().split(" ");

                    for(String rr:rhsTokenElement)
                        productionElements.add(rr.strip());

                    p.get(lhs).add(productionElements);
                }

                line= r.readLine();

            }

        }

        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void print_non_Nterminals()
    {
        StringBuilder sb=new StringBuilder();

        sb.append("non terminals: ");
        for(String non:N)
        {
            sb.append(non).append(" ");

        }
        System.out.println(sb.toString());

    }

    public void print_terminals()
    {
        StringBuilder sb=new StringBuilder();

        sb.append("terminals: ");
        for(String t:E)
        {
            sb.append(t).append(" ");

        }
        System.out.println(sb.toString());

    }

    public void printStartSymbol()
    {
        System.out.println("starting symbol: "+ S);
    }

    public void print_productions()
    {
        StringBuilder sb=new StringBuilder();

        sb.append("productions: \n");
        p.forEach((lhs,rhs)->{

            for(String left:lhs)
            {

            }

        });
        System.out.println(sb.toString());

    }




}
