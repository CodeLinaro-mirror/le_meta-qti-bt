inherit autotools pkgconfig

DESCRIPTION = "Build Google libchrome"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS = "libevent libmodpb64 gtest liblog"

SRC_URI = "git://git.codelinaro.org/clo/la/platform/external/libchrome;protocol=https;nobranch=1;rev=b4b96cdfd447daac679b067c3b969cc5ed22a798;destsuffix=libchrome"
SRC_URI += "file://0001-Add-Support-to-build-libchrome.patch \
           "

S = "${WORKDIR}/libchrome"
LDFLAGS:append = " -llog"

do_install:append() {
  install -D ${WORKDIR}/build/libchrome.pc ${D}${libdir}/pkgconfig/libchrome.pc
}
