inherit autotools-brokensep pkgconfig qprebuilt

DESCRIPTION = "Build Google libevent"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI = "git://source.codeaurora.org/quic/la/platform/external/libevent;protocol=https;nobranch=1;rev=6b11d747012c72746e485a24b68e65e28b8e015f;destsuffix=libevent"
SRC_URI += "file://0001-Delete-default-Makefile.patch"
SRC_URI += "file://0001-Add-GNU-Autotool-Build.patch"

S = "${WORKDIR}/libevent"

CPPFLAGS_append = " ${@bb.utils.contains('VARIANT', 'debug', '-g', '', d)}"

#EXTRA_OECONF += "--with-extra-includes"
#PARALLEL_MAKE = ""

#do_install[noexec]="1"
