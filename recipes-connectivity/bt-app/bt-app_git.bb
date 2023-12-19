inherit autotools pkgconfig logging

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://btapp/ \
           file://bt_audio/ \
           file://stack/system/bt/ \
           file://stack/bluetooth_ext/"

BT_SOURCE = "${WORKDIR}"
S = "${BT_SOURCE}/btapp/bt-app"

EXTRA_OEMAKE += 'BT_SOURCE=${BT_SOURCE}'

def get_depends():
    if "$(BASEMACHINE)" == "mdm9607":
        return  "btvendorhal glib-2.0 property-vault btobex libchrome fluoride"
    else:
        return   "btvendorhal glib-2.0 property-vault libchrome fluoride"

DEPENDS  += "${@get_depends()}"
RDEPENDS:${PN} = "property-vault"

CPPFLAGS:append = " -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
CPPFLAGS:append = " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
# CFLAGS:append = " -DUSE_ANDROID_LOGGING "
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"

EXTRA_OECONF = " \
                --with-common-includes="${BT_SOURCE}/bt_audio/hal/include/" \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
                --with-gengatt \
               "
EXTRA_OECONF += "--enable-target=${BASEMACHINE}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"

do_install:append() {
        install -d ${D}${sysconfdir}/bluetooth/

        #create /data/misc/bluetooth/ folder
        #install -d ${D}${userfsdatadir}/misc/bluetooth/

        if [ -f ${S}/conf/bt_app.conf ]; then
           install -m 0660 ${S}/conf/bt_app.conf ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/conf/AdvertiserConfigFile.txt ]; then
           install -m 0660 ${S}/conf/AdvertiserConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/conf/ServerConfigFile.txt ]; then
           install -m 0660 ${S}/conf/ServerConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/conf/ext_to_mimetype.conf ]; then
           install -m 0660 ${S}/conf/ext_to_mimetype.conf ${D}${sysconfdir}/bluetooth/
        fi
}
