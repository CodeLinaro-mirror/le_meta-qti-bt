inherit autotools pkgconfig logging

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://btapp/ \
           file://bt_audio/ \
           file://stack/system/bt/ \
           file://stack/bluetooth_ext/"

S = "${WORKDIR}"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'

AUTOTOOLS_SCRIPT_PATH = "${S}/btapp/bt-app"

DEPENDS += "btvendorhal glib-2.0 property-vault libchrome fluoride audioroute pa-bt-audio"
RDEPENDS:${PN} = "property-vault"

CPPFLAGS:append = " -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"

EXTRA_OECONF = " \
                --with-common-includes="${S}/bt_audio/hal/include/" \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
                --with-gengatt \
               "
PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"

do_install:append() {
        install -d ${D}${sysconfdir}/bluetooth/

        #create /data/misc/bluetooth/ folder
        #install -d ${D}${userfsdatadir}/misc/bluetooth/

        if [ -f ${S}/btapp/bt-app/conf/bt_app.conf ]; then
           install -m 0660 ${S}/btapp/bt-app/conf/bt_app.conf ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/btapp/bt-app/conf/AdvertiserConfigFile.txt ]; then
           install -m 0660 ${S}/btapp/bt-app/conf/AdvertiserConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/btapp/bt-app/conf/ServerConfigFile.txt ]; then
           install -m 0660 ${S}/btapp/bt-app/conf/ServerConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/btapp/bt-app/conf/ext_to_mimetype.conf ]; then
           install -m 0660 ${S}/btapp/bt-app/conf/ext_to_mimetype.conf ${D}${sysconfdir}/bluetooth/
        fi
}
