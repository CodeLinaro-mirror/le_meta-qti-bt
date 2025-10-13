inherit autotools pkgconfig

DESCRIPTION = "Build BT HOST IPC"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
DEPENDS = "libchrome glib-2.0 fluoride"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bluetooth/bt_audio/"

S = "${WORKDIR}/bluetooth/bt_audio/bthost_ipc"
EXTRA_OEMAKE += 'BT_INC_PATH=${STAGING_INCDIR}/bluetooth'
EXTRA_OECONF = "--with-glib"
CPPFLAGS:append = " -DUSE_ANDROID_LOGGING"

SOLIBS = ".so"
PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} = "dev-so"
FILES:${PN} += "/usr/lib/libbthost_if.so.* /usr/lib/libbthost_if_sink.so.* /usr/lib/libbthost_if*.so"
do_install:append() {
        cd  ${D}/${libdir}/ && ln -sf libbthost_if.so.1.0.0 libbthost_if.so
        cd  ${D}/${libdir}/ && ln -sf libbthost_if_sink.so.1.0.0 libbthost_if_sink.so
}