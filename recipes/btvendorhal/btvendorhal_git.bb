inherit autotools-brokensep

DESCRIPTION = "hardware btvendorhal headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://bluetooth/bt_audio/"
S = "${WORKDIR}/bluetooth/bt_audio"

export WORKSPACE

PR = "r1"

#DEPENDS = "system-core"
DEPENDS += "libhardware"
DEPENDS:remove:vt-64 = "libhardware"
DEPENDS:remove:qcm6490 = "libhardware"
