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

## Updating the System Repository

After installing Debian Lite, the first step is to update the software repository using the following command:

```
sudo apt-get update
```

### Titulo3

```
code
```
