inherit autotools pkgconfig

DESCRIPTION = "Bluetooth OBEX"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "glib-2.0 btvendorhal"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/btapp/obex_profiles/ \
           file://bluetooth/bt_audio/hal/include/ \
           file://bluetooth/stack/system/bt/include/ \
           file://bluetooth/stack/bluetooth_ext/vhal/include/"

S = "${WORKDIR}/bluetooth"

CFLAGS:append = " -DUSE_ANDROID_LOGGING"
EXTRA_OEMAKE += 'BT_SOURCE=${S}'

TARGET_CFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
TARGET_CXXFLAGS += " -fmacro-prefix-map=${WORKDIR}=. -fdebug-prefix-map=${WORKDIR}=."
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

AUTOTOOLS_SCRIPT_PATH = "${S}/btapp/obex_profiles"

EXTRA_OECONF = "--with-glib"
EXTRA_OECONF += "--with-common-includes=${STAGING_INCDIR}"
