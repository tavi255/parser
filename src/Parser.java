import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    private final Grammar grammar;

    public Parser(Grammar grammar)
    {
        this.grammar=grammar;
    }

    public Map<List<String>,List<String>>closure(String input)
    {

       Map<List<String>,List<String>>p=new HashMap<>();
       List<String>lineListT=Arrays.asList(input.split("->"));
       List<String>lineList=lineListT.stream().map(String::trim).collect(Collectors.toList());

       List<String>key=Arrays.asList(lineList.get(0).strip());
       List<String>value=new ArrayList<>();
       String [] token=lineList.get(1).split("\\|");

       for(var str:token)
       {
           value.add(str.strip());
       }

       p.put(key,value);

       int size=0,index;

       String nonT;

       while(size<p.size())
       {
           size=p.size();
           Map<List<String>,List<String>>filteredP=new HashMap<>(p);

           for(Map.Entry elem:filteredP.entrySet())
           {
               value=(List<String>) elem.getValue();

               for(String s:value)
               {
                   index=s.indexOf('.');

                   if(index!=-1 && index<s.length()-1)
                   {
                       nonT=s.substring(index+1).split(" ")[0];
                       Map<List<String>,List<String>>filtered=grammar.filter(nonT);

                       for(Map.Entry elemB:filtered.entrySet())
                       {
                           List<String>keyb=(List<String>)elemB.getKey();
                           List<String>valueB=(List<String>)elemB.getValue();
                           if(!p.containsKey(keyb))
                           {
                               p.put(keyb,valueB.stream().map(x->"." + x).collect(Collectors.toList()));

                           }
                       }

                   }

               }
           }
       }

       return p;

    }

    public Map<List<String>, List<String>> goTo(Map<List<String>, List<String>> productions, String symbol) {

       Map<List<String>,List<String>>nestedMap=new HashMap<>();
       for(Map.Entry elem:productions.entrySet())
       {
           List<String>value=new ArrayList<>((List<String>)elem.getValue());
           List<String>key=(List<String>) elem.getKey();

           int ind2=-1;
           int i=0;

           List<Integer>ind=new ArrayList<>();
           List<Integer>ii=new ArrayList<>();

           for(var x:value)
           {

               ind2=x.indexOf("."+symbol);

               if(ind2!=-1)
               {
                   ind.add(ind2);
                   ii.add(i);
               }


               i++;
           }

           int indice=0;


           for(Integer index:ind)
           {
               if(index!=-1)
               {
                   i=ii.get(indice);

                   String new_symbol="";
                   int space=value.get(i).indexOf(" ",index); //primul spatiu de dupa punct

                   if(index==0)
                   {
                       if(space!=-1)
                       {
                           new_symbol=value.get(i).substring(1,space) + " ." + value.get(i).substring(space+1);

                       }
                       else
                       {
                           new_symbol=value.get(i).substring(1)+".";
                       }
                   }
                   else
                   {
                       if(space!=-1)
                       {
                           new_symbol=value.get(i).substring(0,index)
                                   + value.get(i).substring(index+1,space+1) +"."
                                   + value.get(i).substring(space+1);
                       }
                       else
                       {
                           new_symbol=value.get(i).substring(0,index)
                                   + value.get(i).substring(index+1)+".";
                       }
                   }

                   List<String> y = new LinkedList<>();
                   y.add(new_symbol);
                   value.set(i, new_symbol);
                   Map<List<String>,List<String>>closure=closure(key.get(0) + " -> " +String.join("|",value));
                   nestedMap.putAll(closure);

               }

               indice++;
           }



       }

       return nestedMap;
    }

    public List<Map<List<String>,List<String>>>canocicalCol()
    {
        List<Map<List<String>,List<String>>>result=new ArrayList<>();
        result.add(closure("S' -> .S"));
        boolean ok=true;

        while(ok)
        {
            ok=false;
            List<Map<List<String>,List<String>>>filteredResult=new ArrayList<>(result);

            for(Map<List<String>,List<String>>state:filteredResult)
            {
                List<String>concatenated= Stream.concat(grammar.getN().stream(),grammar.getE().stream()).collect(Collectors.toList());
                for(String elem:concatenated)
                {
                    Map<List<String>,List<String>> goToElem=goTo(state,elem);
                    if(!goToElem.isEmpty() && !included(result,goToElem))
                    {
                        result.add(goToElem);
                        ok=true;
                    }
                }
            }

        }
        return result;
    }

    private boolean included(List<Map<List<String>, List<String>>> result, Map<List<String>, List<String>> goToElem)
    {
        return result.stream().anyMatch((listofStates)->listofStates.entrySet().containsAll(goToElem.entrySet()));
    }


}
