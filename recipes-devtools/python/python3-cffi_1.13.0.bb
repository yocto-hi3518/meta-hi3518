CFFI_BITBUCKET_COMMIT = "8cca638c6631"
PYPI_SRC_URI = "https://bitbucket.org/cffi/cffi/get/${CFFI_BITBUCKET_COMMIT}.tar.gz"

inherit pypi setuptools3
require python-cffi.inc

SRC_URI[md5sum] = "71617a83c1a71bcf0fcf66ae6e4c8ff9"
SRC_URI[sha256sum] = "83a91ee0d222dc3c8eedc4c3d6c04426446b0574dec20d9e679e752aabb7f7a6"

S = "${WORKDIR}/cffi-cffi-${CFFI_BITBUCKET_COMMIT}"
