package view;

import entity.BloodDonation;
import entity.DonationRecord;
import entity.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.BloodDonationLogic;
import logic.DonationRecordLogic;
import logic.LogicFactory;
import logic.PersonLogic;

/**
 *
 * @author Feiqiong Deng
 */
@WebServlet(name = "CreateDonationRecord", urlPatterns = {"/CreateDonationRecord"})
public class CreateDonationRecord extends HttpServlet {
    private String errorMessage = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Create Donation Record</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println( "<div style=\"text-align: center;\">" );
            out.println( "<div style=\"display: inline-block; text-align: left;\">" );
            out.println( "<form method=\"post\">" );
            out.println( "Person ID:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.PERSON_ID );
            out.println( "<br>" );
            out.println( "Donation ID:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.DONATION_ID );
            out.println( "<br>" );
            out.println( "Tested:<br>" );
            out.printf("<select name=\"%s\">", DonationRecordLogic.TESTED);
            out.println("<option value=\"True\">");
            out.println("True");
            out.println("</option>");
            out.println("<option value=\"False\">");
            out.println("False");
            out.println("</option>");
            out.println("</select>");
            out.println( "<br>" );
            out.println( "<br>" );
            out.println( "Administrator:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.ADMINISTRATOR );
            out.println( "<br>" );
            out.println( "Hospital:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.HOSPITAL );
            out.println( "<br>" );
            out.println( "Date Created:<br>" );
            out.printf( "<input type=\"datetime-local\" name=\"%s\" value=\"\" step=\"1\"><br>", DonationRecordLogic.CREATED );
            out.println( "<br>" );
            out.println( "<input type=\"submit\" name=\"view\" value=\"Add and View\">" );
            out.println( "<input type=\"submit\" name=\"add\" value=\"Add\">" );
            out.println( "</form>" );
            if( errorMessage != null && !errorMessage.isEmpty() ){
                out.println( "<p color=red>" );
                out.println( "<font color=red size=4px>" );
                out.println( errorMessage );
                out.println( "</font>" );
                out.println( "</p>" );
            }       
            // clear the error message
            errorMessage = "";
            out.println( "<pre>" );
            out.println( "Submitted keys and values:" );
            out.println( toStringMap( request.getParameterMap() ) );
            out.println( "</pre>" );
            out.println( "</div>" );
            out.println( "</div>" );
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String toStringMap(Map<String, String[]> values) {
        StringBuilder builder = new StringBuilder();
        values.forEach( ( k, v ) -> builder.append( "Key=" ).append( k )
                .append( ", " )
                .append( "Value/s=" ).append( Arrays.toString( v ) )
                .append( System.lineSeparator() ) );
        return builder.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log( "GET" );
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log( "POST" );
        
        DonationRecordLogic donationRecordLogic = LogicFactory.getFor("DonationRecord");
        BloodDonationLogic bloodDonationLogic = LogicFactory.getFor("BloodDonation");
        PersonLogic personLogic = LogicFactory.getFor("Person");
        
        if(request.getParameter(DonationRecordLogic.PERSON_ID).isEmpty() && request.getParameter(DonationRecordLogic.DONATION_ID).isEmpty()){
            try{
                DonationRecord newRecord = donationRecordLogic.createEntity( request.getParameterMap() );
                donationRecordLogic.add(newRecord);
            }catch( Exception ex ) {
                errorMessage = ex.getMessage();
            }           
        }
        
        if(request.getParameter(DonationRecordLogic.PERSON_ID).isEmpty() && !request.getParameter(DonationRecordLogic.DONATION_ID).isEmpty()){
            int inputDonationID = Integer.parseInt(request.getParameter(DonationRecordLogic.DONATION_ID));
            BloodDonation bloodDonation = bloodDonationLogic.getWithId(inputDonationID);
            if(bloodDonation != null){
                try{
                   DonationRecord newRecord = donationRecordLogic.createEntity( request.getParameterMap() );
                   newRecord.setBloodDonation(bloodDonation);
                   donationRecordLogic.add(newRecord);
                }catch( Exception ex ) {
                errorMessage = ex.getMessage();
                }           
            } else {
               errorMessage =  "Donation ID: \"" + inputDonationID + "\" does not exist in the Blood Donation Table";
            }
        }
        
        if(!request.getParameter(DonationRecordLogic.PERSON_ID).isEmpty() && request.getParameter(DonationRecordLogic.DONATION_ID).isEmpty()){
            int inputPersonID = Integer.parseInt(request.getParameter(DonationRecordLogic.PERSON_ID));
            Person person = personLogic.getWithId(inputPersonID);
            if(person != null){
                try{
                   DonationRecord newRecord = donationRecordLogic.createEntity( request.getParameterMap() );
                   newRecord.setPerson(person);
                   donationRecordLogic.add(newRecord);
                }catch( Exception ex ) {
                errorMessage = ex.getMessage();
                }           
            } else {
               errorMessage =  "Person ID: \"" + inputPersonID + "\" does not exist in the Person Table";
            }
        }
        
        if(!request.getParameter(DonationRecordLogic.PERSON_ID).isEmpty() && !request.getParameter(DonationRecordLogic.DONATION_ID).isEmpty()){
            int inputPersonID = Integer.parseInt(request.getParameter(DonationRecordLogic.PERSON_ID));
            Person person = personLogic.getWithId(inputPersonID);
            int inputDonationID = Integer.parseInt(request.getParameter(DonationRecordLogic.DONATION_ID));
            BloodDonation bloodDonation = bloodDonationLogic.getWithId(inputDonationID);
            if(person != null && bloodDonation != null){
                try{
                   DonationRecord newRecord = donationRecordLogic.createEntity( request.getParameterMap() );
                   newRecord.setPerson(person);
                   newRecord.setBloodDonation(bloodDonation);
                   donationRecordLogic.add(newRecord);
                }catch( Exception ex ) {
                errorMessage = ex.getMessage();
                }           
            } else {
               if (person == null && bloodDonation == null){
                errorMessage =  "Neither Person ID nor Dontion ID does not exist";
               } else if(person == null && bloodDonation != null){
                  errorMessage =  "Person ID: \"" + inputPersonID + "\" does not exist in the Person Table";
               } else{
                  errorMessage =  "Donation ID: \"" + inputDonationID + "\" does not exist in the Blood Donation Table";
               }
            }
        }      
       
       if( request.getParameter( "add" ) != null ){
            //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect( "DonationRecordTable" );
        }      
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Create a Donation Record Entity";
    }// </editor-fold>
    
     private static final boolean DEBUG = true;

    public void log( String msg ) {
        if( DEBUG ){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
            getServletContext().log( message );
        }
    }
    
    public void log( String msg, Throwable t ) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
        getServletContext().log( message, t );
    }

}
