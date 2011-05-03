import org.sw7d.Poll
import org.sw7d.PollItem

class BootStrap {

	def init = { servletContext ->
		def poll = new Poll(description: "what is your favorite color?")
		poll.addToPollItems(new PollItem(description:"blue"))
		poll.addToPollItems(new PollItem(description:"red"))
		poll.save(failOnError: true)
	}
	def destroy = {
	}
}
