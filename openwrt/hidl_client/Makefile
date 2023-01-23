include $(TOPDIR)/rules.mk

PKG_NAME:=hidl-client
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0 & BSD-3-Clause

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/bluetooth/tools
CONFIGURE_ARGS += \
                  --with-zlib \
                  --with-glib \
                  --enable-static=yes \
                  --enable-wlan=yes \
                  --enable-bt=yes \
                  --enable-debug=yes \
                  --enable-target=${BASEMACHINE} \
                  --enable-rome=none

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_LIB_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+glib2 +liblog +bttransport +libcutils
endef

define Package/$(PKG_NAME)/Description
	FTM HIDL client
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libhidlclient.so* $(1)/usr/lib/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libhidlclient.so* $(1)/usr/lib/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
