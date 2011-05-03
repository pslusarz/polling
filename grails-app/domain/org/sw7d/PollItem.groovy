package org.sw7d

class PollItem {
    String description
	int votes = 0;
	static belongsTo = [poll:Poll]
    static constraints = {
    }
}
