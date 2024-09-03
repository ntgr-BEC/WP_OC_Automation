proc ntgrQoSBuildQueueMinBandwidthMapping {strDevice mapping} {
    set NumberOfQueues [ntgrProdParamGet [_get_switch_model $strDevice] "cos.queue.NumberOfQueues"]
    set lstMapping {}
    for {set i 0} {$i < $NumberOfQueues} {incr i} {
        lappend lstMapping 0
    }

    foreach {qid bandwidth} $mapping {
        if {$qid >= $NumberOfQueues} {
            Netgear_log_file "ntgrQoSBuildQueueMinBandwidthMapping" "{qid: $qid} {NumberOfQueues: $NumberOfQueues}"
            UnExpectedError
        }
        lset lstMapping $qid $bandwidth
    }

    return $lstMapping
}

