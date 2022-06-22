# BT Oopen source Packages
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'bt-property', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'bt-app', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'fluoride', '', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('BBFILE_COLLECTIONS', 'qti-bt-prop', 'libbt-vendor', '', d)}"
#IMAGE_INSTALL += "gst-bt-app"
IMAGE_INSTALL += "bt-dlkm"
