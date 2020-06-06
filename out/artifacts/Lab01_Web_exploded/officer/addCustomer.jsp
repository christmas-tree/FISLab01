<%--
  Created by IntelliJ IDEA.
  User: tnghi
  Date: 6/4/2020
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Customer</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet"
          type="text/css">
</head>
<body>
<div class="container">
    <h4>Add customer</h4>
    <c:if test="${error}">
        <p style="color: red">Cannot add this customer to database.</p>
    </c:if>
    <c:if test="${success}">
        <p style="color: green">Customer added to pending database.</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/customer/add" method="POST">
        <div class="row">
            <div class="six columns">
                <label for="name">Name</label>
                <input class="u-full-width" name="name" type="text" placeholder="Nguyễn Văn A" id="name" required>
            </div>
            <div class="two columns">
                <label for="birthyear">Birth Year</label>
                <input class="u-full-width" name="birthYear" type="number" placeholder="1999" id="birthyear" required>

            </div>
            <div class="four columns">
                <label for="income">Income</label>
                <input class="u-full-width" name="income" type="number" placeholder="1000000" id="income" required>
            </div>
        </div>
        <div class="row">
            <div class="six columns">
                <label for="city">City</label>
                <select class="u-full-width" id="city" required>
                    <c:forEach var="city" items="${cityList}">
                        <option value="${city.getCityId()}">${city.getCityName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="six columns">
                <label for="city">District</label>
                <select class="u-full-width" id="district" required>
                    <c:forEach var="district" items="${districtList}">
                        <option value="${district.getDistrictId()}" data-city="${district.getCityId()}">${district.getDistrictName()}</option>
                    </c:forEach>
                </select>
            </div>
            <input type="hidden" name="city" id="hiddenCity" value="">
            <input type="hidden" name="district" id="hiddenDistrict" value="">
        </div>
        <input class="button-primary" id="submit" type="submit" value="Submit">
    </form>
    <form action="${pageContext.request.contextPath}/logout" method="POST">
        <button>Log out</button>
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        $("#district > option").hide();
        $("#district option:selected").prop("selected", false)

        $('#city').on('change', function(e) {
            let selector = $(this).val();
            let districtSelect = $("#district > option");
            $("#district option:selected").prop("selected", false);
            districtSelect.hide();
            districtSelect.filter(function(){
                return $(this).data('city') == selector
            }).show();
        });

        $("#city").change(function(){
            var selectedVal = $("#city option:selected").text();
            $("#hiddenCity").val(selectedVal);
            //$("#modelname").submit();
        });
        $("#district").change(function(){
            var selectedVal = $("#district option:selected").text();
            $("#hiddenDistrict").val(selectedVal);
            //$("#modelname").submit();
        });
    });
</script>
</body>
</html>
