include $(TOPDIR)/rules.mk

PKG_NAME:=fluoride
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0 & BSD-3-Clause
PKG_BUILD_DEPENDS:=audio-utils bt-ext

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/system/bt
LOCAL_SRC_EXT ?= $(TOPDIR)/src/vendor/qcom/opensource/bluetooth_ext/system_bt_ext

CONFIGURE_PATH:=system/bt
MAKE_PATH:=system/bt
CONFIGURE_ARGS += \
                  --disable-silent-rules \
                  --disable-dependency-tracking
CONFIGURE_ARGS += \
                  --with-zlib \
                  --enable-static=yes \
                  --enable-target=${BASEMACHINE} \
                  --enable-rome=none

TARGET_CPPFLAGS += -Dtypeof=__typeof__ -D_FORTIFY_SOURCE=2
TARGET_CFLAGS += -Wa,--noexecstack -fvisibility=hidden

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_LIB_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+zlib +btvendorhal +libchrome +bttransport +libutils +libcutils +libhardware
endef

define Package/$(PKG_NAME)/Description
	Bluetooth Fluoride Stack
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/system/bt
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/system/bt/
	mkdir -p $(PKG_BUILD_DIR)/bluetooth_ext/system_bt_ext
	$(CP) $(LOCAL_SRC_EXT)/* $(PKG_BUILD_DIR)/bluetooth_ext/system_bt_ext/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib/pkgconfig
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbluetoothdefault.{a,so*} $(1)/usr/lib/
	cd $(1)/usr/lib/ && $(LN) libbluetoothdefault.so.0 bluetooth.default.so
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libaudioa2dpdefault.{a,so*} $(1)/usr/lib/
	cd $(1)/usr/lib/ && $(LN) libaudioa2dpdefault.so.0 audio.a2dp.default.so
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbttypes.a $(1)/usr/lib/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/pkgconfig/*.pc $(1)/usr/lib/pkgconfig/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/etc/bluetooth
	$(INSTALL_DIR) $(1)/usr/lib
	$(INSTALL_CONF) $(PKG_INSTALL_DIR)/etc/bluetooth/* $(1)/etc/bluetooth/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbluetoothdefault.so* $(1)/usr/lib/
	cd $(1)/usr/lib/ && $(LN) libbluetoothdefault.so.0 bluetooth.default.so
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libaudioa2dpdefault.so* $(1)/usr/lib/
	cd $(1)/usr/lib/ && $(LN) libaudioa2dpdefault.so.0 audio.a2dp.default.so
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
