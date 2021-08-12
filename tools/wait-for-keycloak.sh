#!/usr/bin/env bash

set -euo pipefail

echo -n 'Waiting for Keycloak'

while true; do
    echo -n '.'
    # The 'true' at the end is to prevent script abortion because grep exits with 1 if nothing matches.
    match=$(docker-compose -f src/main/docker/keycloak.yml logs --tail=10 | grep 'started' || true)


    if [[ -n "$match" ]]; then
        echo
        echo "Keycloak is running!"
        break
    fi

    sleep 1
done
