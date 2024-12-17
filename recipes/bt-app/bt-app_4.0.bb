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
DEPENDS:append:kona = " libhardware"
DEPENDS:append:neo = " libhardware "
DEPENDS:remove:sxr2130-mtp  = "audiohal"
DEPENDS:remove:neo  = "audiohal"
DEPENDS:append:qrbx210-rbx  = " libhardware media-headers"
DEPENDS:append:kalama  = " libhardware media-headers"
DEPENDS:remove:kalama  = " audiohal"
DEPENDS:append:pineapple = " libhardware media-headers"
DEPENDS:remove:pineapple = " audiohal"

#CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
#CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"

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
