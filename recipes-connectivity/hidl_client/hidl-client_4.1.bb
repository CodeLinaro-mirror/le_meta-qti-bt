inherit autotools-brokensep pkgconfig

DESCRIPTION = "FTM HIDL client"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r0"
DEPENDS = "glib-2.0 liblog bttransport"

RDEPENDS:${PN} = "libcutils"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt_audio/tools/"
SRC_DIR = "${WORKSPACE}/bluetooth/bt_audio/tools/"
S = "${WORKDIR}/bluetooth/bt_audio/tools"

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = "--with-zlib \
                --enable-static=yes \
                --enable-wlan=yes \
                --enable-bt=yes \
                --enable-debug=yes \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
               "

#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "

