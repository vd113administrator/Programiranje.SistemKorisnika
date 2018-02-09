<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@page import="programiranje.baza_korisnika_web.global.GlobalneFunkcije"%>
<%@page import="programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije"%>

<!DOCTYPE HTML>

<%--
    This file is an entry point for JavaServer Faces application.
--%>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Систем корисника</title>
            <script type="text/javascript" src="./WEB-JS/grupe.js"></script>
            <link type="text/css" rel="stylesheet" href="./WEB-STYLE/GlavnaStranica.css">
            <link type="text/css" rel="stylesheet" href="./WEB-STYLE/Poruke.css">
            <link type="text/css" rel="stylesheet" href="./WEB-STYLE/Grupe.css">
        </head>
        <body alink="blue" link="blue" vlink="blue"> 
        <%
            if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null){
        %>
            <center>
                <font color="green">
                    <b>СИСТЕМ КОРИСНИКА<br>СТРАНИЦА ПРИЈАВЕ</b>
                        <br>Сесија бр.<%=GlobalneFunkcije.getSesijskiObjekti().getConnectionId()%>
                        <br>Пријављени клијент <%=GlobalneFunkcije.getSesijskiObjekti().getSessionId()%>
                </font>
            </center>
            <%
                if(!GlobalneFunkcije.getPorukaInfo().equals("")){
            %>
            <br>
            <div class="message">
                <center>
                <table>
                      <tr>
                          <td>
                              <%=GlobalneFunkcije.getPorukaInfo()%>
                              <%GlobalneFunkcije.resetInfo();%>
                          </td>
                      </tr>
                </table>
                </center>
            </div>
            <%
                }else if(!GlobalneFunkcije.getGreskaInfo().equals("")){
            %>
            <br>
            <div class="error">
                <center>
                <table>
                      <tr>
                          <td>
                              <%=GlobalneFunkcije.getGreskaInfo()%>
                              <%GlobalneFunkcije.resetInfo();%>
                          </td>
                      </tr>
                </table>
                </center>
            </div>
            <%
                }
            %>
            <br><br>
            <div class="container">
                <div class="left">
                        <ul>
                            <li style="color: green">
                                <div onclick="viewKorisniciIn()">Корисник</div>
                            </li>
                            <li style="color: green">
                                <div onclick="viewGrupeIn()">Групе</div>
                            </li>
                        </ul>
                        <h:form>
                            <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <h:commandButton value="Одјава" action="#{mainPageController.logout}"/>
                        </h:form>
                </div>
              <div class="space"></div>
              <div class="right">
              <div style="display: block" id="polje_in">
              <center>
                     <table>
                          <tr>
                            <td>Корисничко име :</td>
                            <td>${prijavaBean.username}</td>
                          </tr>
                          <tr>
                            <td>Идентификација :</td>
                            <td>${registracijaBean.identificator}</td>
                          </tr>
                          <tr>
                            <td>Име :</td>
                            <td>${registracijaBean.name}</td>
                          </tr>
                          <tr>
                            <td>Презиме :</td>
                            <td>${registracijaBean.surname}</td>
                          </tr>
                          <tr>
                            <td>Адреса :</td>
                            <td>${registracijaBean.address}</td>
                          </tr>
                          <tr>
                            <td>Радно мјесто :</td>
                            <td>${registracijaBean.workplaces}</td>
                          </tr>
                          <tr>
                            <td>Телефон :</td>
                            <td>${registracijaBean.telephone}</td>
                          </tr>
                          <tr>
                            <td>Електорнска пошта :</td>
                            <td>${registracijaBean.email}</td>
                          </tr>
                          <tr>
                            <td>Сајтови :</td>
                            <td>${registracijaBean.webs}</td>
                          </tr>
                      </table>
                      <br>
                      <a href="/SistemKorisnikaVebAplikacija/WEB-SRV/korisnickaStranica.jsp">Страница корисника</a>
                      <br><br>
                      <h:form>
                          <h:commandButton value="Одјава" action="#{mainPageController.logout}"/><br>
                          <h:commandButton value="Дерегистрација" action="#{mainPageController.deregistracija}"/><br>
                          <h:commandButton value="Промена параметара" action="#{signedInController.renamePage}"/>
                      </h:form>
              </center>
              </div>
              <div id="grupe_in">
                  <div style="display: none">
                    <center>
                        <h3>ГРУПЕ</h3>
                        <h:form id="unos_grupa">
                            <h:commandButton style="display:none" id="gdata" value="" action="#{grupeController.ocitavanjeOpisaGrupe}" onclick="submit()"></h:commandButton>
                            <table cellspacing='0' class='checkable'>
                                <thead class="checkable">
                                    <th class="checkable"></th>
                                    <th class="checkable">НАЗИВ ГРУПЕ</th>
                                    <th class="checkable">КОРИСНИЧКО ИМЕ АДМИНИСТРАТОРА</th>
                                    <th class="checkable">ОПИСНА СТРАНИЦА ГРУПЕ</th>
                                </thead>
                                ${grupeInfoBean.grupeInfoTableHTML}
                                <tr>
                                    <td class="checkable"><center>#</center></td>
                                    <td class="checkable">
                                        <input type="text" onchange="novaGrupaPrenos()" name="groupname"/>
                                    </td>
                                    <td class="checkable"><%=GlobalneFunkcije.getSesijskiObjekti().getLoggedInUsername()%></td>
                                    <td class="checkable"></td>
                                </tr>
                            </table>
                        </h:form>
                        <br>

                        <h:form id="selekcija_grupa">
                            <h:selectOneRadio onchange="submit()">
                                <f:valueChangeListener type="programiranje.sistem_korisnika.grupe.beans.listener.GrupeIspisListener"/>
                                   <f:selectItem itemValue="1" itemLabel="све групе"/>
                                   <f:selectItem itemValue="2" itemLabel="по чланству"/>
                                   <f:selectItem itemValue="3" itemLabel="по власништву"/>
                            </h:selectOneRadio>
                        </h:form>

                        <br>

                        <h:form id="opcije_grupa">
                           <table>
                           <tr>
                               <td align="center"><h:commandButton value="Додавање групе" action="#{grupeController.dodavanjeGrupe}"/></td>
                               <td align="center"><h:commandButton value="Брисање групе" action="#{grupeController.brisanjeGrupe}"/></td>
                               <td align="center"><h:commandButton value="Преименовање групе" action="#{grupeController.preimenovanjeGrupe}"/></td>
                           </tr>
                           </table>
                           <table>
                           <tr>
                               <td align="center"><h:commandButton value="Учлањење" action="#{grupeController.uclanjenje}"/></td>
                               <td align="center"><h:commandButton value="Изчлањење"action="#{grupeController.isclanjenje}"/></td>
                               <td align="center"><h:commandButton value="Очитавање података корисника" action="#{grupeController.ocitavanjeKorisnika}"/></td>
                           </tr>
                           </table>
                           <input type="hidden" id="grupe_polje" name="grupe_polje"/>
                           <script>generisanjeHTMLCekiranjaGrupa()</script>
                        </h:form>
                        <h:form id="korisnici">
                            <input type="hidden" id="isk_admin" name="isk_admin"/>
                            <input type="hidden" id="isk_grupa" name="isk_grupa"/>
                            <input type="hidden" id="isk_korisnik" name="isk_korisnik"/>
                            <h:commandButton style="display:none" id="isk_ok" action="#{grupeController.iskljucenje}" value=""/>
                            <h:commandButton style="display:none" id="opis_clana" action="#{grupeController.ocitavanjeOpisaClanaGrupe}" value=""/>
                            <h:commandButton style="display:none" id="opis_uloge" action="#{grupeController.ocitavanjeOpisaUlogeGrupe}" value=""/>
                            ${grupeInfoBean.korisniciInfoTableHTML}
                        </h:form>
                    </center>
                  </div>
              </div>
              </div>
            </div>
            <%
                if(SKGlobalneFunkcije.getStranicaGrupa()==2){
                SKGlobalneFunkcije.setStranicaGrupa(1);

            %>
                <script>viewGrupeIn()</script>
        <%
                }
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
        ${stranicaKorisnikaBean.init()}
     </body>
    </html>
</f:view>