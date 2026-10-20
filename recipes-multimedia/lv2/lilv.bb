SUMMARY = "C library for using LV2 plugins"
HOMEPAGE = "http://drobilla.net/software/lilv"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=b7586d531dd1cbd83e2cba36c6046ba6"

DEPENDS = "lv2 serd sord sratom zix"

SRC_URI = "gitsm://github.com/lv2/lilv.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "2feec6a15d3300db3ac0337bafb863308a03cab6"
PV = "0.28.0"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled -Dtools=disabled \
                 -Dbindings_py=disabled"

