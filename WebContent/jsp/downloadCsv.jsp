
<%@page import="java.util.Date"%>

<%
// download
java.util.Date date = new java.util.Date();
String filePath="";
String fileNameNew="bulkLeadAssignmentErrLog"+date;
filePath=(String)session.getAttribute("errLogFilePath");
response.setContentType("text/csv");
// response.setContentType("application/x-msdownload");
// or
// response.setContentType("application/unknown");
response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);
try{
java.io.File uFile= new java.io.File(filePath);
int fSize=(int)uFile.length();
java.io.FileInputStream fis = new java.io.FileInputStream(uFile);
java.io.PrintWriter pw = response.getWriter();
int c=-1;
// Loop to read and write bytes.
while ((c = fis.read()) != -1){
pw.print((char)c);
}
// Close output and input resources.
fis.close();
pw.flush();
pw=null;
}catch(Exception e){
e.printStackTrace();
}
%>


