inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Generic Gatt Interface"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS = "glib-2.0"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/gatt/"

EXTRA_OECONF = " \
                --with-glib \
               "
S = "${WORKDIR}/qcom-opensource/bt/gatt/"

#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "
