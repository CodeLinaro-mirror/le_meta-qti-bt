inherit autotools pkgconfig

DESCRIPTION = "Bluetooth certification tool"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/stack/bluetooth_ext"

S = "${WORKDIR}/bluetooth/stack/bluetooth_ext"

AUTOTOOLS_SCRIPT_PATH = "${S}/certification_tools"

DEPENDS  += "glib-2.0 btvendorhal libchrome fluoride libbsd"

EXTRA_OEMAKE += 'BT_SOURCE=${S}'
EXTRA_OEMAKE += "STAGING_INCDIR=${STAGING_INCDIR}"

EXTRA_OECONF = " \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
               "
