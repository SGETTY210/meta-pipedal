SUMMARY = "Extra NAM models for PiPedal"
LICENSE = "CLOSED"
SRC_URI = "file://models"
S = "${UNPACKDIR}"

do_install() {
    install -d -m 2775 ${D}/var/pipedal/audio_uploads/NeuralAmpModels/Custom
    cp -r ${UNPACKDIR}/models/. ${D}/var/pipedal/audio_uploads/NeuralAmpModels/Custom/
    find ${D}/var/pipedal/audio_uploads/NeuralAmpModels/Custom -type d -exec chmod 2775 {} +
    find ${D}/var/pipedal/audio_uploads/NeuralAmpModels/Custom -type f -exec chmod 0664 {} +
}

FILES:${PN} = "/var/pipedal/audio_uploads/NeuralAmpModels/Custom"
RDEPENDS:${PN} = "pipedal"
