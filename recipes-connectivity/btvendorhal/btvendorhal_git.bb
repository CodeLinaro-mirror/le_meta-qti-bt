inherit autotools-brokensep

DESCRIPTION = "hardware btvendorhal headers"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://bt_audio/ \
           file://btapp/ \
           file://stack/system/bt/"

S = "${WORKDIR}"

AUTOTOOLS_SCRIPT_PATH = "${S}/bt_audio"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'
