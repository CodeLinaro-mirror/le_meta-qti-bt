inherit autotools-brokensep

DESCRIPTION = "hardware btvendorhal headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://bt_audio/ \
           file://btapp/ \
           file://stack/system/bt/"

BT_SOURCE = "${WORKDIR}"
S = "${BT_SOURCE}/bt_audio"

EXTRA_OEMAKE += 'BT_SOURCE=${BT_SOURCE}'

PR = "r1"

DEPENDS += "libhardware"
DEPENDS:remove:vt-64 = "libhardware"
DEPENDS:remove:qcm6490 = "libhardware"
