'''Apache Sling Shell
#requires poster (http://atlee.ca/software/poster/index.html)
'''

import urllib2
import base64
import json
import subprocess
import os

#import poster
#
#def curl(url, username=None, password=None, method='GET', data=None, file_path=None):
#    '''simple HTTP util. similar to curl
#    data should be dict.
#    if file_path is set, the file is uploaded through multipart encoding.
#    when username and password are given, it uses basic auth.'''
#
#    if method == 'POST' and data is None:
#        #force POST by putting data
#        data = {}
#
#    print('%s %s %s:%s' % (method, url, username, password))
#
#
#    if file_path is not None:
#        if data is not None:
#            data['*'] = open(file_path, 'rb')
#        poster.encode.multipart_encode(
#    req = urllib2.Request(url, urllib.urlencode(data) if data is not None else None)
#
#    if username is not None and password is not None:
#        #use basic auth
#        cred = ('%s:%s' % (username, password)).replace('\n', '')
#        encoded = base64.encodestring(cred)
#        req.add_header('Authorization', 'Basic ' + encoded)
#    return urllib2.urlopen(req)

class SlingCurl(object):
    def __init__(self, curl='curl', host='localhost', port=8080, username='admin', password='admin', cwd='/'):
        self.curl = curl
        self.host = host
        self.port = port
        self.username = username
        self.password = password
        self.cwd = cwd #current working directory
        self.json = None #current json. as a cache.

    def _curl_cmd(self, path, props=None, file_path=None):
        '''generates curl command line.
        returns a list that can be used with subprocess.
        @param props is dict. POST params.
        @param file_path is for file uploading.'''

        cmd = [self.curl, '-f', '-s', '-u', "%s:%s" % (self.username, self.password)]

        if props is None:
            props = {}

        if file_path is not None:
            cmd.append('-T')
            cmd.append(file_path)
        else:
            for k,v in props.iteritems():
                cmd.append('-F')
                cmd.append("%s=%s" % (k,v))

        cmd.append('http://%s:%d%s' % (self.host, self.port, path))

        return cmd

    def get_json(self, path=None, level=1, is_tidy=False):
        if path is None:
            path = self.cwd

        tidy_selector = '.tidy' if is_tidy else ''
        cmd = self._curl_cmd('%s%s.%d.json' % (path, tidy_selector, level))
        p = subprocess.Popen(cmd, stdout=subprocess.PIPE)
        out,err = p.communicate()
        d = json.loads(out)
        if path == self.cwd:
            self.json = d #update cache
        return d

    def pwd(self):
        return self.cwd

    def ls(self, path=None):
        if path is None:
            path = self.cwd

        path = os.path.join(self.cwd, path)

        l = []
        d = self.get_json(path)
        for k,v in d.iteritems():
            if isinstance(v, dict):
                l.append(k + '/')
            else:
                l.append(k)
        return l

    def cd(self, path=None):
        if path is None:
            return
        self.cwd = os.path.abspath(os.path.join(self.cwd, path))

        #if path.startswith('/'):
        #    self.cwd = path
        #else:
        #    self.cwd = self.cwd + path

        self.json = None

    def propget(self, prop_name):
        if self.json is None:
            self.json = self.get_json()
        return self.json.get(prop_name)

    def propset(self, prop_name, value, path=None):
        if path is None:
            path = self.cwd
        props = {}
        props[prop_name] = value

        cmd = self._curl_cmd(path, props)
        p = subprocess.Popen(cmd, stdout=subprocess.PIPE)
        out,err = p.communicate()

        if path == self.cwd:
            self.json = None
        return p.returncode
