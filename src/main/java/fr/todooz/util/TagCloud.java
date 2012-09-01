package fr.todooz.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagCloud {
	private List<String> tags = new ArrayList<String>();

    public void add(String... tags) {
    	if (tags == null) {
    		return;
    	}
    	
    	for (String tag : tags) {
    		add(tag);
    	}
    }
    
    public void add(String tag) {
    	if (tag == null || tag.length() == 0) {
    		return;
    	}
    	
    	if (!contains(tag)) {
    		tags.add(tag);
    	}
    }

    public int size() {
        return tags.size();
    }

	public boolean contains(String tag) {
		return tags.contains(tag);
	}

	public void top(int i) {
		if (i < 0) {
			i = 0;
		}
		
		int upperBound = Math.min(i, tags.size());
		
		tags = tags.subList(0, upperBound);
	}

	public void shuffle() {
		Collections.shuffle(tags);
	}

}
