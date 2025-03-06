DESCRIPTION = "QTI BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


inherit module deploy

FILESEXTRAPATHS:prepend := "${WORKSPACE}:"
SRC_URI     =  "file://vendor/qcom/opensource/bt-kernel/"
S = "${WORKDIR}/vendor/qcom/opensource/bt-kernel"
RPROVIDES:${PN} += "kernel-module-bt-fm-slim-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-btpower-${KERNEL_VERSION}"

DEPENDS += "virtual/kernel"
DEPENDS += "virtual/kernel-toolchain-native"
DEPENDS:append:aarch64 = " libgcc"
KERNEL_MODULES = "btpower bt_fm_slim"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

#PR = "r0"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"
EXTRA_OEMAKE += "M=${S}"
EXTRA_OEMAKE += "USE_DEDICATED_KERNEL_LE_TARGET=1"

DEFAULT_PREFERENCE = "-1"

MAKE_TARGETS = "modules"

KERNEL_CC = "${STAGING_BINDIR_NATIVE}/clang/bin/clang -target ${TARGET_ARCH}${TARGET_VENDOR}-${TARGET_OS}"

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${sysconfdir}/
    install -m 0755 ${S}/pwr/btpower.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${S}/slimbus/bt_fm_slim.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}

addtask deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${systemd_unitdir}/*"
FILES:${PN} += "${base_libdir}/modules/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/bt_fm_slim.ko"
