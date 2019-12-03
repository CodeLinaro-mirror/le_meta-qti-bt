inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PACKAGE_ARCH="${MACHINE_ARCH}"

DEPENDS = "zlib btvendorhal libbt-vendor media-headers liblog system-core-headers"

PACKAGECONFIG = "${@bb.utils.contains('COMBINED_FEATURES', 'qti-audio', 'audiohal', '', d)}"
PACKAGECONFIG[audiohal] = "--enable-audiohal, --disable-audiohal, audio-utils libhardware"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://system/bt/ \
           file://vendor/qcom/opensource/bluetooth/"

S = "${WORKDIR}/system/bt/"
S_EXT = "${WORKDIR}/vendor/qcom/opensource/bluetooth/system_bt_ext/"


FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}"
FILES_${PN} += "${sysconfdir}/bluetooth/*"
FILES_${PN} += "${userfsdatadir}/misc/bluetooth/*"
FILES_${PN} += "/persist/bluetooth/"
INSANE_SKIP_${PN} = "dev-so"

CFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
LDFLAGS_append = " -llog "

EXTRA_OECONF = " \
                --with-zlib \
                --with-common-includes="${WORKSPACE}/vendor/qcom/opensource/bluetooth/hal/include/" \
                --with-lib-path=${STAGING_LIBDIR} \
               "

EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'naples', '--enable-som=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'rome', '--enable-rome=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'pronto', '--enable-pronto=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'cherokee', '--enable-cherokee=yes', '', d)}"

do_install_append() {

	install -d ${D}${sysconfdir}/bluetooth/
	install -d ${D}${userfsdatadir}/misc/bluetooth/
	install -d ${D}/persist/bluetooth
	install -d ${D}${includedir}

	cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so.0 bluetooth.default.so
	cd  ${D}/${libdir}/ && ln -s libaudioa2dpdefault.so.0 audio.a2dp.default.so

	if [ -f ${S}conf/auto_pair_devlist.conf ]; then
	   install -m 0660 ${S}conf/auto_pair_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi

#	if [ -f ${S_EXT}conf/interop_database.conf ]; then
#	   install -m 0660 ${S_EXT}conf/interop_database.conf ${D}${userfsdatadir}/misc/bluetooth/
#	fi

	if [ -f ${S}conf/bt_did.conf ]; then
	   install -m 0660 ${S}conf/bt_did.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/bt_stack.conf ]; then
	   install -m 0660 ${S}conf/bt_stack.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/iot_devlist.conf ]; then
	   install -m 0660 ${S}conf/iot_devlist.conf ${D}${sysconfdir}/bluetooth/
	   install -m 0660 ${S}conf/iot_devlist.conf ${D}${userfsdatadir}/misc/bluetooth/
	fi

	if [ -f ${S}/hci/include/bt_vendor_lib.h ]; then
	   install -m 0660 ${S}/hci/include/bt_vendor_lib.h ${D}${includedir}
	fi

}
INHIBIT_PACKAGE_DEBUG_SPLIT="1"
