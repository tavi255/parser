import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

public class Grammar {

    private Set<String>N=new HashSet<>();
    private Set<String>E=new HashSet<>();
    private final HashMap<List<String>,Set<List<String>>>p=new HashMap<>();
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
                String [] lhsTokens=tokens[0].split(" ");
                String [] rhsTokens=tokens[1].split("\\|");

                List<String>lhs=new ArrayList<>();

                for(String l:lhsTokens)
                {
                    if(!lhs.contains(l.strip()))
                        lhs.add(l.strip());
                }


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
                sb.append(st).append(" ");
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
        String [] arr=nt.split(" ");

        if(arr.length==1)
        {
            for(List<String>lhs:p.keySet())
                if(lhs.contains(nt) && lhs.size()==1) {
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
        }

        boolean ok=false;
        int line=0;
        int ln=-1;

        sb.append("productions for: ").append(nt).append("\n");

        for(List<String>lhs:p.keySet())
        {
            if(lhs.size()==arr.length)
            {
                int nr=0;
                for(int i=0;i<lhs.size();i++)
                    if(lhs.get(i).equals(arr[i]))
                        nr++;

                if(nr==lhs.size())
                {
                    ok=true;
                    ln=line;
                }

            }
            line++;
        }


        if(ok)
        {
            line=0;
            for(List<String>lhs:p.keySet())
            {
                if(line==ln)
                {
                    for(String l:lhs)
                        sb.append(l).append(" ");

                    sb.append(" -> ");
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
                line++;
            }





        }

        System.out.println(sb.toString());

    }


    public boolean checkIFCFG()
    {

        boolean startSym=false;
        for(List<String>lhs:p.keySet())
            if(lhs.contains(S))
                startSym=true;

        if(!startSym)
            return false;

        for(List<String>lhs:p.keySet())
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


    public Map<List<String>,List<String>> filter(String nonT)
    {
        Map<List<String>,List<String>>f=new HashMap<>();

        for(List<String>k : p.keySet())
        {
            if(k.contains(nonT))
            {
                List<String> value = p.get(k).iterator().next();
                f.put(k,value);
            }
        }

        return f;

    }



}
