inherit autotools pkgconfig

DESCRIPTION = "Bluetooth certification tool"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI = "file://bluetooth/stack/bluetooth_ext/certification_tools/"

S = "${WORKDIR}/bluetooth/stack/bluetooth_ext/certification_tools/"


DEPENDS  += "glib-2.0 fluoride"

#CPPFLAGS_append = " -DUSE_ANDROID_LOGGING"
#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "

EXTRA_OECONF = " \
                --with-common-includes="${WORKSPACE}/bluetooth/bt_audio/hal/include/" \
                --with-glib \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-chrome-includes="${STAGING_INCDIR}/chrome" \
               "
