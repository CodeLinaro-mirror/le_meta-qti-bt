DESCRIPTION = "QCOM BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit module

FILESPATH =+ "${WORKSPACE}/bluetooth:"
SRC_URI = "file://bt-kernel"

S = "${WORKDIR}/bt-kernel"

RPROVIDES:${PN} += "kernel-module-bt-kernel"
SRC_URI    +=  "file://bt_dlkm"
SRC_URI    +=  "file://bt_dlkm.service"

EXTRA_OEMAKE += "MACHINE='${MACHINE}'"
MAKE_TARGETS = "modules"
MODULES_INSTALL_TARGET = "modules_install"
KERNEL_MODULE_AUTOLOAD += "bt_fm_slim"
