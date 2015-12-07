<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <script src="https://maps.googleapis.com/maps/api/js"></script>
        <script>
          function initialize() {
            var mapCanvas = document.getElementById('map');
            var myLatLng = {lat: ${result.latitude}, lng: ${result.longitude}};
            var mapOptions = {
              center: myLatLng,
              zoom: 18,
              mapTypeId: google.maps.MapTypeId.prototype
            };
            var map = new google.maps.Map(mapCanvas, mapOptions);
            var marker = new google.maps.Marker({
              position: myLatLng,
              map: map
            });
          }
          google.maps.event.addDomListener(window, 'load', initialize);
        </script>
        <script>
            function myFunction() {
                var x = document.getElementById("valueRange").value;
            }
        </script>

        <%@include file="header.jsp" %>
            <div id="body">
                <c:choose>
                    <c:when test="${error eq null}">
                        <div style="margin: auto; width: 1400px; border-bottom: 1px solid black; padding-top: 50px; overflow: hidden">
                            <div style="font-size: 40pt; margin: 10px 0 3px 0;float: left; width: 70%">
                                ${result.street},  ${result.city}, ${result.state}  ${result.zip}
                            </div>
                            <div style="width: 28%; float: left"><p style="text-align: right; padding-top: 30px">Last Update: ${result.lastUpdate}</p></div>
                        </div>              
                        <section>
                            <div id="basic-info">                   
                                <div id="info">
                                    <c:if test="${result.status eq 'posting'}">                        
                                        <div class="posting" style="padding: 10px;"><img src="images/greendot.jpg"/>&nbsp;&nbsp${result.type}</div>
                                        <div class="posting" style="padding-left: 25px;">${result.price}</div>
                                    </c:if>
                                    <c:if test="${result.status eq null}">
                                        <div class="posting" style="padding: 10px">This house is currently not been listing.</div>
                                    </c:if>
                                    <c:if test="${result.status eq 'Sold'}">
                                        <div class="posting" style="padding: 10px;"><img src="images/reddot.jpg"/>&nbsp;&nbsp${result.status} on ${result.lastSoldDate}</div>
                                        <div class="posting" style="padding-left: 45px;">Price: ${result.lastSoldPrice}</div>
                                    </c:if> 
                                                                                                  
                                    <div style="font-size:18pt; margin-top: 10px; border-top: 1px dotted lightgray">
                                        Facts:
                                        <ul>
                                            <li>${result.useCode}</li>
                                            <li>${result.finishedSize} sqft</li>
                                            <li>Bedrooms: ${result.bedroom == null?"--":result.bedroom} </li>
                                            <li>Bathrooms: ${result.bathroom == null?"--":result.bathroom} </li>
                                            <li>Lot: ${result.lotSize} sqft</li>                                   
                                            <li>Built in ${result.yearBuilt == 0? "--":result.yearBuilt}</li>
                                            <li></li>
                                        </ul>
                                 
                                    </div>
                                    <div><p style="margin-top: 10px;font-size: 20pt; color: black; padding: 5px; ">Property description:</p></div>
                                    <div id="desc"> <p style="font-size: 16pt; color: black;padding-left: 5px"> 
                                            &nbsp;&nbsp;&nbsp;&nbsp;     ${result.description eq null? "No property description.":result.description}</p>
                                    </div>   
                                </div>
                            </div>
                                    <div style="width: 700px; padding-left: 30px; padding-bottom: 5px; float: left;">Latitude:${result.latitude}, &nbsp;    Longitude:${result.longitude}</div>
                            <div id="map"></div>
                        </section>
                        <section>
                            <div style="width: 100%;font-size: 30pt;background: greenyellow;padding: 10px;">Zestimate Details</div><!--/*#F8F8F8*/-->
                            <div style="width: 30%; float: left; height: 600px; margin-left: 20px; padding: 20px">
                                <div style="border-bottom: 1px dotted lightgray; font-size: 28pt">Zestimate:</div>
                                <div style="font-size:26pt; padding-bottom: 5px">${result.amount}</div>
                                <div style="overflow: hidden">
                                    <c:choose>
                                        <c:when test="${result.valueChanged>0}">
                                            <div class="value-change" style="border-color: #87d300; border-radius: 5px; color: #87d300">+${result.valueChanged}</div>
                                        </c:when>
                                        <c:when test="${result.valueChanged<0}">
                                            <div class="value-change" style="border-color: red; border-radius: 5px; color: red">${result.valueChanged}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="value-change" style="border-color: black; border-radius: 5px; color: black ">${result.valueChanged}</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="color: black; float: left; margin: 8px 0 0 10px; font-size: 20pt">Last 30 days</div>
                                </div>
                                <div style="margin-right: 100px">                                    
                                    <span id="low">$${result.lowK}</span>
                                    <input type="range" id="valueRange" min="${result.low}" max="${result.high}" step="30000" value="${result.amount}" style="margin-top: 10px">
                                    <span id="high">$${result.highK}</span>
                                </div>
                            </div>
                            <div id="chart">
                                <div><img src="${result.regionChart}" width="100%"/></div>
                            </div>
                        </section>
                       
                    </c:when>
                    <c:otherwise>
                        <div style="margin: auto; width: 1400px; border-bottom: 1px solid black; text-align: right; padding-top: 50px"> </div>
                        <div style="margin-top: 20px; border:1px solid #f1a899;background:#fddfdf;border-radius:7px; ">
                            <p class="error">${error}</p>                           
                        </div>
                            <div style="text-align: right;margin-right: 100px; font-size: 18pt"><a href="index.jsp" style="text-decoration: none"><p><<<--Go back and search again-->>></p></a></div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="footer"> </div>
        </div>
    </body>
</html>
