inherit autotools qlicense pkgconfig

DESCRIPTION = "Build BT HOST IPC"
PR = "r0"

DEPENDS = "liblog glib-2.0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/bluetooth/bthost_ipc/"

S = "${WORKDIR}/vendor/qcom/opensource/bluetooth/bthost_ipc/"

CPPFLAGS_append = " -DUSE_ANDROID_LOGGING -DUSE_LIBHW_AOSP"

LDFLAGS_append = " -llog "

EXTRA_OECONF = "--with-glib"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""