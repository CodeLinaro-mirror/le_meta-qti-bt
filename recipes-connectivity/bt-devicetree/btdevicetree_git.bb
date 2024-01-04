DESCRIPTION = "QCOM BT devicetree"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

inherit module deploy

FILESPATH =+ "${WORKSPACE}/bluetooth/:"
SRC_URI = "file://bt-devicetree/"

BT_SOURCE = "${WORKDIR}"
S = "${BT_SOURCE}/bt-devicetree"
S_EXT = "${BT_SOURCE}/stack/bluetooth_ext/system_bt_ext"

DEPENDS += "virtual/kernel"

DTC := "${KBUILD_OUTPUT}/scripts/dtc/dtc"
KERNEL_INCLUDE := "${STAGING_KERNEL_DIR}/include/"
EXTRA_OEMAKE += "DTC='${DTC}' KERNEL_INCLUDE='${KERNEL_INCLUDE}'"

do_compile() {
    oe_runmake ${EXTRA_OEMAKE} qcm6490-bt
}

do_install() {
    :
}

do_deploy() {
    echo "DTBO Staging path -> " ${DEPLOYDIR}/tech_dtbs
    install -d ${DEPLOYDIR}/tech_dtbs
    install -m 0644 ${S}/*.dtbo ${DEPLOYDIR}/tech_dtbs
}

addtask do_deploy after do_install

