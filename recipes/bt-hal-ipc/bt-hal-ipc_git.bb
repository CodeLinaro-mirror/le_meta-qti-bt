inherit autotools-brokensep pkgconfig systemd

DESCRIPTION = "Bluetooth HAL over IPC"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "common libchrome systemd"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-hal-ipc/"

S = "${WORKDIR}/qcom-opensource/bt/bt-hal-ipc/"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}"
INSANE_SKIP_${PN} = "dev-so"

CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = " \
                --with-zlib \
                --with-common-includes="${WORKSPACE}/qcom-opensource/bt/bthal-hci" \
                --with-lib-path=${STAGING_LIBDIR} \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
                --enable-static=yes \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
               "

do_install_append() {
	cd  ${D}/${libdir}/ && ln -s libbluetoothipc.so.0 bluetoothipc.default.so
}
