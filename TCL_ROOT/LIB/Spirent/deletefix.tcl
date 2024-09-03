rename ::IntStcUtils::deleteHandle ""

##################################################################
#
# Procedure name: deleteHandle
# Input arguments: Handle TopLevel
# Output arguments: none
# Description: This routine deletes handle and all children from 
#              internal data structures. TopLevel is only used
#              this procedure is calling itself recursively. It
#              should never be specified in a call outside of
#              this procedure.
#
##################################################################

proc ::IntStcUtils::deleteHandle {handle {topLevel 1}} {
   global PHX_CMD_OK PHX_CMD_FAIL HANDLE CHILD_HANDLE

   # Let's first recursively delete child objects from the data structures
   if {[info exists CHILD_HANDLE($handle)]} {
       
       # Loop through the children
       foreach {objType childHandle} $CHILD_HANDLE($handle) {
           # I know the namespace qualifier is pointless
           # but the clarity is always worth it when doing recursion
           ::IntStcUtils::deleteHandle $childHandle 0
       }
       
       # Delete the objects children list
       unset CHILD_HANDLE($handle)     
   }
   
   if {[info exists HANDLE($handle)]} {
        # Now delete the object's handle
        unset HANDLE($handle)
   }
   
   # We check the topLevel flag of the call before we search for
   # the parent object. We want to modify the parent's child list
   # and remove this object from it. But if this is a recursive
   # call, we don't want to waste time searching and modifying
   # when it will be unset anyways.
   if {$topLevel} {
       foreach parentHandle [array names CHILD_HANDLE] {
           if {[set index [lsearch -exact $CHILD_HANDLE($parentHandle) $handle]] != -1} {
               if {[llength $CHILD_HANDLE($parentHandle)] == 2} {
                   # This object was it's only child, so
                   # just unset the parent's child list
                   unset CHILD_HANDLE($parentHandle)
               } else {
                   # The parent has more than one child,
                   # so just remove this one from the list
                   set CHILD_HANDLE($parentHandle) [lreplace $CHILD_HANDLE($parentHandle) [expr $index -1] $index]
               }
               # Since we found the parent, we can take an early
               # exit out of this loop
               break
           }
       }
   }
}


##################################################################
#
# Redefine the stc::apply method
#
##################################################################
if {[info procs ::stc::applyFix]==""} {
  rename ::stc::apply ::stc::applyFix
}


proc ::stc::apply {hProject} {
    set childList [stc::show -allChildren $hProject]
    set portIndexList [lsearch -all $childList ethernet]
    set activePortHandleList ""

    foreach portIndex $portIndexList {
        set portHandle [lindex $childList [expr $portIndex + 1]]
        if {[stc::get txFrameRate -from $portHandle] > 0 } {
            lappend activePortHandleList $portHandle
        }
    }

    stc::applyFix $hProject
    stc::sleep 1

    foreach portHandle $activePortHandleList {
        if {[stc::get txFrameRate -from $portHandle] > 0 } {
            continue
        }
        stc::start Start_Traffic $portHandle
    }
}