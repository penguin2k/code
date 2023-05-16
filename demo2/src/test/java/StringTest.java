//用scanner.nextline每次读入一句话，再用split函数拆成一个个单词，循环一百次；
// 创建一个HashMap，key是单词String，value是他出现的次数int
//对每一个读入的单词,判断map里是否有该key，如果有，说明不是第一次出现，给他的value++；没有，说明第一次出现，把key存入，相应的value是1
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
public class StringTest {

    private void PrintHashmap(Map<String, Integer> hmap) {
        //private定义的方法只在该类中可用
        Iterator<Entry<String, Integer>> iter = hmap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key+" "+value);
        }
    }

    public static void main(String[] args)
    {
        String s;
        Map<String , Integer> hmap = new HashMap<String,Integer>(); //key代表字符，value代表出现的次数
        System.out.println("请输入一个字符串：");
        Scanner input = new Scanner(System.in); //input相当于从键盘到程序的一条管道，input是一个名字，也可以起别的名字
        for(int i = 0; i <100; i++) //循环100次，读入100条语句
        {
            s = input.nextLine(); //input.nextLine:他以回车符为结束符，回车符前面的所有内容都保存在s里面，包括空格；input.next:他以空格为结束，则s里只有第一个单词
            String[] Array = s.split(" ");
            int j;
            for( j = 0; j < Array.length; j++)
            {
                if(!hmap.containsKey(Array[j]))
                {
                    hmap.put(Array[j], 1);
                }
                else
                {
                    int count = hmap.get(Array[j]);
                    hmap.put(Array[j],++count);
                }
            }

        }
        //输出hashmap
        StringTest stringtest = new StringTest();
        stringtest.PrintHashmap(hmap);
    }
}