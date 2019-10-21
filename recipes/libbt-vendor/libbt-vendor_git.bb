inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Vendor Library"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "hci-qcomm-init glib-2.0 libutils liblog"

RDEPENDS_${PN} = "libcutils"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://hardware/qcom/bt/libbt-vendor/"

S = "${WORKDIR}/hardware/qcom/bt/libbt-vendor/"

CFLAGS_append = " -DUSE_ANDROID_LOGGING "
LDFLAGS_append = " -llog "

CPPFLAGS_append = "${@bb.utils.contains_any('PREFERRED_VERSION_linux-msm', '4.14', ' -DTIOCPMGET_544D ', '', d)}"

EXTRA_OECONF = "--with-common-includes="${WORKSPACE}/vendor/qcom/opensource/bluetooth/hal/include/" \
                --with-lib-path=${STAGING_LIBDIR} \
                --with-glib \
               "

EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'naples', '--enable-som=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'rome', '--enable-rome=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'pronto', '--enable-pronto=yes', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'cherokee', '--enable-cherokee=yes', '', d)}"

FILES_${PN} += "${sysconfdir}/bluetooth/*"

do_install_append () {
    install -d ${D}${sysconfdir}/bluetooth
    install -m 755 ${S}init.msm.bt.sh ${D}${sysconfdir}/bluetooth/
}
