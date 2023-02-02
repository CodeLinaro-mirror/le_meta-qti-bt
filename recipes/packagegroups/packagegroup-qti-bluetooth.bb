SUMMARY = "Package group to bring in BT releated packages for LE system"

LICENSE = "BSD-3-Clause"

PACKAGE_ARCH = "${TUNE_PKGARCH}"
inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-bluetooth \
'

BTVENDOR ?= 'False'
BTVENDOR:qrbx210-rbx = 'True'

RDEPENDS:${PN} = "\
    bthost-ipc \
    bt-app \
    bt-cert \
    bt-property \
    fluoride \
    bt-dlkm-kernel \
"
