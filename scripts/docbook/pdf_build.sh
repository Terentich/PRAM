#!/bin/bash

SOURCE_DIR="src"
XSL_DRIVER=$SOURCE_DIR/docbook.xsl
MAIN_DOCUMENT=$SOURCE_DIR/project_regulations.xml

fop-1.0/fop -xsl $XSL_DRIVER -xml $MAIN_DOCUMENT -pdf project_regulations.pdf
