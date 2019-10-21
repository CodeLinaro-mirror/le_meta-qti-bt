SUMMARY = "QTI Bluetooth opensource package groups"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
PROVIDES = "${PACKAGES}"

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