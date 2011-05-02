import org.sw7d.Poll
import org.sw7d.PollItem

class BootStrap {

	def init = { servletContext ->
		def poll = new Poll()
		poll.description = "what is your favorite color"
		def item1 = new PollItem()
		item1.description = "blue"
		def item2 = new PollItem()
		item2.description = "red"
		poll.addToPollItems(item1)
		poll.addToPollItems(item2)
		poll.save()
	}
	def destroy = {
	}
}
