inherit autotools pkgconfig

DESCRIPTION = "Build BT HOST IPC"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"


DEPENDS += " liblog glib-2.0 media-headers"
DEPENDS_append_qrb5165 += " libhardware"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bluetooth/bthost_ipc/"

S = "${WORKDIR}/vendor/qcom/opensource/bluetooth/bthost_ipc/"

CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
LDFLAGS_append = " -llog "

EXTRA_OECONF = "--with-glib"
EXTRA_OECONF_append = " --enable-target=${BASEMACHINE}"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
