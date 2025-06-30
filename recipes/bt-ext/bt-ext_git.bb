inherit autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0 & BSD-3-Clause"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
               file://${COREBASE}/meta/files/common-licenses/\
BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bluetooth_ext/"

S = "${WORKDIR}/vendor/qcom/opensource/bluetooth_ext/"

DEPENDS += "libchrome"

do_install:append() {
  install -D ${WORKDIR}/build/bluetoothExt.pc ${D}${libdir}/pkgconfig/bluetoothExt.pc
}

ALLOW_EMPTY:${PN} = "1"
FILES:${PN} += "${libdir}/pkgconfig/"
