<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<title>Annomized Report</title>
			</head>
			<body>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>

    <xsl:template match="annomizedReport">
	<h1>Annomized Report</h1>
	<p>Generate Date: <xsl:value-of select="./generateDate"/></p>
	<table border="1">
		<tr>
			<td>User ID</td>
			<td>First Name</td>
			<td>Last Name</td>
			<td>Total Spend</td>
		</tr>
		<xsl:for-each select="./annomizedUser">
			<tr>
				<td><xsl:value-of select="./uid"/></td>
				<td><xsl:value-of select="./firstName"/></td>
				<td><xsl:value-of select="./lastName"/></td>
				<td><xsl:value-of select="./total_spend"/></td>
			</tr>
		</xsl:for-each>
	</table>
    </xsl:template>

</xsl:stylesheet>