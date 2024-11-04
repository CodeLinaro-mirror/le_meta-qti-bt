include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=bt-dlkm
PKG_VERSION:=1.0
PKG_RELEASE:=${AUTORELEASE}
PKG_LICENSE:=GPLv2

include $(INCLUDE_DIR)/package.mk

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/bt-kernel

define KernelPackage/${PKG_NAME}
	SECTION:=QTI BT OPENSOURCE
	CATEGORY:=External kernel modules
	TITLE:=${PKG_NAME}
endef

define KernelPackage/${PKG_NAME}/Description
	QTI Bluetooth Kernel Modules
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
	rm -f ${PKG_BUILD_DIR}/BUILD.bazel
endef

PKG_EXTRA_KCONFIG:= \
    CONFIG_MSM_BT_POWER=m \
    KO_DIRS=pwr/btpower.ko \

define Build/Compile
	$(MAKE) -C ${LINUX_DIR} M="$(PKG_BUILD_DIR)" \
	$(PKG_EXTRA_KCONFIG) \
	modules
endef

define KernelPackage/${PKG_NAME}/install
	$(INSTALL_DIR) $(1)/lib/modules/$(UNAME_VERSION)/extra
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/pwr/btpower.ko $(1)/lib/modules/$(UNAME_VERSION)/extra/
	$(INSTALL_DIR) $(1)/lib/modules/bt
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/pwr/btpower.ko $(1)/lib/modules/bt/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts
	$(INSTALL_BIN) ./files/bt-dlkm.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_bt_le.sh $(1)/etc/initscripts
endef

$(eval $(call KernelPackage,${PKG_NAME}))

