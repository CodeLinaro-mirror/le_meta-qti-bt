# BT Oopen source Packages
IMAGE_INSTALL += "bt-property"
IMAGE_INSTALL += "bt-app"
IMAGE_INSTALL += "fluoride"
#IMAGE_INSTALL += "start-scripts-btproperty"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-bthost-ipc', 'bthost-ipc', d)}"
#IMAGE_INSTALL += "libbt-vendor"
IMAGE_INSTALL += "bt-cert"
