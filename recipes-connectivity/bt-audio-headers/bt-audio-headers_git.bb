inherit autotools-brokensep

DESCRIPTION = "Header-only recipe for bt_audio"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

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
