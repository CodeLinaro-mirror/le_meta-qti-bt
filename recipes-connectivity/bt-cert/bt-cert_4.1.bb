inherit autotools pkgconfig

DESCRIPTION = "Bluetooth certification tool"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

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
