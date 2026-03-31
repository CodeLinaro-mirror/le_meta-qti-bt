inherit autotools-brokensep

DESCRIPTION = "Header-only recipe for bt_audio"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt_audio/"

S = "${WORKDIR}/bluetooth/bt_audio"
AUTOTOOLS_SCRIPT_PATH = "${S}"

do_compile[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/bt_audio
    cd ${S} && find ./ -name '*.h' | tar czf ${D}${includedir}/bt_audio/bt_audio.tgz -T -
    tar zxvf ${D}${includedir}/bt_audio/bt_audio.tgz -C ${D}${includedir}/bt_audio
    rm -f ${D}${includedir}/bt_audio/bt_audio.tgz
}
