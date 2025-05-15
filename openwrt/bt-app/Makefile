include $(TOPDIR)/rules.mk

PKG_NAME:=bt-app
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=BSD-3-Clause
PKG_BUILD_DEPENDS:=btobex bt-ext

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/qcom-opensource/bt/bt-app
CONFIGURE_ARGS += \
    --with-glib \
    --with-btobex \
    --with-gengatt \
    --enable-target=${BASEMACHINE}

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_BIN_DESC}
	TITLE:=${PKG_NAME}
	USERID:=root:radio=1001 qbt=2888:radio=1001
	DEPENDS:=+btvendorhal +glib2 +libchrome +fluoride +libhardware
endef

define Package/$(PKG_NAME)/Description
	Bluetooth application layer
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/*.a $(1)/usr/lib/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/etc/bluetooth
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_CONF) $(PKG_INSTALL_DIR)/etc/bluetooth/* $(1)/etc/bluetooth/
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/bin/* $(1)/usr/bin/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
