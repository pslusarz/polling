
<%@ page import="org.sw7d.Poll"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'poll.label', default: 'Poll')}" />
<title><g:message code="default.show.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" />
		</a>
		</span> <span class="menuButton"><g:link class="list" action="list">
				<g:message code="default.list.label" args="[entityName]" />
			</g:link>
		</span> <span class="menuButton"><g:link class="create"
				action="create">
				<g:message code="default.new.label" args="[entityName]" />
			</g:link>
		</span>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="dialog">
			<table>
				<tbody>

					<tr class="prop">
						<td valign="top" class="value">
							${fieldValue(bean: pollInstance, field: "description")}
						</td>
						<td valign="top" class="value">
							Votes
						</td>
						<td valign="top" class="value">
							&nbsp;
						</td>

					</tr>

					<g:each in="${pollInstance.pollItems}" var="p" status="i">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td valign="top" style="text-align: left;" class="value"><g:link
									controller="pollItem" action="show" id="${p.id}">
									${p?.description}
								</g:link></td>
							<td valign="top" style="text-align: left;" class="value">
									${p?.votes}
							</td>
							<td valign="top" style="text-align: left;" class="value">
							<g:link controller="poll" action="vote" id="${pollInstance.id}" params="[itemId:p?.id]">
										<g:message code="Vote" args="[entityName]" />
									</g:link>
							
							</td>
						</tr>
					</g:each>
                   
				</tbody>
			</table>
			<g:form method="post" >
			     <g:hiddenField name="id" value="${pollInstance?.id}" />
                 <g:hiddenField name="version" value="${pollInstance?.version}" />
			  <table>
			    <tr>
			      <td><g:textField name="newItemDescription"/></td>
			      <td><g:actionSubmit class="new" action="addItem" value="Add Item" /></td>
			    </tr>
			  </table>
			</g:form>
		</div>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${pollInstance?.id}" />
				<span class="button"><g:actionSubmit class="edit"
						action="edit"
						value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
				</span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</span>
			</g:form>
		</div>
	</div>
</body>
</html>
