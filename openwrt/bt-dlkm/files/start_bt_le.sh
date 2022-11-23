#! /bin/sh

#Copyright (c) 2022 Qualcomm Innovation Center, Inc. All rights reserved.
#SPDX-License-Identifier: BSD-3-Clause-Clear

set -e

BLUETOOTH_KERNEL_MODULE_DIR=/lib/modules/$(uname -r)/kernel/drivers/bluetooth
BLUETOOTH_EXTRA_MODULE_DIR=/lib/modules/$(uname -r)/extra
BLUETOOTH_ALT_MODULE_DIR=/lib/modules/bt

case "$1" in
    start)
	echo "Starting/Loading bluetooth modules"

	if [ -e /sys/bus/platform/drivers/bt_power ]; then
		echo "btpower.ko has already been built-in or loaded"
	elif [ -e ${BLUETOOTH_EXTRA_MODULE_DIR}/btpower.ko ]; then
		insmod ${BLUETOOTH_EXTRA_MODULE_DIR}/btpower.ko
		echo "insmod extra btpower.ko Done"
	elif [ -e ${BLUETOOTH_KERNEL_MODULE_DIR}/btpower.ko ]; then
		insmod ${BLUETOOTH_KERNEL_MODULE_DIR}/btpower.ko
		echo "insmod kernel btpower.ko Done"
	elif [ -e ${BLUETOOTH_ALT_MODULE_DIR}/btpower.ko ]; then
		insmod ${BLUETOOTH_ALT_MODULE_DIR}/btpower.ko
		echo "insmod alt btpower.ko Done"
	else
		echo "$0 ERROR: btpower.ko is missing"
		exit 1
	fi

	echo "done loading bluetooth module"
	;;
    stop|reload|force-reload)
	echo "Bypassing $1 of bluetooth modules"
	;;
    restart)
	$0 stop
	$0 start
	;;
    *)
	echo "Usage: $0 { start | stop | restart}"
	exit 1
	;;
esac &>/dev/kmsg

exit 0
