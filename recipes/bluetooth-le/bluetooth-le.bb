SUMMARY = "Bluetooth LE and GATT libraries"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit cmake pkgconfig systemd

DEPENDS = "fluoride libsystemdq libchrome"
FILESPATH_prepend = "${WORKSPACE}/:"

SRC_URI = " \
        file://vendor/qcom/opensource/bluetooth-le/ \
        file://bluetooth-le.conf \
        "

S = "${WORKDIR}/vendor/qcom/opensource/bluetooth-le"

RDEPENDS_${PN} = "libsystemdq"
CPPFLAGS_append = " -DUSE_LIBHW_AOSP"


PACKAGES =+ "${PN}-lib"

FILES_${PN}-lib = "${libdir}/lib*.so*"

FILES_${PN} += " \
        ${systemd_system_unitdir} \
        ${sysconfdir} \
        "

do_install_append() {
        install -d ${D}/${sysconfdir}/dbus-1/system.d/
        install -m 0644 ${WORKDIR}/bluetooth-le.conf ${D}${sysconfdir}/dbus-1/system.d/
}

RRECOMMENDS_${PN}-lib = "${PN}"

EXTRA_OECMAKE = "-DINCLUDES_SYSROOT=${STAGING_INCDIR}"

