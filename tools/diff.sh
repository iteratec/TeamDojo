#!/bin/bash

# SPDX-FileCopyrightText: the TeamDojo authors
# SPDX-License-Identifier: Apache-2.0

# Bash script which compares each file from two directory structures. And builds a new Diff directory inside the directory given by pwd


if [ "$#" -ne 2 ]
then
    echo "Illegal Number of Arguments"
    exit 1
fi

DIR1="$1"
DIR2="$2"
CWD=`pwd`
DIFFDIR="$CWD/diff-dir"

if [ -d $DIR1 ]
then
    echo $DIR1
    if [ -d $DIR2 ]
    then
        echo $DIR2

        echo "Building Diff Directory..."
        echo "DIFFDIR: $DIFFDIR"
        mkdir -p $DIFFDIR
        LC_ALL=C diff -qr $DIR1 $DIR2 | grep -v '^Only in' | awk '{print $2,$4}' | while read -r line; do
            echo "Line: $line";
            FILE1=`echo $line | cut -d" " -f1`;
            FILE2=`echo $line | cut -d" " -f2`;

            DIFFFILE="$DIFFDIR/`echo $FILE1 | cut -d'/' -f2-`";
            echo "Diffile: $DIFFFILE";
            mkdir -p `dirname $DIFFFILE`;
  #          echo "File1: $FILE1";
 #           echo "File2: $FILE2";
            diff $FILE1 $FILE2 > $DIFFFILE;

        done
        echo "Diff Directory written to $PWD"
    else
        echo "Arg 2 not a valid directory"
        exit 1
    fi
else
    echo "Arg 1 not a valid directory"
    exit 1
fi

echo "Done..."
exit 0




