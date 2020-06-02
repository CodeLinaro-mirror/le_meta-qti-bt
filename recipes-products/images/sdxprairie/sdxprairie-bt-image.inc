# BT Oopen source Packages
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'bt-property', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'bt-app', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'fluoride', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'bt-prop', 'libbt-vendor', '', d)}"
#IMAGE_INSTALL += "gst-bt-app"
IMAGE_INSTALL += "bt-dlkm"
