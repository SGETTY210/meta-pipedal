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

FILES:${PN} += "${libdir}/lv2/ToobAmp.lv2"
INSANE_SKIP:${PN} += "already-stripped"
DEBUG_PREFIX_MAP:append = " -ffile-prefix-map=${UNPACKDIR}/ftxui=/usr/src/debug/${PN}/${PV}/ftxui"

inherit useradd systemd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-f --system netdev; --system pipedal_d"
USERADD_PARAM:${PN} = "--system -N -g pipedal_d -G audio,netdev \
    --home-dir /var/pipedal/home --no-create-home \
    --shell /sbin/nologin pipedal_d"

SRC_URI += "file://pipedald.service file://pipedaladmind.service"
SRC_URI += "file://pipedal-init-config.sh file://pipedal-init-config.service"
SRC_URI += "file://defaults/AudioConfig.json" 
SRC_URI += "file://defaults/ChannelRouterSettings.json" 
SRC_URI += "file://defaults/MidiDevices.json" 
SRC_URI += "file://defaults/wifiConfig.json"

RDEPENDS:${PN} += "networkmanager polkit avahi-daemon"

do_install:append() {
    TEMPLATES=${D}${sysconfdir}/pipedal/config/templates

    # /var/pipedal tree, same ownership/mode that Install() creates
    install -d -m 2775 ${D}/var/pipedal ${D}/var/pipedal/home \
        ${D}/var/pipedal/config ${D}/var/pipedal/audio_uploads \
        ${D}/var/pipedal/web_temp
    install -m 0664 $TEMPLATES/var_config.json ${D}/var/pipedal/config/config.json
    chown -R pipedal_d:pipedal_d ${D}/var/pipedal

    # wpa_supplicant config for the hotspot
    install -d ${D}${sysconfdir}/pipedal/config/wpa_supplicant
    install -m 0660 $TEMPLATES/wpa_supplicant-pipedal.conf \
        ${D}${sysconfdir}/pipedal/config/wpa_supplicant/wpa_supplicant-pipedal.conf
    chgrp netdev ${D}${sysconfdir}/pipedal/config/wpa_supplicant/wpa_supplicant-pipedal.conf

    # realtime limits for the pipedal_d group (same content Install() writes)
    install -d ${D}${sysconfdir}/security/limits.d
    printf '@pipedal_d - rtprio 95\n@pipedal_d - nice -20\n@pipedal_d - memlock unlimited\n' \
        > ${D}${sysconfdir}/security/limits.d/90-pipedal.conf

    # systemd units
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/pipedald.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${UNPACKDIR}/pipedaladmind.service ${D}${systemd_system_unitdir}/

    # extra binary from the upstream install.sh
    install -m 0755 ${B}/src/pipedalProfilePlugin ${D}${bindir}/pipedalProfilePlugin

    install -d ${D}${libexecdir}
    install -m 0755 ${UNPACKDIR}/pipedal-init-config.sh ${D}${libexecdir}/pipedal-init-config.sh
    install -m 0644 ${UNPACKDIR}/pipedal-init-config.service ${D}${systemd_system_unitdir}/

    install -m 0664 ${UNPACKDIR}/defaults/AudioConfig.json ${D}/var/pipedal/AudioConfig.json
    install -m 0664 ${UNPACKDIR}/defaults/ChannelRouterSettings.json ${D}/var/pipedal/ChannelRouterSettings.json
    install -m 0664 ${UNPACKDIR}/defaults/MidiDevices.json ${D}/var/pipedal/MidiDevices.json
    install -m 0664 ${UNPACKDIR}/defaults/wifiConfig.json ${D}/var/pipedal/config/wifiConfig.json
}

SYSTEMD_SERVICE:${PN} = "pipedald.service pipedaladmind.service pipedal-init-config.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "/var/pipedal ${sysconfdir}/security/limits.d ${sysconfdir}/pipedal"
FILES:${PN} += "${libexecdir}/pipedal-init-config.sh"
