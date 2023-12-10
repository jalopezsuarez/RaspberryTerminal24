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

### Flashing the official image onto an 8GB SD Card with Raspberry Pi Imager

Utilizing the official Raspberry Pi Imager program version 1.8.1 to install the image onto an 8 GB SD Card for system deployment.

```
2023-12-05-raspios-bookworm-arm64-lite.img.xz
```

## System: Initial Setup and Advanced Customization for Debian Systems

Essential configurations for Raspberry Debian-based systems.

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

### VIM: Fixing Arrow Key Issues in Vim Editor for Console

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

### Keyboard Setup

A command-line program to help you change keyboard layout to SPANISH.

```
sudo dpkg-reconfigure keyboard-configuration
```

```
Generic 105-key (Intl) PC -> Spanish -> The default for the keyboard layout -> No compose key -> Control+Alt+Backspace to terminate the X server? NO
```

Ensure that the changes take effect immediately, you can either log out and log back in or reboot your system

```
sudo reboot
```

### Configure System Default Locale Language 

Enable only the SPANISH (es_ES.UTF-8) locale: In the configuration dialog, scroll down and find es_ES.UTF-8 in the list. Use the arrow keys to select it and press the spacebar to mark it with an asterisk (*). Press "Tab" to highlight the "OK" button, and then press "Enter" to confirm your selection:

```
sudo dpkg-reconfigure locales
```

Navigate through the menu to find "Europe" and then select "Madrid." This will set your time zone to Europe/Madrid. Press "Enter" to confirm your selection:

```
sudo dpkg-reconfigure tzdata
```

Raspberry Debian Linux Thinclient system should now be using SPANISH as the default system language and have the correct time zone set to Europe/Madrid.

```
sudo vi /etc/profile
```

```
# System Default Language 
export LANGUAGE="es_ES.UTF-8"
export LC_ALL="es_ES.UTF-8"
export LC_CTYPE="es_ES.UTF-8"
export LC_LANG="es_ES.UTF-8"
export LANG="es_ES.UTF-8"
```

Ensure that the changes take effect immediately, you can either log out and log back in or reboot your system

```
sudo reboot
```

### Network: Customizing the Hostname for Network Identification

To improve network identification, change the default hostname 'raspberrypi' to 'terminal' using the following commands. Replace 'raspberrypi' with 'terminal' and save the file:

```
sudo vi /etc/hostname
terminal
```

```
sudo vi /etc/hosts
127.0.1.1 terminal
```

Ensure that the changes take effect immediately, you can either log out and log back in or reboot your system

```
sudo reboot
```

### System: Purge and Update System References Repository

Update the software repository using the following command which update library dependencies, clean and purge system libraries:

```
sudo apt-get update
sudo apt-get -y autoremove --purge
sudo apt-get autoclean
sudo apt-get autoremove
sudo apt-get clean
sudo ldconfig
```

## Terminal Thinclient Resources and Assets

Para comenzar hay que disponer de la estructura de directorios y recursos necesarios para poner en marcha el sistema thinclient. Descomprimir el archivo control-terms.tgz que contiene toda la estrucutra y ficheros necesarios para el sistema:

```
cd
tar zxvf control-terms.tgz
```

```
~/terms/bin [APLICACIONES EJECUTABLES]
~/terms/repos [DESCARGAS ORIGINALES]
~/terms/share [RECURSOS COMPARTIDOS]
~/terms/var [CONTROL DE ESTADO]
```

## Embebed ARM Java / Maven Enviroment

Setting up a specific version of Java 8 Development Envronment and Maven 3 Dependency Management optimized for embedded thin client systems on Barebone Raspberry Pi Systems.

### Java Development Enviroment

```
sudo mkdir /usr/lib/jvm
sudo cp ~/terms/repos/jdk-8u201-linux-arm64-vfp-hflt.tar.gz /usr/lib/jvm
cd /usr/lib/jvm
sudo tar zxvf jdk-8u201-linux-arm64-vfp-hflt.tar.gz
sudo mv [EXTRACTED] jdk8
```

### Maven Java Dependency Management

```
sudo mkdir /usr/lib/mvn
sudo cp ~/terms/repos/apache-maven-3.3.9-bin.tar.gz /usr/lib/mvn
cd /usr/lib/mvn
sudo tar zxvf apache-maven-3.3.9-bin.tar.gz
mv [EXTRACTED] maven3
```

### System Default Java and Maven Environment

Configure default Java and Maven environment system configuration for Raspberry Debian Linux:

```
sudo vi /etc/profile
```

```
# System Default Java and Maven Environment
export JAVA_HOME=/usr/lib/jvm/jdk8
export M2_HOME=/usr/lib/mvn/maven3
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
```

Ensure that the changes take effect immediately, you can either log out and log back in or reboot your system

```
sudo reboot
```

## X-Window: Installing a Minimal X-Window System

To set up a minimal X-Window system, use the following command. This command will install the necessary components for a minimal X-Window environment without additional recommended packages.

```
sudo apt-get install -y --no-install-recommends xinit xserver-xorg
```

### XTerminal: Setting Up the XTerminal System

Install the graphical Xterm console with the following command:

```
sudo apt-get install -y xterm
```

Once installed, you can enhance the console by adjusting colors and font settings in the following file:

```
vi ~/.Xdefaults
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

### X-Window Thinclient: Disabling Terminal/Console Switching in X-Windows

Disabling terminal/console switching in Windows is preferable for the user experience of a thin client system. It prevents users from accessing unnecessary features, simplifying the user experience to the essentials.

Open or create the file /etc/X11/xorg.conf using the following command:

```
sudo vi /etc/X11/xorg.conf
```

Add the following lines inside the file:

```
Section "ServerFlags"
    Option "DontVTSwitch" "true"
EndSection
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

### X-Window VNC Service: Setting Up VNC Server with x11vnc

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

## Tint2: Enhancing Task Management with Tint2

Tint2 provides a taskbar for displaying open windows in your window management system. To install Tint2, use the following command:

```
sudo apt-get install -y tint2
```

With Tint2, you can configure various icons to launch applications using the following file:

```
vi ~/.config/tint2/tint2rc
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
vi ~/.config/tint2/control.desktop
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
vi ~/.config/tint2/poweroff.desktop
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
sudo apt-get install -y libx11-dev libxext-dev libice-dev libxft-dev libimlib2-dev
```

Download the latest version (v0.7.8) from the official GitHub repository with the command:

```
cd ~/terms/repos
wget https://github.com/neagix/idesk/archive/refs/tags/v0.7.8.tar.gz
tar zxvf v0.7.8.tar.gz
```

Execute the following commands to build and install the application:

```
./configure
make -j
sudo make install
```

Once completed, iDesktop will be installed in directory: 

```
/usr/share/idesk
```

### imlib2: Problems with default Debian imbli2 library (missing imglib2-config)

Download version 1.4.2 of 'imlib2' the most recent release that includes the 'imlib2-config' utility required for compiling iDesktop.

```
cd ~/terms/repos
wget https://github.com/kkoudev/imlib2/archive/refs/tags/v1.4.2.tar.gz
tar zxvf v1.4.2.tar.gz
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

### iDesktop Configuration

The iDesktop desktop manager allows you to create icons and set your desktop wallpaper.

Create a directory for iDesktop configuration (current user pi):

```
mkdir ~/.idesktop
```

iDesktop configuration file in user directory:

```
vi  ~/.ideskrc
```

```
table Config
  FontName: gothic
  FontSize: 11
  FontColor: #FFFFFF
  ToolTip.FontSize: 11
  ToolTip.FontName: gothic
  ToolTip.ForeColor: #333333
  ToolTip.BackColor: #FFFFFF
  ToolTip.CaptionOnHover: true
  ToolTip.CaptionPlacement: Right
  Locked: true
  Transparency: 0
  Shadow: true
  ShadowColor: #000000
  ShadowX: 1
  ShadowY: 1 
  Bold: true
  ClickDelay: 300
  IconSnap: true
  SnapWidth: 24
  SnapHeight: 24
  SnapOrigin: TopLeft
  SnapShadow: false
  SnapShadowTrans: 200
  CaptionOnHover: false
  CaptionPlacement: bottom
  FillStyle: fillinvert
  Background.Delay: 0
  Background.Source: None
  Background.File: /home/pi/terms/share/idesk/background.jpg
  Background.Mode: Scale
  Background.Color: #C2CCFF
end
table Actions
  Lock: control right doubleClk
  Reload: middle doubleClk
  Drag: left hold
  EndDrag: left singleClk
  Execute[0]: left doubleClk
  Execute[1]: right doubleClk
end
```

## Conky Version 1.9.0 Compilation and Configuration

This guide assists in compiling and configuring Conky, a system monitor tool.

Intall required dependencies:

```
sudo apt-get install -y libncurses5-dev lua5.1 liblua5.1-0-dev libiw-dev libxdamage-dev libasound2-dev docbook2x xsltproc
```

Download Conky Version 1.9.0 from official source:

```
cd ~/terms/repos
wget https://sourceforge.net/projects/conky/files/conky/1.9.0/conky-1.9.0.tar.gz
tar zxvf conky-1.9.0.tar.gz
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
      <action name="NextWindow">
        <finalactions>
          <action name="Focus"/>
          <action name="Raise"/>
          <action name="Unshade"/>
        </finalactions>
      </action>
    </keybind>
    <keybind key="C-Up">
      <action name="NextWindow">
        <finalactions>
          <action name="Focus"/>
          <action name="Raise"/>
          <action name="Unshade"/>
        </finalactions>
      </action>
    </keybind>
    <keybind key="C-Left">
      <action name="PreviousWindow">
        <finalactions>
          <action name="Focus"/>
          <action name="Raise"/>
          <action name="Unshade"/>
        </finalactions>
      </action>
    </keybind>
    <keybind key="C-Down">
      <action name="PreviousWindow">
        <finalactions>
          <action name="Focus"/>
          <action name="Raise"/>
          <action name="Unshade"/>
        </finalactions>
      </action>
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
vi ~/.config/openbox/autostart.sh
chmod +x ~/.config/openbox/autostart.sh
```

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

## Remmina / FreeRDP: Build Remmina and FreeRDP (latest 2023 stable)

Compilation and installation of the Remmina and FreeRDP tools in their stable versions on a Raspberry Debian Linux system. To achieve this, the script performs the following actions:

```
sudo mkdir /opt/remmina
```

### FreeRDP

```
sudo apt-get install -y libssl-dev
sudo apt-get install -y libavcodec-dev
sudo apt-get install -y libavutil-dev
sudo apt-get install -y libswresample-dev
sudo apt-get install -y libpkcs11-helper1-dev
sudo apt-get install -y libkrb5-dev
sudo apt-get install -y heimdal-dev
sudo apt-get install -y libicu-dev
sudo apt-get install -y libswscale-dev
sudo apt-get install -y libpulse-dev
sudo apt-get install -y libcups2-dev
sudo apt-get install -y libfuse3-dev
sudo apt-get install -y docbook-xsl
sudo apt-get install -y xsltproc
sudo apt-get install -y libusb-1.0-0-dev
```

```
mkdir ~/terms/repos/freerdp_build
cd ~/terms/repos/freerdp_build
wget https://github.com/FreeRDP/FreeRDP/files/12670558/freerdp-2.11.2.tar.gz
tar zxvf freerdp-2.11.2.tar.gz
```

```
cd ~/terms/repos/freerdp_build
sudo rm -rf freerdp/CMakeCache.txt freerdp/CMakeFiles
```

```
cmake -DCMAKE_BUILD_TYPE=Release -DTARGET_ARCH=ARM64 -DARM_FP_ABI=hard -DWITH_CUPS=ON -DWITH_PULSE=ON -DWITH_NEON=OFF -DWITH_WAYLAND=OFF -DCMAKE_INSTALL_PREFIX:PATH=/opt/remmina/freerdp freerdp
```

```
make -j
sudo make install
```

```
sudo ln -s /opt/remmina/freerdp/bin/xfreerdp /usr/local/bin/
echo /opt/remmina/freerdp/lib | sudo tee /etc/ld.so.conf.d/freerdp_devel.conf > /dev/null
sudo ldconfig
```


### Remmina

```
sudo apt-get install -y libatk1.0-dev
sudo apt-get install -y libgdk-pixbuf2.0-dev
sudo apt-get install -y libpango1.0-dev
sudo apt-get install -y libgtk-3-dev
sudo apt-get install -y libharfbuzz-dev
sudo apt-get install -y libgcrypt20-dev
sudo apt-get install -y libsodium-dev
sudo apt-get install -y libssh-dev
sudo apt-get install -y libvte-2.91-dev
sudo apt-get install -y libjson-glib-dev
sudo apt-get install -y libkf5wallet-dev
sudo apt-get install -y python3-dev
sudo apt-get install -y libsecret-1-dev
sudo apt-get install -y gettext
sudo apt-get install -y libavahi-client-dev
```

```
mkdir ~/terms/repos/remmina_build
cd ~/terms/repos/remmina_build
wget https://gitlab.com/Remmina/Remmina/-/archive/v.1.4.33/Remmina-v.1.4.33.tar.gz
tar zxvf Remmina-v.1.4.33.tar.gz
```

```
cd ~/terms/repos/remmina_build
sudo rm -rf remmina/CMakeCache.txt remmina/CMakeFiles
```

```
cmake -DCMAKE_BUILD_TYPE=Release -DHAVE_LIBAPPINDICATOR=OFF -DWITH_PYTHONLIBS=ON -DWITH_X2GO=OFF -DWITH_FREERDP3=OFF -DWITH_WEBKIT2GTK=OFF -DWITH_KF5WALLET=ON -DWITH_NEWS=OFF -DWITH_GVNC=OFF -DWITH_LIBVNCSERVER=OFF -DWITH_SPICE=OFF -DCMAKE_INSTALL_PREFIX:PATH=/opt/remmina/remmina -DCMAKE_PREFIX_PATH=/opt/remmina/freerdp remmina
```

```
make -j
sudo make install
```

```
sudo ln -s /opt/remmina/remmina/bin/remmina /usr/local/bin/
```

### Remmina: Minimalist control buttons for thinclient optimizations (remove toolbar buttons)

To optimize Remmina's thin client mode, simplify the toolbar by removing non-essential features. Keep only 'minimize' and 'log out' options. This requires commenting out code in the 'rcw.c' file to disable remote connection toolbar buttons, followed by recompilation.

```
rcw.c -> /* Duplicate session */
```

### Remmina Extra Pro Tips

Running configured connections with the following command:

```
remmina -c /home/pi/.remmina/1375746771949.remmina
```

IMPORTANT! Fix configuring keyboard layouts in Remmina for RDP between Linux and Windows connections. In the preferences, enable the option described below to ensure seamless keyboard mapping between the client and remote systems.

```
Use client keyboard mapping / Usar la asignación de teclados de cliente.
```

## Enhanced System: Raspberry Debian Linux Thinclient Optimizations (remmina / monopuesto / autorun)

text

```
code
```

### Remina + iDesktop Integration (remmina.py)

text

```
code
```

### Monopuesto Service (monopuesto.py)

text

```
code
```

### Autorun Service

text

```
code
```

## Splash Boot Screen (Plymouth native official)

text

```
code
```

## Supplementary Software Packages

Installation of supplementary software packages:

### wpa_gui - WPA Graphical User Interface

Graphical User Interface (GUI) tool designed for configuring Wi-Fi networks on the Debian Linux system. It is built upon the QT libraries from the KDE environment and serves as a graphical frontend for interacting with wpa_supplicant. This tool enables users to query the current network status, modify configuration settings, and request interactive user input when needed.

```
sudo apt-get install -y wpagui
```

### Geany

Geany is a powerful, stable and lightweight programmer's text editor that provides tons of useful features without bogging down your workflow and has built-in support for programming languages.

```
sudo apt-get install -y geany
```

## System Network Manual Configuration (Raspberry Debian Linux)

text

```
code
```

### Manual Network Setup (network interfaces)

text

```
code
```

### Manual Wi-Fi Setup (wpa_supplicant)

text

```
code
```

## Extra System Tools

text

```
code
```

### Extra Tools: Python2 / Python3

text

```
code
```

### Extra Tools: WiringPi

text

```
code
```
