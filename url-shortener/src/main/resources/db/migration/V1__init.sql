CREATE TABLE UrlShortener
(
	[ID] INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	ShortUrlValue varchar(50) NOT NULL,
	LongUrlValue varchar(MAX) NOT NULL
)

GO



CREATE PROCEDURE [dbo].[checkIfExistsByLongUrl](
  @longUrlValue varchar(MAX )
)
AS
SELECT * FROM UrlShortener WHERE LongUrlValue= @longUrlValue
GO


CREATE PROCEDURE [dbo].[checkIfExistsByShortUrl](
  @shortUrlValue varchar(50)
)
AS
SELECT * FROM UrlShortener WHERE ShortUrlValue = @shortUrlValue
GO




CREATE PROCEDURE [dbo].[uspCreateUrlShortener]
(
	@longUrlValue varchar(MAX),
	@shortUrlValue varchar(50)
)
AS
BEGIN TRANSACTION

    DECLARE @urlId int
	SELECT @urlId = 0

    SELECT @urlId = ID from UrlShortener where LongUrlValue = @longUrlValue

	IF(@urlId > 0)
	    UPDATE UrlShortener SET LongUrlValue = @longUrlValue, ShortUrlValue = @shortUrlValue
	ELSE

	BEGIN
        INSERT INTO UrlShortener(ShortUrlValue,LongUrlValue)
        VALUES(@shortUrlValue,@longUrlValue)
        SELECT @urlId = SCOPE_IDENTITY()

	END

COMMIT
GO