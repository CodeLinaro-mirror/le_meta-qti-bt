inherit autotools-brokensep pkgconfig

DESCRIPTION = "FTM HIDL client"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

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

