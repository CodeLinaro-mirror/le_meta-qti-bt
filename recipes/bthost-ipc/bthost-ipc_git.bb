inherit autotools pkgconfig

DESCRIPTION = "Build BT HOST IPC"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"


DEPENDS += " liblog glib-2.0 media-headers fluoride"
DEPENDS:append_kona += " libhardware"
DEPENDS:append_qrbx210-rbx += " libhardware"
DEPENDS:append_qrb5165 += " libhardware"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bluetooth/bthost_ipc/"

S = "${WORKDIR}/vendor/qcom/opensource/bluetooth/bthost_ipc/"

#CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
#CPPFLAGS:append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#LDFLAGS:append = " -llog "

EXTRA_OECONF = "--with-glib"
EXTRA_OECONF:append_kona = " --enable-target=qrb5165"
EXTRA_OECONF:append_qrbx210-rbx = " --enable-target=qrbx210-rbx"
EXTRA_OECONF:append_qrb5165 = " --enable-target=qrb5165"
SOLIBS = ".so"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_SOLIBSDEV = ""
