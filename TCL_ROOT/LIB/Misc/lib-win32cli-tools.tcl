#
# TSHARK
#
proc WIN32CLI_TSHARK {} {
    return "C:/TOOLS/Wireshark/tshark.exe"
}

proc TSHARK_RFILTER_ARP {} {
    return "arp"
}

proc TSHARK_RFILTER_ARP_REQUEST {} {
    return "arp.opcode == 0x1"
}

proc TSHARK_RFILTER_ARP_REPLY {} {
    return "arp.opcode == 0x2"
}

proc TSHARK_RFILTER_ARP_SENDER_IP {ip} {
    return "arp.src.proto_ipv4 == $ip"
}

proc TSHARK_RFILTER_ARP_TARGET_IP {ip} {
    return "arp.dst.proto_ipv4 == $ip"
}

proc TSHARK_DumpPacketSummary {capturefile readfilter} {
    return [ exec -- "[WIN32CLI_TSHARK]" -t ad -n       -r "$capturefile" -R "$readfilter" 2>NUL ]
}

proc TSHARK_DumpPacketDetail {capturefile readfilter} {
    return [ exec -- "[WIN32CLI_TSHARK]" -t ad -n -V -x -r "$capturefile" -R "$readfilter" 2>NUL ]
}
