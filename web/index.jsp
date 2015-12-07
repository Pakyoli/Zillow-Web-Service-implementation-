<%@include file="header.jsp" %>
        <div style="text-align: center; margin-top: 20px">    
            <div style="width: 50%;margin-top: 25px; float: left">
                <div><img src="images/nyc.png" alt="nyc" width="700" height="516"/></div>
            </div>
            <div style="width: 50%; margin-top: 25px; float: left; height: 520px;">
                <div>
                    <p style="font-size: 16pt">Enter a New York address to see the information of the property</p>
                </div>
                <form action="zServlet" method="GET">
                    <fieldset class="field">
                        <legend style="font-size: 16pt">Enter street address</legend>
                            <label class="addLabel"> Street Address 1:</label><br>
                            <input type="text" name="address1" size="50" placeholder="Street Address 1" required/><br>
                            <label class="addLabel" style="margin-right: 300px"> City:</label><label class="addLabel"> State:</label><br>
                            <input type="text" name="city" size="30" placeholder="City" required style="margin-right:55px"/>
                            <select name="state" style="color:#aaaaaa;width: 100px">
                                <option disabled selected value="">State</option>
                                <option value="NY">NY</option>
                            </select><br>
                            <label class="addLabel"> Zip Code:</label><br>
                            <input type="text" name="zip" placeholder="Zip Code" size="20" maxlength="10" pattern="[0-9]+" title="Zip code should be number only" required/>
                    </fieldset>
                    <input type="submit" class="btn" value="Search"/>
                </form>
            </div>
        </div>
        <footer>

            
        </footer>
        </div>
    </body>
</html>
