include $(TOPDIR)/rules.mk

PKG_NAME:=btobex
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=BSD

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/qcom-opensource/bt/obex_profiles
CONFIGURE_ARGS += --with-glib

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_DEV_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+glib2 +btvendorhal
endef

define Package/$(PKG_NAME)/Description
	Bluetooth OBEX
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib/pkgconfig
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbtobex.a $(1)/usr/lib/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/pkgconfig/*.pc $(1)/usr/lib/pkgconfig/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
