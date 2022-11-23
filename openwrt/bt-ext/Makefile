include $(TOPDIR)/rules.mk

PKG_NAME:=bt-ext
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/bluetooth_ext

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_DEV_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+libchrome
endef

define Package/$(PKG_NAME)/Description
	Bluetooth application layer
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib/pkgconfig
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/pkgconfig/*.pc $(1)/usr/lib/pkgconfig/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
