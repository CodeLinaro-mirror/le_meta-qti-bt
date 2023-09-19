inherit cmake autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/ \
           file://bt-app.conf"

S = "${WORKDIR}/qcom-opensource/bt/bt-app/"

DEPENDS += "btvendorhal glib-2.0 btobex libchrome fluoride audiohal bt-ext libsystemdq"
DEPENDS_remove_mdm9607  = "audiohal"
DEPENDS_append_kona = " libhardware"
DEPENDS_append_neo = " libhardware "
DEPENDS_remove_sxr2130-mtp  = "audiohal"
DEPENDS_remove_neo  = "audiohal"
DEPENDS_append_qrbx210-rbx  = " libhardware media-headers"

#CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_BT_OBEX -DUSE_LIBHW_AOSP -DUSE_GEN_GATT"
#CPPFLAGS_append += " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#CFLAGS_append = " -DUSE_ANDROID_LOGGING "
#LDFLAGS_append = " -llog "


EXTRA_OECONF = " \
                --with-glib \
                --with-btobex \
                --with-gengatt \
               "
EXTRA_OECONF += "--enable-target=${BASEMACHINE}"

do_install_append() {
         install -d ${D}/${sysconfdir}/dbus-1/system.d/
         install -m 0644 ${WORKDIR}/bt-app.conf ${D}${sysconfdir}/dbus-1/system.d/
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN} += "${sysconfdir}/bluetooth/*"
