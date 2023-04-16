SUMMARY = "Package group to bring in BT releated packages for LE system"

LICENSE = "BSD-3-Clause-Clear"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-bluetooth \
'

RDEPENDS_${PN} = "\
    bthost-ipc \
    bt-app \
    bt-cert \
    bt-property \
    fluoride \
    bluetooth-le \
"
