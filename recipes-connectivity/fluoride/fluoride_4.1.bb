inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

#DEPENDS = "zlib libchrome glib-2.0 property-vault qcom-audioroute libbsd"
DEPENDS = "zlib libchrome bttransport glib-2.0 libbsd libcutils libutils media-headers bt-audio-headers"
#RDEPENDS:${PN} = "property-vault"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/stack/system/bt/ \
           file://bluetooth/stack/bluetooth_ext/"

S = "${WORKDIR}/bluetooth/stack/system/bt"
S_EXT = "${WORKDIR}/bluetooth/stack/bluetooth_ext/system_bt_ext"

AUTOTOOLS_SCRIPT_PATH = "${S}"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'
EXTRA_OEMAKE += 'AUDIO_INC_PATH=${STAGING_INCDIR}/bt_audio'

PSEUDO_IGNORE_PATHS = "/dev/,${WORKDIR}/bluetooth,${WORKDIR}/pkgdata-sysroot,${TMPDIR}/sysroots-components"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
INSANE_SKIP:${PN} = "dev-so"

CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
CPPFLAGS:append = " -w -I${STAGING_INCDIR}"
CFLAGS:append = " -w -DNDEBUG  -I${STAGING_INCDIR}"
TARGET_CFLAGS += " -mno-outline-atomics -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
TARGET_CXXFLAGS += " -mno-outline-atomics -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

EXTRA_OECONF = " \
                --with-zlib \
                --with-lib-path=${STAGING_LIBDIR} \
                --enable-static=yes \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
                --disable-dependency-tracking \
               "

#EXTRA_OECONF:append = " --enable-audio=yes "

PACKAGE_ARCH = "${MACHINE_ARCH}"
do_install:append() {

	install -d ${D}${sysconfdir}/bluetooth/

	cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so bluetooth.default.so
	cd  ${D}/${libdir}/ && ln -s libaudioa2dpdefault.so audio.a2dp.default.so

	if [ -f ${S}/conf/auto_pair_devlist.conf ]; then
	   install -m 0660 ${S}/conf/auto_pair_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}/conf/bt_did.conf ]; then
	   install -m 0660 ${S}/conf/bt_did.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}/conf/bt_stack.conf ]; then
	   install -m 0660 ${S}/conf/bt_stack.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S_EXT}/conf/interop_database.conf ]; then
		install -m 0660 ${S_EXT}/conf/interop_database.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S_EXT}/conf/bt_profile.conf ]; then
		install -m 0660 ${S_EXT}/conf/bt_profile.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}/conf/iot_devlist.conf ]; then
	   install -m 0660 ${S}/conf/iot_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi

    install -d ${D}${includedir}/bluetooth
    install -m 0660 ${S_EXT}/include/bt_testapp.h ${D}${includedir}/fluoride/
    cd ${S} && find ./ -name '*.h'|xargs tar czf ${D}${includedir}/bluetooth/bluetooth.tgz
    tar zxvf ${D}${includedir}/bluetooth/bluetooth.tgz -C ${D}${includedir}/bluetooth && rm -f ${D}${includedir}/bluetooth/bluetooth.tgz
}
