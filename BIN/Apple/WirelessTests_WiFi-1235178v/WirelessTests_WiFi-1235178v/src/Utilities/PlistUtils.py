"""
Created on Oct 23, 2012

@author: mwang, aomoto
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import copy

class PlistUtils(object):
    """
    Functions for plist
    """

    @classmethod
    def UpdatePlist(self, basePlist, data):
        """
        Recursive update for nested dictionaries/lists

        Example:
        >>> abc = {'a':0, 'b':{'b':0}, 'c':[{'c':0}]}
        >>> a = {'a':1}
        >>> b = {'b':{'b':2}}
        >>> c = {'c':[{'c':3}]}
        >>> PlistUtils.UpdatePlist(abc, a)
        >>> PlistUtils.UpdatePlist(abc, b)
        >>> PlistUtils.UpdatePlist(abc, c)
        >>> abc
        {'a': 1, 'b': {'b': 2}, 'c': [{'c': 3}]}
        """
        if isinstance(data, dict):
            for (key, val) in data.iteritems():
                if isinstance(val, dict):
                    self.UpdatePlist(basePlist.setdefault(key, {}), val)
                elif isinstance(val, list):
                    self.UpdatePlist(basePlist.setdefault(key, []), val)
                else:
                    basePlist[key] = data[key]
        elif isinstance(data, list):
            for (n, item) in enumerate(data):
                if len(basePlist) <= n:
                    basePlist.append(copy.deepcopy(item))
                else:
                    self.UpdatePlist(basePlist[n], item)

    @classmethod
    def MergePlist(self, base, *plists):
        """ Returns a plist deep copy with merged with provided plists """
        base_copy = copy.deepcopy(base)
        for data in plists:
            self.UpdatePlist(base_copy, data)
        return base_copy


