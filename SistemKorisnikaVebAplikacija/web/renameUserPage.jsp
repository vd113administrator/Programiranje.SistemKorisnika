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
            <title>Систем корисника</title>
        </head>
        <body>
        <%
            if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null){
        %>
              <center>
                  <font color="green">
                    <b>СИТЕМ КОРИСНИКА<br>СТРАНИЦА ПРИЈАВЕ - ПРОМЕНА ПАРАМЕТАРА</b>
                        <br>Сесија бр.<%=GlobalneFunkcije.getSesijskiObjekti().getConnectionId()%>
                        <br>Пријављени клијент <%=GlobalneFunkcije.getSesijskiObjekti().getSessionId()%>
                  </font>  
                  <br><br>
                  <h:form id="preimenovanje_korisnika">
                     <table>
                          <tr>
                            <td>Идентификација :</td>
                            <td>${registracijaBean.identificator}</td>
                          </tr>
                          <tr>
                            <td>Старо корисничко име :</td>
                            <td><h:outputText id="oldusername" value="#{prijavaBean.username}"/></td>
                          </tr>
                          <tr>
                            <td>Корисничко име :</td>
                            <td><h:inputText id="username" value="#{registracijaBean.username}"/></td>
                          </tr>
                          <tr>
                            <td>Име :</td>
                            <td><h:inputText id="name" value="#{registracijaBean.name}"/></td>
                          </tr>
                          <tr>
                            <td>Презиме :</td>
                            <td><h:inputText id="surname" value="#{registracijaBean.surname}"/></td>
                          </tr>
                          <tr>
                            <td>Адреса :</td>
                            <td><h:inputText id="address" value="#{registracijaBean.address}"/></td>
                          </tr>
                          <tr>
                              <td><br></td>
                          </tr>
                          <tr>
                            <td>Стара шифра :</td>
                            <td><h:inputSecret id="oldpassword"/></td>
                          </tr>
                          <tr>
                            <td>Нова шифра :</td>
                            <td><h:inputSecret id="newpassword"/></td>
                          </tr>
                          <tr>
                              <td><br></td>
                          </tr>
                          <tr>
                            <td>Радно мјесто :</td>
                            <td><h:inputTextarea id="jobs" value="#{registracijaBean.workplaces}"/></td>
                          </tr>
                          <tr>
                            <td>Телефон :</td>
                            <td><h:inputTextarea id="phones" value="#{registracijaBean.telephone}"/></td>
                          </tr>
                          <tr>
                            <td>Електорнска пошта :</td>
                            <td><h:inputTextarea id="emails" value="#{registracijaBean.email}"/></td>
                          </tr>
                          <tr>
                            <td>Сајтови :</td>
                            <td><h:inputTextarea id="webs" value="#{registracijaBean.webs}"/></td>
                          </tr>
                      </table> 
                      <br> 
                          <h:commandButton value="Прихватање" action="#{preimenovanjeKorisnikaController.preimenovanje}"/><br>
                          <h:commandButton value="Одступање" action="#{preimenovanjeKorisnikaController.povratak}"/>
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