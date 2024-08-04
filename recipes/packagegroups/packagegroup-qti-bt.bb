SUMMARY = "QTI open source package group for Bluetooth"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += " \
    ${@bb.utils.contains("MACHINE_FEATURES", "qti-bluetooth", bb.utils.contains('PREFERRED_VERSION_linux-msm', '5.15', 'btpower-dlkm', 'bt-dlkm', d), "", d)} \
    "
