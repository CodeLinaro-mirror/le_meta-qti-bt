inherit autotools qcommon qlicense pkgconfig

DESCRIPTION = "Build Google modp_b64"
PR = "r0"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI = "git://source.codeaurora.org/quic/la/platform/external/modp_b64;protocol=https;nobranch=1;rev=2df9f7bc7b980f78e0b367123fcbe1ae94077859;destsuffix=modp_b64"
SRC_URI += "file://0001-Add-Support-to-build-libmodpb64.patch"

S = "${WORKDIR}/modp_b64"

#PARALLEL_MAKE = ""
