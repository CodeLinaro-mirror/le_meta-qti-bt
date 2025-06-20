inherit autotools pkgconfig

DESCRIPTION = "Bluetooth Generic Gatt Interface"
LICENSE = "BSD-3-Clause"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "glib-2.0"

#LDFLAGS:append = " -llog "

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/gatt/"

S = "${WORKDIR}/qcom-opensource/bt/gatt/"

EXTRA_OECONF = "--with-glib"
