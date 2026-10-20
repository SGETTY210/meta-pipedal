SUMMARY = "Lightweight C library for RDF syntax (Turtle, NTriples)"
HOMEPAGE = "https://drobilla.net/software/serd.html"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=558bb64a60a6e1db7037ca99f435ec2e"

SRC_URI = "gitsm://github.com/drobilla/serd.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "849b9ecd8d6c87da79f14e2630daa084002d7555"
PV = "0.32.10"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled -Dtools=disabled"
