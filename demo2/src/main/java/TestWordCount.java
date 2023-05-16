
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestWordCount {
    public static void main(String[] args) {
        String txtString = WordCount.getTxtString("H:\\code\\java\\demo2\\src\\test\\java\\Words");
        Map<String, Integer> treeMap = WordCount.getCounter(txtString);
        treeMap.forEach((key, value) -> System.out.println(key + ": " + value));
        try {
            String line = System.getProperty("line.separator");
            StringBuffer str = new StringBuffer();
            FileWriter fw = new FileWriter("conut.txt", true);
            Set set = treeMap.entrySet();
            Iterator iter = set.iterator();
            while(iter.hasNext()){
                Map.Entry entry = (Map.Entry)iter.next();
                str.append(entry.getKey()+" : "+entry.getValue()).append(line);
            }
            fw.write(str.toString());
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

