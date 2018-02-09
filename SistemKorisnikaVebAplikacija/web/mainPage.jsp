<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@page import="programiranje.baza_korisnika_web.global.GlobalneFunkcije"%>
<%@page import="programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije"%>
<%@page import="programiranje.sistem_korisnika_web.driver.DriverManager"%>

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
        <%
            SKGlobalneFunkcije.setDriverManager(new DriverManager());
            if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null){
        %>
              <center>
                  <font color="teal">
                      <b>БАЗА КОРИСНИКА<br>ПОЧЕТНА СТРАНИЦА</b>
                      <br>Сесија бр.<%=GlobalneFunkcije.getSesijskiObjekti().getConnectionId()%>
                      <br>Пријављени клијент <%=GlobalneFunkcije.getSesijskiObjekti().getSessionId()%>
                  </font>
                  <p>
                      <h:form>
                        <h:commandButton value="Пријава" action="#{mainPageController.signin}"/><br>
                        <h:commandButton value="Регистрација" action="#{mainPageController.signup}"/><br><br>
                        <h:commandButton value="Разконекција од главног сервера" action="#{mainPageController.disconnect}"/><br>
                        <h:commandButton value="Повезивање са главним сервером" action="#{mainPageController.connect}"/><br>
                      </h:form> 
                  </p>
              </center>
        <%
            }else{
        %>
            <center>
            <font color="red">
                <b>БАЗА КОРИСНИКА<br>ГРЕШКА</b>
            </font>
            <p>
                <b>Не постоји веза са сервером.</b> 
                <h:form>
                    <h:commandButton value="Повезивање са главним сервером" action="#{mainPageController.connect}"/>
                </h:form>
            </p>
            </center>
        <%
            }
        %>
        </body>
    </html>
</f:view>
