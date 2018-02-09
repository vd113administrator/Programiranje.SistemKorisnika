<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@page import="programiranje.baza_korisnika_web.global.GlobalneFunkcije"%>

<!DOCTYPE HTML>

<%--
    This file is an entry point for JavaServer Faces application.
--%>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>База корисника</title>
        </head>
        <body>
            <center>
            <font color="red">
                <b>БАЗА КОРИСНИКА<br>ГРЕШКА</b>
            </font>
            <div style="display:none">${mainPageController.disconnect()}</div>
            <p>
                <h:form>
                    <b>${errorBean.error}</b><br><br>
                    <h:commandButton value="Повезивање са главним сервером" action="#{mainPageController.connect}"/>
                </h:form>
            </p>
            </center>
        </body>
    </html>
</f:view>
