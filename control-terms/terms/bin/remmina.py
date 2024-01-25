import os
import shutil

remmina = '/home/pi/.local/share/remmina/'
idesktop = '/home/pi/.idesktop/'

link = """table Icon
Caption: %s
Command: remmina -c /home/pi/.local/share/remmina/%s.remmina
Icon: /home/pi/terms/share/remmina/remmina.png
Width: 64
Height: 64
end"""

shutil.rmtree(idesktop, ignore_errors=True)
if not os.path.exists(idesktop):
    os.makedirs(idesktop)

resources = os.listdir(remmina)
for filename in resources:
    if filename.endswith('.remmina'):
        connection, extension = os.path.splitext(filename)
        shortcut = os.path.join(idesktop, connection + '.lnk')
        with open(os.path.join(remmina, filename), 'r') as f:
            for line in f:
                if line.startswith('name'):
                    property = line.split('=')
                    connectionName = property[1].strip()
                    with open(shortcut, 'w') as connectionFile:
                        connectionFile.write(link % (connectionName, connection))
