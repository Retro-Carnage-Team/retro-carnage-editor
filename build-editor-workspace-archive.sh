#!/usr/bin/bash

TAG_NAME=$1

git clone --depth 1 --branch $1 https://github.com/Retro-Carnage-Team/editor-workspace /tmp/$TAG_NAME
rm -rf /tmp/$TAG_NAME/.git

cd /tmp/$TAG_NAME
zip -r -9 editor-workspace.zip .
cd -
mv /tmp/$TAG_NAME/editor-workspace.zip Core/release/editor-workspace.zip
