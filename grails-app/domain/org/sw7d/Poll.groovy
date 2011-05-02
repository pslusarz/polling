package org.sw7d

class Poll {
	String description
    static hasMany = [pollItems:PollItem]
    static constraints = {
    }
}
