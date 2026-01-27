inherit autotools-brokensep pkgconfig

DESCRIPTION = "Build Google modp_b64"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://git.codelinaro.org/clo/la/platform/external/modp_b64;protocol=ssh;nobranch=1;rev=2df9f7bc7b980f78e0b367123fcbe1ae94077859;destsuffix=modp_b64"
SRC_URI += "file://0001-Add-Support-to-build-libmodpb64.patch"

CXXFLAGS += "-I${S}/modp_64"

S = "${WORKDIR}/modp_b64"
