<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>SIS Report</title>
    </head>
    <body>
		<xsl:apply-templates/>
    </body>
    </html>
    </xsl:template>

    <xsl:template match="sisReport">
	<h1>SIS Report</h1>
	<h2>Scope</h2>
	<p>Last Name Prefix: <xsl:value-of select="@namePrefix"/></p>
	<p>Credit Taken: <xsl:value-of select="@creditTaken"/></p>
	<table border="1">
		<tr>
			<td>CSE ID</td>
			<td>Full name</td>
			<td>CREDIT TAKEN</td>
			<td>CREDIT TAKING</td>
			<td>CREDIT GRADUATE</td>
		</tr>
		<xsl:apply-templates/>
	</table>
    </xsl:template>

    <xsl:template match="studentList">
	<tr>
	  <td><xsl:value-of select="./sid"/></td>
	  <td><xsl:value-of select="./name"/></td>
	  <td><xsl:value-of select="./credit_taken"/></td>
	  <td><xsl:value-of select="./credit_taking"/></td>
	  <td><xsl:value-of select="./credit_graduate"/></td>
	</tr>
    </xsl:template>

</xsl:stylesheet>