SUMMARY = "Lightweight C library of portability wrappers and data structures"
HOMEPAGE = "https://drobilla.net/software/zix.html"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=c17d0f531d833bea1a6c6823ba18c242"

SRC_URI = "gitsm://github.com/drobilla/zix.git;protocol=https;branch=main"

# Pin to a release tag's commit 
SRCREV = "96163e9e071548df1bc2fb3b373c573246e9c5f7"
PV = "0.8.2"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit meson pkgconfig

EXTRA_OEMESON = "-Ddocs=disabled -Dtests=disabled -Dbenchmarks=disabled"
