inherit autotools qcommon qlicense pkgconfig

DESCRIPTION = "Build Google libchrome"
PR = "r0"
DEPENDS = "libevent libmodpb64 gtest"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI = "git://source.codeaurora.org/quic/la/platform/external/libchrome;protocol=https;nobranch=1;rev=b4b96cdfd447daac679b067c3b969cc5ed22a798;destsuffix=libchrome"
SRC_URI += "file://0001-Add-Support-to-build-libchrome.patch"

S = "${WORKDIR}/libchrome"
CPPFLAGS_append = " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"
#PARALLEL_MAKE = ""

#do_compile[noexec]="1"
#do_install[noexec]="1"
