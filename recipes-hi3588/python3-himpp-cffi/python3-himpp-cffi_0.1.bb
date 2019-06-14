LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

#SRC_URI = "git..."

#SRCREV = "v${PV}"
# Modify these as desired
#PV_append = "+git${SRCPV}"

S = "${WORKDIR}"

inherit setuptools3
inherit cmake
inherit python3native

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
#EXTRA_OECMAKE += "-DPYTHON=${PYTHON} -DPYTHON_DISTUTILS_INSTALL_ARGS='${DISTUTILS_INSTALL_ARGS}'"


TARGET_LDFLAGS_remove = "${ASNEEDED}"

DEPENDS = "himpp python3-cffi python3-cffi-native python3-pycparser"
RDEPENDS_${PN} = "himpp-dev python3-cffi"

INSANE_SKIP_${PN} += "dev-deps"

do_install() {
    distutils3_do_install
    cmake_do_install
}
