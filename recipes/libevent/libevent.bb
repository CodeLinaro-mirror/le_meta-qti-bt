inherit autotools qcommon qlicense pkgconfig qprebuilt

DESCRIPTION = "Build Google libevent"
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
