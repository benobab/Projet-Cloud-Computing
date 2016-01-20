<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="text()"></xsl:template>

    <xsl:template match="item">
        <h2>
            <a href="{link}">
                <xsl:value-of select="title"/>
            </a>
        </h2>
        <p>
            <xsl:value-of select="description" disable-output-escaping="yes"/>
        </p>
    </xsl:template>
    <xsl:template match="/rss/channel">
        <html>
            <head>
                <title>
                    <xsl:value-of select="title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <a href="{link}">
                    <xsl:value-of select="title"/>
                </a>
            </h1>
            <xsl:apply-templates/>
        </body>
    </xsl:template>

</xsl:stylesheet>