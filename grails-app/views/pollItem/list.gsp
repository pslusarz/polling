
<%@ page import="org.sw7d.PollItem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pollItem.label', default: 'PollItem')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            
                            <g:sortableColumn property="description" title="${message(code: 'pollItem.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="votes" title="${message(code: 'pollItem.votes.label', default: 'Votes')}" />
                            <td>&nbsp;</td>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pollItemInstanceList}" status="i" var="pollItemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        
                            <td><g:link action="show" id="${pollItemInstance.id}">${fieldValue(bean: pollItemInstance, field: "description")}</g:link></td>
                        
                            <td>${fieldValue(bean: pollItemInstance, field: "votes")}</td>
                            
                            <td><span class="menuButton"><g:link action="vote" id="${pollItemInstance.id}"><g:message code="Vote" args="[entityName]" /></g:link></span></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pollItemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
