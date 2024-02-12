inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "common zlib btvendorhal libbt-vendor "
#DEPENDS:append:kona += " libcutils libhardware"
#DEPENDS:append:neo += " libcutils libhardware"
#DEPENDS:append:qrbx210-rbx += " libcutils libhardware"
#DEPENDS:append:kalama += " libhardware"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://system/bt/ \
           file://vendor/qcom/opensource/bluetooth/"

S = "${WORKDIR}/system/bt/"
S_EXT = "${WORKDIR}/vendor/qcom/opensource/bluetooth/"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"
INSANE_SKIP:${PN} = "dev-so"

#CFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
LDFLAGS:append = " -llog "

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
		--with-common-includes="${WORKSPACE}/vendor/qcom/opensource/bluetooth/hal/include/" \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
                --enable-static=yes \
               "

PACKAGE_ARCH = "${MACHINE_ARCH}"
do_install:append() {
	
	install -d ${D}${userfsdatadir}/misc/bluetooth/

        cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so.0 bluetooth.default.so
        cd  ${D}/${libdir}/ && ln -s libaudioa2dpdefault.so.0 audio.a2dp.default.so
	

        if [ -f ${S}conf/auto_pair_devlist.conf ]; then
           install -m 0660 ${S}conf/auto_pair_devlist.conf ${D}${userfsdatadir}/misc/bluetooth/
        fi

       if [ -f ${S}conf/bt_did.conf ]; then
           install -m 0660 ${S}conf/bt_did.conf ${D}${userfsdatadir}/misc/bluetooth/
        fi

        if [ -f ${S}conf/bt_stack.conf ]; then
           install -m 0660 ${S}conf/bt_stack.conf ${D}${userfsdatadir}/misc/bluetooth/
        fi

        if [ -f ${S}conf/iot_devlist.conf ]; then
           install -m 0660 ${S}conf/iot_devlist.conf ${D}${userfsdatadir}/misc/bluetooth/
        fi

}
