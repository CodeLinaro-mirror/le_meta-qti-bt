inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "zlib btvendorhal libchrome bttransport audio-utils libutils bt-ext"
DEPENDS:append:kona += " libcutils libhardware"
DEPENDS:append:neo += " libcutils libhardware"
DEPENDS:append:qrbx210-rbx += " libcutils libhardware"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/system/bt/ \
           file://vendor/qcom/opensource/bluetooth_ext/"

S = "${WORKDIR}/vendor/qcom/opensource/system/bt/"
S_EXT = "${WORKDIR}/vendor/qcom/opensource/bluetooth_ext/system_bt_ext/"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
INSANE_SKIP:${PN} = "dev-so"

#CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
#CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#LDFLAGS_append_kona += " -lpthread -llog -lcutils"
#LDFLAGS_append_kona += " -Wl,--unresolved-symbols=ignore-in-shared-libs"
#CXX_append_kona += " -Wl,--no-as-needed"
#LDFLAGS_append_qrbx210-rbx += " -lpthread -llog -lcutils"
#LDFLAGS_append_qrbx210-rbx += " -Wl,--unresolved-symbols=ignore-in-shared-libs"
#CXX_append_qrbx210-rbx += " -Wl,--no-as-needed"
BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"
CPPFLAGS:append:kalama += "-mno-outline-atomics"

EXTRA_OECONF = " \
                --with-zlib \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
                --enable-static=yes \
               "

PACKAGE_ARCH = "${MACHINE_ARCH}"
do_install:append() {

        cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so.0 bluetooth.default.so
        cd  ${D}/${libdir}/ && ln -s libaudioa2dpdefault.so.0 audio.a2dp.default.so
}
