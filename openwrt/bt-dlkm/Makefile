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
endef

PKG_EXTRA_KCONFIG:= \
    CONFIG_MSM_BT_POWER=m \

define Build/Compile
	$(KERNEL_MAKE) M="$(PKG_BUILD_DIR)" \
	CC=$(KERNEL_CC) \
	$(PKG_EXTRA_KCONFIG) \
	KERNEL_SRC=$(LINUX_DIR) \
	modules
endef

define KernelPackage/${PKG_NAME}/install
	{ \
		if [ ! -e ${PKG_BUILD_DIR}/pwr/btpower.ko ]; then \
			echo btpower.ko fails to be built; \
			exit 1; \
		fi; \
		if which modinfo &>/dev/null; then \
			MY_KERNEL_RELEASE=$$$$$$$$(modinfo -F vermagic ${PKG_BUILD_DIR}/pwr/btpower.ko | awk '{print $$$$$$$$1}'); \
			MY_MOD_INSTALL_DIR=$(1)/lib/modules/$$$$$$$${MY_KERNEL_RELEASE}/extra; \
		elif which grep &>/dev/null; then \
			MY_KERNEL_RELEASE=$$$$$$$$(grep -a -o 'vermagic=[^[:space:]]\+' $(PKG_BUILD_DIR)/pwr/btpower.ko 2>/dev/null | \
					awk -F= '{print $$$$$$$$2}'); \
			MY_MOD_INSTALL_DIR=$(1)/lib/modules/$$$$$$$${MY_KERNEL_RELEASE}/extra; \
		else \
			MY_MOD_INSTALL_DIR=$(1)/lib/modules/bt; \
		fi; \
		echo MY_MOD_INSTALL_DIR=$$$$$$$${MY_MOD_INSTALL_DIR}; \
		$(INSTALL_DIR) $$$$$$$${MY_MOD_INSTALL_DIR}; \
		$(INSTALL_BIN) $(PKG_BUILD_DIR)/pwr/btpower.ko $$$$$$$${MY_MOD_INSTALL_DIR}/; \
	}
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts
	$(INSTALL_BIN) ./files/bt-dlkm.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_bt_le.sh $(1)/etc/initscripts
endef

$(eval $(call KernelPackage,${PKG_NAME}))

