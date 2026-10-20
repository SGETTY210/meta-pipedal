SUMMARY = "Lightweight C library for storing RDF data in memory"
HOMEPAGE = "https://drobilla.net/software/sord.html"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=b7586d531dd1cbd83e2cba36c6046ba6"

DEPENDS = "serd zix"

SRC_URI = "gitsm://github.com/drobilla/sord.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "5ab719fdae9b6db1204974f749dc64e62d2029af"
PV = "0.16.22"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled -Dtools=disabled"
