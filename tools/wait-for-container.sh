#!/usr/bin/env bash

set -euo pipefail

#
# Waits until a given needle in compose log occurs.
#

USAGE="$(basename "{0}") <service name> <compose file> <needle>"

NAME="${1:-}"
COMPOSE_FILE="${2:-}"
NEEDLE="${3:-}"
# How long to wait each check iteration in seconds:
SLEEP=1
# Multiplied with SLEEP is the timeout in seconds:
TIMEOUT=150

if [[ -z "${NAME}" ]]; then
    echo >&2 "ERROR: No name as first argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

if [[ -z "${COMPOSE_FILE}" ]]; then
    echo >&2 "ERROR: No compose file  as second argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

if [[ -z "${NEEDLE}" ]]; then
    echo >&2 "ERROR: No needle as third argument given!"
    echo >&2 "${USAGE}"
    exit 1
fi

echo -n "Waiting for ${NAME}"

COUNTER=0
while true; do
    echo -n '.'
    # The 'true' at the end is to prevent script abortion because grep exits with 1 if nothing matches.
    MATCH=$(docker-compose -f "${COMPOSE_FILE}" logs --tail=10 | grep "${NEEDLE}" || true)

    if [[ -n "${MATCH}" ]]; then
        echo
        echo "${NAME} is running!"
        break
    fi

    if [[ $COUNTER -gt $TIMEOUT ]]; then
        echo >&2 "ERROR: Timeout exceeded! ${NAME} didn't came up."
        echo >&2 "ERROR: Can't find needle in log output: '${NEEDLE}'"
        exit 1
    fi

    sleep $SLEEP
    (( COUNTER++ )) || true
done
