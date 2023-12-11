import os
import shutil
import mmap

remmina = ('/home/pi/.local/share/remmina/')
idesktop = ('/home/pi/.idesktop/')

link = """table Icon
Caption: %s
Command: remmina -c /home/pi/.local/share/remmina/%s.remmina
Icon: /home/pi/terms/share/remmina/remmina.png
Width: 64
Height: 64
end"""

shutil.rmtree(idesktop)
if not os.path.exists(idesktop):
    os.makedirs(idesktop)

resources = os.listdir(remmina)
for filename in resources:
    if filename.endswith('.remmina'):
        connection, extension = os.path.splitext(filename)
        shortcut = idesktop + connection + '.lnk'
        with open(remmina + filename,'r') as f:
            m = mmap.mmap(f.fileno(), 0, prot=mmap.PROT_READ)
            while True:
                line = m.readline()
                if line == '': break
                if line.startswith('name'):
                    property = line.split('=')
                    connectionName = property[1].strip()
                    connectionFile = open(shortcut, 'w')
                    connectionFile.write(link % (connectionName, connection))
                    connectionFile.close()
