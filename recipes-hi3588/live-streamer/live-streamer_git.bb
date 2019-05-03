# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   COPYING
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://COPYING;md5=8f0e2cd40e05189ec81232da84bd6e1a"

SRC_URI = "git://github.com/hi35xx/live-streamer.git;protocol=https \
           file://0001-Fix-ipcam-media-iface-header.patch \
           file://0002-SC1135-support.patch file://0001-Add-link-to-vqe.patch \
           "

# Modify these as desired
PV = "1.0.0+git${SRCPV}"
SRCREV = "e68ebd7e21f447eba6d89cc8a78a8f5a724fc620"

S = "${WORKDIR}/git"

# NOTE: unable to map the following pkg-config dependencies: freetype2 fontconfig SDL_ttf dbus-c++-1 SDL_image sdl
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: ev
#       (this is based on recipes that have previously been built and packaged)

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit pkgconfig autotools

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = "--with-hi3518v100mpp=yes"

DEPENDS = "himpp libdbus-c++ libdbus-c++-native libev fontconfig libsdl-ttf libsdl-image live555"

RDEPENDS_${PN} += "himpp himpp-dev"

INSANE_SKIP_${PN} += "file-rdeps dev-deps"
TARGET_LDFLAGS_remove = "${ASNEEDED}"
