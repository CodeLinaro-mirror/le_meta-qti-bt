SUMMARY = "QTI Bluetooth opensource package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

PACKAGES = ' \
    packagegroup-qti-bluetooth \
    ${@bb.utils.contains("COMBINED_FEATURES", "qti-bluetooth", "packagegroup-qti-bluetooth-opensource", "", d)} \
    '
RDEPENDS_packagegroup-qti-bluetooth = ' \
    ${@bb.utils.contains("COMBINED_FEATURES", "qti-bluetooth", "packagegroup-qti-bluetooth-opensource", "", d)} \
    '
RDEPENDS_packagegroup-qti-bluetooth-opensource = ' \
    bt-app \
    bt-property \
    fluoride \
    libbt-vendor \
    '