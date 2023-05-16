import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码来自：https://www.oschina.net/code/snippet_1170650_53378
 */
public class NetUtil {
    /**
     * 执行单条指令
     * @param cmd 命令
     * @return 执行结果
     * @throws Exception
     */
    public static String command(String cmd) throws Exception{
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        StringBuilder result = new StringBuilder();
        byte[] data = new byte[256];
        while(in.read(data) != -1){
            String encoding = System.getProperty("sun.jnu.encoding");
            result.append(new String(data,encoding));
        }
        return result.toString();
    }


    /**
     * 获取mac地址
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getMacAddress(String ip) throws Exception{
        String result = command("arp -a "+ip);
        String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(result);
        StringBuilder mac = new StringBuilder();
        while (matcher.find()) {
            String temp = matcher.group();
            mac.append(temp);
        }
        return mac.toString();
    }
}
