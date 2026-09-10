inherit linux-kernel-base deploy module

DESCRIPTION = "QTI BT drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "pebble"

DEPENDS += "virtual/kernel virtual/kernel-toolchain-native linux-msm-headers kernel-module-wlan-platform btdevicetree kernel-module-audiodlkm audiodlkm-headers"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt-kernel/"
SRC_URI += "file://bt_dlkm"
SRC_URI += "file://bt_dlkm.service"

S = "${WORKDIR}/bluetooth/bt-kernel"
B = "${S}"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"

MODULE_LIST = "btpower.ko btfmcodec.ko bt_fm_swr.ko "

# ============ Build parameters ============
EXTRA_OEMAKE:append = " M=${S}"
EXTRA_OEMAKE:append = " TARGET_SUPPORT=${BASEMACHINE}"
EXTRA_OEMAKE:append = " KCPPFLAGS='-I${STAGING_INCDIR} -I${STAGING_INCDIR}/soc-repo -I${STAGING_INCDIR}/soc-repo/uapi'"
EXTRA_OEMAKE:append = " CONFIG_MSM_BT_POWER=m CONFIG_BTFM_CODEC=m CONFIG_BTFM_SWR=m"

# ============ Toolchain configuration ============
KERNEL_CC = "${STAGING_BINDIR_NATIVE}/clang/bin/clang \
             -target ${TARGET_ARCH}${TARGET_VENDOR}-${TARGET_OS}"
KERNEL_LD = "${STAGING_BINDIR_NATIVE}/clang/bin/ld.lld"
KERNEL_AR = "${STAGING_BINDIR_NATIVE}/clang/bin/llvm-ar"
KERNEL_OBJCOPY = "${STAGING_BINDIR_NATIVE}/clang/bin/llvm-objcopy"
KERNEL_STRIP = "${STAGING_BINDIR_NATIVE}/clang/bin/llvm-strip"

MAKE_TARGETS = "modules"

# ============ Compile-task flags ============
do_compile[depends] += "virtual/kernel:do_shared_workdir"

do_install() {
    install -d ${S}/unstripped
    find "${S}" -name '*.ko' -exec install -m 0755 -D {} "${S}/unstripped"/ \;

    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}

    STRIP_TOOL="${STAGING_DIR_NATIVE}/usr/bin/aarch64-oe-linux/aarch64-oe-linux-strip"
    if [ ! -x "$STRIP_TOOL" ]; then
        STRIP_TOOL="cp"
    fi

    for module in ${MODULE_LIST}; do
        if [ "${STRIP_TOOL}" = "cp" ]; then
            cp ${S}/unstripped/${module} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/${module}
        else
            ${STRIP_TOOL} --strip-debug ${S}/unstripped/${module} -o ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/${module}
        fi
    done

    install -d ${D}${sbindir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -m 755 ${WORKDIR}/bt_dlkm ${D}${sbindir}/initscripts/bt_dlkm
    install -m 0644 ${WORKDIR}/bt_dlkm.service ${D}${systemd_unitdir}/system/bt_dlkm.service

    sed -i 's|^SourcePath=/etc|SourcePath=/usr/sbin|' ${D}${systemd_unitdir}/system/bt_dlkm.service
    sed -i 's|^ExecStart=/etc|ExecStart=/usr/sbin|' ${D}${systemd_unitdir}/system/bt_dlkm.service
    sed -i 's|^ExecStop=/etc|ExecStop=/usr/sbin|' ${D}${systemd_unitdir}/system/bt_dlkm.service
    cd ${D}${systemd_unitdir}/system/multi-user.target.wants/ && ln -snf ../bt_dlkm.service bt_dlkm.service
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    install -m 0755 ${S}/unstripped/*.ko ${DEPLOYDIR}/kernel_modules
}

addtask do_deploy after do_install

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${sbindir}/initscripts/*"
FILES:${PN} += "${systemd_unitdir}/*"