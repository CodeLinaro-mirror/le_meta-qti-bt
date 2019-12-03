inherit autotools pkgconfig

DESCRIPTION = "Bluetooth Generic Gatt Interface"
LICENSE = "BSD"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

DEPENDS += "glib-2.0 libhardware btvendorhal system-core-headers"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/gatt/ \
           file://qcom-opensource/bt/bt-app/"

S = "${WORKDIR}/qcom-opensource/bt/gatt/"

EXTRA_OECONF = "--with-glib"
