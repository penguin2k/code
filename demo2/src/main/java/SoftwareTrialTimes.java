import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SoftwareTrialTimes {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("H:\\code\\java\\demo2\\src\\main\\java\\times.txt"));

        String line = br.readLine();
        int times = Integer.parseInt(line);

        if (times > 0) {
            System.out.println("������" + --times + "�����û���");
            FileWriter fw = new FileWriter("H:\\code\\java\\demo2\\src\\main\\java\\times.txt");
            fw.write(times + "");
            fw.close();
        }else{
            System.out.println("����ʹ�ô����ѵ�,�빺������!");
        }

        br.close();

    }
}