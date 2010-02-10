#!/bin/bash

SOURCE_DIR="src"
XSL_DRIVER=$SOURCE_DIR/docbook.xsl
MAIN_DOCUMENT=$SOURCE_DIR/project_regulations.xml

xsltproc --nonet $XSL_DRIVER $MAIN_DOCUMENT
