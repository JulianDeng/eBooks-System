<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>Book Sold Report</title>
    </head>
    <body>
		<xsl:apply-templates/>
    </body>
    </html>
    </xsl:template>

    <xsl:template match="bookVisitReport">
	<h1>Book Sold Report</h1>
	<p>Generate Date: <xsl:value-of select="./generateDate"/></p>
	<p>Visit Type: <xsl:value-of select="./visitType"/></p>
	<table border="1">
		<xsl:for-each select="./monthlyVisitReport">
			<tr>
				<td colspan="5">reportMonth: <xsl:value-of select="./reportMonth"/></td>
			</tr>
			<tr>
				<td>Date</td>
				<td>BookID</td>
				<td>Title</td>
				<td>Price</td>
				<td>Category</td>
			</tr>
			<xsl:for-each select="./orderItemList">
				<tr>
					<td><xsl:value-of select="./date"/></td>
					<td><xsl:value-of select="./bookInfo/bid"/></td>
					<td><xsl:value-of select="./bookInfo/title"/></td>
					<td><xsl:value-of select="./bookInfo/price"/></td>
					<td><xsl:value-of select="./bookInfo/category"/></td>
				</tr>
			</xsl:for-each>
		</xsl:for-each>
	</table>
    </xsl:template>

</xsl:stylesheet>