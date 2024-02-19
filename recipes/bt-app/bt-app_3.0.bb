inherit cmake autotools pkgconfig

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/ \
           file://bt-app.conf \
           file://bt-app.service \
                   file://bt-app_conf_systemd_tmpfiles.conf \
                   file://bt-app.sh \
                   file://bt-app-etc.sh "

S = "${WORKDIR}/qcom-opensource/bt/bt-app"

DEPENDS += "btvendorhal glib-2.0 btobex libchrome fluoride audiohal bt-ext"
DEPENDS:remove:mdm9607  = "audiohal"
DEPENDS:append:kona = " libhardware"
DEPENDS:append:neo = " libhardware pa-bt-audio"
DEPENDS:remove:sxr2130-mtp  = "audiohal"
DEPENDS:remove:neo  = "audiohal"
DEPENDS:append:qrbx210-rbx  = " libhardware media-headers"

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

do_install:append() {
         install -d ${D}/${sysconfdir}/dbus-1/system.d/
         install -d ${D}${systemd_system_unitdir}
         install -d ${D}${systemd_system_unitdir}/multi-user.target.wants/
         install -m 0644 ${WORKDIR}/bt-app.conf ${D}${sysconfdir}/dbus-1/system.d/
         install -m 0644 ${WORKDIR}/bt-app.service ${D}${systemd_system_unitdir}

         # enable the service for multi-user.target
         ln -sf ${systemd_system_unitdir}/bt-app.service \
                ${D}${systemd_system_unitdir}/multi-user.target.wants/bt-app.service

                 install -d ${D}${sysconfdir}/tmpfiles.d
                 install -m 0644 ${WORKDIR}/bt-app_conf_systemd_tmpfiles.conf \
                                 -D ${D}${sysconfdir}/tmpfiles.d/bt-app_conf_systemd_tmpfiles.conf

                                   install -d ${D}${base_sbindir}/
         install -D -m 0755 ${WORKDIR}/bt-app.sh ${D}${base_sbindir}/bt-app.sh
         install -D -m 0755 ${WORKDIR}/bt-app-etc.sh ${D}${base_sbindir}/bt-app-etc.sh
}

inherit cmake pkgconfig systemd

SYSTEMD_SERVICE_${PN} = " \
                         bt-app.service \
                        "

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${PN} += "${sysconfdir}/bluetooth/*"
FILES:${PN} += "${systemd_unitdir}"
FILES:${PN} += "${systemd_system_unitdir}"
