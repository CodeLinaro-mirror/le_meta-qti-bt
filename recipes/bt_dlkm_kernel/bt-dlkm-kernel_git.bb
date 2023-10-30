DESCRIPTION = "QTI BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit module

FILESPATH =+ "${WORKSPACE}/bluetooth:"
#FILESEXTRAPATHS:prepend = "${WORKSPACE}/bluetooth:"
SRC_URI = "file://bt-kernel"

S = "${WORKDIR}/bt-kernel"

RPROVIDES:${PN} += "kernel-module-bt-kernel"
RM_WORK_EXCLUDE += "${PN}"
SRC_URI    +=  "file://bt_dlkm"
SRC_URI    +=  "file://bt_dlkm.service"

EXTRA_OEMAKE += "MACHINE='${MACHINE}'"
MAKE_TARGETS = "modules"
MODULES_INSTALL_TARGET = "modules_install"

do_install:append() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}/usr/include/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}

#        # strip debug symbols and sign the module
#        ${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/11.3.0/strip \
#              --strip-debug ${WORKDIR}/vendor/qcom/opensource/bt-kernel/pwr/btpower.ko

    install -m 0755 ${WORKDIR}/bt-kernel/slimbus/bt_fm_slim.ko -D ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_system_unitdir}/bt_dlkm.service
}

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${systemd_unitdir}/*"