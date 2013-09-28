package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.adventures.Quest;

public class AdventureManager implements Serializable{
	private static final long serialVersionUID = -6467228156574058045L;

	public int NewEggIndex;
	public int CurrentAreaIndex;
	private List<Quest> _quests;
	public List<Quest> GetQuests(){ return _quests; }
	public boolean AddQuest(Quest quest){
		for (int i = 0; i < _quests.size(); i++){
			if (_quests.get(i).Name.equals(quest.Name))
				return false;
		}
		_quests.add(quest);
		return true;
	}
	public Quest GetQuest(String name){
		for (int i = 0; i < _quests.size(); i++){
			if (_quests.get(i).Name.equals(name)){
				return _quests.get(i);
			}
		}
		return null;
	}
	public void RemoveQuest(Quest quest){
		for (int i = 0; i < _quests.size(); i++){
			if (_quests.get(i).Name.equals(quest.Name)){
				_quests.remove(i);
				break;
			}
		}
	}
	
	public AdventureManager(){
		NewEggIndex = -1;
		CurrentAreaIndex = -1;
		_quests = new ArrayList<Quest>();
	}
}
