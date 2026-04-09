inherit autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/btapp/"
SRC_URI += "file://bt-conf_systemd_tmpfiles.conf"

S = "${WORKDIR}/bluetooth/btapp"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'
EXTRA_OEMAKE += "STAGING_INCDIR=${STAGING_INCDIR}"

AUTOTOOLS_SCRIPT_PATH = "${S}/bt-app"

DEPENDS += "btvendorhal glib-2.0 btobex libchrome libcutils fluoride audio-route pa-bt-audio libbsd"
#RDEPENDS:${PN} = "property-vault"

CPPFLAGS:append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"
TARGET_CFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
TARGET_CXXFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

EXTRA_OECONF = " \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
                --with-gengatt \
                --with-btobex \
                --with-btcte \
               "

EXTRA_OECONF:append = " --enable-audio=yes "

do_install:append() {
        install -d ${D}${sysconfdir}/bluetooth/

        install -d 0775 ${D}${userfsdatadir}/misc/bluetooth
        install -d 0775 ${D}${userfsdatadir}/vendor/bluetooth/ssrdump
        #install common systemd files
        install -m 0644 ${WORKDIR}/bt-conf_systemd_tmpfiles.conf \
        -D ${D}${sysconfdir}/tmpfiles.d/bt-conf_systemd_tmpfiles.conf

        if [ -f ${S}/bt-app/conf/bt_app.conf ]; then
           install -m 0660 ${S}/bt-app/conf/bt_app.conf ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/bt-app/conf/AdvertiserConfigFile.txt ]; then
           install -m 0660 ${S}/bt-app/conf/AdvertiserConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/bt-app/conf/ServerConfigFile.txt ]; then
           install -m 0660 ${S}/bt-app/conf/ServerConfigFile.txt ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/bt-app/conf/ext_to_mimetype.conf ]; then
           install -m 0660 ${S}/bt-app/conf/ext_to_mimetype.conf ${D}${sysconfdir}/bluetooth/
        fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${userfsdatadir}/misc/bluetooth"
FILES:${PN} += "${userfsdatadir}/vendor/bluetooth/ssrdump"
