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

            for(String st:lhs)
                sb.append(st).append(",");
            sb.replace(sb.length()-1,sb.length()," -> ");
            for(List<String>ls:rhs)
            {
                for(String elem:ls)
                    sb.append(elem).append(" ");
                sb.append("|");
            }

            sb.replace(sb.length()-1,sb.length(),"");
            sb.append("\n");

        });


        System.out.println(sb.toString());

    }

    public void print_productions_forNonTerminal(String nt)
    {
        StringBuilder sb=new StringBuilder();

        sb.append("productions for: ").append(nt).append("\n");

        for(Set<String>lhs:p.keySet())
            if(lhs.contains(nt))
            {
                sb.append(nt).append(" -> ");
                Set<List<String>>rhs=p.get(lhs);

                for(List<String>ls:rhs)
                {
                    for(String st:ls)
                    {
                        sb.append(st).append(" ");
                    }
                    sb.append("|");
                }

                sb.replace(sb.length()-1,sb.length(),"");


            }

        System.out.println(sb.toString());

    }


    public boolean checkIFCFG()
    {

        boolean startSym=false;
        for(Set<String>lhs:p.keySet())
            if(lhs.contains(S))
                startSym=true;

        if(!startSym)
            return false;

        for(Set<String>lhs:p.keySet())
        {
            if(lhs.size()>1)
                return false;

            else if(!N.contains(lhs.iterator().next()))
                return false;

            Set<List<String>>rhs=p.get(lhs);

            for(List<String>ls:rhs)
            {
                for(String st:ls)
                    if(!N.contains(st) && !E.contains(st))
                        return false;
            }

        }

        return true;


    }




}
