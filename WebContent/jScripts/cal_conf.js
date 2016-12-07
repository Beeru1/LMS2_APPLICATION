
//Define calendar(s): addCalendar ("Unique Calendar Name", "Window title", "Form element's name", Form name")
//addCalendar("Calendar1", "Select Date", "UM_Date", "headerform");
addCalendar("LaunchDate", "Select Date", "launchDate", "arcCreateArcForm");
addCalendar("SalesPrepaidDate", "Select Date", "salesDate", "arcSalesdataPrepaidForm");
addCalendar("SalesPostpaidDate", "Select Date", "salesDate", "arcSalesdataPostpaidForm");
addCalendar("ExpenseDate", "Select Date", "expenseDate", "arcExpenseInvestmentsForm");
addCalendar("InvestmentDate", "Select Date", "investementDate", "arcExpenseInvestmentsForm");
addCalendar("SalesOtherServicesDate", "Select Date", "salesDate", "arcSalesdataOtherservicesForm");
addCalendar("MarketingReqFromDate", "Select Date", "fromDate", "arcMarketingReqReportForm");
addCalendar("MarketingReqToDate", "Select Date", "toDate", "arcMarketingReqReportForm");
addCalendar("ArcWalkinFromDate", "Select Date", "fromDate", "arcWalkinReportForm");
addCalendar("ArcWalkinToDate", "Select Date", "toDate", "arcWalkinReportForm");
addCalendar("ArcEarningROIFromDate", "Select Date", "fromDate", "arcEarningROIReportForm");
addCalendar("ArcEarningROIToDate", "Select Date", "toDate", "arcEarningROIReportForm");

// default settings for English
// Uncomment desired lines and modify its values 
// setFont("verdana", 9);
setWidth(90, 1, 15, 1);
// setColor("#cccccc", "#cccccc", "#ffffff", "#ffffff", "#333333", "#cccccc", "#333333");
// setFontColor("#333333", "#333333", "#333333", "#ffffff", "#333333");
setFormat("dd/mm/yyyy");
// setSize(200, 200, -200, 16);

// setWeekDay(0);
// setMonthNames("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
// setDayNames("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
// setLinkNames("[Close]", "[Clear]");
