import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
    public static void main(String[] args) throws Exception {
        //定义缓冲字符输入流
        BufferedReader br = new BufferedReader(new FileReader("H:\\code\\java\\demo2\\src\\test\\java\\Words"));
        //定义词频统计哈希映射
        Map<String, Integer> wc = new HashMap<>();
        //定义行字符串变量
        String nextLine = "";
        //读取文件，遍历所有行
        while ((nextLine = br.readLine()) != null) {
            //按空格拆分，得到单词数组
            String[] words = nextLine.split(" ");
            //遍历单词数组,进行单词计数
            for (String word : words) {
                wc.put(word, wc.containsKey(word) ? wc.get(word) + 1 : 1);
            }
        }
        //按照指定格式输出词频统计结果
        for (String key : wc.keySet()) {
            System.out.println("( " + key + ", " + wc.get(key) + ")");
        }
    }
}
