inherit autotools-brokensep

DESCRIPTION = "hardware btvendorhal headers"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS = "fluoride"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt_audio/"

S = "${WORKDIR}/bluetooth/bt_audio"

AUTOTOOLS_SCRIPT_PATH = "${S}"
TARGET_CFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
TARGET_CXXFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

EXTRA_OEMAKE += 'BT_INC_PATH=${STAGING_INCDIR}/bluetooth'
