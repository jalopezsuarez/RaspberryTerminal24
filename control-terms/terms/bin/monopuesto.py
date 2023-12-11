import sys
import os
import shutil
import mmap

remmina = ('/home/pi/.local/share/remmina/')

resources = os.listdir(remmina)
for filename in resources:
    if filename.endswith('.remmina'):
        with open(remmina + filename,'r') as f:
            print remmina + filename
            sys.exit(0)
