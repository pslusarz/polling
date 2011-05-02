package org.sw7d

class PollItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pollItemInstanceList: PollItem.list(params), pollItemInstanceTotal: PollItem.count()]
    }

    def create = {
        def pollItemInstance = new PollItem()
        pollItemInstance.properties = params
        return [pollItemInstance: pollItemInstance]
    }

    def save = {
        def pollItemInstance = new PollItem(params)
        if (pollItemInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pollItem.label', default: 'PollItem'), pollItemInstance.id])}"
            redirect(action: "show", id: pollItemInstance.id)
        }
        else {
            render(view: "create", model: [pollItemInstance: pollItemInstance])
        }
    }

    def show = {
        def pollItemInstance = PollItem.get(params.id)
        if (!pollItemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pollItemInstance: pollItemInstance]
        }
    }

    def edit = {
        def pollItemInstance = PollItem.get(params.id)
        if (!pollItemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pollItemInstance: pollItemInstance]
        }
    }

    def update = {
        def pollItemInstance = PollItem.get(params.id)
        if (pollItemInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (pollItemInstance.version > version) {
                    
                    pollItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pollItem.label', default: 'PollItem')] as Object[], "Another user has updated this PollItem while you were editing")
                    render(view: "edit", model: [pollItemInstance: pollItemInstance])
                    return
                }
            }
            pollItemInstance.properties = params
            if (!pollItemInstance.hasErrors() && pollItemInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pollItem.label', default: 'PollItem'), pollItemInstance.id])}"
                redirect(action: "show", id: pollItemInstance.id)
            }
            else {
                render(view: "edit", model: [pollItemInstance: pollItemInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
            redirect(action: "list")
        }
    }
    def vote = {
    	def pollItemInstance = PollItem.get(params.id)
		redirect(action: "list")
		if (pollItemInstance) {
			
    		if (params.version) {
    			def version = params.version.toLong()
    			if (pollItemInstance.version > version) {
    				flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'pollItem.label', default: 'PollItem')])}"
                    return
    			} 
    		}
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

    def delete = {
        def pollItemInstance = PollItem.get(params.id)
        if (pollItemInstance) {
            try {
                pollItemInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pollItem.label', default: 'PollItem'), params.id])}"
            redirect(action: "list")
        }
    }
}
