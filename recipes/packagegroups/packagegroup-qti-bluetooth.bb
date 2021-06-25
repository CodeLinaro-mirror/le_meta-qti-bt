SUMMARY = "Package group to bring in BT releated packages for LE system"

LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-bluetooth \
'

BTVENDOR ?= 'False'
BTVENDOR_qrbx210-rbx = 'True'

RDEPENDS_${PN} = "\
    bthost-ipc \
    bt-app \
    ${@oe.utils.conditional('BTVENDOR', 'True', 'libbt-vendor', '', d)} \
    bt-cert \
    bt-property \
    fluoride \
"
