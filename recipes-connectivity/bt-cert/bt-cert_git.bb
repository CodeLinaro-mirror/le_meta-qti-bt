inherit autotools pkgconfig

DESCRIPTION = "Bluetooth certification tool"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://stack/bluetooth_ext/ \
           file://stack/system/bt/ \
           file://bt_audio/"

S = "${WORKDIR}"

AUTOTOOLS_SCRIPT_PATH = "${S}/stack/bluetooth_ext/certification_tools"

DEPENDS  += "glib-2.0 btvendorhal libchrome fluoride"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'

EXTRA_OECONF = " \
                --with-common-includes="${S}/bt_audio/hal/include/" \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
               "
