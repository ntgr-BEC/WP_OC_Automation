proc ntgrRoutingARPAddStaticARP {strDevice arp} {
    set ip [keylget arp ip]
    set mac [keylget arp mac]

    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "arp $ip $mac"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingARPDeleteStaticARP {strDevice arp} {
    set ip [keylget arp ip]
    set mac [keylget arp mac]

    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no arp $ip"
    ntgrCLIExecute $strDevice "exit"
}
