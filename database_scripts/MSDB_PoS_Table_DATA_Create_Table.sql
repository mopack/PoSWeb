use [PoS]

GO
/****** Object:  Table [dbo].[DATA]    Script Date: 2020/7/16 �U�� 02:47:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DATA](
	[ID] [nvarchar](50) NOT NULL,
	[VALUE] [nvarchar](max) NULL

	PRIMARY KEY CLUSTERED (
		[ID] ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO