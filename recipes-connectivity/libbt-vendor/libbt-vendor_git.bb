inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Vendor Library"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "common hci-qcomm-init glib-2.0"



FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/libbt-vendor/libbt-vendor/"

S = "${WORKDIR}/bluetooth/libbt-vendor/libbt-vendor"


#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = "--with-common-includes="${WORKSPACE}/bluetooth/bt_audio/hal/include/" \
                --with-lib-path=${STAGING_LIBDIR} \
                --enable-target=${BASEMACHINE} \
                --enable-rome=${BASEPRODUCT} \
                --with-glib \
               "

FILES:${PN} += "${userfsdatadir}/misc/bluetooth/*"

do_install:append () {
    install -d ${D}${userfsdatadir}/misc/bluetooth
    install -m 755 ${S}/init.msm.bt.sh ${D}${userfsdatadir}/misc/bluetooth/
}
