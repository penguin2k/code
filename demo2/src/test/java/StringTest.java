//��scanner.nextlineÿ�ζ���һ�仰������split�������һ�������ʣ�ѭ��һ�ٴΣ�
// ����һ��HashMap��key�ǵ���String��value�������ֵĴ���int
//��ÿһ������ĵ���,�ж�map���Ƿ��и�key������У�˵�����ǵ�һ�γ��֣�������value++��û�У�˵����һ�γ��֣���key���룬��Ӧ��value��1
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
public class StringTest {

    private void PrintHashmap(Map<String, Integer> hmap) {
        //private����ķ���ֻ�ڸ����п���
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
        Map<String , Integer> hmap = new HashMap<String,Integer>(); //key�����ַ���value������ֵĴ���
        System.out.println("������һ���ַ�����");
        Scanner input = new Scanner(System.in); //input�൱�ڴӼ��̵������һ���ܵ���input��һ�����֣�Ҳ������������
        for(int i = 0; i <100; i++) //ѭ��100�Σ�����100�����
        {
            s = input.nextLine(); //input.nextLine:���Իس���Ϊ���������س���ǰ����������ݶ�������s���棬�����ո�input.next:���Կո�Ϊ��������s��ֻ�е�һ������
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
        //���hashmap
        StringTest stringtest = new StringTest();
        stringtest.PrintHashmap(hmap);
    }
}