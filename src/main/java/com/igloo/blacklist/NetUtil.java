
package com.igloo.blacklist;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
	/**
	 * 아이피정보를 RealIP로 변경시켜준다.
	 * @param ip 아이피정보 127.0.0.1과 같은형태.
	 * @return long으로 변환된 realIP값.
	 * @throws UnknownHostException 잘못된아이피정보가 넘겨졌을경우.
	 */
	public static long getRealIP(String ip)throws UnknownHostException {
        
		long realIP = InetAddress.getByName(ip.trim()).hashCode();
        
		if (realIP < 0) {
            return realIP ^ 0xFFFFFFFF00000000L;
        } else {
        	return realIP;
        }
    }
		
	/**
	 * 범위를 가진 아이피정보에 대한 RealIP정보를 얻는다.
	 * <p><pre>
	 * 1) 127.0.0.1~255.255.255.255 과 같은경우의 인자값.
	 * 2) 127.0.0.1/255 와 같은 인자값.
	 * 3) 127.0.0.1의 경우와 같은 평이한 인자값.
	 * 
	 * 1)2)의 경우에는 각각의 범위의 시작값과 범위의 마지막값(RealIP)이 long[2]배열에 들어가게되며
	 * 3)과같은경우엔 동일한 RealIP값이 long[2]에 중복되어서 들어가 있게된다.
	 * 
	 * </pre></p>
	 * @param ips 범위를 가진 아이피정보.
	 * @return RealIP의 배열값 2개가 들어있다.
	 * @throws UnknownHostException 잘못된 아이피정보가 넘겨졌을경우.
	 */
	public static long[] getRealIPWithRange(String ips) throws UnknownHostException {
        if(ips.indexOf("~") > 0) {
            long[] comp = new long[2];
            String[] tmp = ips.split("~");
            comp[0] = getRealIP(tmp[0].trim());
            comp[1] = getRealIP(tmp[1].trim());
            return comp;
        }
        else if(ips.indexOf("/") > 0) {
            long[] comp = new long[2];
            String[] tmp = ips.split("/");
            long tmp_ip = getRealIP(tmp[0].trim());
            int netmask = 32 - Integer.parseInt(tmp[1].trim());
            
            if(netmask < 0 || netmask > 32) {
                throw new UnknownHostException(ips+" is invalid");
            }            
            comp[0] = (tmp_ip & ((~0) - ((1 << netmask) - 1))); // start real ip
            comp[1] = (tmp_ip | ((1 << netmask) - 1)); // end real ip
            return comp;
        }
        else if(ips.endsWith(".")){
        	long[] comp = new long[2];
        	String[] tmp = {ips, ips};
        	while(tmp[0].split("\\.").length < 4){
        		tmp[0] += "0.";
        		tmp[1] += "255.";
        	}
        	comp[0] = getRealIP(tmp[0].replaceAll("\\.$", ""));
        	comp[1] = getRealIP(tmp[1].replaceAll("\\.$", ""));
        	return comp;
        }
        else {
        	long[] comp = new long[2];
        	long realIP=getRealIP(ips);
        	comp[0]=realIP;
        	comp[1]=realIP;
            return comp;
        }
    }
	
	public static String getProtocolName(String strNumber) {
		try {
			return getProtocolName(Integer.parseInt(strNumber));
		} catch (NumberFormatException e) {
			return "-";
		}
	}
	
	public static String getProtocolName(int number) {
		switch (number) {
		case 0:
			return "HOPOPT";
		case 1:
			return "ICMP";
		case 2:
			return "IGMP";
		case 3:
			return "GGP";
		case 4:
			return "IP";
		case 5:
			return "ST";
		case 6:
			return "TCP";
		case 7:
			return "CBT";
		case 8:
			return "EGP";
		case 9:
			return "IGP";
		case 10:
			return "BBN-RCC-MON";
		case 11:
			return "NVP-II";
		case 12:
			return "PUP";
		case 13:
			return "ARGUS";
		case 14:
			return "EMCON";
		case 15:
			return "XNET";
		case 16:
			return "CHAOS";
		case 17:
			return "UDP";
		case 18:
			return "MUX";
		case 19:
			return "DCN-MEAS";
		case 20:
			return "HMP";
		case 21:
			return "PRM";
		case 22:
			return "XNS-IDP";
		case 23:
			return "TRUNK-1";
		case 24:
			return "TRUNK-2";
		case 25:
			return "LEAF-1";
		case 26:
			return "LEAF-2";
		case 27:
			return "RDP";
		case 28:
			return "IRTP";
		case 29:
			return "ISO-TP4";
		case 30:
			return "NETBLT";
		case 31:
			return "MFE-NSP";
		case 32:
			return "MERIT-INP";
		case 33:
			return "DCCP";
		case 34:
			return "3PC";
		case 35:
			return "IDPR";
		case 36:
			return "XTP";
		case 37:
			return "DDP";
		case 38:
			return "IDPR-CMTP";
		case 39:
			return "TP++";
		case 40:
			return "IL";
		case 41:
			return "IPv6";
		case 42:
			return "SDRP";
		case 43:
			return "IPv6-Route";
		case 44:
			return "IPv6-Frag";
		case 45:
			return "IDRP";
		case 46:
			return "RSVP";
		case 47:
			return "GRE";
		case 48:
			return "DSR";
		case 49:
			return "BNA";
		case 50:
			return "ESP";
		case 51:
			return "AH";
		case 52:
			return "I-NLSP";
		case 53:
			return "SWIPE";
		case 54:
			return "NARP";
		case 55:
			return "MOBILE";
		case 56:
			return "TLSP";
		case 57:
			return "SKIP";
		case 58:
			return "IPv6-ICMP";
		case 59:
			return "IPv6-NoNxt";
		case 60:
			return "IPv6-Opts";
		case 62:
			return "CFTP";
		case 64:
			return "SAT-EXPAK";
		case 65:
			return "KRYPTOLAN";
		case 66:
			return "RVD";
		case 67:
			return "IPPC";
		case 69:
			return "SAT-MON";
		case 70:
			return "VISA";
		case 71:
			return "IPCV";
		case 72:
			return "CPNX";
		case 73:
			return "CPHB";
		case 74:
			return "WSN";
		case 75:
			return "PVP";
		case 76:
			return "BR-SAT-MON";
		case 77:
			return "SUN-ND";
		case 78:
			return "WB-MON";
		case 79:
			return "WB-EXPAK";
		case 80:
			return "ISO-IP";
		case 81:
			return "VMTP";
		case 82:
			return "SECURE-VMTP";
		case 83:
			return "VINES";
		case 84:
			return "TTP";
		case 85:
			return "NSFNET-IGP";
		case 86:
			return "DGP";
		case 87:
			return "TCF";
		case 88:
			return "EIGRP";
		case 89:
			return "OSPFIGP";
		case 90:
			return "Sprite-RPC";
		case 91:
			return "LARP";
		case 92:
			return "MTP";
		case 93:
			return "AX.25";
		case 94:
			return "IPIP";
		case 95:
			return "MICP";
		case 96:
			return "SCC-SP";
		case 97:
			return "ETHERIP";
		case 98:
			return "ENCAP";
		case 100:
			return "GMTP";
		case 101:
			return "IFMP";
		case 102:
			return "PNNI";
		case 103:
			return "PIM";
		case 104:
			return "ARIS";
		case 105:
			return "SCPS";
		case 106:
			return "QNX";
		case 107:
			return "A/N";
		case 108:
			return "IPComp";
		case 109:
			return "SNP";
		case 110:
			return "Compaq-Peer";
		case 111:
			return "IPX-in-IP";
		case 112:
			return "VRRP";
		case 113:
			return "PGM";
		case 115:
			return "L2TP";
		case 116:
			return "DDX";
		case 117:
			return "IATP";
		case 118:
			return "STP";
		case 119:
			return "SRP";
		case 120:
			return "UTI";
		case 121:
			return "SMP";
		case 122:
			return "SM";
		case 123:
			return "PTP";
		case 124:
			return "ISIS over IPv4";
		case 125:
			return "FIRE";
		case 126:
			return "CRTP";
		case 127:
			return "CRUDP";
		case 128:
			return "SSCOPMCE";
		case 129:
			return "IPLT";
		case 130:
			return "SPS";
		case 131:
			return "PIPE";
		case 132:
			return "SCTP";
		case 133:
			return "FC";
		case 134:
			return "RSVP-E2E-IGNORE";
		case 135:
			return "Mobility Header";
		case 136:
			return "UDPLite";
		case 137:
			return "MPLS-in-IP";
		case 138:
			return "manet";
		case 139:
			return "HIP";
		case 140:
			return "Shim6";
		default:
			return "(" + number + ")";
		}
	}
	
	
	public static String realIP2String(long realIP) {
		// Big Endian
		return (realIP / 256 / 256 / 256 % 256) + "."
				+ (realIP / 256 / 256 % 256) + "." + (realIP / 256 % 256) + "."
				+ (realIP % 256);
		
		// Little Endian
		//return (realIP % 256) + "." + (realIP / 256 % 256) + "." 
		//	+ (realIP / 256 / 256 % 256) + "."
		//	+ (realIP / 256 / 256 / 256 % 256);
	}
}
