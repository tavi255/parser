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





    }



}
