import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args)
    {
        Grammar g =new Grammar("D:\\lftc lab\\parserr\\parser\\g1.txt");

        Parser p=new Parser(g);


        Map<List<String>, List<String>> closure=p.closure("S' -> .S");

        StringBuilder sb=new StringBuilder();

        for(List<String>k: closure.keySet())
        {
            String key=k.get(0);
            sb.append(key).append(" -> ");
            for(String v: closure.get(k))
            {
                sb.append(v).append("|");
            }

            sb.replace(sb.length()-1,sb.length(),"\n");

        }

        System.out.println(sb);

        Map<List<String>, List<String>> gto=p.goTo(closure,"S");

        StringBuilder sb2=new StringBuilder();

        for(List<String>k: gto.keySet())
        {
            String key=k.get(0);
            sb2.append(key).append(" -> ");
            for(String v: gto.get(k))
            {
                sb2.append(v).append("|");
            }

            sb2.replace(sb2.length()-1,sb2.length(),"\n");

        }

        System.out.println(sb2);


        List<Map<List<String>, List<String>>>canonicaCol=p.canocicalCol();

        int i=0;

        for(Map<List<String>,List<String>>iteration:canonicaCol)
        {
            System.out.println("Iteration: "+i);

            StringBuilder sb3=new StringBuilder();

            for(List<String>k: iteration.keySet())
            {
                String key=k.get(0);
                sb3.append(key).append(" -> ");
                for(String v: iteration.get(k))
                {
                    sb3.append(v).append("|");
                }

                sb3.replace(sb3.length()-1,sb3.length(),"\n");

            }

            System.out.println(sb3);


            i++;
        }

    }





}
