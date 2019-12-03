inherit autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PACKAGE_ARCH="${MACHINE_ARCH}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/ \
           file://qcom-opensource/bt/obex_profiles"

S = "${WORKDIR}/qcom-opensource/bt/bt-app/"

DEPENDS = "btvendorhal gen-gatt glib-2.0 btobex liblog"

PACKAGECONFIG = "${@bb.utils.contains('COMBINED_FEATURES', 'qti-audio', 'audiohal', '', d)}"
PACKAGECONFIG[audiohal] = "--enable-audiohal --with-common-includes="${STAGING_INCDIR}/mm-audio/qahw_api/inc", \
                           --disable-audiohal, audiohal qahw"

CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP"
CFLAGS_append = " -DUSE_ANDROID_LOGGING "
LDFLAGS_append = " -llog "

EXTRA_OECONF = " \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-btobex \
               "

FILES_${PN} += "${sysconfdir}/bluetooth/*"

do_install_append() {
        install -d ${D}${sysconfdir}/bluetooth/

        if [ -f ${S}conf/bt_app.conf ]; then
           install -m 0660 ${S}conf/bt_app.conf ${D}${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}conf/ext_to_mimetype.conf ]; then
           install -m 0660 ${S}conf/ext_to_mimetype.conf ${D}${sysconfdir}/bluetooth/
        fi
}
