SUMMARY = "LV2 plugin standard headers and specifications"
HOMEPAGE = "https://lv2plug.in"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=b1b33d6fcf7d70cf1512e3c481768a9c"

SRC_URI = "gitsm://github.com/lv2/lv2.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "0bcde338db1c63bbc503b4d1f6d7b55ed43154af"
PV = "1.18.10"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled -Dplugins=disabled"

FILES:${PN} += "${libdir}/lv2"
