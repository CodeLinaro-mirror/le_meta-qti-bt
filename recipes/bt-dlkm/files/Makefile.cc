KBDIR ?= /lib/modules/$(shell uname -r)/build
$(info KBDIR=${KBDIR})

obj-m += btpower.o

SRC := $(shell pwd)

all:
	$(MAKE) -C ${KBDIR} M=${SRC}

modules_install:
	$(MAKE) -C ${KBDIR} M=${SRC} modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
