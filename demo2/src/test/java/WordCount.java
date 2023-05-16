import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
    public static void main(String[] args) throws Exception {
        //���建���ַ�������
        BufferedReader br = new BufferedReader(new FileReader("H:\\code\\java\\demo2\\src\\test\\java\\Words"));
        //�����Ƶͳ�ƹ�ϣӳ��
        Map<String, Integer> wc = new HashMap<>();
        //�������ַ�������
        String nextLine = "";
        //��ȡ�ļ�������������
        while ((nextLine = br.readLine()) != null) {
            //���ո��֣��õ���������
            String[] words = nextLine.split(" ");
            //������������,���е��ʼ���
            for (String word : words) {
                wc.put(word, wc.containsKey(word) ? wc.get(word) + 1 : 1);
            }
        }
        //����ָ����ʽ�����Ƶͳ�ƽ��
        for (String key : wc.keySet()) {
            System.out.println("( " + key + ", " + wc.get(key) + ")");
        }
    }
}
