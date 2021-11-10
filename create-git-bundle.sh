#!/bin/sh

BUNDLE_FILE=adplacement.bundle

if [[ -f "${BUNDLE_FILE}" ]]; then
  rm -f "${BUNDLE_FILE}"
fi

git bundle create "${BUNDLE_FILE}" master
