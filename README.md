## Creating Thin Client System for Industrial Environments with Raspberry Pi and Debian OS

Creating a thin client terminal using Raspberry Debian OS, customized for industrial settings, leveraging the Raspberry Pi barebone built on ARM architecture.

```
Raspberry Pi OS Lite (64-bit)
Debian version: 12 (bookworm)
System: 64-bit
Kernel version: 6.1
Release: December 5th 2023
Compatible: 3B, 3B+, 3A+, 4B, 400, 5, CM3, CM3+, CM4, CM4S, Zero 2 W
```

## Flashing the official image onto an 8GB SD Card with Raspberry Pi Imager

Utilizing the official Raspberry Pi Imager program version 1.8.1 to install the image onto an 8 GB SD Card for system deployment.

```
2023-12-05-raspios-bookworm-arm64-lite.img.xz
```

###  User and Password Configuration on First Boot

```
User: pi
Password: [PASS]
```

### Advanced Configuration Settings for Barebone and Debian System to Maximize SD Card Storage

Using the configuration utility, access advanced settings for both the barebone hardware and Debian OS to ensure optimal SD card storage utilization, with automatic file system expansion during installation.

```
sudo raspi-config
```

```
raspi-config :: 3 Interface Options :: I1 SSH
raspi-config :: 6 Advanced Options :: A1 Expand filesystem
```

### Updating the System Repository

After installing Debian Lite, the first step is to update the software repository using the following command:

```
sudo apt-get update
```

## VIM: Fixing Arrow Key Issues in Vim Editor for Console

To address special key issues, such as arrow keys and delete functions, within the console editor, you can use the following commands:

For the current user (pi):

```
echo "set nocompatible" > ~/.vimrc
echo "set backspace=indent,eol,start" >> ~/.vimrc
```

For the root user:

```
sudo su
echo "set nocompatible" > ~/.vimrc
echo "set backspace=indent,eol,start" >> ~/.vimrc
```

## Network: Customizing the Hostname for Network Identification

To improve network identification, change the default hostname 'raspberrypi' to 'terminal' using the following commands. Replace 'raspberrypi' with 'terminal' and save the file:

```
sudo vi /etc/hostname
terminal
```

```
sudo vi /etc/hosts
127.0.1.1 terminal
```

## X-Window: Installing a Minimal X-Window System

To set up a minimal X-Window system, use the following command. This command will install the necessary components for a minimal X-Window environment without additional recommended packages.

```
sudo apt-get install -y --no-install-recommends xinit xserver-xorg
```

### Openbox: Configuring Openbox Window Manager for Raspberry Pi

The Openbox window manager for XWindow is a minimalist option that consumes only 7MB of memory, making it ideal for low-capacity devices like Raspberry Pi. After installing Openbox, you can apply the 'win10mod.obt' theme to give your windows a Windows 10-style appearance.

To install Openbox, use the following command:

```
sudo apt-get install -y openbox
```

To manually start XWindow after installing Openbox, use the following command:

```
startx
```

## VNC Service: Setting Up VNC Server with x11vnc

For VNC server functionality, we recommend using x11vnc.

Install necessary fonts and VNC server:

```
sudo apt-get install -y xfonts-75dpi xfonts-100dpi
```

```
sudo apt-get install -y x11vnc
```

Create a password for your VNC access credentials:

```
x11vnc -storepasswd
```

Start x11vnc with password authentication, repeat, sharing, and continuous operation:

```
starts &
x11vnc -usepw -repeat -shared -forever &
```

## XTerminal: Setting Up the XTerminal System

Install the graphical Xterm console with the following command:

```
sudo apt-get install -y xterm
```

Once installed, you can enhance the console by adjusting colors and font settings in the following file:

```
vi /home/pi/.Xdefaults
```

NOTE: Leave a space at the beginning and end of the file!

```

XTerm*background: black
XTerm*foreground: WhiteSmoke
XTerm*faceSize: 11
XTerm*faceName: DejaVu Sans Mono
XTerm*renderFont: true

```

These adjustments will help you customize and improve your XTerminal experience.

## Tint2: Enhancing Task Management with Tint2

Tint2 provides a taskbar for displaying open windows in your window management system. To install Tint2, use the following command:

```
sudo apt-get install -y tint2
```

With Tint2, you can configure various icons to launch applications using the following file:

```
vi /home/pi/.config/tint2/tint2rc
```

This configuration simplifies Tint2 for a streamlined and efficient user experience, minimizing unnecessary features:

```
#---------------------------------------------
# TINT2 CONFIG FILE BYLEODELACRUZ
#---------------------------------------------
#
#---------------------------------------------
# BACKGROUND AND BORDER
#---------------------------------------------

rounded = 0
border_width = 0
background_color = #000000 100
border_color = #ffffff 0

rounded = 0
border_width = 0
background_color = #5c90b8 20
border_color = #ffffff 50

rounded = 0
border_width = 0
background_color = #8e857c 30
border_color = #FFFFFF 50

rounded = 0
border_width = 0
background_color = #8e857c 30
border_color = #ffffff 50

#---------------------------------------------
# PANEL
#---------------------------------------------
panel_monitor = all
panel_items = TLC
panel_position = bottom center
panel_size = 100% 24k
panel_margin = 0 0
panel_padding = 0 0
font_shadow = 0
panel_background_id = 1
wm_menu = 0
panel_dock = 0
panel_layer = bottom

#---------------------------------------------
# TASKBAR
#---------------------------------------------
#taskbar_mode = multi_desktop
taskbar_mode = single_desktop
taskbar_padding = 1 1 2
taskbar_background_id = 0
#taskbar_active_background_id = 0

#---------------------------------------------
# TASKS
#---------------------------------------------
task_icon = 0
task_text = 1
task_maximum_size = 350 24
task_centered = 1
task_padding = 30 0
task_font = Sans 9
task_font_color = #ffffff 60
task_background_id = 3
task_icon_asb = 100 0 0
# replace STATUS by 'urgent', 'active' or 'iconfied'
#task_STATUS_background_id = 2
#task_STATUS_font_color = #ffffff 85
#task_STATUS_icon_asb = 100 0 0
# example:
task_active_background_id = 4
task_active_font_color = #ffffff 60
#task_active_font_color = #ffffff 60
#task_active_font_color = #ca6e59 60
task_active_icon_asb = 100 0 0
urgent_nb_of_blink = 8

#---------------------------------------------
# MOUSE ACTION ON TASK
#---------------------------------------------
mouse_middle = none
mouse_right = none
mouse_scroll_up = none
mouse_scroll_down = none

#---------------------------------------------
# CLOCK
#---------------------------------------------
time1_format = %a %H:%M
time1_font = DejaVu Sans Bold 9
clock_font_color = #ffffff 76
clock_padding = 6 0 3
clock_background_id = 0

#---------------------------------------------
# LAUNCHER
#---------------------------------------------
launcher_padding = 0 0 3
launcher_background_id = 1
launcher_icon_size = 24
launcher_item_app = /home/pi/.config/tint2/control.desktop
launcher_item_app = /home/pi/.config/tint2/poweroff.desktop
launcher_icon_theme =

# End of config
```

Once the main configuration file is set up, we create taskbar shortcuts for a Control Panel and Shutdown:

Control Panel taskbar shortcut:

```
vi /home/pi/.config/tint2/control.desktop
```
```
[Desktop Entry]
Name=Panel de Control
Comment=Panel de Control
Exec=/home/pi/terms/bin/control.sh
Icon=/home/pi/terms/share/tint2/control.png
Type=Application
```

Shutdown taskbar shortcut:

```
vi /home/pi/.config/tint2/poweroff.desktop
```
```
[Desktop Entry]
Name=Apagar
Comment=Apagar
Exec=/home/pi/terms/bin/poweroff.sh
Icon=/home/pi/terms/share/tint2/poweroff.png
Type=Application
```

## Build: Essential Dependencies for Debian Source Code Compilation

Automatically installs essential dependencies required for compiling applications from source code on Linux Debian systems, ensuring a smooth and successful compilation process.

```
sudo apt-get install -y build-essential
```
```
sudo apt-get install -y make automake cmake git subversion checkinstall unzip libtool
```

## iDesktop: Lightweight Linux Desktop Management

iDesktop is a tool that enables efficient customization and administration of the desktop environment in Linux systems. It can be obtained from the official website on SourceForge with version 0.7.5 or from the GitHub repository with the latest version 0.7.8.
```
https://sourceforge.net/projects/idesk/ (0.7.5)
https://github.com/neagix/idesk (latest version 0.7.8)
```

### iDesktop: Latest Version Compilation and Installation

The compilation option obtains the most recent version of iDesktop directly from the official application's source code repository. This process includes both compiling and installing iDesktop. The following steps are undertaken to complete this procedure:

Install necessary dependencies using the following command:
```
sudo apt-get install -y libx11-dev libimlib2-dev libxft-dev
```

Download the latest version (v0.7.8) from the official GitHub repository with the command:
```
wget https://github.com/neagix/idesk/archive/refs/tags/v0.7.8.tar.gz
```

Execute the following commands to build and install the application:
```
autoreconf --install
./configure
make -j
sudo make install
```

Once completed, iDesktop will be installed in directory: 
```
/usr/share/idesk
```

### iDesktop Configuration

The iDesktop desktop manager allows you to create icons and set your desktop wallpaper.

Create a directory for iDesktop configuration (current user pi):
```
mkdir ~/.idesktop
```

Copy the default iDesktop configuration file to your user directory:
```
cp /usr/local/share/idesk/dot.ideskrc ~/.ideskrc
```

### imlib2: Problems with default Debian imbli2 library (missing imglib2-config)

Download version 1.4.2 of 'imlib2' the most recent release that includes the 'imlib2-config' utility required for compiling iDesktop.

```
wget https://github.com/kkoudev/imlib2/archive/refs/tags/v1.4.2.tar.gz
```

Perform the following steps for 'imlib2-config' configuration:

```
sudo cp /usr/include/freetype2/ft2build.h /usr/include/ft2build.h
sudo ln -s /usr/include/freetype2/freetype /usr/include/freetype
```

```
./autogen.sh
make -j
sudo make install
sudo cp imlib2-config /usr/bin
```

## Conky Version 1.9.0 Compilation and Configuration

This guide assists in compiling and configuring Conky, a system monitor tool.

Intall required dependencies:
```
sudo apt-get install -y libncurses5-dev lua5.1 liblua5.1-0-dev libiw-dev libxdamage-dev
```

Download Conky Version 1.9.0 from official source:
```
wget https://sourceforge.net/projects/conky/files/conky/1.9.0/
```

Fix the source code in order to compile. Edit the file 'src/conky.c' and 'src/conky.h' to add the following code modifications:

```
src/conky.c
/* no buffers in used memory? */
extern int no_buffers;
enum ifup_strictness_enum ifup_strictness;
```

```
src/conky.h
/* if_up strictness selector 
 * needed by conky.c and linux.c (and potentially others) */
extern enum ifup_strictness_enum {
    IFUP_UP,
    IFUP_LINK,
    IFUP_ADDR
} ifup_strictness;
```

Build and intall Conky application:

```
./autogen.sh
./configure --build=arm --enable-wlan
make -j
sudo make install
```

```
sudo setcap cap_net_raw,cap_net_admin=eip /usr/local/bin/conky
```

Configure Conky create a file '~/.conkyrc' and insert the following configuration:

```
vi ~/.conkyrc
```

```
background yes
alignment top_left

out_to_console no
out_to_stderr no

own_window yes
own_window_type desktop
own_window_transparent no
own_window_hints undecorated,above,sticky,skip_taskbar,skip_pager
own_window_colour black

use_spacer none
pad_percents 0

gap_x 0
gap_y 0

border_width 0
border_margin 0
draw_borders no
stippled_borders 0
border_inner_margin 0
border_outer_margin 0

default_color e9e9e9
default_outline_color white
default_shade_color white

draw_shades no
draw_outline no
draw_borders no
draw_graph_borders no

update_interval 1.0
cpu_avg_samples 2
net_avg_samples 2

double_buffer yes
no_buffers yes

minimum_size 5125 16
maximum_width 5125

use_xft yes
xftfont DejaVu Sans:size=10:weight=bold
uppercase no
extra_newline no

TEXT 
 ${nodename}  ${uptime}  ${execi 30 cat /sys/class/thermal/thermal_zone0/temp | awk '{print substr($0,0,3)}'}C${if_existing /sys/class/net/ppp0/operstate}  VPN${endif}${if_existing /sys/class/net/eth0/operstate up}  Ethernet ${addr eth0}${endif}${if_existing /sys/class/net/wlan0/operstate up}  Wi-Fi ${addr wlan0} ${wireless_essid wlan0} ${wireless_link_qual_perc wlan0}%${endif}  ${if_existing /sys/class/net/eth0/operstate up}eth0 ${downspeed eth0}(${totaldown eth0} descarga) / ${upspeed eth0}(${totalup eth0} subida) ${endif}${if_existing /sys/class/net/wlan0/operstate up}wlan0 ${downspeed wlan0}(${totaldown wlan0} descarga) / ${upspeed wlan0}(${totalup wlan0} subida) ${endif}
```

## Openbox Configuration and Optimizations

Openbox provides a minimalist lightweight and highly customizable window manager environment for your desktop, and we'll delve into various ways configure and optimizing it to tailor to our specific needs.

### Fine-tuning Openbox, enhancing your desktop experience, and maximizing productivity
 
Remove native menu options, disable specific key combinations like ALT+TAB, and introduce efficient keyboard shortcuts for rapid application switching.

```
cp ~/.config/openbox/rc.xml ~/.config/openbox/rc.xml.bak
```

```
vi ~/.config/openbox/rc.xml
```

Disable specific key combinations, and introduce efficient keyboard shortcuts:
```
  <keyboard>
    <!-- terminal -->
    <keybind key="C-A-Delete">
      <action name="Execute">
        <command>/home/pi/terms/bin/control.sh</command>
      </action>
    </keybind>
    <keybind key="C-Right">
      <action name="NextWindow"/>
    </keybind>
    <keybind key="C-Up">
      <action name="NextWindow"/>
    </keybind>
    <keybind key="C-Left">
      <action name="PreviousWindow"/>
    </keybind>
    <keybind key="C-Down">
      <action name="PreviousWindow"/>
    </keybind>
  </keyboard>
```

Minimize user interaction while maintaining control:
```
  <context name="Root">
    <!-- terminal -->
  </context>
```

### Openbox Automatic Startup Configuration

Set up Openbox for automatic execution of scripts and programs that prepare your graphical environment before initialization, automating essential tasks, from launching a VNC server to customizing your desktop environment.

```
# Openbox autostart.sh
# Programs that will run after Openbox has started

# VNC Server
x11vnc -usepw -repeat -shared -forever &

# Windows Envoronment
#(python /home/pi/terms/bin/remmina.py && idesk) &
idesk &
tint2 &
conky -q &

# Keyboard Touch
if [ -f /home/pi/terms/var/keyboard.enabled ] ; then
   (/usr/bin/florence) &
fi

# Monopuesto Service
if [ -f /home/pi/terms/var/monopuesto.enabled ] ; then
   (sudo systemctl start monopuesto.service) &
fi

# Autorun Service
if [ -f /home/pi/terms/var/autorun.enabled ] ; then
   (sleep 8s && sudo systemctl start autorun.service) &
fi  
```


- 
