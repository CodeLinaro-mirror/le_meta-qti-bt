include $(TOPDIR)/rules.mk

PKG_NAME:=bt-property
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/qcom-opensource/bt/property-ops
CONFIGURE_ARGS += --with-glib

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_BIN_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+glib2
endef

define Package/$(PKG_NAME)/Description
	Bluetooth Property Daemon
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/bin/* $(1)/usr/bin/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
