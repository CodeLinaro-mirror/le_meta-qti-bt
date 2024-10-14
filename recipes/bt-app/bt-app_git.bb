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
DEPENDS_remove_mdm9607  = "audiohal"
DEPENDS_append_kona = " libhardware"
DEPENDS_append_qrb5165 += " libhardware"
DEPENDS_remove_sxr2130-mtp  = "audiohal"
DEPENDS_append_qrbx210-rbx  = " libhardware media-headers"
DEPENDS_append_qcs610  = " libhardware media-headers"
DEPENDS_append_qcs6490  = " libhardware media-headers"

#CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
#CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "


EXTRA_OECONF = " \
                --with-glib \
                --with-btobex \
                --with-gengatt \
               "
EXTRA_OECONF += "--enable-target=${BASEMACHINE}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN} += "${sysconfdir}/bluetooth/*"
FILES_${PN} += "${userfsdatadir}/misc/bluetooth/*"

do_install_append() {
        #create /data/misc/bluetooth/ folder
        install -d ${D}${userfsdatadir}/misc/bluetooth/

        if [ -f ${S}conf/AdvertiserConfigFile.txt ]; then
           install -m 0660 ${S}conf/AdvertiserConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
        fi

        if [ -f ${S}conf/ServerConfigFile.txt ]; then
           install -m 0660 ${S}conf/ServerConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
        fi
}

do_install_append_qrb5165-ifb() {
	install -d ${D}${sysconfdir}/bluetooth/

	if [ -f ${S}conf/IFB_bt_app.conf ]; then
	   install -m 0660 ${S}conf/IFB_bt_app.conf ${D}${sysconfdir}/bluetooth/bt_app.conf
	fi

}
