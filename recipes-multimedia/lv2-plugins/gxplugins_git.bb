SUMMARY = "GxPlugins LV2 guitar amps and pedals (Guitarix project, rerdavies fork)"
HOMEPAGE = "https://github.com/rerdavies/GxPlugins.lv2"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

SRC_URI = "gitsm://github.com/rerdavies/GxPlugins.lv2.git;protocol=https;branch=master"
SRCREV = "3a325270b99a41af5ca160e50278bd5469b697e8"
PV = "1.0+git"

# From the README: libc6-dev, libcairo2-dev, libx11-dev, x11proto-dev, lv2-dev
DEPENDS = "lv2 cairo libx11 xorgproto"

inherit pkgconfig

EXTRA_OEMAKE = "CC='${CC}' CXX='${CXX}' LD='${LD}' AR='${AR}'"

do_compile:prepend() {
    find ${S} \( -name Makefile -o -name '*.mk' \) -exec sed -i \
        -e 's/-msse[0-9.]*//g' \
        -e 's/-mfpmath=sse//g' \
        -e 's/-march=native//g' {} +
}

do_compile() {
    oe_runmake
}

do_install() {
    for d in ${S}/Gx*.lv2; do
        oe_runmake -C $d install DESTDIR=${D} INSTALL_DIR=${libdir}/lv2
    done
}

FILES:${PN} += "${libdir}/lv2"
INSANE_SKIP:${PN} += "already-stripped ldflags"
