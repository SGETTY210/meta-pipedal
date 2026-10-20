SUMMARY = "PiPedal guitar effects pedal with web UI"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=107c9274bf86a01a994d271bfb2f9685"

SRC_URI = "gitsm://github.com/rerdavies/pipedal.git;protocol=https;branch=main;name=pipedal \
    git://github.com/ArthurSonzogni/ftxui;protocol=https;branch=main;name=ftxui;destsuffix=ftxui"

SRCREV_pipedal = "787c45014c2501c00ae49a96bc792cfd3abb88a7"
SRCREV_ftxui = "cdf28903a7781f97ba94d30b79c3a4b0c97ccce7"
SRCREV_FORMAT = "pipedal_ftxui"

PV = "2.0.110"
PV_ftxui = "5.0.0"

inherit cmake pkgconfig systemd useradd

DEPENDS = "lilv lv2 boost alsa-lib systemd util-linux avahi \
           networkmanager icu sdbus-c++ libzip gperftools \
           pipewire bzip2 openssl librsvg nodejs-native"

EXTRA_OECMAKE = "-DPIPEDAL_DISABLE_COPYRIGHT_BUILD=ON -DPIPEDAL_EXCLUDE_TESTS=ON -DFETCHCONTENT_SOURCE_DIR_FTXUI=${UNPACKDIR}/ftxui"
#EXTRA_OECMAKE += "-DCMAKE_CXX_FLAGS='${CXXFLAGS} -Wno-error=unused-result -Wno-error=deprecated-declarations'"
EXTRA_OECMAKE += "-DCMAKE_INCLUDE_CURRENT_DIR=ON"

# PROTOTYPE ONLY: lets the React/npm step reach the network.
do_compile[network] = "1"
DEPENDS += " nodejs-native"

do_compile:prepend() {
    export HOME="${WORKDIR}/npm-home"
    mkdir -p "${HOME}"
    cd ${S}/vite
    npm install
    cd ${B}
}

USERADD_PACKAGES = "${PN}"

USERADD_PARAM:${PN} = "--system --no-create-home \
    --home-dir /var/pipedal --shell /usr/sbin/nologin \
    --groups audio -g pipedal pipedal"

GROUPADD_PARAM:${PN} = "--system pipedal"

FILES:${PN} += "${libdir}/lv2/ToobAmp.lv2"
INSANE_SKIP:${PN} += "already-stripped"
DEBUG_PREFIX_MAP:append = " -ffile-prefix-map=${UNPACKDIR}/ftxui=/usr/src/debug/${PN}/${PV}/ftxui"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/src/pipedalProfilePlugin ${D}${bindir}/pipedalProfilePlugin
}
