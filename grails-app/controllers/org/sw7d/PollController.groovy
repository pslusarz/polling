package org.sw7d

class PollController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pollInstanceList: Poll.list(params), pollInstanceTotal: Poll.count()]
    }

    def create = {
        def pollInstance = new Poll()
        pollInstance.properties = params
        return [pollInstance: pollInstance]
    }

    def save = {
        def pollInstance = new Poll(params)
        if (pollInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'poll.label', default: 'Poll'), pollInstance.id])}"
            redirect(action: "show", id: pollInstance.id)
        }
        else {
            render(view: "create", model: [pollInstance: pollInstance])
        }
    }

    def show = {
        def pollInstance = Poll.get(params.id)
        if (!pollInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pollInstance: pollInstance]
        }
    }

    def edit = {
        def pollInstance = Poll.get(params.id)
        if (!pollInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pollInstance: pollInstance]
        }
    }

    def update = {
        def pollInstance = Poll.get(params.id)
        if (pollInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (pollInstance.version > version) {
                    
                    pollInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'poll.label', default: 'Poll')] as Object[], "Another user has updated this Poll while you were editing")
                    render(view: "edit", model: [pollInstance: pollInstance])
                    return
                }
            }
            pollInstance.properties = params
            if (!pollInstance.hasErrors() && pollInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'poll.label', default: 'Poll'), pollInstance.id])}"
                redirect(action: "show", id: pollInstance.id)
            }
            else {
                render(view: "edit", model: [pollInstance: pollInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pollInstance = Poll.get(params.id)
        if (pollInstance) {
            try {
                pollInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'poll.label', default: 'Poll'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def vote = {
		def pollInstance = Poll.get(params.id)
		def pollItemInstance = PollItem.get(params.itemId)
		render(view: "show", model:[pollInstance:pollInstance])
		if (pollItemInstance) {
			pollItemInstance.votes += 1
			if (!pollItemInstance.hasErrors() && pollItemInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pollItem.label', default: 'PollItem'), pollItemInstance.id])}"

			}
			else {
				flash.message = "${message('Could not register your vote. Please try again!')}"
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
		}
		
	}
	
	def addItem = {
		def pollInstance = Poll.get(params.id)
		def pollItemInstance = new PollItem(description:params.newItemDescription)
		pollInstance.addToPollItems(pollItemInstance)
		pollInstance.save(flush:true)
		render(view: "show", model: [pollInstance: pollInstance])
	}

}
