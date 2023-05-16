
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;
public class WordCount {
    public static String getTxtString(String path) {
        StringBuilder sBuilder = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            char[] buffer = new char[512];
            int len;
            while ((len = br.read(buffer)) != -1) {
                sBuilder.append(new String(buffer, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sBuilder.toString();
    }
    public static Map<String, Integer> getCounter(String txtString) {
        Map<String, Integer> treeMap = new TreeMap<>();
        String[] strArr = txtString.split("[\\s?',!;.\u3000]+");
        for (String s : strArr) {
            treeMap.put(s, treeMap.getOrDefault(s, 0) + 1);
        }
        return treeMap;
    }
}

