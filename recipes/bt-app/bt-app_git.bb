inherit autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/"

S = "${WORKDIR}/qcom-opensource/bt/bt-app/"

DEPENDS += "btvendorhal glib-2.0 btobex libchrome fluoride audiohal bt-ext"
DEPENDS:remove:mdm9607  = "audiohal"
DEPENDS:remove:qcs610  = "audiohal"
DEPENDS:append:kona = " libhardware"
DEPENDS:append:qrb5165 += " libhardware"
DEPENDS:remove:sxr2130-mtp  = "audiohal"
DEPENDS:append:qrbx210-rbx  = " libhardware media-headers"
DEPENDS:append:qcs610  = " libhardware media-headers"
DEPENDS:append:qcs6490  = " libhardware media-headers"

#CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
#CPPFLAGS:append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#CFLAGS:append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS:append = " -llog "


EXTRA_OECONF = " \
                --with-glib \
                --with-btobex \
                --with-gengatt \
               "
EXTRA_OECONF += "--enable-target=${BASEMACHINE}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"

do_install:append() {
        #create /data/misc/bluetooth/ folder
        install -d ${D}${userfsdatadir}/misc/bluetooth/

        if [ -f ${S}conf/AdvertiserConfigFile.txt ]; then
           install -m 0660 ${S}conf/AdvertiserConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
        fi

        if [ -f ${S}conf/ServerConfigFile.txt ]; then
           install -m 0660 ${S}conf/ServerConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
        fi
}

do_install:append:qrb5165-ifb() {
	install -d ${D}${sysconfdir}/bluetooth/

	if [ -f ${S}conf/IFB_bt_app.conf ]; then
	   install -m 0660 ${S}conf/IFB_bt_app.conf ${D}${sysconfdir}/bluetooth/bt_app.conf
	fi

}

do_install:append:qrbx210-rbx() {
        install -d ${D}${sysconfdir}/bluetooth/

        if [ -f ${S}conf/RBX_bt_app.conf ]; then
           install -m 0660 ${S}conf/RBX_bt_app.conf ${D}${sysconfdir}/bluetooth/bt_app.conf
        fi

}
