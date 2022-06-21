SUMMARY = "Package group to bring in BT releated packages for LE system"

LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-bluetooth \
'

RDEPENDS_${PN} = "bt-dlkm"
RDEPENDS_${PN} += "bt-property"
RDEPENDS_${PN} += "bt-app"
RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'libbt-vendor', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'fluoride', '', d)}"
#RDEPENDS_${PN} += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'gst-bt-app', '', d)}"
