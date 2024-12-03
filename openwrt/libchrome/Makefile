include $(TOPDIR)/rules.mk

PKG_NAME:=libchrome
PKG_VERSION:=3.0
PKG_RELEASE:=${AUTORELEASE}
PKG_FIXUP:= autoreconf
PKG_INSTALL:=1
PKG_LICENSE:=Apache-2.0

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL=https://git.codelinaro.org/clo/la/platform/external/libchrome.git
PKG_SOURCE_VERSION:=b4b96cdfd447daac679b067c3b969cc5ed22a798
PKG_MIRROR_HASH:=b01496a3daf2f26ac140f38665d89266bf0d5f2d6efec6de663e2e04d9bec806

include $(INCLUDE_DIR)/package.mk

ifneq (${VARIANT},perf)
    TARGET_CPPFLAGS += -g
endif
TARGET_CPPFLAGS += -DUSE_MUSL -D__UCLIBC__ -DOWRT_BUILD
TARGET_LDFLAGS += -llog
MAKE_VARS += WORKSPACE=${TOPDIR}/src

define Package/$(PKG_NAME)
	SECTION:=libs
	CATEGORY:=Libraries
	TITLE:=${PKG_NAME}
	DEPENDS:=+libexecinfo +libevent2 +libmodpb64 +gtest +liblog
endef

define Package/$(PKG_NAME)/Description
	Build Google libchrome
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include
	$(INSTALL_DIR) $(1)/usr/lib/pkgconfig
	$(CP) $(PKG_INSTALL_DIR)/usr/include/* $(1)/usr/include/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libchrome.{a,so*} $(1)/usr/lib/
	$(CP) ${PKG_BUILD_DIR}/*.pc $(1)/usr/lib/pkgconfig/
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/libchrome.so* $(1)/usr/lib/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
