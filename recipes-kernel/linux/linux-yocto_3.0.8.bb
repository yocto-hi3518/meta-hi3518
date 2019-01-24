SUMMARY = "Linux kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM ?= "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KBRANCH ?= "master"
KMACHINE ?= "${MACHINE}"

inherit kernel

LINUX_VERSION ?= "3.0.8"

SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v3.x/linux-${LINUX_VERSION}.tar.xz \
    file://defconfig \
    file://0001-hi3518-buildroot-from-01-05-2017.patch \
"

KBUILD_DEFCONFIG_KMACHINE ?= "hi3518e_full_defconfig"


SRC_URI[md5sum] = "9b430c2289d83452e85f6ef3cb7a48ad"
SRC_URI[sha256sum] = "aacd271b91ca73c1f5df3c64f2ef0b825e247f023b9a4774869c8a280c00c3de"

DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}"

S = "${WORKDIR}/linux-${PV}"

COMPATIBLE_MACHINE = "hi3518"

