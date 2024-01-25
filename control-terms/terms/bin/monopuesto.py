import sys
import os

remmina = '/home/pi/.local/share/remmina/'

resources = os.listdir(remmina)
for filename in resources:
    if filename.endswith('.remmina'):
        filepath = os.path.join(remmina, filename)
        print(filepath)
        sys.exit(0)
