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
BTVENDOR:qcs40x = 'True'
BTVENDOR:qcm6490 = 'True'

RDEPENDS:${PN} = "\
    fluoride \
    btvendorhal \
    libchrome \
    bt-app \
    bt-dlkm-kernel " 
