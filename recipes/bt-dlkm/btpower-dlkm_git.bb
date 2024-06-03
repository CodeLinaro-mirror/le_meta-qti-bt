DESCRIPTION = "QTI Bluetooth Power Driver"
LICENSE = "BSD-3-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit systemd module linux-kernel-base

DEPENDS = "virtual/kernel"
DEPENDS += "${@bb.utils.contains_any('MACHINE', 'sa525m', 'bt-devicetree', '', d)}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bt-kernel/"
SRC_URI += " \
            file://bluetooth_power.sh \
            file://bluetooth_power.service \
           "

RM_WORK_EXCLUDE += "${PN}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

BT_BUILD_OUT="${WORKDIR}/vendor/qcom/opensource/bt-kernel-out"

do_compile() {

    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../vendor/qcom/opensource/bt-kernel/ \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${BT_BUILD_OUT}/ \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh "CONFIG_MSM_BT_POWER=m"
}

do_install() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
       install -d ${D}${systemd_unitdir}/system
       install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
       install -m 0644 ${WORKDIR}/bluetooth_power.service ${D}${systemd_unitdir}/system
       ln -sf ${systemd_unitdir}/system/bluetooth_power.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/bluetooth_power.service
       install -d ${D}${sysconfdir}/initscripts
       install -m 0555 ${WORKDIR}/bluetooth_power.sh ${D}${sysconfdir}/initscripts
       install -m 0755 ${BT_BUILD_OUT}/pwr/btpower.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/btpower.ko
    fi
}

FILES:${PN} += "${systemd_unitdir}/system/"
FILES:${PN} += "${sysconfdir}/systemd/system/"
FILES:${PN} += "${sysconfdir}/initscripts/"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

RPROVIDES:${PN} += "kernel-module-btpower-${KERNEL_VERSION}"
