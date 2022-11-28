import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private final Grammar grammar;

    public Parser(Grammar grammar)
    {
        this.grammar=grammar;
    }

    public Map<List<String>,List<String>>closure(String input)
    {
        Map<List<String>,List<String>>p=new HashMap<>();
        List<String>lineListT= Arrays.asList(input.split("->"));
        List<String>lineList=lineListT.stream().map(String::trim).collect(Collectors.toList());
        List<String>key=Arrays.asList(lineList.get(0).split("\\|"));
        List<List<String>>value=new ArrayList<>();
        List<String>token=List.of(lineList.get(1).split("\\|"));

        for(var str:token)
        {
            List<String>prod=Arrays.asList(str.strip().split(" "));
            value.add(prod);

        }

        return null;

    }



}
