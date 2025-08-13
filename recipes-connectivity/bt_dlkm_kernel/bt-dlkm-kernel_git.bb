DESCRIPTION = "QTI BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


inherit linux-kernel-base deploy

FILESEXTRAPATHS:prepend := "${WORKSPACE}:"
SRC_URI     =  "file://bluetooth/bt-kernel/"
SRC_URI    +=  "file://bt_dlkm"
SRC_URI    +=  "file://bt_dlkm.service"
S = "${WORKDIR}/bluetooth/bt-kernel"
DEPENDS += "virtual/kernel wlan-platform btdevicetree "
DEPENDS:append:kera = " ar-audiodlkm ar-audiodlkm-headers "
DEPENDS:append:sun = " ar-audiodlkm ar-audiodlkm-headers "

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"
INTERMEDIATE_KERNEL_PATH = "${WORKDIR}/out/${KERNEL_DEFCONFIG}"
MODULE_LIST = "btpower.ko bt_fm_slim.ko"
MODULE_LIST:sun = "btpower.ko btfmcodec.ko bt_fm_swr.ko btfm_slim_codec.ko"

SYMVERS = "KBUILD_EXTRA_SYMBOLS=${STAGING_DIR_HOST}/usr/lib/modules/${KERNEL_VERSION}/cnsswlan-kernel/Module.symvers"
SYMVERS:append:kera = " KBUILD_EXTRA_SYMBOLS+=${STAGING_DIR_HOST}/usr/lib/modules/${KERNEL_VERSION}/extra/Module.symvers"
SYMVERS:append:sun = " KBUILD_EXTRA_SYMBOLS+=${STAGING_DIR_HOST}/usr/lib/modules/${KERNEL_VERSION}/extra/Module.symvers"
EXT_COMPILE_CONFIG = " CONFIG_MSM_BT_POWER=m CONFIG_BTFM_SLIM=m"
EXT_COMPILE_CONFIG:append:kera = " CONFIG_BTFM_CODEC=m CONFIG_BTFM_SWR=m"
EXT_COMPILE_CONFIG:remove:sun = " CONFIG_BTFM_SLIM=m"
EXT_COMPILE_CONFIG:append:sun = " CONFIG_BTFM_CODEC=m CONFIG_BTFM_SWR=m CONFIG_SLIM_BTFM_CODEC=m"

do_configure[noexec] = "1"

do_compile[depends] += "virtual/kernel:do_shared_workdir"
do_compile[cleandirs] += "${INTERMEDIATE_KERNEL_PATH}"
do_compile() {
    cd ${KERNEL_PLATFORM_PATH}
    BUILD_CONFIG=msm-kernel/${KERNEL_CONFIG} \
    EXT_MODULES=${EXT_MODULES} \
    ROOTDIR=${WORKDIR}/ \
    KERNEL_KIT=${KERNEL_PREBUILT_PATH} \
    OUT_DIR=${INTERMEDIATE_KERNEL_PATH} \
    INPLACE_COMPILE=y \
    MODULE_OUT=${S} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    ./build/build_module.sh \
    ${SYMVERS} \
    ${EXT_COMPILE_CONFIG}
}

do_install() {
    install -d ${S}/unstripped
    install -m 0755 `find ${S}/*/ -name *.ko` -D ${S}/unstripped

    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}

    STRIP_TOOL="${STAGING_DIR_NATIVE}/usr/bin/aarch64-oe-linux/aarch64-oe-linux-strip"
    if [ ! -x "$STRIP_TOOL" ]; then
      STRIP_TOOL="${STAGING_DIR_NATIVE}/usr/bin/aarch64-oe-linux/aarch64-oe-linux-strip"
      if [ ! -x "$STRIP_TOOL" ]; then
        STRIP_TOOL="cp"
      fi
    fi

    echo $STRIP_TOOL

    # strip debug symbols
    for module in ${MODULE_LIST}; do
      if [ "${STRIP_TOOL}" = "cp" ]; then
        cp ${S}/unstripped/${module} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/${module}
      else
        ${STRIP_TOOL} --strip-debug ${S}/unstripped/${module} -o ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/${module}
      fi
    done
}

do_install:append:sun() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_install:append:kera() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_install:append:kalama() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_install:append:qcm2290-mtp() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_install:append:qcm4325-mtp() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sysconfdir}/initscripts
    install -m 0644 ${WORKDIR}/bt_dlkm.service -D ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -s ../bt_dlkm.service bt_dlkm.service
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    install -m 0755 ${S}/unstripped/*.ko ${DEPLOYDIR}/kernel_modules
}

addtask do_deploy after do_install

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${systemd_unitdir}/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${base_libdir}/modules/*"
