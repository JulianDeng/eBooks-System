<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>Orders</title>
    </head>
    <body>
		<xsl:apply-templates/>
    </body>
    </html>
    </xsl:template>

    <xsl:template match="orderList">
		<h1>Orders</h1>
		<table border="1">
			<tr>
				<td>Order ID</td>
				<td>User ID</td>
				<td>Status</td>
				<td>Street</td>
				<td>Province</td>
				<td>Country</td>
				<td>ZIP</td>
				<td>Phone</td>
			</tr>
			<xsl:for-each select="./orderItem">
				<tr>
					<td><xsl:value-of select="./orderId"/></td>
					<td><xsl:value-of select="./userId"/></td>
					<td><xsl:value-of select="./status"/></td>
					<td><xsl:value-of select="./address/street"/></td>
					<td><xsl:value-of select="./address/province"/></td>
					<td><xsl:value-of select="./address/country"/></td>
					<td><xsl:value-of select="./address/zip"/></td>
					<td><xsl:value-of select="./address/phone"/></td>
				</tr>
			</xsl:for-each>
		</table>
    </xsl:template>
</xsl:stylesheet>