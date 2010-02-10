<?xml version="1.0" encoding="utf-8"?> 
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="/usr/share/xml/docbook/stylesheet/docbook-xsl/html/chunk.xsl"/>

<xsl:param name="chunker.output.encoding">utf-8</xsl:param>
<xsl:param name="chunk.first.sections">1</xsl:param>
<xsl:param name="l10n.gentext.default.language">ru</xsl:param>

<!--
<xsl:param name="html.stylesheet">default.css</xsl:param>
-->

<xsl:param name="base.dir">target/</xsl:param>
<xsl:param name="section.autolabel">1</xsl:param>
<xsl:param name="use.id.as.filename">1</xsl:param>

</xsl:stylesheet>
