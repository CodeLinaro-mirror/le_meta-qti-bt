inherit autotools-brokensep pkgconfig

DESCRIPTION = "hardware btvendorhal headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://vendor/qcom/opensource/bluetooth/"
S = "${WORKDIR}/vendor/qcom/opensource/bluetooth/"

PR = "r1"

DEPENDS += " libcutils system-core-headers "
#DEPENDS:remove:kalama = "libhardware"

EXTRA_OECONF = "--with-common-includes=${WORKSPACE}/system/core/include/ "
