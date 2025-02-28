SUMMARY = "QTI open source package group for Bluetooth"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += " \
    btpower-dlkm \
    "
