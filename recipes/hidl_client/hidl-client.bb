inherit autotools qcommon qlicense

DESCRIPTION = "FTM HIDL client"
PR = "r0"
DEPENDS = "common glib-2.0 system-core liblog bttransport"

RDEPENDS_${PN} = "libcutils"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bluetooth/tools/"
SRC_DIR = "${WORKSPACE}/vendor/qcom/opensource/bluetooth/tools/"
S = "${WORKDIR}/vendor/qcom/opensource/bluetooth/tools/"

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = "--with-lib-path=${STAGING_LIBDIR} \
                --with-zlib \
                --with-glib \
                --enable-static=yes \
                --enable-wlan=yes \
                --enable-bt=yes \
                --enable-debug=yes \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
               "

CFLAGS_append = " -DUSE_ANDROID_LOGGING "
LDFLAGS_append = " -llog "

