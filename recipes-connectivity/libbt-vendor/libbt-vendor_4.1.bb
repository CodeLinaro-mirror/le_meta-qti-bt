inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Vendor Library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "common hci-qcomm-init glib-2.0"

RDEPENDS:${PN} = "libcutils"

FILESPATH =+ "${WORKSPACE}:"

SRC_URI = "file://bluetooth/libbt-vendor/libbt-vendor/"

S = "${WORKDIR}/bluetooth/libbt-vendor/libbt-vendor"

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF = "--enable-target=${BASEMACHINE} \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-glib \
               "
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES:${PN} += "${bindir}/bluetooth/*"

do_install:append () {
    install -d ${D}${bindir}/bluetooth
    install -m 755 ${S}/init.msm.bt.sh ${D}${bindir}/bluetooth/
}
