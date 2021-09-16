#!/bin/sh

# This runs tokei (https://github.com/XAMPPRocky/tokei) to generate some statistics about the 
# source code of this project. You might want to adapt the path to the tokei executable. To 
# run this with every commit, add ". ./cloc.sh" to your pre-commit git hook.
~/.cargo/bin/tokei -t "Java,Shell,Markdown,Plain Text" . > tokei.txt
git add tokei.txt