include $(TOPDIR)/rules.mk

PKG_NAME:=libmodpb64
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0
PKG_LICENSE_FILES:=LICENSE

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL=https://git.codelinaro.org/clo/la/platform/external/modp_b64.git
PKG_SOURCE_VERSION:=8354701edf5646b019e062a3572d9470da19e99e
PKG_MIRROR_HASH:=0cd09cf319387e49e38100acea104e5ec3d3d94ce95a9fd9d97064dfb9df7f44

include $(INCLUDE_DIR)/package.mk

TARGET_CXXFLAGS += -I${PKG_BUILD_DIR}/modp_b64
ifneq (${VARIANT},perf)
    TARGET_CPPFLAGS += -g
endif

define Package/$(PKG_NAME)
	SECTION:=libs
	CATEGORY:=Libraries
	TITLE:=${PKG_NAME}
	DEPENDS:=+libstdcpp
endef

define Package/$(PKG_NAME)/Description
	Build Google modp_b64
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib/pkgconfig
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libmodpb64.{a,so*} $(1)/usr/lib/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/pkgconfig/*.pc $(1)/usr/lib/pkgconfig/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libmodpb64.so* $(1)/usr/lib/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
