PYPI_SRC_URI = "https://bitbucket.org/cffi/cffi/get/tip.tar.gz"


inherit pypi setuptools3
require python-cffi.inc

SRC_URI[md5sum] = "0a4d477121d292e0bc280b455132e154"
SRC_URI[sha256sum] = "0d0e327a6f95da47c671105d7e9be11af00441aeace46977b5e82d94fe9c5c6d"
S = "${WORKDIR}/cffi-cffi-90d267a96234"
