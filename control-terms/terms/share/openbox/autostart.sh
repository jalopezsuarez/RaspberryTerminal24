# Openbox autostart.sh
# Programs that will run after Openbox has started

# VNC Server
x11vnc -usepw -repeat -shared -forever &

# Windows Envoronment
python /home/pi/terms/bin/remmina.py
idesk &
tint2 &
conky -q &

# Monopuesto Service
if [ -f /home/pi/terms/var/monopuesto.enabled ] ; then
   (sudo systemctl start monopuesto.service) &
fi

# Autorun Service
if [ -f /home/pi/terms/var/autorun.enabled ] ; then
   (sleep 8s && sudo systemctl start autorun.service) &
fi  
