#!/bin/sh
f=/var/pipedal/config/service.conf
[ -e "$f" ] && exit 0
install -d -m 2775 -o pipedal_d -g pipedal_d /var/pipedal/config
printf 'uuid=%s\ndeviceName=PiPedal\nserver_port=8080\n' \
    "$(cat /proc/sys/kernel/random/uuid)" > "$f"
chown pipedal_d:pipedal_d "$f"
chmod 664 "$f"
