#!/usr/bin/env bash

set -euo pipefail

#
# Waits until a given needle in compose log occurs.
#

USAGE="$(basename "{0}") <service name> <compose file> <needle>"
NAME="${1:-}"
COMPOSE_FILE="${2:-}"
NEEDLE="${3:-}"

if [[ -z "${NAME}" ]]; then
    echo >&2 "No name as first argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

if [[ -z "${COMPOSE_FILE}" ]]; then
    echo >&2 "No compose file  as second argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

if [[ -z "${NEEDLE}" ]]; then
    echo >&2 "No needle as third argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

echo -n "Waiting for ${NAME}"

while true; do
    echo -n '.'
    # The 'true' at the end is to prevent script abortion because grep exits with 1 if nothing matches.
    MATCH=$(docker-compose -f "${COMPOSE_FILE}" logs --tail=10 | grep "${NEEDLE}" || true)

    if [[ -n "${MATCH}" ]]; then
        echo
        echo "${NAME} is running!"
        break
    fi

    sleep 1
done
