
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ServerThread implements Runnable{

    Socket client = null;
    ServerSocket server = null;
    String ip = null;
    int port = 0;
    String uid = null;
    static ArrayList<String> uid_arr = new ArrayList<String>();
    static Map<String, ServerThread> cm = new ConcurrentHashMap<>();
    public ServerThread(Socket client, ServerSocket server,String ip, int port) {
        this.client = client;
        this.server = server;
        this.ip = ip;
        this.port = port;
        this.uid = ip+":"+port;
        
    }
    @Override
    public void run() {

        uid_arr.add(uid);
        cm.put(uid,this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            InputStream in=client.getInputStream();
            OutputStream out=client.getOutputStream();
            String welcom = sdf.format(new Date())+"\n成功连接服务器...\n服务器IP: " + server.getInetAddress().getLocalHost().getHostAddress() + ", 端口: 6666\n客户端IP: " + ip + ", 端口: " + port + "\n";
            out.write(welcom.getBytes());
            updateOnlineList(out);
            byte[] buf = new byte[1024];
            int len = 0;
            while(true){
                len=in.read(buf);
                String msg=new String(buf,0,len);
                String type=msg.substring(0,msg.indexOf('/'));
                String chat=msg.substring(msg.indexOf('/')+1);
                if(type.equals("Exit")){
                    uid_arr.remove(uid_arr.indexOf(this.uid));
                    cm.remove(this.uid);
                    updateOnlineList(out);
                    break;
                }
                else if(type.equals("Chat")) {
                    String[] receiveArr = chat.substring(0, chat.indexOf('/')).split(",");
                    String word = chat.substring(chat.indexOf('/') + 1);
                    System.out.println(word);
                    chatOnlineList(out, uid, receiveArr, word);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void chatOnlineList(OutputStream out, String uid, String[] receiveArr, String word)throws Exception {

        for(String tmp:receiveArr){
            out=cm.get(tmp).client.getOutputStream();
            out.write(("Chat/" + uid + "/" + word).getBytes());
        }
    }

    private void updateOnlineList(OutputStream out) throws Exception {

            for (String tmp : uid_arr) {
                out = cm.get(tmp).client.getOutputStream();
                StringBuilder sb = new StringBuilder("OnlineListUpdate/");
                for (String member : uid_arr) {
                    sb.append(member);
                    if (uid_arr.indexOf(member) != uid_arr.size() - 1)
                        sb.append(",");
                }
                out.write(sb.toString().getBytes());
            }

    }

}
public class Server {

    public static void main(String[] args) throws Exception{
        ServerSocket server=new ServerSocket(6666);
      System.out.println("Server online...."+server.getInetAddress().getLocalHost().getHostAddress()+","+6666);

        while(true) {
            Socket client = server.accept();
            String ip=client.getInetAddress().getHostAddress();
            int port=client.getPort();
            new Thread(new ServerThread(client, server, ip, port)).start();
        }
    }
}
