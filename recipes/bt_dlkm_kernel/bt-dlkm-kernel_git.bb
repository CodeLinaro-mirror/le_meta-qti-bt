DESCRIPTION = "QTI BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


inherit linux-kernel-base deploy

PR = "r0"

DEPENDS = "rsync-native wlan-platform btdevicetree"
# DEPENDS += "bc-native bison-native"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://vendor/qcom/opensource/bt-kernel/"
SRC_URI    +=  "file://bt_dlkm"
SRC_URI    +=  "file://bt_dlkm.service"
SRC_URI += "file://kernel-5.15/kernel_platform"
SRC_URI += "file://kernel-5.15/out/${KERNEL_DEFCONFIG}"

S = "${WORKDIR}/vendor/qcom/opensource/bt-kernel"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"
KERNEL_VERSION = "${@get_kernelversion_headers('${STAGING_KERNEL_BUILDDIR}')}"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

do_configure() {
}

do_compile() {

    cd ${WORKDIR}/kernel-5.15/kernel_platform && \

    KBUILD_OPTIONS+="CONFIG_BTFM_SLIM=m" \
    KBUILD_EXTRA_SYMBOLS=${D}${base_libdir}/modules/${KERNEL_VERSION}/cnsswlan-kernel/Module.symvers \
    BUILD_CONFIG=msm-kernel/${KERNEL_CONFIG} \
    EXT_MODULES=../../vendor/qcom/opensource/bt-kernel \
    ROOTDIR=${WORKDIR}/ \
    MODULE_MSM_BT_POWER=m \
    MODULE_OUT=${S} \
    OUT_DIR=${WORKDIR}/kernel-5.15/out/${KERNEL_DEFCONFIG} \
    KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
    ./build/build_module.sh
}

do_install() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -d ${D}/usr/include/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}

#        # strip debug symbols and sign the module
#        ${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/11.3.0/strip \
#              --strip-debug ${WORKDIR}/vendor/qcom/opensource/bt-kernel/pwr/btpower.ko

    install -m 0755 ${WORKDIR}/vendor/qcom/opensource/bt-kernel/pwr/btpower.ko -D ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${WORKDIR}/vendor/qcom/opensource/bt-kernel/slimbus/bt_fm_slim.ko -D ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
}

do_install:append() {
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_deploy() {
# Deploy unstripped kernel modules into ${DEPLOYDIR}/kernel_modules for debugging purposes
    install -d ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/vendor/qcom/opensource/bt-kernel/*/*.ko ${DEPLOYDIR}/kernel_modules
}

addtask deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${systemd_unitdir}/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*.ko"
FILES:${PN} += "${base_libdir}/modules/*.ko"
