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
        <%
            if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null){
        %>
              <center>
                  <font color="teal">
                    <b>БАЗА КОРИСНИКА<br>СТРАНИЦА ПРИЈАВЕ</b>
                         <br>Сесија бр.<%=GlobalneFunkcije.getSesijskiObjekti().getConnectionId()%>
                         <br>Пријављени клијент <%=GlobalneFunkcije.getSesijskiObjekti().getSessionId()%>
                  </font>
                  <br><br>
                      <h:form id="signInForm">
                      <h:commandButton value="Потврда" action="#{signInController.submit}"/><br><br>
                      <table>
                          <tr>
                            <td>Корисничко име :</td>
                            <td><h:inputText id="username"/></td>
                          </tr>
                          <tr>
                            <td>Лозинка :</td>
                            <td><h:inputSecret id="password"/></td>
                          </tr>
                      </table>
                      <br>
                      <h:commandButton value="Повратак" action="#{signInController.mainpage}"/>
                      </h:form> 
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
                    <h:commandButton  value="Повезивање са главним сервером" action="#{mainPageController.connect}"/>
                </h:form>
            </p>
            </center>
        <%
            }
        %>
        </body>
    </html>
</f:view>