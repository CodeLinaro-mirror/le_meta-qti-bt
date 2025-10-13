DESCRIPTION = "QTI Bluetooth Power Driver"
LICENSE = "BSD-3-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit systemd module linux-kernel-base qdlkm

DEPENDS = "virtual/kernel"
DEPENDS += "${@bb.utils.contains_any('MACHINE', 'sa510m sa510m-1g sa510m-1G sa535m sa535m-emmc', 'bt-devicetree', '', d)}"

FILESEXTRAPATHS:prepend := "${WORKSPACE}/:${THISDIR}/files:"

SRC_URI = "file://vendor/qcom/opensource/bt-kernel/"
SRC_URI += " \
            file://bluetooth_power.sh \
            file://bluetooth_power.service \
           "

# RM_WORK_EXCLUDE += "${PN}"

BT_BUILD_OUT="${WORKDIR}/vendor/qcom/opensource/bt-kernel-out"

B:sa535m      = "${WORKDIR}/vendor/qcom/opensource/bt-kernel"
B:sa535m-emmc = "${WORKDIR}/vendor/qcom/opensource/bt-kernel"
BT_BUILD_OUT:sa535m      ="${B}/pwr/"
BT_BUILD_OUT:sa535m-emmc ="${B}/pwr/"

# EXTRA_OEMAKE += " V=1"
EXTRA_OEMAKE += " CONFIG_MSM_BT_POWER=m"
EXTRA_OEMAKE:append:sa535m      = " TARGET_PLATFORM=sa535m"
EXTRA_OEMAKE:append:sa535m-emmc = " TARGET_PLATFORM=sa535m"

TARGET_VARIANT= "${@bb.utils.contains('KERNEL_VARIANT', 'perf_', 'perf_defconfig', 'debug_defconfig', d)}"

TARGET_BOARD_PLATFORM ?= "sa510m"
TARGET_BOARD_PLATFORM:sa510m-1g = "sa510m.1g"
TARGET_BOARD_PLATFORM:sa510m_1g = "sa510m.1g"

do_compile() {
    cd ${KERNEL_PLATFORM_PATH} && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../vendor/qcom/opensource/bt-kernel \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ENABLE_DDK_BUILD=true \
    VARIANT=${TARGET_VARIANT} \
    TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
    MODULE_OUT=${BT_BUILD_OUT}/ \
    TARGET_SUPPORT=sa510m \
    ./build/build_module.sh
}

do_compile:sa535m() {
	oe_runmake
}
do_compile:sa535m-emmc() {
	oe_runmake
}

do_install() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
       install -d ${D}${systemd_unitdir}/system
       install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
       install -m 0644 ${WORKDIR}/bluetooth_power.service ${D}${systemd_unitdir}/system
       ln -sf ${systemd_unitdir}/system/bluetooth_power.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/bluetooth_power.service
       install -d ${D}${sysconfdir}/initscripts
       install -m 0555 ${WORKDIR}/bluetooth_power.sh ${D}${sysconfdir}/initscripts

       LD_LIBRARY_PATH=${KERNEL_PREBUILT_DISTDIR}/openssl/lib64/ \
       ${KERNEL_PREBUILT_DISTDIR}/sign-file sha1 ${KERNEL_PREBUILT_DISTDIR}/signing_key.pem \
       ${KERNEL_PREBUILT_DISTDIR}/signing_key.x509 ${BT_BUILD_OUT}/btpower.ko

       install -m 0755 ${BT_BUILD_OUT}/btpower.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/btpower.ko
    fi
}

do_install_sa535m() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
       install -d ${D}${systemd_unitdir}/system
       install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
       install -m 0644 ${WORKDIR}/bluetooth_power.service ${D}${systemd_unitdir}/system
       ln -sf ${systemd_unitdir}/system/bluetooth_power.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/bluetooth_power.service
       install -d ${D}${sysconfdir}/initscripts
       install -m 0555 ${WORKDIR}/bluetooth_power.sh ${D}${sysconfdir}/initscripts

       # LD_LIBRARY_PATH=${KERNEL_PREBUILT_DISTDIR}/openssl/lib64/ \
       # ${KERNEL_PREBUILT_DISTDIR}/sign-file sha1 ${KERNEL_PREBUILT_DISTDIR}/signing_key.pem \
       # ${KERNEL_PREBUILT_DISTDIR}/signing_key.x509 ${BT_BUILD_OUT}/btpower.ko

       install -m 0755 ${B}/pwr/btpower.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/btpower.ko
    fi
}

do_install:sa535m() {
	do_install_sa535m
}

do_install:sa535m-emmc() {
	do_install_sa535m
}

FILES:${PN} += "${systemd_unitdir}/system/"
FILES:${PN} += "${sysconfdir}/systemd/system/"
FILES:${PN} += "${sysconfdir}/initscripts/"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

RPROVIDES:${PN} += "kernel-module-btpower-${KERNEL_VERSION}"
