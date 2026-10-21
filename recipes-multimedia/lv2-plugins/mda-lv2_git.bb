SUMMARY = "MDA plugins ported to LV2"
HOMEPAGE = "https://drobilla.net/software/mda-lv2.html"
LICENSE = "0BSD & GPL-2.0-or-later & MIT"
LIC_FILES_CHKSUM = "file://LICENSES/0BSD.txt;md5=2f64463e343c207db61b3b2a18d50d7a \
                    file://LICENSES/GPL-2.0-or-later.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://LICENSES/MIT.txt;md5=995598bc9de2b4c987c2cb87fc24f341"

SRC_URI = "git://gitlab.com/drobilla/mda-lv2.git;protocol=https;branch=main"
SRCREV = "fb85abef3e5400cc8b93fe79b727e8baef9f685c"
PV = "1.2.12+git"

DEPENDS = "lv2"

inherit meson pkgconfig

FILES:${PN} += "${libdir}/lv2"
