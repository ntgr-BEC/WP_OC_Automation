#!/bin/sh

################################################################################   
#
#  File Name		: Netgear-lib.tcl
#
#  Description       	:
#         This file sources all the library/API that can be used gloablly
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

################################# Common Libraries #############################
source ../LIB/Misc/lib-common-utils.tcl
source ../LIB/Misc/lib-show-command.tcl
source ../LIB/Misc/lib-global-command.tcl
source ../LIB/Misc/lib-switch-access.tcl
source ../LIB/Misc/lib-stp-support.tcl
source ../LIB/Misc/lib-poch-support.tcl
source ../LIB/Misc/lib-vlan-support.tcl
source ../LIB/Misc/lib-dot1p-support.tcl
source ../LIB/Misc/lib-rip-support.tcl
source ../LIB/Misc/lib-igmpsnp-support.tcl
source ../LIB/Misc/lib-mldsnp-support.tcl
source ../LIB/Misc/lib-tg-support.tcl
source ../LIB/Misc/lib-ip-support.tcl
source ../LIB/Misc/lib-jframe-support.tcl
source ../LIB/Misc/lib-pmirror-support.tcl
source ../LIB/Misc/lib-stormctrl-support.tcl
source ../LIB/Misc/lib-sntp-support.tcl
source ../LIB/Misc/lib-stk-support.tcl
source ../LIB/Misc/lib-vrrp-support.tcl
source ../LIB/Misc/lib-static-ip-support.tcl
source ../LIB/Misc/lib-ospf-support.tcl
source ../LIB/Misc/lib-mac-acl-support.tcl
source ../LIB/Misc/lib-ip-acl-support.tcl
source ../LIB/Misc/lib-qos-support.tcl
source ../LIB/Misc/lib-dhcp-support.tcl
source ../LIB/Misc/lib-dot1x-support.tcl
source ../LIB/Misc/lib-ipc-sockettcp.tcl
source ../LIB/Misc/lib-testbed-setup.tcl
source ../LIB/Misc/lib-dos.tcl
source ../LIB/Misc/lib-copy-support.tcl
source ../LIB/Misc/lib-ping-support.tcl
source ../LIB/Misc/lib-traceroute-support.tcl
source ../LIB/Misc/lib-timerange-support.tcl
source ../LIB/Misc/lib-udp_relay-support.tcl
source ../LIB/Misc/lib-psa-support.tcl

################################# Layer2 Libraries #############################
source ../LIB/Layer2/lib-vlan.tcl
source ../LIB/Layer2/lib-stp.tcl
source ../LIB/Layer2/lib-poch.tcl
source ../LIB/Layer2/lib-dot1p.tcl
source ../LIB/Layer2/lib-igmpsnp.tcl
source ../LIB/Layer2/lib-igmpsnooping.tcl
source ../LIB/Layer2/lib-mldsnooping.tcl
source ../LIB/Layer2/lib-pmirror.tcl
source ../LIB/Layer2/lib-stormctrl.tcl
source ../LIB/Layer2/lib-ntgr-stk.tcl
source ../LIB/layer2/lib-mac-acl.tcl
source ../LIB/layer2/lib-ntgr-qos.tcl
source ../LIB/layer2/lib-port-secure.tcl
source ../LIB/layer2/lib-dot1x.tcl
source ../LIB/layer2/lib-protected-port.tcl
source ../LIB/layer2/lib-mac-filter.tcl
source ../LIB/layer2/lib-lldp.tcl
source ../LIB/layer2/lib-switch.tcl
source ../LIB/layer2/lib-isdp.tcl
source ../LIB/layer2/lib-mac-addrtable.tcl
source ../LIB/layer2/lib-garp.tcl
source ../LIB/layer2/lib-poe.tcl
source ../LIB/layer2/lib-mvr.tcl
source ../LIB/layer2/lib-mlag.tcl
source ../LIB/layer2/lib-dhcpsnooping.tcl
source ../LIB/layer2/lib-ipv6dhcpsnooping.tcl
source ../LIB/layer2/lib-captiveportal.tcl
source ../LIB/layer2/lib-access-control.tcl
source ../LIB/layer2/lib-vlantrunking.tcl
################################# Layer3 Libraries ##############################
source ../LIB/Layer3/lib-rip.tcl
source ../LIB/Layer3/lib-ip.tcl
source ../LIB/Layer3/lib-jframe.tcl
source ../LIB/Layer3/lib-sntp.tcl
source ../LIB/Layer3/lib-vrrp.tcl
source ../LIB/Layer3/lib-static-ip.tcl
source ../LIB/Layer3/lib-ospf.tcl
source ../LIB/Layer3/lib-ip-acl.tcl
source ../LIB/Layer3/lib-login.tcl
source ../LIB/layer3/lib-routing.tcl
source ../LIB/layer3/lib-dhcp.tcl
source ../LIB/layer3/lib-snmp.tcl
#source ../LIB/layer3/lib-radius.tcl
source ../LIB/layer3/lib-user.tcl
source ../LIB/layer3/lib-aaa.tcl
source ../LIB/layer3/lib-copy.tcl
source ../LIB/layer3/lib-dns.tcl
source ../LIB/layer3/lib-http.tcl
source ../LIB/layer3/lib-ping.tcl
source ../LIB/layer3/lib-traceroute.tcl
source ../LIB/layer3/lib-ipv6.tcl
source ../LIB/layer3/lib-boot.tcl
source ../LIB/layer3/lib-password.tcl
source ../LIB/layer3/lib-timerange.tcl
source ../LIB/layer3/lib-udp_relay.tcl
source ../LIB/layer3/lib-dhcp_l2relay.tcl
source ../LIB/layer3/lib-remote_diagnostic.tcl
source ../LIB/layer3/lib-router_discovery.tcl
source ../LIB/layer3/lib-ip_source_guard.tcl
source ../LIB/Layer3/lib-ospfv3.tcl
source ../LIB/layer3/lib-dhcpv6.tcl
source ../LIB/layer3/lib-routing-multicast.tcl
source ../LIB/layer3/lib-ipv6_source_guard.tcl

################################# Spirent Libraries #############################
source ../LIB/Spirent/lib-spirent.tcl
source ../LIB/Spirent/lib-spirent-util.tcl
source ../LIB/Spirent/deletefix.tcl


################################# IXIA Libraries ################################
source ../LIB/Ixia/lib-ixia.tcl
source ../LIB/Ixia/lib-ixia-util.tcl
source ../LIB/Ixia/lib-ixautomate.tcl
source ../LIB/Ixia/lib_ixiaNetwork.tcl
################################# SIFOS Libraries ################################
source ../LIB/Sifos/lib-psl.tcl

########################### CLI Abstraction Layer ##############################

source ../LIB/CAL/cal-global-cmd.tcl
source ../LIB/CAL/cal-ntgr-vlan.tcl
source ../LIB/CAL/cal-ntgr-stp.tcl
source ../LIB/CAL/cal-ntgr-poch.tcl
source ../LIB/CAL/cal-dot1p.tcl
source ../LIB/CAL/cal-ntgr-rip.tcl
source ../LIB/CAL/cal-igmpsnp.tcl
source ../LIB/CAL/cal-igmpsnooping.tcl
source ../LIB/CAL/cal-mldsnooping.tcl
source ../LIB/CAL/cal-ntgr-ip.tcl
source ../LIB/CAL/cal-ntgr-jframe.tcl
source ../LIB/CAL/cal-ntgr-pmirror.tcl
source ../LIB/CAL/cal-ntgr-stormctrl.tcl
source ../LIB/CAL/cal-ntgr-sntp.tcl
source ../LIB/CAL/cal-ntgr-stk.tcl
source ../LIB/CAL/cal-ntgr-vrrp.tcl
source ../LIB/CAL/cal-ntgr-static-ip.tcl
source ../LIB/CAL/cal-ntgr-ospf.tcl
source ../LIB/CAL/cal-ntgr-ip-acl.tcl
source ../LIB/CAL/cal-ntgr-mac-acl.tcl
source ../LIB/CAL/cal-ntgr-login.tcl
source ../LIB/CAL/cal-ntgr-routing.tcl
source ../LIB/CAL/cal-ntgr-qos.tcl
source ../LIB/CAL/cal-ntgr-dhcp.tcl
source ../LIB/CAL/cal-ntgr-snmp.tcl
source ../LIB/CAL/cal-ntgr-port-secure.tcl
source ../LIB/CAL/cal-ntgr-dot1x.tcl
#source ../LIB/CAL/cal-ntgr-radius.tcl
source ../LIB/CAL/cal-ntgr-user.tcl
source ../LIB/CAL/cal-ntgr-protected-port.tcl
source ../LIB/CAL/cal-ntgr-mac-filter.tcl
source ../LIB/CAL/cal-ntgr-dos.tcl
source ../LIB/CAL/cal-ntgr-aaa.tcl
source ../LIB/CAL/cal-ntgr-copy.tcl
source ../LIB/CAL/cal-ntgr-dns.tcl
source ../LIB/CAL/cal-ntgr-ipv6.tcl
source ../LIB/CAL/cal-ntgr-boot.tcl
source ../LIB/CAL/cal-ntgr-switch.tcl
source ../LIB/CAL/cal-ntgr-isdp.tcl
source ../LIB/CAL/cal-ntgr-timerangel.tcl
source ../LIB/CAL/cal-ntgr-udp_relay.tcl
source ../LIB/CAL/cal-ntgr-dhcp-l2relay.tcl
source ../LIB/CAL/cal-ntgr-mac-addrtable.tcl
source ../LIB/CAL/cal-ntgr-garp.tcl
source ../LIB/CAL/cal-ntgr-poe.tcl
source ../LIB/CAL/cal-ntgr-mlag.tcl
source ../LIB/NTGR/ntgr-cli-libinit.tcl
source ../LIB/CAL/cal-ntgr-mvr.tcl
source ../LIB/CAL/cal-dhcpsnooping.tcl
source ../LIB/CAL/cal-ntgr-router-discovery.tcl
source ../LIB/CAL/cal-ntgr-ip-source-guard.tcl
source ../LIB/CAL/cal-ntgr-ospfv3.tcl 
source ../LIB/CAL/cal-ntgr-dhcpv6.tcl
source ../LIB/CAL/cal-ntgr-routing-multicast.tcl
source ../LIB/CAL/cal-ntgr-access_control.tcl
source ../LIB/CAL/cal-ntgr-ipv6-source-guard.tcl
source ../LIB/CAL/cal-ntgr-vlantrunk.tcl
##########################  UNIT Abstraction Layer Libraries ####################
source ../LIB/UAL/ual-ntgr-traffic.tcl

##########################  Generic APIs for abstraction ####################
source ../LIB/API/lib-ntgr-connect.tcl
source ../LIB/API/lib-ntgr-upgrade.tcl
source ../LIB/API/lib-ntgr-log.tcl

