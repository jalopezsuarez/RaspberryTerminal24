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
sudo apt-get -y install x11vnc
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

