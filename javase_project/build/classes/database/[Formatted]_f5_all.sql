--seleclt all and f5
--check csdl cũ có tồn tại hay không, nếu có thì xóa trước khi tạo (tránh lỗi 'database currently in use' khi tồn tại csdl cũ)
IF DB_ID('doansem2') IS NOT NULL
BEGIN
	USE master
	ALTER DATABASE doansem2 SET SINGLE_USER WITH ROLLBACK IMMEDIATE
	DROP DATABASE doansem2
END

IF DB_ID('doansem2') IS NULL
BEGIN
	CREATE DATABASE doansem2
END
GO
USE doansem2

--Product------------------------------------------------------------------------
CREATE TABLE tblProduct (
    ProductID int IDENTITY (1, 1) PRIMARY KEY,
    ProductName nvarchar(max) NOT NULL,
    UnitPrice money,
    QtyOnHand int,
    ProductStatus nvarchar(20)
)

CREATE TABLE tblProductDetail (
    ProductID int FOREIGN KEY REFERENCES tblProduct (ProductID),
    Picture varbinary(max),
    ProcessorCPU nvarchar(50),
    Ram nvarchar(50),
    Sizes nvarchar(50),
    Weights nvarchar(50),
    HDD_SSD nvarchar(50),
    GPU nvarchar(50),
    Battery nvarchar(50),
    PRIMARY KEY (ProductID)
)

--Employee------------------------------------------------------------------------
CREATE TABLE tblEmployee (
    EmpID int IDENTITY (1, 1) PRIMARY KEY,
    EmpName nvarchar(100) NOT NULL,
	DoB date NOT NULL,
	Gender nvarchar(10) NOT NULL,    
    EmpAddress nvarchar(100) NOT NULL,
    TellNo varchar(15) UNIQUE NOT NULL,
    Email varchar(100) UNIQUE NOT NULL,    
    Salary money,
	EmpStatus nvarchar(20)
)

CREATE TABLE tblPosition (
    PosID int PRIMARY KEY IDENTITY (1, 1),
    PosName nvarchar(50)
)

CREATE TABLE tblAccount (
    AccID int PRIMARY KEY IDENTITY (1, 1),
    Username nvarchar(50) UNIQUE,
    Passwords nvarchar(50),
    EmpID int FOREIGN KEY REFERENCES tblEmployee (EmpID),
    PosID int FOREIGN KEY REFERENCES tblPosition (PosID)
)

--Receipt---------------------------------------------------------------------------
CREATE TABLE tblProvider (
    ProviderID int IDENTITY (1, 1) PRIMARY KEY,
    ProviderName nvarchar(100) NOT NULL,
    ProviderAddress nvarchar(100) NOT NULL,
    TellNo varchar(15) UNIQUE NOT NULL
)

CREATE TABLE tblReceiptNote (
    RepID int PRIMARY KEY IDENTITY (1, 1),
    EmpID int FOREIGN KEY REFERENCES tblEmployee (EmpID),
    RepTotalMoney money DEFAULT 0,
    Dates date NOT NULL,
    ProviderID int FOREIGN KEY REFERENCES tblProvider (ProviderID)
)

CREATE TABLE tblReceiptNoteDetail (
    RepID int FOREIGN KEY REFERENCES tblReceiptNote (RepID) ON DELETE CASCADE,
    ProductID int FOREIGN KEY REFERENCES tblProduct (ProductID),
    QtyReceipt int,
    RepPrice money,
    PRIMARY KEY (RepID, ProductID)
)

--Bill------------------------------------------------------------------------------
CREATE TABLE tblCustomer (
    CustID int IDENTITY (1, 1) PRIMARY KEY,
    CustName nvarchar(100) NOT NULL,
	Gender nvarchar(10) NOT NULL,
    CustAddress nvarchar(100) NOT NULL,
    TellNo varchar(15) UNIQUE NOT NULL,
    Email varchar(100) UNIQUE NOT NULL
)

CREATE TABLE tblBill (
    BillID int PRIMARY KEY IDENTITY (1, 1),
    EmpID int FOREIGN KEY REFERENCES tblEmployee (EmpID),
    TotalMoney money,
    Dates date NOT NULL,
    CustID int FOREIGN KEY REFERENCES tblCustomer (CustID)
)

CREATE TABLE tblBillDetails (
    BillID int FOREIGN KEY REFERENCES tblBill (BillID) ON DELETE CASCADE,
    ProductID int FOREIGN KEY REFERENCES tblProduct (ProductID),
    QtyonHand int,
    PRIMARY KEY (BillID, ProductID)
)
GO

--FUNCTIONS/TRIGGERS-----------------------------------------------------------------------------------------------------------------
--function tính tổng tiền của hóa đơn theo ID hóa đơn (fn_ : prefix of function's name; @p_ : prefix of parameter's name)
CREATE FUNCTION fn_TotalMoney (@p_billid int)
RETURNS money
AS
BEGIN
    DECLARE @p_totalmoney money
    SET @p_totalmoney = (SELECT Sum(tblBillDetails.QtyonHand * tblProduct.UnitPrice) FROM tblBillDetails
        JOIN tblProduct ON tblBillDetails.ProductID = tblProduct.ProductID WHERE tblBillDetails.BillID = @p_billid)
    IF @p_totalmoney IS NULL
        SET @p_totalmoney = 0
    RETURN @p_totalmoney
END
GO

--function tính tổng số lượng đã bán theo ID sp (all time)
CREATE FUNCTION fn_SumQtyOnhand (@p_productid int)
RETURNS int
AS
BEGIN
    DECLARE @p_sumQtyOnhand int
    SELECT @p_sumQtyOnhand = Sum(QtyonHand) FROM tblBillDetails WHERE ProductID = @p_productid
    IF @p_sumQtyOnhand IS NULL
        SET @p_sumQtyOnhand = 0
    RETURN @p_sumQtyOnhand
END
GO

--function tính tổng số lượng đã nhập theo ID sp (all time)
CREATE FUNCTION fn_SumQtyReceipted (@p_productid int)
RETURNS int
AS
BEGIN
    DECLARE @p_qtyreceipted int
    SELECT @p_qtyreceipted = Sum(QtyReceipt) FROM tblReceiptNoteDetail WHERE ProductID = @p_productid
    IF @p_qtyreceipted IS NULL
        SET @p_qtyreceipted = 0
    RETURN @p_qtyreceipted
END
GO

--function tính tổng số lượng sp hiện có
CREATE FUNCTION fn_QtyOnHand (@p_productid int)
RETURNS int
AS
BEGIN
    RETURN dbo.fn_SumQtyReceipted(@p_productid) - dbo.fn_SumQtyOnhand(@p_productid)
END
GO

--trigger tblBillDetails----------------------------------------------------------------------------------------------------------
CREATE TRIGGER trg_tblBillDetails
ON tblBillDetails
AFTER INSERT, DELETE, UPDATE
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        UPDATE tblBill
            --call function tính tổng tiền của hóa đơn (dbo : database object)
            SET TotalMoney = dbo.fn_TotalMoney(inserted.BillID)
            FROM inserted
                JOIN tblBillDetails ON inserted.BillID = tblBillDetails.BillID WHERE tblBill.BillID = inserted.BillID AND tblBillDetails.BillID = tblBill.BillID

        UPDATE tblProduct
            SET QtyOnHand = dbo.fn_QtyOnHand(inserted.ProductID)
            FROM inserted WHERE tblProduct.ProductID = inserted.ProductID

        IF EXISTS (SELECT * FROM tblProduct WHERE QtyOnHand < 0)
            ROLLBACK TRANSACTION
    END
    ELSE
    BEGIN
        IF NOT EXISTS (SELECT * FROM inserted)
        BEGIN
            UPDATE tblBill
                SET TotalMoney = dbo.fn_TotalMoney(deleted.BillID)
                FROM deleted WHERE tblBill.BillID = deleted.BillID

            UPDATE tblProduct
                SET QtyOnHand = dbo.fn_QtyOnHand(deleted.ProductID)
                FROM deleted WHERE tblProduct.ProductID = deleted.ProductID

            IF EXISTS (SELECT * FROM tblProduct WHERE QtyOnHand < 0)
                ROLLBACK TRANSACTION
        END
        ELSE
        BEGIN
            UPDATE tblBill
                SET TotalMoney = dbo.fn_TotalMoney(deleted.BillID)
                FROM deleted WHERE tblBill.BillID = deleted.BillID

            UPDATE tblProduct
                SET QtyOnHand = dbo.fn_QtyOnHand(deleted.ProductID)
                FROM deleted WHERE tblProduct.ProductID = deleted.ProductID

            UPDATE tblBill
                SET TotalMoney = dbo.fn_TotalMoney(inserted.BillID)
                FROM inserted
                    JOIN tblBillDetails ON inserted.BillID = tblBillDetails.BillID WHERE tblBill.BillID = inserted.BillID AND tblBillDetails.BillID = tblBill.BillID

            UPDATE tblProduct
                SET QtyOnHand = dbo.fn_QtyOnHand(inserted.ProductID)
                FROM inserted WHERE tblProduct.ProductID = inserted.ProductID

            IF EXISTS (SELECT * FROM tblProduct WHERE QtyOnHand < 0)
                ROLLBACK TRANSACTION
        END
    END
END
GO

--trigger ReceiptNoteDetail------------------------------------------------------------------------------------------------------
CREATE TRIGGER trg_ReceiptNoteDetail
ON tblReceiptNoteDetail
AFTER INSERT
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM deleted)
	BEGIN
		UPDATE tblProduct
			SET QtyOnHand = dbo.fn_QtyOnHand(inserted.ProductID)
			FROM inserted WHERE tblProduct.ProductID = inserted.ProductID

		UPDATE tblReceiptNote
			SET RepTotalMoney = (SELECT Sum(RepPrice * QtyReceipt) FROM tblReceiptNoteDetail WHERE RepID = inserted.RepID)
			FROM inserted WHERE tblReceiptNote.RepID = inserted.RepID
	END
END
GO

--trigger deleteReceiptNoteDetails------------------------------------------------------------------------------------------------
CREATE TRIGGER trg_deleteReceiptNoteDetails
ON tblReceiptNoteDetail
AFTER DELETE
AS
BEGIN
	UPDATE tblProduct
		SET QtyOnHand = dbo.fn_QtyOnHand(deleted.ProductID)
		FROM deleted WHERE tblProduct.ProductID = deleted.ProductID

	UPDATE tblReceiptNote
		SET RepTotalMoney = (SELECT Sum(RepPrice * QtyReceipt) FROM tblReceiptNoteDetail WHERE RepID = deleted.RepID)
		FROM deleted WHERE tblReceiptNote.RepID = deleted.RepID
END
GO

--trigger updateReceiptNoteDetails-------------------------------------------------------------------------------------------------
CREATE TRIGGER trg_updateReceiptNoteDetails
ON tblReceiptNoteDetail
AFTER UPDATE
AS
BEGIN
	UPDATE tblProduct
		SET QtyOnHand = dbo.fn_QtyOnHand(deleted.ProductID)
		FROM deleted WHERE tblProduct.ProductID = deleted.ProductID

	UPDATE tblReceiptNote
		SET RepTotalMoney = (SELECT Sum(RepPrice * QtyReceipt) FROM tblReceiptNoteDetail WHERE RepID = deleted.RepID)
		FROM deleted WHERE tblReceiptNote.RepID = deleted.RepID

	UPDATE tblProduct
		SET QtyOnHand = dbo.fn_QtyOnHand(inserted.ProductID)
		FROM inserted WHERE tblProduct.ProductID = inserted.ProductID

	UPDATE tblReceiptNote
		SET RepTotalMoney = (SELECT Sum(RepPrice * QtyReceipt) FROM tblReceiptNoteDetail WHERE RepID = inserted.RepID)
		FROM inserted WHERE tblReceiptNote.RepID = inserted.RepID
END
GO


--PROCEDURES-------------------------------------------------------------------------------------------------------------------------------------------------------------
--productReportByDates
CREATE PROC productReportByDates @p_dateFrom date, @p_dateTo date
AS
BEGIN
    SELECT ProductName,
           Sum(bd.QtyonHand) AS 'ProductSoldInSpecificDate' FROM tblBillDetails AS bd
        JOIN tblProduct AS p ON p.ProductID = bd.ProductID
        JOIN tblBill AS b ON bd.BillID = b.BillID WHERE Dates BETWEEN @p_dateFrom AND @p_dateTo
    GROUP BY ProductName
	ORDER BY Sum(bd.QtyonHand) DESC
END;
GO

--revenueReportByDates
CREATE PROC revenueReportByDates @p_dateFrom date, @p_dateTo date
AS
BEGIN
	SELECT b.Dates,
		   Sum(bd.QtyonHand * p.UnitPrice) AS 'totalBill',
		   Sum(bd.QtyonHand * getLatestUnitPrice.RepPrice) AS 'totalFundPerBill',
		   Sum(bd.QtyonHand * p.UnitPrice) - Sum(bd.QtyonHand * getLatestUnitPrice.RepPrice) AS 'totalProfit' FROM
		(SELECT rd.ProductID,
				RepPrice,
				Dates,
				Row_number() OVER (PARTITION BY rd.ProductID ORDER BY Dates DESC) AS duplicateRow FROM tblReceiptNoteDetail AS rd
			JOIN tblReceiptNote AS r ON r.RepID = rd.RepID) AS getLatestUnitPrice
		JOIN tblBillDetails AS bd ON bd.ProductID = getLatestUnitPrice.ProductID
		JOIN tblProduct AS p ON p.ProductID = bd.ProductID
		JOIN tblBill AS b ON b.BillID = bd.BillID WHERE duplicateRow = 1 AND b.Dates BETWEEN @p_dateFrom AND @p_dateTo
	GROUP BY b.Dates,
			 b.BillID,
			 TotalMoney
	ORDER BY b.Dates
END;
GO

--revenueReportSumByMonths
CREATE PROC revenueReportSumByMonths @p_dateFrom date, @p_dateTo date
AS
BEGIN
	SELECT FORMAT(t1.Dates, 'MM-yyyy') AS 'monthsOfAYear',
		   Sum(t1.totalBill) AS 'totalBillPerMonth',
		   Sum(t1.totalFundPerBill) AS 'totalFundPerMonth',
		   Sum(t1.totalProfit) AS 'totalProfitPerMonth' FROM
		(SELECT b.Dates,
				Sum(bd.QtyonHand * p.UnitPrice) AS 'totalBill',
				Sum(bd.QtyonHand * getLatestUnitPrice.RepPrice) AS 'totalFundPerBill',
				Sum(bd.QtyonHand * p.UnitPrice) - Sum(bd.QtyonHand * getLatestUnitPrice.RepPrice) AS 'totalProfit' FROM
			(SELECT rd.ProductID,
					RepPrice,
					Dates,
					Row_number() OVER (PARTITION BY rd.ProductID ORDER BY Dates DESC) AS duplicateRow FROM tblReceiptNoteDetail AS rd
				JOIN tblReceiptNote AS r ON r.RepID = rd.RepID) AS getLatestUnitPrice
			JOIN tblBillDetails AS bd ON bd.ProductID = getLatestUnitPrice.ProductID
			JOIN tblProduct AS p ON p.ProductID = bd.ProductID
			JOIN tblBill AS b ON b.BillID = bd.BillID WHERE duplicateRow = 1 AND b.Dates BETWEEN @p_dateFrom AND @p_dateTo
		GROUP BY b.Dates,
				 b.BillID,
				 TotalMoney) AS t1
	GROUP BY FORMAT(t1.Dates, 'MM-yyyy')
END;
GO

--procedure tổng tiền hóa đơn xuất (all time)
CREATE PROC tonghoadonxuat
AS
BEGIN
    SELECT Month(Dates) AS 'Tháng',
           Year(Dates) AS 'Năm',
           Sum(TotalMoney) AS 'Tổng tiền' FROM tblBill
    GROUP BY Month(Dates),
             Year(Dates)
    ORDER BY Month(Dates) DESC, Year(Dates) DESC
END;
GO

--procedure tổng tiền hóa đơn nhập (all time)
CREATE PROC tonghoadonnhap
AS
BEGIN
	SELECT Month(Dates) AS 'Tháng',
		   Year(Dates) AS 'Năm',
		   Sum(RepTotalMoney) AS 'Tổng tiền' FROM tblReceiptNote
	GROUP BY Month(Dates),
			 Year(Dates)
	ORDER BY Month(Dates) DESC, Year(Dates) DESC
END;
GO

--procedure tổng lãi (all time)
CREATE PROC tonglai
AS
BEGIN
	SELECT Month(tblBill.Dates) AS 'Tháng',
		   Year(tblBill.Dates) AS 'Năm',
		   (Sum(TotalMoney) - Sum(RepTotalMoney)) AS 'Tổng tiền' FROM tblBill,
																	  tblReceiptNote
	GROUP BY Month(tblBill.Dates),
			 Year(tblBill.Dates)
	ORDER BY Month(tblBill.Dates) DESC, Year(tblBill.Dates) DESC
END;
GO

--data-------------------------------------------------------------------------------------------------------------------------------
INSERT INTO tblProvider VALUES (N'Nhà cung cấp Dell', N'Quảng Nam', '0163529223')
INSERT INTO tblProvider VALUES (N'Nhà cung cấp Apple', N'Quảng Nam', '0163531323')
INSERT INTO tblProvider VALUES (N'Nhà cung cấp Lenovo', N'Quảng Nam', '0163526543')
INSERT INTO tblProvider VALUES (N'Nhà cung cấp Asus', N'Đà Nẵng', '0161329223')
INSERT INTO tblProvider VALUES (N'Nhà cung cấp Acer', N'Đà Nẵng', '0161329458')
INSERT INTO tblProvider VALUES (N'Nhà cung cấp HP', N'Đà Nẵng', '0161329249')

INSERT INTO tblCustomer VALUES (N'Nguyễn Thị Kim Tâm', N'Nữ', N'Quảng Nam', '0163529223', 'tamntk@gmail.com')
INSERT INTO tblCustomer VALUES (N'Nguyễn Thị Thảo Hiền', N'Nữ', N'Quảng Nam', '0163531323', 'hienntt@gmail.com')
INSERT INTO tblCustomer VALUES (N'Ngô Thị Lưu Mỹ', N'Nữ', N'Quảng Nam', '0163526543', 'myntl@gmail.com')
INSERT INTO tblCustomer VALUES (N'Trịnh Công Thành', N'Nam', N'Đà Nẵng', '0161329223', 'thanhtc@gmail.com')
INSERT INTO tblCustomer VALUES (N'Hồ Duy Linh', N'Nam', N'Đà Nẵng', '0163631223', 'linhhd@gmail.com')

INSERT INTO tblEmployee VALUES (N'Nguyễn Văn Trí', '02/24/2000', N'Nam', N'Quảng Nam', '0969402217', 'trinv@gmail.com', 3000, null)
INSERT INTO tblEmployee VALUES (N'Dương Xuân Dưỡng', '07/17/2000', N'Nam', N'Quảng Nam', '0951502217', 'duongdx@gmail.com', 3000, null)
INSERT INTO tblEmployee VALUES (N'Lê Thị Kim Thoa', '11/05/1999', N'Nữ', N'Quảng Nam', '0969402237', 'thoaltk@gmail.com', 3000, null)
INSERT INTO tblEmployee VALUES (N'Tăng Thị Diễm Hương', '02/13/2000', N'Nữ', N'Quảng Nam', '0962376217', 'huongttd@gmail.com', 3000, null)
INSERT INTO tblEmployee VALUES (N'Trịnh Quang Phúc', '12/07/2000', N'Nam', N'Quảng Nam', '0961234517', 'phuctq@gmail.com', 3000, null)

INSERT INTO tblPosition VALUES (N'Quản lý')
INSERT INTO tblPosition VALUES (N'Nhân viên')

INSERT INTO tblAccount VALUES ('trinv', '299132688689127175738334524183350839358', 1, 1)
INSERT INTO tblAccount VALUES ('duongdx', '299132688689127175738334524183350839358', 2, 1)
INSERT INTO tblAccount VALUES ('thoaltk', '299132688689127175738334524183350839358', 3, 2)
INSERT INTO tblAccount VALUES ('user', '173447602773428053556316684567667297915', 4, 2)
INSERT INTO tblAccount VALUES ('admin', '173447602773428053556316684567667297915', 5, 1)

INSERT INTO tblProduct VALUES (N'Apple Macbook Air 2020', 25000000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Laptop Asus Vivobook X509FJ-EJ053T', 15000000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Laptop Lenovo Legion Y540-15IRH 81SY0037VN', 14000000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Laptop Dell Inspiron N3593 70205744', 17000000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Apple Macbook Air 2019', 21000000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Acer Nitro 5 2019', 17500000, 100, NULL);
INSERT INTO tblProduct VALUES (N'Laptop HP Pavilion 15', 18500000, 100, NULL);

INSERT INTO tblProductDetail VALUES (1, NULL, N'i3-10th', N'8GB', N'13 Inches', N'2KG', N'256GB', N'AMD Radeon', N'4 hours');
INSERT INTO tblProductDetail VALUES (2, NULL, N'Core i5-8265U', N'2GB', N'15.6 FHD', N'2KG', N'1TB', N'MX230', N'5 hours');
INSERT INTO tblProductDetail VALUES (3, NULL, N'Core i5-9300H', N'4GB', N'15.6 FHD', N'2KG', N'500GB', N'GTX 1650', N'4 hours');
INSERT INTO tblProductDetail VALUES (4, NULL, N'Core i5-1035G1', N'4GB', N'15.6 FHD', N'2KG', N'256GB', N'MX230', N'5 hours');
INSERT INTO tblProductDetail VALUES (5, NULL, N'i5', N'8GB', N'13 Inches', N'2KG', N'128GB', N'AMD Radeon', N'4 hours');
INSERT INTO tblProductDetail VALUES (6, NULL, N'Intel Core i5', N'8GB', N'15.6 FHD', N'2KG', N'SSD 512 GB', N'NVIDIA GeForce GTX 1650', N'4 hours');
INSERT INTO tblProductDetail VALUES (7, NULL, N'Intel Core i5', N'4GB', N'15.6 FHD', N'1.9 kg', N'SSD 512 GB', N'ANVIDIA Geforce MX130', N'5 hours');

INSERT INTO tblReceiptNote VALUES (1, 0, '12/07/2019', 1)
INSERT INTO tblReceiptNote VALUES (2, 0, '11/28/2019', 2)
INSERT INTO tblReceiptNote VALUES (3, 0, '02/01/2020', 3)
INSERT INTO tblReceiptNote VALUES (4, 0, '05/17/2020', 4)

INSERT INTO tblReceiptNoteDetail VALUES (1, 1, 50, 22000000)
INSERT INTO tblReceiptNoteDetail VALUES (1, 2, 50, 12000000)
INSERT INTO tblReceiptNoteDetail VALUES (1, 3, 50, 11000000)
INSERT INTO tblReceiptNoteDetail VALUES (2, 1, 50, 22500000)
INSERT INTO tblReceiptNoteDetail VALUES (2, 2, 50, 12500000)
INSERT INTO tblReceiptNoteDetail VALUES (3, 4, 50, 13000000)
INSERT INTO tblReceiptNoteDetail VALUES (4, 4, 50, 13000000)
INSERT INTO tblReceiptNoteDetail VALUES (4, 5, 50, 18000000)
INSERT INTO tblReceiptNoteDetail VALUES (3, 6, 50, 15000000)
INSERT INTO tblReceiptNoteDetail VALUES (4, 7, 50, 15500000)

--data for form bao cao thong ke
--2019
INSERT INTO tblBill VALUES (1, 0, '01/17/2019', 1)
INSERT INTO tblBill VALUES (2, 0, '01/03/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '01/17/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '02/03/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '02/17/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '03/03/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '03/17/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '03/03/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '04/17/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '04/03/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '05/17/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '05/03/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '05/17/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '06/03/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '06/17/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '07/03/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '07/17/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '07/03/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '08/17/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '08/03/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '09/17/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '09/03/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '09/17/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '10/03/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '10/17/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '11/03/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '11/17/2019', 3)
INSERT INTO tblBill VALUES (4, 0, '11/03/2019', 4)
INSERT INTO tblBill VALUES (2, 0, '12/17/2019', 2)
INSERT INTO tblBill VALUES (3, 0, '12/03/2019', 3)

INSERT INTO tblBillDetails VALUES (1, 1, 3)
INSERT INTO tblBillDetails VALUES (1, 2, 3)
INSERT INTO tblBillDetails VALUES (2, 2, 1)
INSERT INTO tblBillDetails VALUES (3, 3, 2)
INSERT INTO tblBillDetails VALUES (3, 4, 2)
INSERT INTO tblBillDetails VALUES (4, 1, 1)
INSERT INTO tblBillDetails VALUES (5, 2, 2)
INSERT INTO tblBillDetails VALUES (5, 3, 2)
INSERT INTO tblBillDetails VALUES (6, 4, 4)
INSERT INTO tblBillDetails VALUES (7, 4, 5)
INSERT INTO tblBillDetails VALUES (7, 5, 5)
INSERT INTO tblBillDetails VALUES (8, 5, 7)
INSERT INTO tblBillDetails VALUES (9, 6, 8)
INSERT INTO tblBillDetails VALUES (9, 7, 8)
INSERT INTO tblBillDetails VALUES (10, 7, 5)
INSERT INTO tblBillDetails VALUES (11, 1, 3)
INSERT INTO tblBillDetails VALUES (11, 7, 3)
INSERT INTO tblBillDetails VALUES (12, 2, 1)
INSERT INTO tblBillDetails VALUES (13, 3, 2)
INSERT INTO tblBillDetails VALUES (13, 5, 2)
INSERT INTO tblBillDetails VALUES (14, 1, 1)
INSERT INTO tblBillDetails VALUES (15, 2, 2)
INSERT INTO tblBillDetails VALUES (15, 7, 2)
INSERT INTO tblBillDetails VALUES (16, 4, 4)
INSERT INTO tblBillDetails VALUES (17, 4, 5)
INSERT INTO tblBillDetails VALUES (17, 3, 5)
INSERT INTO tblBillDetails VALUES (18, 5, 7)
INSERT INTO tblBillDetails VALUES (19, 6, 8)
INSERT INTO tblBillDetails VALUES (19, 3, 8)
INSERT INTO tblBillDetails VALUES (20, 7, 5)
INSERT INTO tblBillDetails VALUES (21, 1, 3)
INSERT INTO tblBillDetails VALUES (21, 6, 3)
INSERT INTO tblBillDetails VALUES (22, 2, 1)
INSERT INTO tblBillDetails VALUES (23, 3, 2)
INSERT INTO tblBillDetails VALUES (23, 6, 2)
INSERT INTO tblBillDetails VALUES (24, 1, 1)
INSERT INTO tblBillDetails VALUES (25, 2, 2)
INSERT INTO tblBillDetails VALUES (25, 7, 2)
INSERT INTO tblBillDetails VALUES (26, 4, 4)
INSERT INTO tblBillDetails VALUES (27, 4, 5)
INSERT INTO tblBillDetails VALUES (27, 1, 5)
INSERT INTO tblBillDetails VALUES (28, 5, 7)
INSERT INTO tblBillDetails VALUES (29, 6, 8)
INSERT INTO tblBillDetails VALUES (29, 4, 8)
INSERT INTO tblBillDetails VALUES (30, 7, 5)

--2020
INSERT INTO tblBill VALUES (4, 0, '01/03/2020', 4)
INSERT INTO tblBill VALUES (2, 0, '01/17/2020', 2)
INSERT INTO tblBill VALUES (3, 0, '02/03/2020', 3)
INSERT INTO tblBill VALUES (4, 0, '02/17/2020', 4)
INSERT INTO tblBill VALUES (2, 0, '02/03/2020', 2)
INSERT INTO tblBill VALUES (3, 0, '03/17/2020', 3)
INSERT INTO tblBill VALUES (4, 0, '03/03/2020', 4)
INSERT INTO tblBill VALUES (2, 0, '04/17/2020', 2)
INSERT INTO tblBill VALUES (3, 0, '04/03/2020', 3)
INSERT INTO tblBill VALUES (4, 0, '04/17/2020', 4)
INSERT INTO tblBill VALUES (2, 0, '05/03/2020', 2)
INSERT INTO tblBill VALUES (3, 0, '05/17/2020', 3)
INSERT INTO tblBill VALUES (4, 0, '06/03/2020', 4)
INSERT INTO tblBill VALUES (2, 0, '06/17/2020', 2)
INSERT INTO tblBill VALUES (3, 0, '06/03/2020', 3)

INSERT INTO tblBillDetails VALUES (32, 1, 3)
INSERT INTO tblBillDetails VALUES (33, 2, 1)
INSERT INTO tblBillDetails VALUES (33, 4, 1)
INSERT INTO tblBillDetails VALUES (34, 3, 2)
INSERT INTO tblBillDetails VALUES (35, 1, 1)
INSERT INTO tblBillDetails VALUES (35, 6, 1)
INSERT INTO tblBillDetails VALUES (36, 2, 2)
INSERT INTO tblBillDetails VALUES (37, 4, 4)
INSERT INTO tblBillDetails VALUES (37, 7, 4)
INSERT INTO tblBillDetails VALUES (38, 4, 5)
INSERT INTO tblBillDetails VALUES (39, 5, 7)
INSERT INTO tblBillDetails VALUES (39, 2, 7)
INSERT INTO tblBillDetails VALUES (40, 6, 8)
INSERT INTO tblBillDetails VALUES (41, 7, 5)
INSERT INTO tblBillDetails VALUES (41, 3, 5)
INSERT INTO tblBillDetails VALUES (42, 4, 4)
INSERT INTO tblBillDetails VALUES (43, 4, 5)
INSERT INTO tblBillDetails VALUES (43, 3, 5)
INSERT INTO tblBillDetails VALUES (44, 5, 7)
INSERT INTO tblBillDetails VALUES (45, 6, 8)
INSERT INTO tblBillDetails VALUES (45, 2, 8)
