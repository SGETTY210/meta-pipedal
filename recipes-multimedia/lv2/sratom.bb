SUMMARY = "Library for serializing LV2 atoms to and from RDF"
HOMEPAGE = "https://drobilla.net/software/sratom.html"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=2f1561398541c1c258ee27165a6e91d1"

DEPENDS = "lv2 serd sord"

SRC_URI = "gitsm://github.com/lv2/sratom.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "68bd465f0de009d58ae39e89eb029c728e74751f"
PV = "0.6.22"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled"
