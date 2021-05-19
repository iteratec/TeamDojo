#!/usr/bin/env bash

set -ueo pipefail

#
# This script compares what files are in two direcotries
#
# We uyse this to see if we miss some files from v1 in v2.
# We assume that the two directories to compares are siblings
# (in the same parent directory). Eg.:
# foo/ (COMMON_BASE_DIR)
#   teamDojoV1/ (BASE_DIR_A)
#   teamDojoV2/ (BASE_DIR_B)
#

USAGE="USAGE: $(basename "$0") <dir a> <dir b>"

if [ $# != 2 ]; then
    echo "To few arguments!"
    echo "${USAGE}"
    exit 1
fi

# We use absolute paths so we cane strip the common base path.
DIR_A=$(realpath "${1}")
DIR_B=$(realpath "${2}")

# https://stackoverflow.com/questions/6973088/longest-common-prefix-of-two-strings-in-bash
COMMON_BASE_DIR=$(printf "%s\n%s\n" "${DIR_A}" "${DIR_B}" | sed -e 'N;s/^\(.*\).*\n\1.*$/\1/')
BASE_DIR_A=$(echo "${DIR_A}" | sed "s|${COMMON_BASE_DIR}||" | awk -F/ ' { print $1 } ')
BASE_DIR_B=$(echo "${DIR_B}" | sed "s|${COMMON_BASE_DIR}||" | awk -F/ ' { print $1 } ')

find_files() {
    find "${1}/src" -type f -name '*.java' | \
    # We strip the base dir so.
    sed "s|${COMMON_BASE_DIR}${2}/||" | \
    # We strip the different base package names.
    sed "s|com/iteratec/teamdojo|BASEPACKAGE|" | \
    sed "s|de/otto/teamdojo|BASEPACKAGE|"
}

# Sort and diff the found files.
diff \
    <(find_files "${DIR_A}" "${BASE_DIR_A}" | sort) \
    <(find_files "${DIR_B}" "${BASE_DIR_B}" | sort)
