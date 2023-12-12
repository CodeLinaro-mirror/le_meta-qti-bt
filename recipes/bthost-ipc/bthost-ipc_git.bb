inherit autotools pkgconfig

DESCRIPTION = "Build BT HOST IPC"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

DEPENDS = "glib-2.0 fluoride"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt_audio/bthost_ipc/"

S = "${WORKDIR}/bluetooth/bt_audio/bthost_ipc"

#CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
#CPPFLAGS:append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#LDFLAGS:append = " -llog "

EXTRA_OECONF = "--with-glib"
SOLIBS = ".so"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_SOLIBSDEV = ""
