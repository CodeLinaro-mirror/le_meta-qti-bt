inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "common zlib btvendorhal libchrome bttransport libutils libcutils audio-utils"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/system/bt/ \
           file://vendor/qcom/opensource/bluetooth_ext/"

S = "${WORKDIR}/vendor/qcom/opensource/system/bt/"
S_EXT = "${WORKDIR}/vendor/qcom/opensource/bluetooth_ext/system_bt_ext/"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
INSANE_SKIP:${PN} = "dev-so"

CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
CPPFLAGS:append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
CPPFLAGS:append += " -w -I${STAGING_INCDIR}"
CPPFLAGS:append:qrb5165 += "-mno-outline-atomics"
LDFLAGS:append = " -llog "
CFLAGS:append = " -w -DNDEBUG  -I${STAGING_INCDIR}"

LDFLAGS:append = " -llog "
BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = " \
                --with-zlib \
                --with-common-includes="${WORKSPACE}/vendor/qcom/opensource/system/bt" \
                --with-lib-path=${STAGING_LIBDIR} \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
                --enable-static=yes \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
               "

PACKAGE_ARCH = "${MACHINE_ARCH}"
do_install:append() {

	install -d ${D}${sysconfdir}/bluetooth/

	cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so.0 bluetooth.default.so
	cd  ${D}/${libdir}/ && ln -s libaudioa2dpdefault.so.0 audio.a2dp.default.so

	if [ -f ${S}conf/auto_pair_devlist.conf ]; then
	   install -m 0660 ${S}conf/auto_pair_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/bt_did.conf ]; then
	   install -m 0660 ${S}conf/bt_did.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/bt_stack.conf ]; then
	   install -m 0660 ${S}conf/bt_stack.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S_EXT}conf/interop_database.conf ]; then
		install -m 0660 ${S_EXT}conf/interop_database.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S_EXT}conf/bt_profile.conf ]; then
		install -m 0660 ${S_EXT}conf/bt_profile.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/iot_devlist.conf ]; then
	   install -m 0660 ${S}conf/iot_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi
}
