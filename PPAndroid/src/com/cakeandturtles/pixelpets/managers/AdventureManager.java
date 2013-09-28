package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.adventures.Quest;

public class AdventureManager implements Serializable{
	private static final long serialVersionUID = -6467228156574058045L;

	public boolean InBattle;
	public int NewEggIndex;
	public int CurrentAreaIndex;
	public int ConsecutiveAdventureCounter;
	public AreaManager LostWoods;
	public AreaManager PoisonLake;
	public AreaManager CaveMountains;
	public AreaManager WindyFields;
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
		InBattle = false;
		NewEggIndex = -1;
		CurrentAreaIndex = -1;
		ConsecutiveAdventureCounter = 0;
		LostWoods = new AreaManager("Lost Woods");
		PoisonLake = new AreaManager("Poison Lake");
		CaveMountains = new AreaManager("Cave Mountains");
		WindyFields = new AreaManager("Windy Fields");
		_quests = new ArrayList<Quest>();
	}
}
