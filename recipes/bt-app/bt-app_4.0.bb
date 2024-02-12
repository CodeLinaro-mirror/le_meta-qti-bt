inherit autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/"

S = "${WORKDIR}/qcom-opensource/bt/bt-app/"

DEPENDS += "btvendorhal gen-gatt glib-2.0 btobex"
#DEPENDS:remove:mdm9607  = "audiohal"
#DEPENDS:append:kona = " libhardware"
#DEPENDS:append:neo = " libhardware "
#DEPENDS:remove:sxr2130-mtp  = "audiohal"
#DEPENDS:remove:neo  = "audiohal"
#DEPENDS:append:qrbx210-rbx  = " libhardware media-headers"
#DEPENDS:append:kalama  = " libhardware media-headers"
#DEPENDS:remove:kalama  = " audiohal"

CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
CPPFLAGS:append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
CFLAGS:append = " -DUSE_ANDROID_LOGGING "
LDFLAGS:append = " -llog "
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"

EXTRA_OECONF = " \
		--with-common-includes="${WORKSPACE}/vendor/qcom/opensource/bluetooth/hal/include/" \
		--with-glib \
                --with-btobex \
                --with-gengatt \
                --with-btvendorhal \
		"
EXTRA_OECONF += "--enable-target=${BASEMACHINE}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
#FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"
#FILES:${PN} += "/data /data/misc /data/misc/bluetooth"

do_install:append() {
    #create /data/misc/bluetooth/ folder
    install -d ${D}${userfsdatadir}/misc/bluetooth/

    if [ -f ${S}conf/AdvertiserConfigFile.txt ]; then
      install -m 0660 ${S}conf/AdvertiserConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
    fi

    if [ -f ${S}conf/ServerConfigFile.txt ]; then
      install -m 0660 ${S}conf/ServerConfigFile.txt ${D}${userfsdatadir}/misc/bluetooth/
    fi
    
    if [ -f ${S}conf/bt_app.conf ]; then
           install -m 0660 ${S}conf/bt_app.conf ${D}${userfsdatadir}/misc/bluetooth/
    fi

    if [ -f ${S}conf/ext_to_mimetype.conf ]; then
           install -m 0660 ${S}conf/ext_to_mimetype.conf ${D}${userfsdatadir}/misc/bluetooth/
    fi

}


