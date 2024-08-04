DESCRIPTION = "Bluetooth Kernel Modules"
HOMEPAGE = "https://source.codeaurora.org/quic/le/meta-qti-bt"

LICENSE = "BSD-3-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SECTION = "qti-bt"

inherit systemd
inherit module

DEPENDS = "virtual/kernel"

kernel_dir := "${WORKSPACE}/kernel/msm-${PREFERRED_VERSION_linux-msm}"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${kernel_dir}:"

SRC_URI = " \
            file://drivers/bluetooth/ \
            file://Makefile.cc \
           "
SRC_URI += " \
            file://bluetooth_power.sh \
            file://bluetooth_power.service \
           "

S = "${WORKDIR}"
B = "${S}/drivers/bluetooth"

do_patch_btdrv() {
    cp -f ${S}/Makefile.cc ${B}/Makefile
}
do_patch[postfuncs] += "do_patch_btdrv"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
       install -d ${D}${systemd_unitdir}/system
       install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
       install -m 0644 ${WORKDIR}/bluetooth_power.service ${D}${systemd_unitdir}/system
       ln -sf ${systemd_unitdir}/system/bluetooth_power.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/bluetooth_power.service
       install -d ${D}${sysconfdir}/initscripts
       install -m 0755 ${WORKDIR}/bluetooth_power.sh ${D}${sysconfdir}/initscripts
    fi
}

FILES:${PN} += "${systemd_unitdir}/system/"
FILES:${PN} += "${sysconfdir}/systemd/system/"
FILES:${PN} += "${sysconfdir}/initscripts/"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

RPROVIDES:${PN} += "kernel-module-btpower-${KERNEL_VERSION}"
