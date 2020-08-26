SUMMARY = "BLUETOOTH open source package groups"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-bt \
'

RDEPENDS_${PN} = "bt-dlkm"
RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'libbt-vendor', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'fluoride', '', d)}"
RDEPENDS_${PN} += "bt-property"
RDEPENDS_${PN} += "bt-app"
#RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'gst-bt-app', '', d)}"
