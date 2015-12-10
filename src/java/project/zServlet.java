package project;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.netbeans.saas.zillow.ZillowRealEstateService;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.zillow.ZillowRealEstateServiceAuthenticator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import zillow.realestateservice.searchresults.SearchResultsProperty;

@WebServlet(name = "zServlet", urlPatterns = {"/zServlet"})
public class zServlet extends HttpServlet {

    private final String unittype = "dollar";
    private final String width = "600";
    private final String height = "300";
    private final String chartduration = "1year"; 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String apiKey = ZillowRealEstateServiceAuthenticator.getApiKey();
        
        Result r = new Result();
        String error = null;
        String s =null;
        List<String> sl = new ArrayList<>();
        
        String address = request.getParameter("address1").replaceAll(" ", "+");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String citystatezip = city + "+" + state + "+" + zip;

        GregorianCalendar gc = new GregorianCalendar();       

        try {
            //get deep search results using getDeepSearchResults web service
            RestResponse result = ZillowRealEstateService.getDeepSearchResults(address, citystatezip);
            
            if(result.getResponseCode()==200){ //check the response code and see if we get a response
                
                if (result.getDataAsObject(zillow.realestateservice.searchresults.Searchresults.class) instanceof zillow.realestateservice.searchresults.Searchresults) {
                    zillow.realestateservice.searchresults.Searchresults resultObj = result.getDataAsObject(zillow.realestateservice.searchresults.Searchresults.class);
                    
                    if(resultObj.getMessage().getCode().intValue()==0){ //check the code and see if the response contains results
                        //put the results into a SearchResultsProperty list
                        SearchResultsProperty resultList = resultObj.getResponse().getResults().getResult().get(0) ;

                        String zpid = String.valueOf(resultList.getZpid());
                        
                        r.setStreet(resultList.getAddress().getStreet());
                        r.setCity(resultList.getAddress().getCity());
                        r.setState(resultList.getAddress().getState());
                        r.setZip(resultList.getAddress().getZipcode());
                        r.setLatitude(resultList.getAddress().getLatitude());
                        r.setLongitude(resultList.getAddress().getLongitude());
                        r.setAmount(resultList.getZestimate().getAmount().getValue());
                        r.setBathroom(resultList.getBathrooms());
                        r.setBedroom(resultList.getBedrooms());
                        r.setUseCode(resultList.getUseCode());
                        r.setYearBuilt(resultList.getYearBuilt());
                        r.setLotSize(resultList.getLotSizeSqFt());
                        r.setFinishedSize(resultList.getFinishedSqFt());
                        r.setLastUpdate(resultList.getZestimate().getLastUpdated());
                        r.setLastSoldDate(resultList.getLastSoldDate()!=null?resultList.getLastSoldDate():null);
                        if(resultObj.getResponse().getResults().getResult().contains((Object)resultList.getLastSoldPrice())){
                            r.setLastSoldPrice(resultList.getLastSoldPrice().getValue());
                        }
                        r.setHigh(resultList.getZestimate().getValuationRange().getHigh().getValue().toString());
                        r.setLow(resultList.getZestimate().getValuationRange().getLow().getValue().toString());
                        r.setValueChanged(resultList.getZestimate().getValueChange().getValue());
                        
                        //get chart using zillow getChart web service
                        RestResponse result1 = ZillowRealEstateService.getChart(zpid, unittype, width, height, chartduration);
                        if (result1.getDataAsObject(zillow.realestateservice.chart.Chart.class) instanceof zillow.realestateservice.chart.Chart) {
                            zillow.realestateservice.chart.Chart chartObj = result1.getDataAsObject(zillow.realestateservice.chart.Chart.class);
                            r.setChart(chartObj.getResponse().getUrl()); 
                        }
                        
                        //get updated property details
                        DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
                        Document detailDoc = docBuilder.parse("http://www.zillow.com/webservice/GetUpdatedPropertyDetails.htm?zws-id="+apiKey+"&zpid="+zpid);

                        //check if there is any response result
                        if(detailDoc.getElementsByTagName("code").item(0).getTextContent().equals("0")){
                                                  
                            Element details =(Element)detailDoc.getElementsByTagName("response").item(0);

                            if(details.getElementsByTagName("homeDescription").getLength()!=0){
                                r.setDescription(details.getElementsByTagName("homeDescription").item(0).getTextContent());
                            } else {
                                r.setDescription("This owner didn't leave any discription for this house.");
                            }

                            if(r.getLastSoldDate()!=null){
                                SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
                                gc.setTime(sdf.parse(r.getLastSoldDate()));
                                gc.add((GregorianCalendar.YEAR), 2);
                                if(gc.compareTo(Calendar.getInstance())>=0){
                                    r.setStatus("Sold");
                                }
                            }

                            if(details.getElementsByTagName("posting").getLength()!=0){
                                r.setStatus("posting");
                                r.setType(details.getElementsByTagName("type").item(0).getTextContent());
                                r.setPrice(details.getElementsByTagName("price").item(0).getTextContent());
                            } 
                        } 
                    } else {
                        error = "No record found for this address. Please search again.";
                    }
                }
            } else {
                error = "No record found for this address. Please search again..";
            }
        } catch (IOException | JAXBException | ParserConfigurationException | SAXException | ParseException | NullPointerException ex ) {
            error = "Under maintenance, please check back later.";
            Logger.getLogger(zServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(error==null){
            request.setAttribute("result", r);
        }
        request.setAttribute("error", error);
        request.getRequestDispatcher("zestimate.jsp").forward(request, response);      
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
