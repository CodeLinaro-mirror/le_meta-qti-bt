QTIBT_SECTION := QTI BT OPENSOURCE
CATEGORY_BIN_DESC := Utilities
CATEGORY_LIB_DESC := Libraries
CATEGORY_DEV_DESC := Development

TARGET_CPPFLAGS += -DUSE_MUSL -DOWRT_BUILD
MAKE_VARS += WORKSPACE=${TOPDIR}/src

ifneq (${VARIANT},perf)
#TARGET_CFLAGS += -g
endif

ifeq ($(TARGET_MACHINE),sdx65)
    BASEMACHINE :=sdxlemur
else ifeq ($(TARGET_MACHINE),sdx35)
    BASEMACHINE :=
else ifeq ($(TARGET_MACHINE),sdx75)
    BASEMACHINE :=sdx75
else
    $(warning Unknow TARGET_MACHINE "$(TARGET_MACHINE)", Use sdx75 by default.)
    BASEMACHINE :=sdx75
endif
