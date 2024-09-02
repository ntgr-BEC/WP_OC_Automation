#Author: Daniel Dalton
#email: ddalton5@apple.com
#created: July 5th 2012
#Description: This is a simple Mailer class written in Python that uses SMTP to send a message over relay.apple.com
#Usage: Instantiate an object like 'm = Mailer(<sender>,<destination>)' and send a message via 'm.send(<subject>, <body>, *attachments)'

import os.path
import smtplib
import mimetypes
from email import encoders
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.audio import MIMEAudio
from email.mime.multipart import MIMEMultipart

class Mailer:
    """Send mail via Mailer(sender, destination) from sender to destination
        for multiple destinations, use commas to separate emails within quotes (python MIMELib doesn't support list notation)"""
    def __init__(self, sender='WirelessAutomation@group.apple.com', destination='Mobile_WiFi_QA_Reports@group.apple.com', server='relay.apple.com', port=25):        
        self.server = server
        self.port = port        
        self.sender_address = sender
        self.destination = destination
        
    def __unicode__(self):
        return 'Mailer Class'

    def Send(self, subject='subject', body='body', attachments=[] ):        
        """Invoke myMailer.Send(subject='subject', body='body', attachments=[attachments]) with subject, body, and PATH to attachments"""
        msg = MIMEMultipart()
        msg['Subject'] = subject
        msg['From'] = self.sender_address
        msg['To'] = self.destination
        #If html is in the text of the body
        #specifically checks for <html>, <head>, and <body> tags
        if( str.find(body,'<html>')!=-1 ):
            if(str.rfind(body,'</html>')!=-1
               &str.find(body,'<head>')!=-1
               &str.rfind(body,'</head>')!=-1
               &str.find(body,'<body>')!=-1
               &str.rfind(body,'</body>')!=-1 ):  
                print 'html'          
                msg.attach(MIMEText(body,'html'))
            else:
                print "Improperly formatted html"
        else:
            msg.attach(MIMEText(body))
        if attachments:
            for atch in attachments:
                citem, encoding = mimetypes.guess_type(atch)
                if citem is None or encoding is not None:
                    # No guess could be made, or the file is encoded (compressed), so
                    # use a generic bag-of-bits item.
                    citem = 'application/octet-stream'
                mainitem, subitem = citem.split('/', 1)
                if mainitem == 'text':           
                    fp = open(atch)
                    item = MIMEText(fp.read(), _subtype=subitem)
                    fp.close()
                elif mainitem == 'image':
                    fp = open(atch, 'rb')
                    item = MIMEImage(fp.read(), _subtype=subitem)
                    fp.close()
                elif mainitem == 'audio':
                    fp = open(atch, 'rb')
                    item = MIMEAudio(fp.read(), _subtype=subitem)
                    fp.close()
                else:
                    fp = open(atch, 'rb')
                    item = MIMEBase(mainitem, subitem)
                    item.set_payload(fp.read())
                    msg.attach(item)
                    fp.close()
                    # Encode the payload using Base64
                    encoders.encode_base64(msg)
                    
                item.add_header('Content-Disposition', 'attachment', filename="%s" % os.path.basename(atch))
                msg.attach(item)
    
        try:
            s = smtplib.SMTP(self.server)
            s.sendmail(msg['From'], msg['To'], msg.as_string())
            s.quit()
        except:
            print "Mail Delivery Failure"
           
           
if __name__ == '__main__':
    mymailer = Mailer()
    #mymailer.Send()
    mymailer.Send(subject='Dinosaurs Subject to Interrogation', body='Body of Legislature rule Unconstitutional',attachments=['./sub/test.txt', './sub/soup.png'])
    #mymailer.Send('Dinosaurs Subject to Interrogation', '<html><head>Body of Legislature rule Unconstitutional<body><table border="1"><tr><td>row 1, cell 1</td><td>row 1, cell 2</td></tr><tr><td>row 2, cell 1</td><td>row 2, cell 2</td></tr></table></body></head></html>',['test2.html', 'test.txt', 'soup.png'])
    
    