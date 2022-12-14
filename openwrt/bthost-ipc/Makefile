include $(TOPDIR)/rules.mk

PKG_NAME:=bthost-ipc
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0
PKG_BUILD_DEPENDS:=media-headers

include $(INCLUDE_DIR)/package.mk
include ../qtibt-common.mk

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/bluetooth/bthost_ipc
CONFIGURE_ARGS += \
                  --with-glib \
                  --enable-target=${BASEMACHINE}
TARGET_CPPFLAGS += -Dtypeof=__typeof__

define Package/$(PKG_NAME)
	SECTION:=${QTIBT_SECTION}
	CATEGORY:=${CATEGORY_LIB_DESC}
	TITLE:=${PKG_NAME}
	DEPENDS:=+liblog +glib2 +fluoride +libhardware
endef

define Package/$(PKG_NAME)/Description
	Build BT HOST IPC
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbthost_if.{a,so*} $(1)/usr/lib/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbthost_if_sink.{a,so*} $(1)/usr/lib/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbthost_if.so* $(1)/usr/lib/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libbthost_if_sink.so* $(1)/usr/lib/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
